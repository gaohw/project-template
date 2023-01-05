package com.project.template.module.test

import android.os.Bundle
import android.view.View
import com.ctsi.android.lib.im.CtsiIM
import com.ctsi.android.lib.im.ui.fragment.ChatFragment
import com.ctsi.vip.lib.framework.base.BaseActivity
import com.ctsi.vip.lib.framework.base.BaseViewModel
import com.project.template.R
import com.project.template.databinding.ActivityImSampleBinding

/**
 * Class : IMSampleActivity
 * Create by GaoHW at 2023-1-5 9:28.
 * Description:
 */
class IMSampleActivity : BaseActivity<BaseViewModel, ActivityImSampleBinding>() {

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        CtsiIM.userManager().setCurrentUser("gao")
        CtsiIM.messageManager().sendTextMessage("xxyy", "测试文本消息")
        CtsiIM.messageManager().sendTextMessage("admin", "测试文本消息")

        supportFragmentManager.beginTransaction()
            .add(R.id.layout_container, ChatFragment())
            .commitAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
        updateUnreadCount()
    }

    private fun updateUnreadCount() {
        val count = CtsiIM.messageManager().getUnreadCount()
        mBinding.tvMessageUnread.text = if (count < 99) "$count" else "99+"
        mBinding.tvMessageUnread.visibility = if (count > 0) View.VISIBLE else View.GONE
    }
}