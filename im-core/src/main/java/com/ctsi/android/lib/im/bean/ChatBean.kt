package com.ctsi.android.lib.im.bean

/**
 * Class : MessageChatBean
 * Create by GaoHW at 2022-12-30 9:28.
 * Description:
 */
class ChatBean {
    var user: UserBean? = null
    var message: MessageBean? = null
    var unreadCount: Int = 0     //未读消息数
}