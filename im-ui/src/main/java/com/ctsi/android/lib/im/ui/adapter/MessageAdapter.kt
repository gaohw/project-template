package com.ctsi.android.lib.im.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.enums.Def
import com.ctsi.android.lib.im.ui.R
import com.ctsi.android.lib.im.ui.adapter.viewholder.MsgTextViewHolder
import com.ctsi.android.lib.im.ui.adapter.viewholder.MsgViewHolder

/**
 * Class : MessageAdapter
 * Create by GaoHW at 2023-1-3 15:01.
 * Description:
 */
class MessageAdapter : RecyclerView.Adapter<MsgViewHolder>() {

    private val messageData: MutableList<MessageBean> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> MsgTextViewHolder(inflater.inflate(R.layout.im_item_message_text_right, parent, false))
            2 -> MsgTextViewHolder(inflater.inflate(R.layout.im_item_message_text_left, parent, false))
            else -> throw IllegalArgumentException("viewtype $viewType is unknown!!!")
        }
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val message = getItemOrNull(position)
        if (message != null) {
            val user = CtsiIM.userManager().getUserById(message.msgFrom)
            Glide.with(holder.root).load(user?.userAvatar).placeholder(R.drawable.im_ic_avatar_default)
                .into(holder.ivAvatar)
            holder.tvName.text = user?.userName
            holder.updateMessage(position, message)
        } else {
            //do something
        }
    }

    fun setNewData(data: List<MessageBean>?) {
        messageData.clear()
        if (!data.isNullOrEmpty()) {
            messageData.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addData(message: MessageBean?) {
        if (message != null) {
            val index = itemCount
            messageData.add(message)
            notifyItemRangeChanged(index, 1)
        }
    }

    fun addData(position: Int, message: MessageBean?) {
        if (message != null) {
            messageData.add(position, message)
            notifyItemInserted(position)
        }
    }

    fun addAllData(data: List<MessageBean>?) {
        if (!data.isNullOrEmpty()) {
            val index = itemCount
            messageData.addAll(data)
            notifyItemRangeChanged(index, data.size)
        }
    }

    fun getItemOrNull(position: Int) = messageData.getOrNull(position)

    override fun getItemCount(): Int = messageData.size

    override fun getItemViewType(position: Int): Int {
        val message = messageData.getOrNull(position)
        return when (message?.msgType) {
            Def.TYPE_IMAGE -> if (message.isSelf()) 3 else 4
            Def.TYPE_TEXT -> if (message.isSelf()) 1 else 2
            else -> 0
        }
    }
}