package com.ctsi.vip.module.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ctsi.vip.lib.common.base.BaseViewModel
import com.ctsi.vip.module.login.bean.UserBean
import com.ctsi.vip.module.login.repository.LoginRepository as repository

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
class LoginViewModel : BaseViewModel() {

    val userData = MutableLiveData<UserBean?>()

    fun login(username: String, password: String) = launch {
        showDialog("登录中...")
        // 0=正常,1=新增待审批,2=修改待审批
        val status = repository.queryUserStatus(username)
        if (status == "0") {
            val user = repository.login(username, password)
                ?.apply { this.status = status }
            userData.value = user
        } else {
            userData.value = UserBean().apply { this.status = status }
        }
    }.invokeOnCompletion { dismissDialog() }
}