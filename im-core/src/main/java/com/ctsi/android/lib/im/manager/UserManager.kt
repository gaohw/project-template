package com.ctsi.android.lib.im.manager

import com.ctsi.android.lib.im.bean.UserBean
import com.ctsi.android.lib.im.core.IUserManager

/**
 * Class : IMUserManager
 * Create by GaoHW at 2022-12-30 8:25.
 * Description: 用户管理类
 */
internal object UserManager : IUserManager {

    private var curUser: UserBean? = null
    private val userQueue = mutableSetOf<UserBean>()

    override fun setCurrentUser(id: String?) {
        if (id.isNullOrEmpty()) {
            MessageManager.close(curUser?.userId)
            curUser = null
            return
        }
        if (curUser?.userId == id) {
            return
        }
        //todo 获取用户信息
        curUser = UserBean().apply {
            userId = id
            userName = id
        }
        MessageManager.connect()        //连接服务期
    }

    override fun getCurrentUser(): UserBean? = curUser

    override fun getUserById(id: String?): UserBean? = userQueue.find { it.userId == id }

    fun addUser(userBean: UserBean) {
        userQueue.add(userBean)
    }
}