package com.ctsi.vip.lib.common.http

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
object HttpConstants {

    object Status {
        const val Success = "200"
        const val Success0 = "0"

        const val JsonParseError = "-1"
        const val TokenInvalidError = "401"
    }

    const val KEY_ACCESS_TOKEN = "key_access_token"
    const val KEY_LOGIN_COOKIE = "key_login_cookie"
}