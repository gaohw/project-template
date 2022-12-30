package com.ctsi.android.lib.im.bean

import com.blankj.utilcode.util.TimeUtils
import com.ctsi.android.lib.im.enums.Def.MessageType
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
        try {
            val date = TimeUtils.string2Date(msgTime, "yyyy-MM-dd HH:mm:ss")
            return if (TimeUtils.isToday(date)) {
                TimeUtils.date2String(date, "HH:mm")
            } else {
                val curYear = Calendar.getInstance().apply { time = Date() }.get(Calendar.YEAR)
                val msgYear = Calendar.getInstance().apply { time = date }.get(Calendar.YEAR)
                if (msgYear == curYear) {
                    TimeUtils.date2String(date, "MM月dd日")
                } else {
                    TimeUtils.date2String(date, "yyyy年MM月dd日")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun toSendMessage(): String {
        return "{\"system\":\"ctsiapp\",\"from\":\"$msgFrom\",\"to\":\"$msgTo\",\"content\":\"$msgContent\"}"
    }
}

