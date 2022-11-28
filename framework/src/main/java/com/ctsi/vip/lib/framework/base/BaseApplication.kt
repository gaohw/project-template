package com.ctsi.vip.lib.framework.base

import android.app.Application
import android.content.Context
import com.ctsi.vip.lib.framework.base.delegate.AppDelegate

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
open class BaseApplication : Application() {

    private val mAppDelegate: AppDelegate by lazy { AppDelegate(this) }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        mAppDelegate.setDebugMode(isDebugMode()).attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate.onTerminate(this)
    }

    protected open fun isDebugMode(): Boolean = false
}