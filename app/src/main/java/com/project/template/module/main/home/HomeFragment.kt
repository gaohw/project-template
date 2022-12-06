package com.project.template.module.main.home

import com.ctsi.vip.lib.framework.base.BaseFragment
import com.project.template.R
import com.project.template.databinding.FragmentHomeBinding
import com.project.template.module.main.MainViewModel

/**
 * Created by GaoHW at 2022-7-5.
 *
 * Desc:
 */
class HomeFragment : BaseFragment<MainViewModel, FragmentHomeBinding>() {

    override fun onFragmentCreate() {
        mBinding.vTitle.showBack(false).setTitleText("首页").setTitleLightMode(false).fitSystemWindows(true)
            .setTitleBackground(R.color.color_main)

        mBinding.btnTest.setOnClickListener { mViewModel.getUnreadMessage() }
    }
}