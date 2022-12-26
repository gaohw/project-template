package com.ctsi.vip.lib.framework.base.integration

import com.ctsi.vip.lib.framework.http.RetrofitManager
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor

/**
 * Class : GlobalConfigModule
 * Create by GaoHW at 2022-10-13 10:06.
 * Description:
 */
class GlobalConfigModule private constructor(builder: Builder) {
    private var mApiUrl: HttpUrl? = null
    private var mTimeOutMills: Long? = builder.timeOutMills
    private var mInterceptors: List<Interceptor>? = null
    private var mRetrofitConfiguration: RetrofitManager.RetrofitConfiguration? = null
    private var mOkhttpConfiguration: RetrofitManager.OkhttpConfiguration? = null
    private var globalErrorHandler: ErrorHandler? = null

    init {
        this.mApiUrl = builder.apiUrl
        this.mInterceptors = builder.interceptors
        this.mRetrofitConfiguration = builder.retrofitConfiguration
        this.mOkhttpConfiguration = builder.okhttpConfiguration
        this.globalErrorHandler = builder.errorHandler
    }

    fun getApiUrl(): HttpUrl? = mApiUrl

    fun getTimeOutMills(): Long = mTimeOutMills ?: 10000

    fun getHttpInterceptors() = mInterceptors

    fun getOkhttpConfiguration() = mOkhttpConfiguration

    fun getRetrofitConfiguration() = mRetrofitConfiguration

    fun getGlobalErrorHandler() = globalErrorHandler

    class Builder internal constructor() {
        internal var apiUrl: HttpUrl? = null
        internal var timeOutMills: Long = 10000
        internal var interceptors: MutableList<Interceptor> = mutableListOf()
        internal var retrofitConfiguration: RetrofitManager.RetrofitConfiguration? = null
        internal var okhttpConfiguration: RetrofitManager.OkhttpConfiguration? = null
        internal var errorHandler: ErrorHandler? = null

        fun baseUrl(url: String): Builder {
            this.apiUrl = url.toHttpUrlOrNull()
            return this
        }

        //网络超时
        fun setRequestTimeOut(mills: Long): Builder {
            this.timeOutMills = mills
            return this
        }

        //动态添加interceptor
        fun addInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }

        fun setRetrofitConfiguration(configuration: RetrofitManager.RetrofitConfiguration): Builder {
            this.retrofitConfiguration = configuration
            return this
        }

        fun setOkhttpConfiguration(configuration: RetrofitManager.OkhttpConfiguration): Builder {
            this.okhttpConfiguration = configuration
            return this
        }

        fun setGlobalErrorHandler(errorHandler: ErrorHandler?): Builder {
            this.errorHandler = errorHandler
            return this
        }

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }
}