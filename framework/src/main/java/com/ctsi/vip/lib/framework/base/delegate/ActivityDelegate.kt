package com.ctsi.vip.lib.framework.base.delegate

import android.os.Bundle
import com.ctsi.vip.lib.framework.base.lifecycles.IActivityLifecycle

/**
 * Class : ActivityDelegate
 * Create by GaoHW at 2022-10-13 10:37.
 * Description:
 */
class ActivityDelegate(val activity: IActivity) : IActivityLifecycle {

    companion object {
        @JvmStatic
        val ACTIVITY_DELEGATE = "ACTIVITY_DELEGATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

    override fun onDestroy() {

    }
}