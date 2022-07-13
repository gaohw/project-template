package com.ctsi.vip.lib.common.utils.download

import android.text.TextUtils
import com.blankj.utilcode.util.EncryptUtils
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Class : DownladManager
 * Create by GaoHW at 2022-7-6 17:17.
 * Description:
 */
object DownloadManager {

    private val okHttp by lazy { OkHttpClient() }
    private val downloadTasks: MutableMap<String, DownloadTask> = mutableMapOf()

    fun enqueue(url: String, path: String, listener: DownloadListener? = null) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            listener?.onDownloadFailed("无效的下载信息")
            return
        }
        synchronized(this) {
            val key = EncryptUtils.encryptMD5ToString(url)
            if (downloadTasks.containsKey(key)) {
                listener?.onDownloadFailed("任务已经在下载队列中")
                return
            }
            val builder = Request.Builder().url(url)
            listener?.onPreDownload(builder)

            val call = okHttp.newCall(builder.build())
            val task = DownloadTask(key, call, path)

            downloadTasks[key] = task
            task.start(listener)
        }
    }

    @Synchronized
    fun cancel(key: String) {
        val task = downloadTasks[key]
        if (task != null) {
            task.cancel()
            downloadTasks.remove(key)
        }
    }

    @Synchronized
    fun hasTask(url: String): Boolean {
        val key = EncryptUtils.encryptMD5ToString(url)
        return downloadTasks.containsKey(key)
    }
}