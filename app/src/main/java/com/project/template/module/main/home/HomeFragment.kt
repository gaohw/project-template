package com.project.template.module.main.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import cc.shinichi.library.ImagePreview
import com.ctsi.vip.lib.framework.base.BaseFragment
import com.donkingliang.imageselector.utils.ImageSelector
import com.project.template.R
import com.project.template.databinding.FragmentHomeBinding
import com.project.template.module.main.MainViewModel
import com.project.template.module.test.IMSampleActivity

/**
 * Created by GaoHW at 2022-7-5.
 *
 * Desc:
 */
class HomeFragment : BaseFragment<MainViewModel, FragmentHomeBinding>() {

    private val REQUEST_IMAGE = 0X01;

    override fun onFragmentCreate() {
        mBinding.vTitle.showBack(false).setTitleLightMode(false).fitSystemWindows(true)
            .setTitleBackground(R.color.color_main).setTitleText("首页")

        mBinding.btnImageSelect.setOnClickListener {
            ImageSelector.builder()
                .setSingle(true).setCrop(true)
                .start(this, REQUEST_IMAGE); // 打开相册
        }

        mBinding.btnImSample.setOnClickListener {
            startActivity(Intent(requireContext(), IMSampleActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            data?.getStringArrayListExtra(ImageSelector.SELECT_RESULT)?.let {
                ImagePreview.getInstance().setContext(requireContext())
                    .setImageList(it).setShowDownButton(false)
                    .start()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}