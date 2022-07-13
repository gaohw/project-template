package com.ctsi.vip.lib.common.utils.download

import com.blankj.utilcode.util.FileUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.*
import java.io.File
import java.io.IOException

/**
 * Class : DownloadRunnable
 * Create by GaoHW at 2022-7-6 17:17.
 * Description:
 */
internal class DCallback(
    private val path: String,
    private var downListener: DownloadListener
) : Callback {

    override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful && response.body != null) {
            try {
                val file = File(path)
                if (FileUtils.createOrExistsFile(file)) {
                    val sink = file.sink()
                    val progress = ProgressSink(sink, response.body!!.contentLength(),
                        object : ProgressListener {
                            override fun onProgress(curCount: Long, totalCount: Long) {
                                downListener.onDownloadProgress(curCount, totalCount)
                            }
                        })
                    val bufferSink = progress.buffer()
                    try {
                        bufferSink.writeAll(response.body!!.source())
                        downListener.onDownloadSuccess()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        downListener.onDownloadFailed(e.message)
                    } finally {
                        bufferSink.close()
                    }
                } else {
                    downListener.onDownloadFailed("文件创建失败，请重试")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                downListener.onDownloadFailed(e.message)
            }
        } else {
            downListener.onDownloadFailed(response.message)
        }
    }

    override fun onFailure(call: Call, e: IOException) {
        downListener.onDownloadFailed(e.message)
    }

    inner class ProgressSink(
        sink: Sink,
        private val totalCount: Long = 0,
        private val listener: ProgressListener? = null
    ) : ForwardingSink(sink) {

        private var curCount: Long = 0

        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            curCount += byteCount
            listener?.onProgress(curCount, totalCount)
        }
    }

    internal interface ProgressListener {
        fun onProgress(curCount: Long, totalCount: Long)
    }
}