package com.project.template.http.interceptors

import com.blankj.utilcode.util.SPUtils
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

        val token = SPUtils.getInstance().getString("key_access_token")
        newBuilder.addHeader("Authorization", "Bearer $token")

        val cacheCookie = SPUtils.getInstance().getString("key_login_cookie")
        if (!cacheCookie.isNullOrEmpty()) {
            newBuilder.addHeader("Cookie", cacheCookie)
        }

        val response = chain.proceed(newBuilder.build())
        response.header("Set-Cookie")?.let {
            SPUtils.getInstance().put("key_login_cookie", it)
        }
        return response
    }
}