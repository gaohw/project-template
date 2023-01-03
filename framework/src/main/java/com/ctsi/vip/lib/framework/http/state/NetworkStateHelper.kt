package com.ctsi.vip.lib.framework.http.state

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LifecycleOwner
import com.ctsi.vip.lib.framework.utils.unpeeklivedata.domain.message.MutableResult

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
object NetworkStateHelper {

    private val curNetworkState = MutableResult<NetState>()

    fun addObserver(owner: LifecycleOwner, block: (state: NetState) -> Unit) {
        curNetworkState.observe(owner) { block.invoke(it) }
    }

    fun removeObserver(owner: LifecycleOwner) {
        curNetworkState.removeObservers(owner)
    }

    internal fun registerReceiver(application: Application) {
        try {
            application.registerReceiver(
                NetworkStateReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    internal fun networkStateChange(state: NetState) {
        curNetworkState.value = state
    }
}