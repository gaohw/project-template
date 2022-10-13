package com.ctsi.vip.lib.framework.base.lifecycles

import android.os.Bundle
import androidx.annotation.Nullable

/**
 * Class : IActivityLifecycle
 * Create by GaoHW at 2022-10-13 10:32.
 * Description:
 */
interface IActivityLifecycle {

    fun onCreate(@Nullable savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroy()

}