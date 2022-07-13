package com.ctsi.vip.lib.common.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import com.ctsi.vip.lib.common.R

/**
 * Created by GaoHW at 2022-7-1.
 *
 * Desc: 提示信息dialog
 */
class AlertMsgDialog private constructor(private val builder: Builder) : AlertDialog(builder.context, R.style.AlertMsgDialog) {

    private var tvMsg: TextView? = null

    private var hDivider: View? = null
    private var vDivider: View? = null
    private var btnPositive: TextView? = null
    private var btnNegative: TextView? = null

    private val mHandler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_alert_msg)

        tvMsg = findViewById(R.id.tv_alert_msg)
        hDivider = findViewById(R.id.h_divider)
        vDivider = findViewById(R.id.v_divider)
        btnPositive = findViewById(R.id.btn_positive)
        btnNegative = findViewById(R.id.btn_negative)

        tvMsg?.text = builder.message
        vDivider?.visibility = View.VISIBLE
        setDialogButton(btnPositive, builder.textPositive, builder.callbackPositive)
        setDialogButton(btnNegative, builder.textNegative, builder.callbackNegative)
        hDivider?.visibility = if (vDivider?.visibility == View.VISIBLE) View.VISIBLE else View.GONE

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
        }
    }

    override fun onStart() {
        super.onStart()
        window?.setBackgroundDrawableResource(R.drawable.shape_white_5dp)
    }

    class Builder(val context: Context) {

        var message: String? = null
        var autoDismiss: Boolean = false
        var autoDismissInterval: Long = 0

        var textPositive: String? = null
        var colorPositive: Int = 0
        var callbackPositive: DialogInterface.OnClickListener? = null
        var textNegative: String? = null
        var colorNegative: Int = 0
        var callbackNegative: DialogInterface.OnClickListener? = null

        fun setAlertMessage(msg: String): Builder {
            this.message = msg
            return this
        }

        fun setAutoDismiss(dismiss: Boolean, interval: Long = 3000): Builder {
            this.autoDismiss = dismiss
            this.autoDismissInterval = interval
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