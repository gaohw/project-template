package com.project.template.module.main

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
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
                ToastUtils.showShort(it?.message)
            }) {
            MainRepository.getAllUnreadMessage()
        }
    }
}