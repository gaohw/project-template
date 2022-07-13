package com.ctsi.vip.module.login.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.ctsi.vip.lib.common.Routers
import com.ctsi.vip.lib.common.base.BaseActivity
import com.ctsi.vip.lib.common.widget.dialog.AlertMsgDialog
import com.ctsi.vip.module.login.databinding.ActivityLoginBinding
import com.ctsi.vip.module.login.viewmodel.LoginViewModel

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
@Route(path = Routers.PATH_LOGIN, name = "登录activity")
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        mBinding.btnLogin.setOnClickListener {
            mViewModel.login("18811442439", "123456")
        }

        mViewModel.userData.observe(this) { user ->
            if (user != null) {
                when (user.status) {
                    "0" -> {
                        Routers.navigation(Routers.PATH_MAIN)
                        finish()
                    }
                    else -> {
                        AlertMsgDialog.Builder(this)
                            .setAlertMessage("此账号正在审核中，请耐心等待或联系管理员")
                            .setNegativeButton("取消")
                            .setAutoDismiss(true)
                            .build().show()
                    }
                }
            } else {
                ToastUtils.showShort("登录失败，请重试")
            }
        }
    }
}