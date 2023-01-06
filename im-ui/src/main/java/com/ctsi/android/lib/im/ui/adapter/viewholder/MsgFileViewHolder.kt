package com.ctsi.android.lib.im.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.FileUtils
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.ui.R
import java.io.File

/**
 * Class : MsgFileViewHolder
 * Create by GaoHW at 2023-1-6 16:06.
 * Description:
 */
class MsgFileViewHolder(view: View) : MsgViewHolder(view) {

    val tvFileName: TextView = view.findViewById(R.id.tv_file_name)
    val tvFileSize: TextView = view.findViewById(R.id.tv_file_size)
    val ivFileType: ImageView = view.findViewById(R.id.iv_file_type)

    override fun updateMessage(position: Int, message: MessageBean) {
        ivFileType.setImageResource(R.drawable.im_ic_file_default)
        tvFileName.text = getFileNameByPath(message.msgContent)
        tvFileSize.text = getFileSize(message.msgContent, message.fileSize)
    }

    private fun getFileNameByPath(path: String?): String? {
        return FileUtils.getFileName(path)
    }

    private fun getFileSize(path: String?, size: String?): String? {
        return try {
            ConvertUtils.byte2FitMemorySize(size!!.toLong())
        } catch (e: Exception) {
            FileUtils.getSize(path)
        }
    }
}