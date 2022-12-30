package com.ctsi.android.lib.im.interfaces

import com.ctsi.android.lib.im.bean.ChatBean

/**
 * Class : IMMessageListener
 * Create by GaoHW at 2022-12-30 9:11.
 * Description:
 */
interface MessageChatListener {

    fun onDisconnect() {

    }

    fun onReceiveMessage(chat: ChatBean, oldPosition: Int)
}