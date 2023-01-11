package com.ctsi.android.lib.im.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class : MessageEntity
 * Create by GaoHW at 2023-1-10 9:10.
 * Description:
 */
@Entity(tableName = "message")
internal class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    var cid: Long = 0

    var id: String = ""
    var content: String? = null
    var from: String? = null
    var to: String? = null
    var type: String? = null
    var time: String? = null
    var chatId: String? = null

    var read: Int = 0     //已读状态 0未读 1已读
    var send: Int = 0     //发送状态 0发送中 1发送成功 2发送失败 3已撤回
}