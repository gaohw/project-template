package com.ctsi.android.lib.im.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.bean.UserBean
import com.ctsi.android.lib.im.interfaces.MessageListener
import com.ctsi.android.lib.im.ui.adapter.MessageAdapter
import com.ctsi.android.lib.im.ui.databinding.ImActivityChatBinding

/**
 * Class : ChatRoomActivity
 * Create by GaoHW at 2023-1-3 11:39.
 * Description:
 */
open class ChatRoomActivity : AppCompatActivity(), MessageListener {

    private lateinit var mBinding: ImActivityChatBinding
    private lateinit var userBean: UserBean

    private val messageAdapter: MessageAdapter = MessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, false)
        userBean = CtsiIM.userManager().getUserById(intent?.getStringExtra("user_id")) ?: return
        mBinding = ImActivityChatBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.layoutTitle.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        mBinding.btnBack.setOnClickListener { onBackPressed() }
        mBinding.tvChatUsername.text = userBean.userName

        mBinding.layoutInput.setInputSendListener {
            CtsiIM.messageManager().sendTextMessage("${userBean.userId}", it)
        }
        mBinding.rvMessage.adapter = messageAdapter
        mBinding.rvMessage.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        CtsiIM.messageManager().setMessageListener(this)
        messageAdapter.setNewData(CtsiIM.messageManager().getMessageList("${userBean.userId}", 0))
    }

    override fun getChatId(): String? = userBean.userId

    override fun onReceiveMessage(message: MessageBean) {
        messageAdapter.addData(0, message)
        mBinding.rvMessage.scrollToPosition(0)
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