package com.ctsi.android.lib.im.interfaces

import com.ctsi.android.lib.im.bean.MessageBean

/**
 * Class : IMMessageChatListener
 * Create by GaoHW at 2022-12-30 9:12.
 * Description:
 */
interface MessageListener {

    fun onDisconnect() {

    }

    fun onReceiveMessage(message: MessageBean)
}