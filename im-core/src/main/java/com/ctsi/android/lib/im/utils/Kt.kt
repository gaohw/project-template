package com.ctsi.android.lib.im.utils

import com.ctsi.android.lib.im.bean.ChatBean
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.bean.UserBean
import com.ctsi.android.lib.im.db.entity.MessageEntity
import com.ctsi.android.lib.im.db.entity.UserEntity
import com.ctsi.android.lib.im.manager.MessageManager
import com.ctsi.android.lib.im.manager.UserManager

/**
 * Class : KT
 * Create by GaoHW at 2023-1-10 10:35.
 * Description: 扩展函数集合
 */

internal fun MessageBean.toEntity(): MessageEntity = MessageEntity().apply {
    id = "$msgId"
    chatId = msgChatId
    to = msgTo
    from = msgFrom
    type = msgType
    time = msgTime
    content = msgContent

    read = readStatus
    send = sendStatus
}

internal fun MessageEntity.toBean(): MessageBean = MessageBean().apply {
    msgId = id
    msgChatId = chatId
    msgTo = to
    msgFrom = from
    msgType = type
    msgTime = time
    msgContent = content

    readStatus = read
    sendStatus = send
}

internal fun UserBean.toEntity(): UserEntity = UserEntity().apply {
    id = "$userId"
    name = userName
    avatar = userAvatar
}

internal fun UserEntity.toBean(): UserBean = UserBean().apply {
    userId = id
    userName = name
    userAvatar = avatar
}