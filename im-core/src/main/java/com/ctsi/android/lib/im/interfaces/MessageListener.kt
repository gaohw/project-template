package com.ctsi.android.lib.im.interfaces

import com.ctsi.android.lib.im.bean.MessageBean

/**
 * Class : IMMessageChatListener
 * Create by GaoHW at 2022-12-30 9:12.
 * Description:
 */
interface MessageListener {

    fun getChatId(): String?

    fun onDisconnect() {

    }

    fun onMessageList(start: Int, data: MutableList<MessageBean>?) {

    }

    fun onReceiveMessage(message: MessageBean)
}