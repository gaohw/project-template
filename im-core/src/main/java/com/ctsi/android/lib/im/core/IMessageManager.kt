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

    fun sendImageMessage(to: String, path: String)

    fun sendFileMessage(to: String, path: String)

    fun getMessage(id: String): MessageBean?

    fun getMessageChatList(): MutableList<ChatBean>?

    fun getMessageList(id: String, start: Int): MutableList<MessageBean>?

    fun getUnreadCount(): Int

    fun requestChatList()

    fun requestMessageInChat(chatId: String, offset: Int = -1)
}