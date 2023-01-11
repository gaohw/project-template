package com.ctsi.android.lib.im

import android.content.Context
import com.blankj.utilcode.util.PathUtils
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
    val DEFAULT_PATH = PathUtils.getInternalAppFilesPath() + "/im"

    fun enableDebug(debug: Boolean): CtsiIM {
        isDebug = debug
        return this
    }

    fun init(context: Context) {
        //do nothing
    }

    fun userManager(): IUserManager = UserManager

    fun messageManager(): IMessageManager = MessageManager
}