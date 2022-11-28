package com.project.template.config

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.ctsi.vip.lib.framework.base.lifecycles.IAppLifecycle
import com.ctsi.vip.lib.framework.base.integration.ConfigModule
import com.ctsi.vip.lib.framework.base.integration.GlobalConfigModule
import com.project.template.http.interceptors.TokenInterceptor

/**
 * Class : AppModuleConfig
 * Create by GaoHW at 2022-10-13 14:31.
 * Description:
 */
class AppModuleConfig : ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.baseUrl("http://27.128.228.208:9067/").addInterceptor(TokenInterceptor())
    }

    override fun injectAppLifecycle(context: Context, lifecycles: List<IAppLifecycle>) {

    }

    override fun injectActivityLifecycle(context: Context, lifecycles: List<Application.ActivityLifecycleCallbacks>) {

    }

    override fun injectFragmentLifecycle(
        context: Context, lifecycles: List<FragmentManager.FragmentLifecycleCallbacks>
    ) {

    }
}