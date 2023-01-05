package com.ctsi.android.lib.im.ui.adapter

import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.android.lib.im.bean.ChatBean
import com.ctsi.android.lib.im.enums.Def
import com.ctsi.android.lib.im.ui.R
import com.ctsi.android.lib.im.ui.activity.ChatRoomActivity

/**
 * Class : ChatAdapter
 * Create by GaoHW at 2023-1-3 15:00.
 * Description:
 */
class ChatAdapter(data: MutableList<ChatBean>?) :
    BaseQuickAdapter<ChatBean, BaseViewHolder>(R.layout.im_item_chat, data) {

    init {
        setOnItemClickListener { adapter, _, position ->
            val chat = adapter.getItemOrNull(position) as ChatBean?
            val userId = chat?.user?.userId
            if (!userId.isNullOrEmpty()) {
                ChatRoomActivity.chatPrivate(context, userId)
                //更新未读数
                chat.unreadCount = 0
                notifyItemChanged(position, "unread")
            }
        }
    }

    override fun convert(holder: BaseViewHolder, item: ChatBean) {
        Glide.with(context).load(item.user?.userAvatar)
            .placeholder(R.drawable.im_ic_avatar_default)
            .into(holder.getView(R.id.iv_user_avatar))
        holder.setText(R.id.tv_user_name, item.user?.userName)
        holder.setText(R.id.tv_message_time, item.message?.messageTime())
        holder.setText(
            R.id.tv_message_content, when (item.message?.msgType) {
                Def.TYPE_TEXT -> item.message?.msgContent
                Def.TYPE_IMAGE -> "[图片]"
                else -> null
            }
        )
        holder.setText(R.id.tv_message_unread, "${item.unreadCount}")

        holder.setVisible(R.id.v_divider, holder.adapterPosition > 0)
        holder.setVisible(R.id.tv_message_unread, item.unreadCount > 0)
    }

    override fun convert(holder: BaseViewHolder, item: ChatBean, payloads: List<Any>) {
        when (payloads.firstOrNull()) {
            "update" -> {
                holder.setText(R.id.tv_message_time, item.message?.messageTime())
                holder.setText(R.id.tv_message_content, item.message?.msgContent)
                holder.getView<TextView>(R.id.tv_message_unread).text = "${item.unreadCount}"
                holder.setVisible(R.id.tv_message_unread, item.unreadCount > 0)
            }
            "unread" -> {
                holder.getView<TextView>(R.id.tv_message_unread).text = "${item.unreadCount}"
                holder.setVisible(R.id.tv_message_unread, item.unreadCount > 0)
            }
        }
    }
}