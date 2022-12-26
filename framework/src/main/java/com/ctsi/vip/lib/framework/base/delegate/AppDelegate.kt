package com.ctsi.vip.lib.framework.base.delegate

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.ctsi.vip.lib.framework.AppContext
import com.ctsi.vip.lib.framework.base.lifecycles.DefaultActivityLifecycle
import com.ctsi.vip.lib.framework.base.lifecycles.IAppLifecycle
import com.ctsi.vip.lib.framework.http.RetrofitManager
import com.ctsi.vip.lib.framework.http.state.NetworkStateHelper
import com.ctsi.vip.lib.framework.base.integration.ConfigModule
import com.ctsi.vip.lib.framework.base.integration.GlobalConfigModule
import com.ctsi.vip.lib.framework.utils.ManifestParser


/**
 * Class : AppDelegate
 * Create by GaoHW at 2022-10-13 9:56.
 * Description:
 */
class AppDelegate constructor(val application: Application) : IAppLifecycle {

    private var isDebug: Boolean = false
    private var mApplication: Application? = null
    private var globalConfigModule: GlobalConfigModule? = null
    private val mDefaultActivityLifecycle by lazy { DefaultActivityLifecycle(mFragmentLifecycles) }

    private val mModules: MutableList<ConfigModule>
    private val mAppLifecycles = mutableListOf<IAppLifecycle>()
    private val mActivityLifecycles = mutableListOf<Application.ActivityLifecycleCallbacks>()
    private val mFragmentLifecycles = mutableListOf<FragmentManager.FragmentLifecycleCallbacks>()

    init {
        this.mModules = ManifestParser(application).parse().toMutableList()
        for (module in mModules) {
            module.injectAppLifecycle(application, mAppLifecycles)
            module.injectActivityLifecycle(application, mActivityLifecycles)
        }
    }

    fun setDebugMode(debug: Boolean): AppDelegate {
        this.isDebug = debug
        return this
    }

    override fun attachBaseContext(context: Context?) {
        mAppLifecycles.forEach { it.attachBaseContext(context) }
    }

    override fun onCreate(application: Application) {
        mApplication = application
        mApplication?.registerActivityLifecycleCallbacks(mDefaultActivityLifecycle)
        mActivityLifecycles.forEach { mApplication?.registerActivityLifecycleCallbacks(it) }

        mAppLifecycles.forEach { it.onCreate(application) }

        //初始化
        Utils.init(application) //工具类
        if (isDebug) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application) //ARouter
        NetworkStateHelper.registerReceiver(application) //网络相关

        getGlobalConfigModule(application)
        RetrofitManager.init(
            application, globalConfigModule?.getApiUrl(), globalConfigModule?.getHttpInterceptors(),
            globalConfigModule?.getRetrofitConfiguration(), globalConfigModule?.getOkhttpConfiguration()
        )
    }

    override fun onTerminate(application: Application) {
        mAppLifecycles.forEach { it.onTerminate(application) }
    }

    private fun getGlobalConfigModule(context: Context) {
        val builder = GlobalConfigModule.Builder()
        for (module in mModules) {
            module.applyOptions(context, builder)
        }
        mModules.clear()
        globalConfigModule = builder.build()
    }

    fun getGlobalConfigModule(): GlobalConfigModule? = globalConfigModule
}