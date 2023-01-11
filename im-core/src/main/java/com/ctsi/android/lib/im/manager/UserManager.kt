package com.ctsi.android.lib.im.manager

import com.blankj.utilcode.util.Utils
import com.ctsi.android.lib.im.bean.UserBean
import com.ctsi.android.lib.im.core.IUserManager
import com.ctsi.android.lib.im.db.DatabaseManager
import com.ctsi.android.lib.im.utils.toBean
import com.ctsi.android.lib.im.utils.toEntity

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
            DatabaseManager.close()
            curUser = null
            return
        }
        if (curUser?.userId == id) {
            return
        }
        curUser = UserBean().apply { userId = id; userName = id }
        //连接服务期&数据库
        MessageManager.connect()
        DatabaseManager.connect(Utils.getApp(), id)
        DatabaseManager.enqueue { manager ->
            manager.userDao()?.queryAllUser()?.forEach { userQueue.add(it.toBean()) }
        }
    }


    override fun currentUser(): UserBean? = curUser

    override fun getUserById(id: String?): UserBean? = userQueue.find { it.userId == id }

    fun addUser(userBean: UserBean) {
        userQueue.add(userBean)
        DatabaseManager.enqueue { it.userDao()?.insertUser(userBean.toEntity()) }
    }
}