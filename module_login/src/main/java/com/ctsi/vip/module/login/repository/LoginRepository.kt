package com.ctsi.vip.module.login.repository

import com.blankj.utilcode.util.ToastUtils
import com.ctsi.vip.lib.common.base.BaseRepository
import com.ctsi.vip.lib.common.http.ApiParam
import com.ctsi.vip.lib.common.http.HttpConstants
import com.ctsi.vip.module.login.UserInfoUtil
import com.ctsi.vip.module.login.bean.UserBean

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */

object LoginRepository : BaseRepository() {

    /**
     * 根据用户名查询用户账户章台
     *
     * @param username 用户名
     * @return status
     */
    suspend fun queryUserStatus(username: String): String? {
        val param = ApiParam.create()
            .addParam("userName", username)
        val response = request<String> {
            apiService.doJsonPost("/ctsi-admin/user-status/$username", param.getBody())
        }
        return if (response.code == HttpConstants.Status.Success) {
            response.msg
        } else {
            ToastUtils.showShort(response.msg)
            null
        }
    }

    /**
     * 登录
     *
     * @param username 用户名(手机)
     * @param password 密码
     *
     * @return user 构造的用户bean
     */
    suspend fun login(username: String, password: String): UserBean? {
        val param = ApiParam.create()
            .addParam("username", username)
            .addParam("password", password)
        val response = requestJson {
            apiService.doJsonPost("/ctsi-admin/login", param.getBody())
        }
        if (response.getCode() == HttpConstants.Status.Success) {
            val user = UserBean().apply {
                this.username = username
                this.password = password
                this.token = response.optString("token")
            }
            UserInfoUtil.setUserInfo(user)
            return user
        } else {
            UserInfoUtil.setUserInfo(null)
            ToastUtils.showShort(response.getMsg())
            return null
        }
    }
}