package com.ctsi.vip.lib.common.http

import com.ctsi.vip.lib.common.http.interceptor.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
object RetrofitManager {

    private var mBaseUrl: String? = null

    private val okClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().client(okClient).baseUrl("$mBaseUrl")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    internal fun setBaseUrl(url: String) {
        this.mBaseUrl = url
    }

    internal fun <T> create(service: Class<T>): T = retrofit.create(service)
}