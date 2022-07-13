package com.ctsi.vip.lib.common.http.interceptor

import com.blankj.utilcode.util.SPUtils
import com.ctsi.vip.lib.common.http.HttpConstants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by GaoHW at 2022-6-30.
 *
 * Desc:
 */
class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newBuilder = request.newBuilder()

        val token = SPUtils.getInstance().getString(HttpConstants.KEY_ACCESS_TOKEN)
        newBuilder.addHeader("Authorization", "Bearer $token")

        val cacheCookie = SPUtils.getInstance().getString(HttpConstants.KEY_LOGIN_COOKIE)
        if (!cacheCookie.isNullOrEmpty()) {
            newBuilder.addHeader("Cookie", cacheCookie)
        }

        val response = chain.proceed(newBuilder.build())
        response.header("Set-Cookie")?.let {
            SPUtils.getInstance().put(HttpConstants.KEY_LOGIN_COOKIE, it)
        }
        return response
    }
}