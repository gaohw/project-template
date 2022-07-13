package com.ctsi.vip.module.login

import com.blankj.utilcode.util.SPUtils
import com.ctsi.vip.lib.common.http.HttpConstants
import com.ctsi.vip.lib.common.utils.JsonUtil
import com.ctsi.vip.module.login.bean.UserBean

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
object UserInfoUtil {

    private const val KEY_USER = "key_user"
    private const val KEY_IS_LOGIN = "key_is_login"

    private var isLogin: Boolean? = null

    fun isLogin(): Boolean {
        if (isLogin == null) {
            isLogin = SPUtils.getInstance().getBoolean(KEY_IS_LOGIN, false)
        }
        return isLogin!!
    }

    fun setUserInfo(user: UserBean?) {
        if (user != null) {
            isLogin = true
            SPUtils.getInstance().put(KEY_IS_LOGIN, true)
            SPUtils.getInstance().put(KEY_USER, JsonUtil.toJson(user))
            SPUtils.getInstance().put(HttpConstants.KEY_ACCESS_TOKEN, user.token)
        } else {
            SPUtils.getInstance().put(KEY_USER, "")
            SPUtils.getInstance().put(KEY_IS_LOGIN, false)
            SPUtils.getInstance().put(HttpConstants.KEY_ACCESS_TOKEN, "")
            isLogin = false
        }
    }
}