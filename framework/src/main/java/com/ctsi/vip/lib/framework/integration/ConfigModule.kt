package com.ctsi.vip.lib.framework.integration

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.ctsi.vip.lib.framework.base.lifecycles.IAppLifecycle


/**
 * Class : ConfigModule
 * Create by GaoHW at 2022-10-13 10:04.
 * Description:
 */
interface ConfigModule {

    fun applyOptions(context: Context, builder: GlobalConfigModule.Builder)

    fun injectAppLifecycle(context: Context, lifecycles: List<IAppLifecycle>)

    fun injectActivityLifecycle(context: Context, lifecycles: List<Application.ActivityLifecycleCallbacks>)

    fun injectFragmentLifecycle(context: Context, lifecycles: List<FragmentManager.FragmentLifecycleCallbacks>)
}