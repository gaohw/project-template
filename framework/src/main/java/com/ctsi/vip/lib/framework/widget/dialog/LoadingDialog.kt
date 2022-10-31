package com.ctsi.vip.lib.framework.widget.dialog

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.ctsi.vip.lib.common.R

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc: 加载弹窗 Loading Dialog
 */
sealed class Status {
    data class Show(val msg: String? = null) : Status()
    object Dismiss : Status()
}

class LoadingDialog private constructor(builder: Builder) : AlertDialog(builder.context) {

    private var tvMsg: TextView? = null
    private var pbLoading: ProgressBar? = null
    private var layoutRoot: LinearLayout? = null

    private var msgAlert: String? = builder.msg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.c_dialog_loading)

        tvMsg = findViewById(R.id.tv_loading)
        pbLoading = findViewById(R.id.pb_loading)
        layoutRoot = findViewById(R.id.layout_root)
    }

    override fun onStart() {
        super.onStart()
        window?.setDimAmount(0f)
        window?.setBackgroundDrawableResource(R.color.transparent)

        if (tvMsg?.text != msgAlert) {
            tvMsg?.text = msgAlert
        }
    }

    fun setLoadingMsg(msg: String): LoadingDialog {
        msgAlert = msg
        return this
    }

    class Builder(val context: Context) {

        var msg: String = "加载中..."

        fun setLoadingMsg(msg: String): Builder {
            this.msg = msg
            return this
        }

        fun build(): LoadingDialog {
            return LoadingDialog(this)
        }
    }
}