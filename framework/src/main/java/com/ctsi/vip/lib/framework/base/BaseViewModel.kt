package com.ctsi.vip.lib.framework.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctsi.vip.lib.framework.AppContext
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

    fun launch(
        onError: ((Throwable?) -> Unit)? = null, onStart: (() -> Unit)? = null, onComplete: (() -> Unit)? = null,
        requestBlock: suspend CoroutineScope.() -> Unit
    ) {
        onStart?.invoke()     //before request
        viewModelScope.launch {
            try {
                withTimeout(getTimeOutMills()) { requestBlock() }
            } catch (e: Exception) {
                e.printStackTrace()
                cancel("${e.message}", e)
            }
        }.invokeOnCompletion { throwable ->
            if (throwable != null) {
                if (AppContext.getGlobalErrorHandler()?.handleError(throwable) != true) {
                    onError?.invoke(throwable)
                }
            }
            onComplete?.invoke()      //after request
        }
    }

    protected open fun getTimeOutMills(): Long {
        return AppContext.getGlobalConfigModule()?.getTimeOutMills() ?: 10000
    }
}