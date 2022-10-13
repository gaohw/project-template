package com.ctsi.vip.lib.framework.widget.dialog

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.ctsi.vip.lib.common.R

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
sealed class Status {
    data class Show(val msg: String? = null) : Status()
    object Dismiss : Status()
}

class LoadingDialog private constructor(builder: Builder) : AlertDialog(builder.context) {

    private var tvMsg: TextView? = null
    private var msgAlert: String? = "加载中"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)

        tvMsg = findViewById<TextView>(R.id.tv_loading)
    }

    override fun onStart() {
        super.onStart()
        window?.setDimAmount(0f)
        window?.setBackgroundDrawableResource(R.color.transparent)

        if (tvMsg?.text != msgAlert) {
            tvMsg?.text = msgAlert
        }
    }

    override fun setMessage(message: CharSequence?) {
        msgAlert = message?.toString()
    }

    class Builder(val context: Context) {

        fun build(): LoadingDialog {
            return LoadingDialog(this)
        }
    }
}