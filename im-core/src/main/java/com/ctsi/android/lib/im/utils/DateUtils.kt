package com.ctsi.android.lib.im.utils

import com.blankj.utilcode.util.TimeUtils
import java.util.*

/**
 * Class : DateUtils
 * Create by GaoHW at 2023-1-4 9:28.
 * Description:
 */
object DateUtils {

    fun formatDate(date: Date?): String? {
        if (date == null) return null
        try {
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
}