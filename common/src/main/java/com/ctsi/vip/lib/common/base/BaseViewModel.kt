package com.ctsi.vip.lib.common.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.vip.lib.common.Routers
import com.ctsi.vip.lib.common.utils.SingleLiveEvent
import com.ctsi.vip.lib.common.widget.dialog.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val mLoadingStatus: SingleLiveEvent<Status> = SingleLiveEvent()

    fun showDialog(msg: String? = null) {
        mLoadingStatus.postValue(Status.Show(msg))
    }

    fun dismissDialog() {
        mLoadingStatus.postValue(Status.Dismiss)
    }

    fun launch(errorBlock: ((Throwable) -> Unit)? = null, runBlock: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch {
            try {
                withTimeout(5000) { runBlock() }
            } catch (e: Exception) {
                e.printStackTrace()
                cancel("${e.message}}", e)
            }
        }.invokeOnCompletion {
            //针对具体exception做相应处理，未包含错误需在viewmodel中调用invokeOnCompletion自行处理
            when (it) {
                is BaseRepository.TokenInvalidException -> {
                    //go login
                    ToastUtils.showShort("登录已超时，请重新登录")
                    Routers.login(clear = true)
                }
                else -> it?.let { errorBlock?.invoke(it) }
            }
        }
}