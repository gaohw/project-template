package com.project.template

import com.ctsi.vip.lib.common.AppContext
import com.ctsi.vip.lib.common.BaseApplication

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        AppContext.setBaseUrl("http://192.168.153.2:9067/")
    }
}