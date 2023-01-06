package com.ctsi.android.lib.im.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Class : Kt
 * Create by GaoHW at 2023-1-6 16:47.
 * Description:
 */

fun ViewGroup.inflate(@LayoutRes resource: Int, attach: Boolean = false): View {
    val inflater = LayoutInflater.from(context)
    return inflater.inflate(resource, this, attach)
}