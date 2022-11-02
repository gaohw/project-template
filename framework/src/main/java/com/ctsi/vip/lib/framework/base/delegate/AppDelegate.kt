package com.ctsi.vip.lib.framework.base.delegate

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.ctsi.vip.lib.framework.AppContext
import com.ctsi.vip.lib.framework.base.lifecycles.DefaultActivityLifecycle
import com.ctsi.vip.lib.framework.base.lifecycles.IAppLifecycle
import com.ctsi.vip.lib.framework.integration.ConfigModule
import com.ctsi.vip.lib.framework.integration.GlobalConfigModule
import com.ctsi.vip.lib.framework.utils.ManifestParser


/**
 * Class : AppDelegate
 * Create by GaoHW at 2022-10-13 9:56.
 * Description:
 */
class AppDelegate constructor(context: Context) : IAppLifecycle {

    private var isDebug: Boolean = false
    private var mApplication: Application? = null
    private val mDefaultActivityLifecycle by lazy { DefaultActivityLifecycle(mFragmentLifecycles) }

    private val mModules: MutableList<ConfigModule>
    private val mAppLifecycles = mutableListOf<IAppLifecycle>()
    private val mActivityLifecycles = mutableListOf<Application.ActivityLifecycleCallbacks>()
    private val mFragmentLifecycles = mutableListOf<FragmentManager.FragmentLifecycleCallbacks>()

    init {
        this.mModules = ManifestParser(context).parse().toMutableList()
        for (module in mModules) {
            module.injectAppLifecycle(context, mAppLifecycles)
            module.injectActivityLifecycle(context, mActivityLifecycles)
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
        val globalConfigModule = getGlobalConfigModule(application)

        mApplication = application
        mApplication?.registerActivityLifecycleCallbacks(mDefaultActivityLifecycle)
        mActivityLifecycles.forEach { mApplication?.registerActivityLifecycleCallbacks(it) }

        mAppLifecycles.forEach { it.onCreate(application) }

        //初始化
        AppContext.debug(isDebug).init(application, globalConfigModule)
    }

    override fun onTerminate(application: Application) {

    }

    private fun getGlobalConfigModule(context: Context): GlobalConfigModule {
        val builder = GlobalConfigModule.Builder()
        for (module in mModules) {
            module.applyOptions(context, builder)
        }
        mModules.clear()
        return builder.build()
    }
}