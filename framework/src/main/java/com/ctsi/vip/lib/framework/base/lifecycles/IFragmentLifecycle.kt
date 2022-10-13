package com.ctsi.vip.lib.framework.base.lifecycles

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable


/**
 * Class : IFragmentLifecycle
 * Create by GaoHW at 2022-10-13 12:12.
 * Description:
 */
interface IFragmentLifecycle {

    fun onAttach(context: Context)

    fun onCreate(@Nullable savedInstanceState: Bundle?)

    fun onCreateView(@Nullable view: View?, @Nullable savedInstanceState: Bundle?)

    fun onActivityCreate(@Nullable savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroyView()

    fun onDestroy()

    fun onDetach()

    /**
     * Return true if the fragment is currently added to its activity.
     */
    fun isAdded(): Boolean
}