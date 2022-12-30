package com.ctsi.android.lib.im.core

/**
 * Class : IMWebSocketCallback
 * Create by GaoHW at 2022-12-30 9:01.
 * Description:
 */
internal interface IMWebSocketCallback {

    fun onReceiveMessage(msg: String)

    fun onConnect() {}

    fun onClosed() {}

    fun onRetryOut() {}
}