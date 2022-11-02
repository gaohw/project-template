package com.ctsi.vip.lib.framework.base

import android.app.Application
import android.content.Context
import com.ctsi.vip.lib.framework.base.delegate.AppDelegate

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
abstract class BaseApplication : Application() {

    private var appDelegate: AppDelegate? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (appDelegate == null) {
            appDelegate = AppDelegate(base)
                .setDebugMode(isDebugMode())
        }
        appDelegate?.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        appDelegate?.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        appDelegate?.onTerminate(this)
    }

    abstract fun isDebugMode(): Boolean
}