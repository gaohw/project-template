package com.ctsi.android.lib.im.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import cn.mtjsoft.inputview.InputView
import cn.mtjsoft.inputview.entity.FunctionEntity
import cn.mtjsoft.inputview.iml.AdapterItemClickListener
import cn.mtjsoft.inputview.iml.SendClickListener
import cn.mtjsoft.inputview.iml.VoiceOverListener
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.android.lib.im.ui.R

/**
 * Class : BottomInputView
 * Create by GaoHW at 2022-7-21 9:32.
 * Description:
 */
class BottomInputLayout : FrameLayout {

    private var inputView: InputView? = null
    private var textSendListener: OnTextSendListener? = null
    private var voiceSendListener: OnVoiceSendListener? = null
    private var functionClickListener: OnFunctionClickListener? = null

    private val functionData = listOf(
        FunctionEntity(R.drawable.im_ic_func_pic, "照片"), FunctionEntity(R.drawable.im_ic_func_shot, "拍摄"),
        FunctionEntity(R.drawable.im_ic_func_file, "文件"), FunctionEntity(R.drawable.im_ic_func_video, "通话"),
        FunctionEntity(R.drawable.im_ic_func_location, "位置"), FunctionEntity(R.drawable.im_ic_func_usercard, "名片")
    )

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.im_layout_bottom_input, this)
        inputView = findViewById(R.id.v_input)
        inputView
            ?.setFuncationData(functionData)
            ?.setSendClickListener(object : SendClickListener {
                override fun onSendClick(view: View, content: String) {
                    textSendListener?.onTextSend(content)
                }
            })?.setFuncationClickListener(object : AdapterItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    functionClickListener?.onFuncClick(position, functionData[position].name)
                }
            })
            ?.setVoiceOverListener(object : VoiceOverListener {
                // 没有录音权限回调，在这里申请权限
                override fun noPermission(permission: String) {
                    PermissionUtils.permission(permission).request()
                }

                override fun onOver(fileName: String, filePath: String, duration: Int) {
                    voiceSendListener?.onVoiceSend(fileName, filePath, duration)
                    // 播放PCM格式音频
                    //PCMAudioPlayer.instance.startPlay(filePath)
                }
            })
    }

    fun setTextSendListener(listener: OnTextSendListener?): BottomInputLayout {
        textSendListener = listener
        return this
    }

    fun setVoiceSendListener(listener: OnVoiceSendListener?): BottomInputLayout {
        voiceSendListener = listener
        return this
    }

    fun setFunctionClickListener(listener: OnFunctionClickListener?): BottomInputLayout {
        functionClickListener = listener
        return this
    }

    fun hide() {
        inputView?.hideKeyboardAndInputView()
    }


    fun interface OnTextSendListener {
        fun onTextSend(text: String)
    }

    fun interface OnVoiceSendListener {
        fun onVoiceSend(name: String, path: String, duration: Int)
    }

    fun interface OnFunctionClickListener {
        fun onFuncClick(position: Int, name: String)
    }
}