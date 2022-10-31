package com.ctsi.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.ScreenUtils
import com.ctsi.vip.lib.common.R

/**
 * Created by GaoHW at 2022-7-1.
 *
 * Desc: 提示信息dialog
 */
class AlertMsgDialog private constructor(private val builder: Builder) : AlertDialog(builder.context) {

    private var tvTitle: TextView? = null
    private var tvMessage: TextView? = null

    private var hDivider: View? = null
    private var vDivider: View? = null
    private var btnPositive: TextView? = null
    private var btnNegative: TextView? = null

    private val mHandler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.c_dialog_alert_msg)
        setCancelable(builder.cancelable)

        tvTitle = findViewById(R.id.tv_alert_title)
        tvMessage = findViewById(R.id.tv_alert_msg)

        hDivider = findViewById(R.id.h_divider)
        vDivider = findViewById(R.id.v_divider)
        btnPositive = findViewById(R.id.btn_positive)
        btnNegative = findViewById(R.id.btn_negative)

        tvTitle?.text = builder.title
        tvTitle?.visibility = if (builder.title.isNullOrEmpty()) View.GONE else View.VISIBLE
        tvMessage?.text = builder.message
        vDivider?.visibility = View.VISIBLE
        setDialogButton(btnPositive, builder.textPositive, builder.callbackPositive)
        setDialogButton(btnNegative, builder.textNegative, builder.callbackNegative)

        if (builder.autoDismiss) {
            mHandler.postDelayed({ dismiss() }, builder.autoDismissInterval)
        }
    }

    private fun setDialogButton(button: TextView?, text: String?, callback: DialogInterface.OnClickListener?) {
        if (text.isNullOrEmpty()) {
            button?.visibility = View.GONE
            vDivider?.visibility = View.GONE
        } else {
            button?.text = text
            button?.setOnClickListener {
                if (callback != null) {
                    callback.onClick(this, 0)
                } else {
                    dismiss()
                }
            }
            button?.visibility = View.VISIBLE
            hDivider?.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        window?.setBackgroundDrawableResource(R.drawable.c_shape_white_5dp)
        window?.attributes?.apply {
            width = (ScreenUtils.getAppScreenWidth() * 0.8).toInt()
        }
    }

    override fun dismiss() {
        super.dismiss()
        mHandler.removeCallbacksAndMessages(null)
    }

    class Builder(val context: Context) {

        var title: String? = null
        var message: String? = null
        var cancelable: Boolean = true
        var autoDismiss: Boolean = false
        var autoDismissInterval: Long = 0

        var textPositive: String? = null
        var colorPositive: Int = 0
        var callbackPositive: DialogInterface.OnClickListener? = null
        var textNegative: String? = null
        var colorNegative: Int = 0
        var callbackNegative: DialogInterface.OnClickListener? = null

        fun setAlertTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setAlertMessage(msg: String): Builder {
            this.message = msg
            return this
        }

        fun setAutoDismiss(dismiss: Boolean, interval: Long = 3000): Builder {
            this.autoDismiss = dismiss
            this.autoDismissInterval = interval
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun setPositiveColor(@ColorInt color: Int): Builder {
            this.colorPositive = color
            return this
        }

        fun setPositiveButton(text: String, callback: DialogInterface.OnClickListener? = null): Builder {
            this.textPositive = text
            this.callbackPositive = callback
            return this
        }

        fun setNegativeColor(@ColorInt color: Int): Builder {
            this.colorNegative = color
            return this
        }

        fun setNegativeButton(text: String, callback: DialogInterface.OnClickListener? = null): Builder {
            this.textNegative = text
            this.callbackNegative = callback
            return this
        }

        fun build(): AlertMsgDialog {
            return AlertMsgDialog(this)
        }
    }
}