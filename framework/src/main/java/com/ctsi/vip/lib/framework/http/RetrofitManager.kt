package com.ctsi.vip.lib.framework.http

import android.content.Context
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
object RetrofitManager {

    private const val TIME_OUT = 10L

    private var okClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    internal fun init(
        context: Context, apiUrl: HttpUrl?, interceptors: List<Interceptor>?,
        retrofitConfiguration: RetrofitConfiguration?, okhttpConfiguration: OkhttpConfiguration?
    ) {
        if (okClient == null) {
            val builder = OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            interceptors?.forEach { builder.addInterceptor(it) }
            okhttpConfiguration?.configOkhttp(context, builder)
            builder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            okClient = builder.build()
        }

        if (retrofit == null) {
            val builder = Retrofit.Builder().baseUrl(apiUrl!!).client(okClient!!)
            retrofitConfiguration?.configRetrofit(context, builder)
            retrofit = builder.build()
        }
    }

    internal fun <T> create(service: Class<T>): T = retrofit!!.create(service)

    /**
     * [Retrofit] 自定义配置接口
     */
    interface RetrofitConfiguration {
        fun configRetrofit(context: Context, builder: Retrofit.Builder)
    }

    /**
     * [OkHttpClient] 自定义配置接口
     */
    interface OkhttpConfiguration {
        fun configOkhttp(context: Context, builder: OkHttpClient.Builder)
    }
}