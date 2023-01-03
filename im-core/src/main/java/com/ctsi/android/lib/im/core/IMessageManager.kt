package com.ctsi.android.lib.im.core

import com.ctsi.android.lib.im.bean.ChatBean
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.interfaces.MessageListener
import com.ctsi.android.lib.im.interfaces.MessageChatListener

/**
 * Class : IMessageManager
 * Create by GaoHW at 2022-12-30 8:47.
 * Description:
 */
interface IMessageManager {

    fun setMessageListener(listener: MessageListener?)

    fun setMessageChatListener(listener: MessageChatListener?)

    fun sendTextMessage(to: String, content: String)

    fun getMessageChatList(): MutableList<ChatBean>?

    fun getMessageList(id: String, page: Int): MutableList<MessageBean>?

    fun requestChatList(page: Int)

    fun readMessageInChat(chatId: String)
}