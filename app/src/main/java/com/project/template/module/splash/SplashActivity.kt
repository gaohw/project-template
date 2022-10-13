package com.project.template.module.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.ctsi.vip.lib.framework.base.BaseActivity
import com.ctsi.vip.lib.framework.base.BaseViewModel
import com.project.template.databinding.ActivitySplashBinding
import com.project.template.module.main.MainActivity
import com.project.template.module.test.shortcut.ShortcutActivity

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {

    private val timer = object : CountDownTimer(3000, 1000) {

        @SuppressLint("SetTextI18n")
        override fun onTick(remain: Long) {
            mBinding.btnSplashSkip.text = "跳过${remain / 1000}秒"
        }

        override fun onFinish() {
            goNextPage()
        }
    }

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        val clazz =
            when (intent?.getStringExtra("next")) {
                "shortcut" -> ShortcutActivity::class.java
                else -> null
            }
        if (clazz != null) {
            goNextPage(clazz)
        } else {
            mBinding.btnSplashSkip.setOnClickListener { goNextPage() }

            timer.start()
        }
    }

    private fun goNextPage(clazz: Class<*> = MainActivity::class.java) {
        if (isFinishing || isDestroyed) {
            return
        }
//        if (UserInfoUtil.isLogin()) {
//            startActivity(Intent(this, clazz).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//        } else {
//            Routers.navigation(Routers.PATH_LOGIN)
//        }
//        Routers.navigation(Routers.PATH_LOGIN)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
}