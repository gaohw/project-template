package com.ctsi.vip.lib.common.utils.download

import okhttp3.Call

/**
 * Class : DownloadTask
 * Create by GaoHW at 2022-7-7 10:09.
 * Description:
 */
internal class DownloadTask(private val key: String, private val call: Call, path: String) : DownloadListener {

    private var outListener: DownloadListener? = null
    private var callback: DCallback = DCallback(path, this)

    fun start(listener: DownloadListener?) {
        outListener = listener
        listener?.onDownloadStart(key)

        call.enqueue(callback)
    }

    fun cancel() {
        call.cancel()
    }

    override fun onDownloadSuccess() {
        DownloadManager.cancel(key)
        outListener?.onDownloadSuccess()
    }

    override fun onDownloadFailed(cause: String?) {
        DownloadManager.cancel(key)
        outListener?.onDownloadFailed(cause)
    }

    override fun onDownloadProgress(curCount: Long, totalCount: Long) {
        super.onDownloadProgress(curCount, totalCount)
        outListener?.onDownloadProgress(curCount, totalCount)
    }
}