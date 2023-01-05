package com.project.template.module.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.android.lib.im.ui.fragment.ChatFragment
import com.ctsi.vip.lib.framework.base.BaseActivity
import com.project.template.R
import com.project.template.databinding.ActivityMainBinding
import com.project.template.module.main.home.HomeFragment
import com.project.template.module.main.mine.MineFragment

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
//@Route(path = Routers.PATH_MAIN, name = "首页")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private var lastBackTime: Long = 0

    private val titles = listOf("首页", "我的")
    private val tabIcons = listOf(R.drawable.selector_tab_home, R.drawable.selector_tab_mine)
    private val fragments = listOf(HomeFragment(), MineFragment())

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        mBinding.vpMain.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int = fragments.size

            override fun getItem(position: Int): Fragment = fragments[position]
        }
        mBinding.tabMain.setupWithViewPager(mBinding.vpMain, false)

        mBinding.tabMain.removeAllTabs()
        for (i in fragments.indices) {
            val tab = mBinding.tabMain.newTab().apply {
                setText(titles[i])
                setIcon(tabIcons[i])
            }
            mBinding.tabMain.addTab(tab)
        }

        //获取未读消息数
        mViewModel.getUnreadMessage()
    }

    override fun onBackPressed() {
        val curTime = System.currentTimeMillis()
        if (curTime - lastBackTime > 1000) {
            lastBackTime = curTime
            ToastUtils.showShort("再次点击退出应用")
        } else {
            super.onBackPressed()
        }
    }
}