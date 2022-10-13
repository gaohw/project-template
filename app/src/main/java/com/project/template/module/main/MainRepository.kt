package com.project.template.module.main

import com.ctsi.vip.lib.framework.base.BaseRepository
import com.ctsi.vip.lib.framework.http.ApiParam

/**
 * Class : MainRepository
 * Create by GaoHW at 2022-10-13 15:22.
 * Description:
 */
object MainRepository : BaseRepository() {

    suspend fun getAllUnreadMessage(): Int {
        val response = request<Int> {
            apiService.doPost("/ctsi-admin/kj-message/deliver/all-unread-num", ApiParam.create().getParams())
        }
        return response.data ?: 0
    }
}