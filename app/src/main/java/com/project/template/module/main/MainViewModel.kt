package com.project.template.module.main

import com.blankj.utilcode.util.LogUtils
import com.ctsi.vip.lib.framework.base.BaseViewModel

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
class MainViewModel : BaseViewModel() {

    fun getUnreadMessage() {
        launch(
            onError = {
                LogUtils.eTag("OkHttp", "========> ${it.message}")
            }) {
            val num = MainRepository.getAllUnreadMessage()
            LogUtils.eTag("OkHttp", "========> $num")
        }
    }

    override fun goLogin() {

    }
}