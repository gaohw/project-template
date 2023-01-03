package com.ctsi.android.lib.im.ui.widget

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.android.lib.im.ui.R

/**
 * Class : BottomInputView
 * Create by GaoHW at 2022-7-21 9:32.
 * Description:
 */
class BottomInputLayout : FrameLayout {

    var edtInput: EditText?
    var btnSend: TextView?
    private var inputSendListener: OnInputSendListener? = null
    private val roundOutlineProvider by lazy {
        object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                if (view == null || outline == null) {
                    return
                }
                outline.setRoundRect(0, 0, view.width, view.height, ConvertUtils.dp2px(5f).toFloat())
            }
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.im_layout_bottom_input, this)
        edtInput = findViewById(R.id.edt_content)
        btnSend = findViewById(R.id.btn_send)

        edtInput?.outlineProvider = roundOutlineProvider
        edtInput?.clipToOutline = true
//        edtInput?.filters = arrayOf(EmojiInputFilter())
        edtInput?.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                btnSend?.isEnabled = true
                btnSend?.setBackgroundResource(R.color.im_bottom_input_enable)
            } else {
                btnSend?.isEnabled = false
                btnSend?.setBackgroundResource(R.color.im_bottom_input_disable)
            }
        }

        btnSend?.outlineProvider = roundOutlineProvider
        btnSend?.clipToOutline = true
        btnSend?.setOnClickListener {
            val input = edtInput?.text?.toString()?.trim()
            if (input.isNullOrEmpty()) {
                ToastUtils.showShort("请输入回复内容")
            } else {
                inputSendListener?.onInputSend(input)
                edtInput?.text = null
            }
        }
    }

    fun showSoftInput() {
        edtInput?.requestFocus()
        edtInput?.let { KeyboardUtils.showSoftInput(it) }
    }

    fun hideSoftInput(clearFocus: Boolean = false) {
        if (clearFocus) {
            edtInput?.clearFocus()
        }
        edtInput?.let { KeyboardUtils.hideSoftInput(it) }
    }

    fun setInputSendListener(listener: OnInputSendListener?) {
        this.inputSendListener = listener
    }

    fun interface OnInputSendListener {
        fun onInputSend(input: String)
    }
}