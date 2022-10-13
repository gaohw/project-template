package com.ctsi.vip.lib.framework.http.state

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
sealed class NetState {
    data class Wifi(val enable: Boolean) : NetState()
    data class Mobile(val enable: Boolean) : NetState()
    object Disconnect : NetState()
}