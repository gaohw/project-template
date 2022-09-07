package com.ctsi.vip.lib.common.widget.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.BarUtils
import com.ctsi.vip.lib.common.R

/**
 * Class : CommonTitle
 * Create by GaoHW at 2022-7-13 9:58.
 * Description:
 */
class CommonTitle : FrameLayout {

    private val btnBack: ImageButton
    private val tvTitle: TextView
    private val tvRight: TextView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        LayoutInflater.from(context).inflate(R.layout.layout_common_title, this)
        btnBack = findViewById(R.id.c_btn_back)
        tvTitle = findViewById(R.id.c_tv_title)
        tvRight = findViewById(R.id.c_tv_right)
    }

    fun showBack(show: Boolean = true): CommonTitle {
        btnBack.visibility = if (show) View.VISIBLE else View.INVISIBLE
        return this
    }

    fun fitSystemWindows(fit: Boolean = true): CommonTitle {
        if (fit) {
            setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        } else {
            setPadding(0, 0, 0, 0)
        }
        return this
    }

    fun setTitleLightMode(isLight: Boolean): CommonTitle {
        if (isLight) {
            btnBack.setImageResource(R.drawable.c_ic_back_black)
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.color_3))
            tvRight.setTextColor(ContextCompat.getColor(context, R.color.color_3))
        } else {
            btnBack.setImageResource(R.drawable.c_ic_back_white)
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.color_f))
            tvRight.setTextColor(ContextCompat.getColor(context, R.color.color_f))
        }
        return this
    }

    fun setTitleText(title: String): CommonTitle {
        tvTitle.text = title
        return this
    }

    fun setTitleBackground(resource: Int): CommonTitle {
        setBackgroundResource(resource)
        return this
    }

    fun setTitleRightOption(option: String, block: () -> Unit) {
        tvRight.text = option
        tvRight.visibility = View.VISIBLE
        tvRight.setOnClickListener { block.invoke() }
    }
}