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
    internal var mApiUrl: HttpUrl? = null
    internal var mInterceptors: List<Interceptor>? = null
    internal var mRetrofitConfiguration: RetrofitManager.RetrofitConfiguration? = null
    internal var mOkhttpConfiguration: RetrofitManager.OkhttpConfiguration? = null

    init {
        this.mApiUrl = builder.apiUrl
        this.mInterceptors = builder.interceptors
        this.mRetrofitConfiguration = builder.retrofitConfiguration
        this.mOkhttpConfiguration = builder.okhttpConfiguration
    }

    class Builder internal constructor() {
        internal var apiUrl: HttpUrl? = null
        internal var interceptors: MutableList<Interceptor> = mutableListOf()
        internal var retrofitConfiguration: RetrofitManager.RetrofitConfiguration? = null
        internal var okhttpConfiguration: RetrofitManager.OkhttpConfiguration? = null

        fun baseUrl(url: String): Builder {
            this.apiUrl = url.toHttpUrlOrNull()
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

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }
}