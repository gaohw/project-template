package com.ctsi.vip.lib.framework.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.vip.lib.framework.router.RouterHub
import com.ctsi.vip.lib.framework.utils.SingleLiveEvent
import com.ctsi.vip.lib.framework.widget.dialog.Status
import kotlinx.coroutines.*

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
                withTimeout(10000) { runBlock() }
            } catch (e: Exception) {
                e.printStackTrace()
                cancel("${e.message}}", e)
            }
        }.invokeOnCompletion {
            //针对具体exception做相应处理，未包含错误需在viewmodel中调用invokeOnCompletion自行处理
            when (it) {
                is CancellationException -> {
                    if (it.cause is BaseRepository.TokenInvalidException) {
                        goLogin()
                    } else {
                        it.let { errorBlock?.invoke(it) }
                    }
                }
                is BaseRepository.TokenInvalidException -> goLogin()
                else -> it?.let { errorBlock?.invoke(it) }
            }
        }

    protected open fun goLogin() {
        //go login
        ToastUtils.showShort("登录已超时，请重新登录")
        RouterHub.login(clear = true)
    }
}