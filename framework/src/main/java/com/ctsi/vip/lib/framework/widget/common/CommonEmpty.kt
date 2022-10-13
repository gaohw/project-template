package com.ctsi.vip.lib.framework.widget.common

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.ColorUtils
import com.ctsi.vip.lib.common.R

/**
 * Class : CommonEmpty
 * Create by GaoHW at 2022-7-8 10:21.
 * Description:
 */
class CommonEmpty : LinearLayout {

    private var ivEmpty: ImageView
    private var tvEmpty: TextView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        ivEmpty = ImageView(context)
        ivEmpty.scaleType = ImageView.ScaleType.CENTER
        ivEmpty.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        tvEmpty = TextView(context)
        tvEmpty.setTextColor(ColorUtils.getColor(R.color.color_9))
        tvEmpty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        tvEmpty.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            .apply { setMargins(0, 20, 0, 0) }

        addView(ivEmpty)
        addView(tvEmpty)
    }

    fun setEmptyIcon(@DrawableRes resource: Int): CommonEmpty {
        ivEmpty.setImageResource(resource)
        return this
    }

    fun setEmptyText(text: String?): CommonEmpty {
        tvEmpty.text = text
        return this
    }
}