package com.ctsi.android.lib.im.core

import com.ctsi.android.lib.im.bean.UserBean

/**
 * Class : IUserManager
 * Create by GaoHW at 2022-12-30 8:47.
 * Description:
 */
interface IUserManager {

    fun setCurrentUser(id: String?)

    fun getCurrentUser(): UserBean?

    fun getUserById(id: String?): UserBean?
}