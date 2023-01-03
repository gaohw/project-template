package com.ctsi.android.lib.im.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.android.lib.im.bean.ChatBean
import com.ctsi.android.lib.im.interfaces.MessageChatListener
import com.ctsi.android.lib.im.ui.R
import com.ctsi.android.lib.im.ui.adapter.ChatAdapter
import com.ctsi.android.lib.im.ui.databinding.ImFragmentChatBinding

/**
 * Class : IMChatFragment
 * Create by GaoHW at 2023-1-3 9:20.
 * Description:
 */
class ChatFragment : Fragment(), MessageChatListener {

    private lateinit var mBinding: ImFragmentChatBinding
    private var chatAdapter: ChatAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = ImFragmentChatBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.layoutChatTitle.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        mBinding.btnSend.setOnClickListener {
            val user = mBinding.edtUser.text
            val content = mBinding.edtContent.text
            CtsiIM.messageManager()
                .sendTextMessage("$user", "$content")
        }

        chatAdapter = ChatAdapter(CtsiIM.messageManager().getMessageChatList())
        chatAdapter?.setEmptyView(layoutInflater.inflate(R.layout.im_layout_chat_empty, null)
            .apply {
                findViewById<ImageView>(R.id.iv_empty_icon).setImageResource(R.drawable.im_ic_chat_empty)
                findViewById<TextView>(R.id.tv_empty_text).text = "暂无聊天记录~"
            })
        mBinding.rvChatHistory.layoutManager = LinearLayoutManager(requireContext());
        mBinding.rvChatHistory.adapter = chatAdapter
        mBinding.layoutRefresh.setOnRefreshListener {
            CtsiIM.messageManager().requestChatList(0)
        }
        CtsiIM.messageManager().setMessageChatListener(this)
    }

    override fun onChatList(page: Int, data: List<ChatBean>?) {
        super.onChatList(page, data)
        if (!data.isNullOrEmpty()) {
            if (page == 0) {
                chatAdapter?.setNewInstance(data as MutableList<ChatBean>?)
            } else {
                chatAdapter?.addData(data)
            }
        }
        if (mBinding.layoutRefresh.isRefreshing) {
            mBinding.layoutRefresh.finishRefresh()
        }
        if (mBinding.layoutRefresh.isLoading) {
            mBinding.layoutRefresh.finishLoadMore()
        }
    }

    override fun onReceiveMessage(chat: ChatBean, oldPosition: Int) {
//        when (oldPosition) {
//            -1 -> {
//                chatAdapter?.notifyItemInserted(0)
//            }
//            0 -> {
//                chatAdapter?.notifyItemChanged(0, "update")
//            }
//            else -> {
//                chatAdapter?.notifyItemRemoved(oldPosition)
//                chatAdapter?.notifyItemInserted(0)
//            }
//        }
        //todo 更新
        chatAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        CtsiIM.messageManager().setMessageChatListener(null)
        super.onDestroyView()
    }
}