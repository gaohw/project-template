package com.ctsi.vip.lib.common

import android.app.Application

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppContext.init(this)
    }
}