package com.ctsi.vip.lib.framework.base

import android.app.Application
import android.content.Context
import com.ctsi.vip.lib.framework.AppContext
import com.ctsi.vip.lib.framework.base.delegate.AppDelegate

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        AppContext.init(this)
        AppContext.getAppDelegate()?.setDebugMode(isDebugMode())?.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        AppContext.getAppDelegate()?.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        AppContext.getAppDelegate()?.onTerminate(this)
    }

    protected open fun isDebugMode(): Boolean = false
}