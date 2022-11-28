package com.ctsi.vip.lib.framework

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.LogUtils
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
        return this
    }

    fun getContext(): Context? = mAppContext?.get()

    fun getApplication(): Application? = getContext() as Application?
}