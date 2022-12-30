package com.ctsi.android.lib.im.core

import android.util.Log
import com.ctsi.android.lib.im.CtsiIM
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * Class : WebSocketManager
 * Create by GaoHW at 2022-12-26 8:55.
 * Description:
 */
internal object IMConnectManager : WebSocketListener() {

    private const val TAG = "IMConnectManager"
    private const val MAX_RECONNECT = 3
    private const val CODE_CLOSE_MANUAL = 1011

    private val sOkHttpClient by lazy {
        OkHttpClient.Builder()
            .pingInterval(30, TimeUnit.SECONDS) // 设置 PING 帧发送间隔---保活
            .build();
    }
    private val mWebSocketCache = mutableMapOf<String, Builder>()

    fun connect(socket: String) {
        val builder = mWebSocketCache[socket]
        if (builder == null) {
            mWebSocketCache[socket] = Builder(socket).connect()
        } else {
            log("$socket already connected!!")
        }
    }

    fun close(socket: String) {
        val builder = mWebSocketCache[socket]
        if (builder != null) {
            builder.close()
            mWebSocketCache.remove(socket)
        }
    }

    fun closeAll() {
        mWebSocketCache.onEach { it.value.close() }
        mWebSocketCache.clear()
    }

    /* 发送消息 */
    fun sendMessage(socket: String, message: String): Boolean {
        val builder = mWebSocketCache[socket]
        if (builder != null) {
            return builder.sendMessage(message)
        } else {
            log("$socket has not been connected!!")
        }
        return false
    }

    fun sendMessage(message: String) {
        mWebSocketCache.onEach { it.value.sendMessage(message) }
    }

    /* 监听callback */
    fun registerCallback(socket: String, callback: IMWebSocketCallback) {
        mWebSocketCache[socket]?.registerCallback(callback)
    }

    fun removeCallback(socket: String, callback: IMWebSocketCallback) {
        mWebSocketCache[socket]?.removeCallback(callback)
    }

    fun removeCallback(callback: IMWebSocketCallback) {
        mWebSocketCache.onEach { it.value.removeCallback(callback) }
    }

    private fun log(msg: String) {
        if (CtsiIM.isDebug) {
            Log.d(TAG, msg)
        }
    }

    private class Builder constructor(private val url: String) : WebSocketListener() {

        private var mReconnect: Int = 0
        private var mWebSocket: WebSocket? = null
        private val mSocketCallbacks: MutableSet<IMWebSocketCallback> = mutableSetOf()

        fun connect(): Builder {
            close()
            if (url.startsWith("ws")) {
                url
            } else {
                "ws://$url"
            }.let { url ->
                val request = Request.Builder().url(url).build()
                //创建WebSocket
                sOkHttpClient.newWebSocket(request, this)
            }
            return this
        }

        private fun reconnect() {
            if (mReconnect < MAX_RECONNECT) {
                Thread.sleep(5000)
                mReconnect++
                log("reconnecting: $mReconnect")
                //重连
                connect()
            } else {
                log("reconnect over $MAX_RECONNECT times, please check!!")
                mSocketCallbacks.forEach { it.onRetryOut() }
                close()
            }
        }

        fun close() {
            mWebSocket?.close(CODE_CLOSE_MANUAL, "关闭")
            mWebSocket = null
        }

        fun registerCallback(callback: IMWebSocketCallback) {
            mSocketCallbacks.add(callback)
        }

        fun removeCallback(callback: IMWebSocketCallback) {
            mSocketCallbacks.remove(callback)
        }

        fun sendMessage(message: String): Boolean {
            val send = mWebSocket?.send(message)
            if (send == true) {
                log("$url sendMessage ====> 发送成功")
            } else {
                log("$url sendMessage ====> 发送失败")
            }
            return send ?: false
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            val connect = response.code == 101
            if (connect) {
                log("$url onOpen ====> success!")
                //发送连接成功消息
//                webSocket.send("Hello, connect success!")
                //初始化
                mReconnect = 0
                mWebSocket = webSocket
                mSocketCallbacks.forEach { it.onConnect() }
            } else {
                log("$url onOpen ====> failed! ${response.message}")
                reconnect()
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            log("$url onFailure ====>\n${t.cause} ${t.message}")
            t.printStackTrace()
            reconnect()
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            onReceiveMessage(webSocket, bytes.hex())
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            onReceiveMessage(webSocket, text)
        }

        private fun onReceiveMessage(webSocket: WebSocket, message: String) {
            log("onReceiveMessage ====>\n$message")
            mSocketCallbacks.forEach { it.onReceiveMessage(message) }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            log("$url onClosing ====> $code $reason")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            log("$url onClosed ====> $code $reason")
            mSocketCallbacks.forEach { it.onClosed() }
            if (code != CODE_CLOSE_MANUAL) {
                //非手动关闭则重新连接
                reconnect()
            }
        }
    }
}