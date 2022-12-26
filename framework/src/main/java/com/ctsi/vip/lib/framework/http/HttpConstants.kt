package com.ctsi.vip.lib.framework.http

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
object HttpConstants {

    object Status {
        const val Success = "200"
        const val Success0 = "0"
    }

    object Error {
        const val NetError = "-2"
        const val UnknownError = "-100"
        const val JsonParseError = "-1"
        const val TokenInvalidError = "401"
    }
}