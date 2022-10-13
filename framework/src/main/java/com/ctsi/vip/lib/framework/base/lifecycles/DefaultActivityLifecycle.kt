package com.ctsi.vip.lib.framework.base.lifecycles

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ctsi.vip.lib.framework.base.delegate.ActivityDelegate
import com.ctsi.vip.lib.framework.base.delegate.IActivity
import com.ctsi.vip.lib.framework.utils.cache.Cache
import com.ctsi.vip.lib.framework.utils.cache.IntelligentCache


/**
 * Class : DefaultActivityLifecycle
 * Create by GaoHW at 2022-10-13 10:34.
 * Description:
 */
class DefaultActivityLifecycle constructor(private val fragmentLifecycles: List<FragmentManager.FragmentLifecycleCallbacks>) :
    Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is IActivity) {
            var activityDelegate = fetchActivityDelegate(activity)
            if (activityDelegate == null) {
                val cache = activity.provideCache()
                activityDelegate = ActivityDelegate(activity)
                cache.put(IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE), activityDelegate)
            }
            activityDelegate.onCreate(savedInstanceState)
        }
        //
        registerFragmentLifecycleCallbacks(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        fetchActivityDelegate(activity)?.onStart()
    }

    override fun onActivityResumed(activity: Activity) {
        fetchActivityDelegate(activity)?.onResume()
    }

    override fun onActivityPaused(activity: Activity) {
        fetchActivityDelegate(activity)?.onPause()
    }

    override fun onActivityStopped(activity: Activity) {
        fetchActivityDelegate(activity)?.onStop()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        fetchActivityDelegate(activity)?.onSaveInstanceState(outState)
    }

    override fun onActivityDestroyed(activity: Activity) {
        fetchActivityDelegate(activity)?.run {
            onDestroy()
            (activity as IActivity).provideCache().clear()
        }
    }

    private fun registerFragmentLifecycleCallbacks(activity: Activity) {
        val useFragment = if (activity is IActivity) activity.useFragment() else false
        if (activity is FragmentActivity && useFragment) {
            val supportFragmentManager = activity.supportFragmentManager
            supportFragmentManager.registerFragmentLifecycleCallbacks(DefaultFragmentLifecycle(), true)
            fragmentLifecycles.forEach { supportFragmentManager.registerFragmentLifecycleCallbacks(it, true) }
        }
    }

    private fun fetchActivityDelegate(activity: Activity): ActivityDelegate? {
        var activityDelegate: ActivityDelegate? = null
        if (activity is IActivity) {
            val cache: Cache<String, Any?> = activity.provideCache()
            activityDelegate = cache[IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE)] as ActivityDelegate?
        }
        return activityDelegate
    }
}