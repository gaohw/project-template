package com.ctsi.vip.lib.framework.base.lifecycles

import android.app.Application
import android.content.Context


/**
 * Class : AppLifecycle
 * Create by GaoHW at 2022-10-13 9:54.
 * Description:
 */
interface IAppLifecycle {
    fun attachBaseContext(context: Context?)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}