package com.ctsi.vip.lib.widget.common

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Class : Radius
 * Create by GaoHW at 2022-7-20 15:32.
 * Description:
 */
class RoundOutlineProvider(val radius: Float) : ViewOutlineProvider() {

    override fun getOutline(view: View?, outline: Outline?) {
        if (view == null || outline == null) {
            return
        }
        outline.setRoundRect(0, 0, view.width, view.height, radius)
    }
}