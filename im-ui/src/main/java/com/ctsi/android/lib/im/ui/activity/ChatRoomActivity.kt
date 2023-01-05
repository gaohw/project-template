package com.ctsi.android.lib.im.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.*
import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.bean.UserBean
import com.ctsi.android.lib.im.enums.Def.TYPE_TEXT
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

    private val REQUEST_IMAGE = 0X01

    private lateinit var mBinding: ImActivityChatBinding
    private lateinit var userBean: UserBean
    private var mPageNo: Int = 0

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
                    0 -> openImageSelect()
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
//        mBinding.layoutRefresh.setOnRefreshListener {
//            CtsiIM.messageManager().requestMessageInChat("${userBean.userId}", mPageNo + 1)
//        }
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
//        if (mBinding.layoutRefresh.isRefreshing) {
//            mBinding.layoutRefresh.finishRefresh()
//        }
        if (data.isNullOrEmpty()) {
            return
        }
        mPageNo = page
        if (page == 0) {
            messageAdapter.setNewData(data)
            mBinding.rvMessage.run {
                postDelayed({ scrollToPosition(0) }, 200)
            }
        } else {
            messageAdapter.addAllData(data)
        }
    }

    private fun openImageSelect() {
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

    override fun onReceiveMessage(message: MessageBean) {
        messageAdapter.addData(0, message)
        mBinding.rvMessage.scrollToPosition(0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            val path = UriUtils.uri2File(data?.data)?.absolutePath
            CtsiIM.messageManager().sendImageMessage("${userBean.userId}", "$path")
        } else super.onActivityResult(requestCode, resultCode, data)
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