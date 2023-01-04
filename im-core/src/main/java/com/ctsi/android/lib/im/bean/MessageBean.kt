package com.ctsi.android.lib.im.bean

import com.blankj.utilcode.util.TimeUtils
import com.ctsi.android.lib.im.enums.Def.MessageType
import com.ctsi.android.lib.im.manager.UserManager
import com.ctsi.android.lib.im.utils.DateUtils
import java.util.*

/**
 * Class : MessageBean
 * Create by GaoHW at 2022-12-30 8:29.
 * Description:
 */
class MessageBean {

    constructor()

    constructor(@MessageType type: String) {
        msgType = type
    }

    var msgType: String? = null
    var msgFrom: String? = null
    var msgTo: String? = null
    var msgTime: String? = null
    var msgContent: String? = null

    var readStatus: Int = 0      //已读状态 0未读 1已读
    var sendStatus: Int = 0      //发送状态 0发送中 1成功 2失败

    fun messageTime(): String? {
        return try {
            val date = TimeUtils.string2Date(msgTime, "yyyy-MM-dd HH:mm:ss")
            DateUtils.formatDate(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun isSelf() = msgFrom == UserManager.currentUser()?.userId

    fun toSendMessage(): String {
        return "{\"system\":\"ctsiapp\",\"from\":\"$msgFrom\",\"to\":\"$msgTo\",\"content\":\"$msgContent\"}"
    }
}

