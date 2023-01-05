package com.ctsi.android.lib.im.ui.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.ui.R

/**
 * Class : MsgTextViewHolder
 * Create by GaoHW at 2023-1-3 15:07.
 * Description:
 */
class MsgTextViewHolder(view: View) : MsgViewHolder(view) {

    private val tvMsgContent: TextView = view.findViewById(R.id.tv_message_content)

    override fun updateMessage(position: Int, message: MessageBean) {
        tvMsgContent.text = message.msgContent
    }
}