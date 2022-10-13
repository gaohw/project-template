package com.ctsi.vip.lib.framework.http.state

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
internal class NetworkStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            //获取联网状态的NetworkInfo对象
            val info: NetworkInfo? = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO)
            if (info != null) {
                //如果当前的网络连接成功并且网络连接可用
                val state = if (NetworkInfo.State.CONNECTED == info.state && info.isAvailable) {
                    getConnectionType(info.type, true)
                } else {
                    getConnectionType(info.type, false)
                }
                NetworkStateHelper.networkStateChange(state)
            } else {
                NetworkStateHelper.networkStateChange(NetState.Disconnect)
            }
        }
    }

    private fun getConnectionType(type: Int, isConnect: Boolean): NetState = when (type) {
        ConnectivityManager.TYPE_MOBILE -> NetState.Mobile(isConnect)
        ConnectivityManager.TYPE_WIFI -> NetState.Wifi(isConnect)
        else -> NetState.Disconnect
    }
}