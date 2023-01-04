package com.ctsi.android.lib.im.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.ui.R

/**
 * Class : MessageViewHolder
 * Create by GaoHW at 2023-1-3 15:04.
 * Description:
 */
abstract class MsgViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val ivAvatar: ImageView = root.findViewById(R.id.iv_user_avatar)
    val tvName: TextView = root.findViewById(R.id.tv_user_name)
    val tvTime: TextView = root.findViewById(R.id.tv_message_time)

    abstract fun updateMessage(position: Int, message: MessageBean)
}