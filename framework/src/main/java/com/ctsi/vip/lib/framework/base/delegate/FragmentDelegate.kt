package com.ctsi.vip.lib.framework.base.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ctsi.vip.lib.framework.base.lifecycles.IFragmentLifecycle

/**
 * Class : FragmentDelegate
 * Create by GaoHW at 2022-10-13 13:56.
 * Description:
 */
class FragmentDelegate(val fm: FragmentManager, val fragment: IFragment) : IFragmentLifecycle {

    companion object {
        @JvmStatic
        val FRAGMENT_DELEGATE = "FRAGMENT_DELEGATE"
    }

    override fun onAttach(context: Context) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
    }

    override fun onCreateView(view: View?, savedInstanceState: Bundle?) {
    }

    override fun onActivityCreate(savedInstanceState: Bundle?) {
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

    override fun onDestroyView() {
    }

    override fun onDestroy() {

    }

    override fun onDetach() {
    }

    override fun isAdded(): Boolean {
        return (fragment as Fragment?)?.isAdded ?: false
    }
}