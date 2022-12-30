package com.ctsi.android.lib.im

import android.content.Context
import com.ctsi.android.lib.im.core.IMConnectManager
import com.ctsi.android.lib.im.core.IMessageManager
import com.ctsi.android.lib.im.core.IUserManager
import com.ctsi.android.lib.im.manager.MessageManager
import com.ctsi.android.lib.im.manager.UserManager

/**
 * Class : CtsiIM
 * Create by GaoHW at 2022-12-30 8:24.
 * Description: IM管理类
 */
object CtsiIM {

    var isDebug: Boolean = false

    fun enableDebug(debug: Boolean): CtsiIM {
        isDebug = debug
        return this
    }

    fun init(context: Context) {

    }

    fun userManager(): IUserManager = UserManager

    fun messageManager(): IMessageManager = MessageManager
}