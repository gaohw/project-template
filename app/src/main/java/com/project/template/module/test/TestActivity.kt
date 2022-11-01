package com.project.template.module.test

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.vip.lib.framework.base.BaseActivity
import com.ctsi.vip.lib.framework.base.BaseViewModel
import com.project.template.R
import com.project.template.databinding.ActivityTestBinding

/**
 * Class : TestActivity
 * Create by GaoHW at 2022-7-21 9:50.
 * Description:
 */
class TestActivity : BaseActivity<BaseViewModel, ActivityTestBinding>() {

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.color_main))

        mBinding.layoutTitle.setTitleText("Test").showBack(true)
            .setTitleBackground(R.color.color_main).setTitleLightMode(false)
        mBinding.layoutInput.setInputSendListener { input -> ToastUtils.showShort(input) }
    }
}