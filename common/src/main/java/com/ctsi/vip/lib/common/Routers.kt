package com.ctsi.vip.lib.common

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
object Routers {

    private const val APP = "/app"
    private const val LOGIN = "/login"

    //group login
    const val PATH_LOGIN = "$LOGIN/activity/login"

    //group app
    const val PATH_MAIN = "$APP/activity/main"

    /**
     * 直接进入登录页面
     *
     * @param bundle 携带参数
     * @param clear 是否清空task
     */
    fun login(bundle: Bundle? = null, clear: Boolean = false) {
        if (clear) {
            navigation(PATH_LOGIN, bundle, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_NEW_TASK)
        } else {
            navigation(PATH_LOGIN, bundle)
        }
    }

    /**
     * 跳转指定路径的页面
     *
     * @param path 路径
     * @param bundle 参数(可选)
     * @param flags Intent的flag参数(可选)
     */
    fun navigation(path: String, bundle: Bundle? = null, vararg flags: Int) {
        val intent = ARouter.getInstance().build(path)
        bundle?.let { intent.with(it) }
        flags.forEach { flag -> intent.addFlags(flag) }
        intent.navigation()
    }
}