package com.ctsi.vip.lib.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.ctsi.vip.lib.common.http.RetrofitManager
import com.ctsi.vip.lib.common.http.state.NetworkStateHelper
import java.lang.ref.WeakReference

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
object AppContext {

    private var mAppContext: WeakReference<Context>? = null

    fun init(application: Application): AppContext {
        if (mAppContext != null) {
            LogUtils.d("AppContext has already bean inited!!!")
            return this
        }
        mAppContext = WeakReference(application)

        Utils.init(application)

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
        NetworkStateHelper.registerReceiver(application)
        return this
    }

    fun setBaseUrl(url: String) {
        RetrofitManager.setBaseUrl(url)
    }

    fun getContext(): Context? = mAppContext?.get()

    fun getApplication(): Application? = getContext() as Application?
}