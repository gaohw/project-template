package com.ctsi.vip.lib.framework

import android.app.Application
import com.ctsi.vip.lib.framework.base.delegate.AppDelegate
import com.ctsi.vip.lib.framework.base.integration.ErrorHandler
import com.ctsi.vip.lib.framework.base.integration.GlobalConfigModule
import java.lang.ref.WeakReference

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
object AppContext {

    private var appDelegate: AppDelegate? = null

    fun init(application: Application): AppContext {
        //        if (delegateReference != null) {
        //            LogUtils.d("AppContext has already bean inited!!!")
        //            return this
        //        }
        appDelegate = AppDelegate(application)
        return this
    }

    fun getAppDelegate(): AppDelegate? = appDelegate

    fun getApplication(): Application? = getAppDelegate()?.application

    fun getGlobalConfigModule(): GlobalConfigModule? = getAppDelegate()?.getGlobalConfigModule()

    fun getGlobalErrorHandler(): ErrorHandler? = getGlobalConfigModule()?.globalErrorHandler
}