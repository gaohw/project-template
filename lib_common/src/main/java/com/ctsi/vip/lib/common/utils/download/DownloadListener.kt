package com.ctsi.vip.lib.common.utils.download

import okhttp3.Request

/**
 * Class : DownloadListener
 * Create by GaoHW at 2022-7-6 17:34.
 * Description:
 */
interface DownloadListener {
    fun onDownloadSuccess()
    fun onDownloadFailed(cause: String?)

    fun onPreDownload(builder: Request.Builder) {
        //预下载
    }

    fun onDownloadStart(key: String) {
        //下载开始
    }

    fun onDownloadProgress(curCount: Long, totalCount: Long) {
        //下载进度
    }
}