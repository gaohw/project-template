package com.ctsi.android.lib.im.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.*
import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.bean.UserBean
import com.ctsi.android.lib.im.interfaces.MessageListener
import com.ctsi.android.lib.im.ui.R
import com.ctsi.android.lib.im.ui.adapter.MessageAdapter
import com.ctsi.android.lib.im.ui.databinding.ImActivityChatBinding

/**
 * Class : ChatRoomActivity
 * Create by GaoHW at 2023-1-3 11:39.
 * Description:
 */
open class ChatRoomActivity : AppCompatActivity(), MessageListener {

    private val REQUEST_IMAGE = 0x01
    private val REQUESR_FILE = 0x03

    private lateinit var mBinding: ImActivityChatBinding
    private lateinit var userBean: UserBean
    private var mPageNo: Int = 0

    private var mAfterPause: Boolean = false
    private var mScrollDistance: Int = 0

    private val messageAdapter: MessageAdapter = MessageAdapter()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = CtsiIM.userManager().getUserById(intent?.getStringExtra("user_id"))
        if (user == null) {
            ToastUtils.showShort("无效的会话，请重新进入")
            finish()
            return
        }
        userBean = user
        mBinding = ImActivityChatBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        window.statusBarColor = ColorUtils.getColor(R.color.im_title)

        mBinding.tvChatUsername.text = userBean.userName
        mBinding.btnBack.setOnClickListener { finish() }
        mBinding.layoutInput
            .setTextSendListener {
                CtsiIM.messageManager().sendTextMessage("${userBean.userId}", it)
            }.setVoiceSendListener { _, path, duration ->
                ToastUtils.showShort("录音时长：$duration 秒，存储地址：$path")
            }
            .setFunctionClickListener { position, name ->
                when (position) {
                    0 -> requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                        try {
                            val intent =
                                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                            startActivityForResult(intent, REQUEST_IMAGE)
                        } catch (e: Exception) {
                            ToastUtils.showShort("图片选择器打开失败，请重试")
                            e.printStackTrace()
                        }
                    }
                    1 -> requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA) {
                        ToastUtils.showShort("拍摄")
                    }
                    2 -> requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                        try {
                            val intent = Intent(Intent.ACTION_GET_CONTENT).setType("*/*")
                                .addCategory(Intent.CATEGORY_OPENABLE)
                            startActivityForResult(intent, REQUESR_FILE)
                        } catch (e: Exception) {
                            ToastUtils.showShort("文件选择器打开失败，请重试")
                            e.printStackTrace()
                        }
                    }
                    else -> ToastUtils.showShort("功能点击：$position $name")
                }
            }
        mBinding.rvMessage.adapter = messageAdapter
        mBinding.rvMessage.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        mBinding.rvMessage.setOnTouchListener { _, _ ->
            mBinding.layoutInput.hide()
            false
        }
        mBinding.rvMessage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //解决页面切换后RecyclerView自动滑动的问题
                //设置为stackFromEnd获知reverseLayout导致，为找到原因
                if (mAfterPause) {
                    mAfterPause = false
                    val distance = computeScrollDistance()
                    if (distance != mScrollDistance) {
                        recyclerView.scrollBy(0, distance - mScrollDistance)
                    }
                }
            }
        })
        mBinding.layoutRefresh.setOnRefreshListener {
            CtsiIM.messageManager().requestMessageInChat("${userBean.userId}", mPageNo + 1)
        }
        KeyboardUtils.registerSoftInputChangedListener(this) { height ->
            if (height > 0) {
                mBinding.rvMessage.scrollToPosition(0)
            }
        }
        //监听&获取聊天数据
        CtsiIM.messageManager().setMessageListener(this)
        CtsiIM.messageManager().requestMessageInChat("${userBean.userId}", 0)
    }

    override fun getChatId(): String? = userBean.userId

    override fun onMessageList(page: Int, data: MutableList<MessageBean>?) {
        if (mBinding.layoutRefresh.isRefreshing) {
            mBinding.layoutRefresh.finishRefresh()
        }
        if (data.isNullOrEmpty()) {
            return
        }
        mPageNo = page
        if (page == 0) {
            messageAdapter.setNewData(data)
            mBinding.rvMessage.run {
                postDelayed({ scrollToPosition(0) }, 100)
            }
        } else {
            messageAdapter.addAllData(data)
        }
    }

    private fun requestPermission(vararg permissions: String?, function: () -> Unit) {
        PermissionUtils.permission(*permissions).callback(object : PermissionUtils.SimpleCallback {
            override fun onGranted() {
                function.invoke()
            }

            override fun onDenied() {
                ToastUtils.showShort("权限获取失败，请重试")
            }
        }).request()
    }

    override fun onReceiveMessage(message: MessageBean) {
        messageAdapter.addData(0, message)
        mBinding.rvMessage.scrollToPosition(0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_IMAGE || requestCode == REQUESR_FILE)
            && resultCode == RESULT_OK
        ) {
            val path = UriUtils.uri2File(data?.data)?.absolutePath
            if (ImageUtils.isImage(path)) {
                CtsiIM.messageManager().sendImageMessage("${userBean.userId}", "$path")
            } else {
                CtsiIM.messageManager().sendFileMessage("${userBean.userId}", "$path")
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPause() {
        super.onPause()
        mAfterPause = true
        mScrollDistance = computeScrollDistance()
    }

    private fun computeScrollDistance(): Int {
        val range = mBinding.rvMessage.computeVerticalScrollRange()
        val extent = mBinding.rvMessage.computeVerticalScrollExtent()
        val offset = mBinding.rvMessage.computeVerticalScrollOffset()
        return range - extent - offset
    }

    override fun onDestroy() {
        CtsiIM.messageManager().setMessageListener(null)
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun chatPrivate(context: Context, userId: String) {
            val intent = Intent(context, ChatRoomActivity::class.java)
                .putExtra("user_id", userId)
            context.startActivity(intent)
        }
    }
}