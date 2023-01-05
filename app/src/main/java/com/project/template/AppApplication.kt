package com.project.template

import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.vip.lib.framework.base.BaseApplication

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        CtsiIM.enableDebug(isDebugMode()).init(this)
    }

    override fun isDebugMode(): Boolean = BuildConfig.DEBUG
}