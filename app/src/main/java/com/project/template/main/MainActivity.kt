package com.project.template.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.vip.lib.common.Routers
import com.ctsi.vip.lib.common.base.BaseActivity
import com.project.template.R
import com.project.template.databinding.ActivityMainBinding
import com.project.template.main.home.HomeFragment
import com.project.template.main.mine.MineFragment

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
@Route(path = Routers.PATH_MAIN, name = "首页")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private var lastBackTime: Long = 0

    private val titles = listOf("首页", "我的")
    private val tabIcons = listOf(R.drawable.selector_tab_home, R.drawable.selector_tab_mine)
    private val fragments = listOf<Fragment>(HomeFragment(), MineFragment())

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