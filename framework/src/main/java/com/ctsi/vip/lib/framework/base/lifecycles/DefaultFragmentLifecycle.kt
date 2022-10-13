package com.ctsi.vip.lib.framework.base.lifecycles

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ctsi.vip.lib.framework.base.delegate.FragmentDelegate
import com.ctsi.vip.lib.framework.base.delegate.IFragment
import com.ctsi.vip.lib.framework.utils.cache.Cache
import com.ctsi.vip.lib.framework.utils.cache.IntelligentCache
import com.ctsi.vip.lib.framework.utils.cache.IntelligentCache.Companion.getKeyOfKeep


/**
 * Class : DefaultFragmentLifecycle
 * Create by GaoHW at 2022-10-13 12:12.
 * Description:
 */
class DefaultFragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, fragment: Fragment, context: Context) {
        super.onFragmentAttached(fm, fragment, context)
        if (fragment is IFragment) {
            var fragmentDelegate = fetchFragmentDelegate(fragment)
            if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {
                fragmentDelegate = FragmentDelegate(fm, fragment)
                fragment.provideCache().put(IntelligentCache.getKeyOfKeep(FragmentDelegate.FRAGMENT_DELEGATE), fragmentDelegate);
            }
            fragmentDelegate.onAttach(context)
        }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onCreate(savedInstanceState)
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        fetchFragmentDelegate(f)?.onCreateView(v, savedInstanceState)
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        fetchFragmentDelegate(f)?.onActivityCreate(savedInstanceState)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegate(f)?.onStart()
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegate(f)?.onResume()
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegate(f)?.onPause()
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegate(f)?.onStop()
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onSaveInstanceState(outState)
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegate(f)?.onDestroyView()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegate(f)?.onDestroy()
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegate(f)?.onDetach()
    }

    private fun fetchFragmentDelegate(fragment: Fragment): FragmentDelegate? {
        if (fragment is IFragment) {
            val cache: Cache<String, Any?> = fragment.provideCache()
            return cache[getKeyOfKeep(FragmentDelegate.FRAGMENT_DELEGATE)] as FragmentDelegate?
        }
        return null
    }
}