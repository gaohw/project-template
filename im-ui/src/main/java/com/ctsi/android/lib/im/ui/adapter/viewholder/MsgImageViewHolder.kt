package com.ctsi.android.lib.im.ui.adapter.viewholder

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.ctsi.android.lib.im.bean.MessageBean
import com.ctsi.android.lib.im.ui.R

/**
 * Class : MsgImageViewHolder
 * Create by GaoHW at 2023-1-5 14:46.
 * Description:
 */
class MsgImageViewHolder(view: View) : MsgViewHolder(view) {

    private val ivImage: ImageView = view.findViewById(R.id.iv_message_image)

    init {
        ivImage.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline?) {
                outline?.setRoundRect(0, 0, view.width, view.height, 10f)
            }
        }
        ivImage.clipToOutline = true
        ivImage.setOnClickListener { ToastUtils.showShort("查看大图") }
    }

    override fun updateMessage(position: Int, message: MessageBean) {
        Glide.with(itemView).load(message.msgContent).placeholder(R.drawable.im_shape_image_default)
            .into(ivImage)
    }
}