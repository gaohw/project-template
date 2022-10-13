package com.ctsi.vip.lib.framework.base.delegate

import com.ctsi.vip.lib.framework.utils.cache.Cache

/**
 * Class : IFragment
 * Create by GaoHW at 2022-10-13 12:13.
 * Description:
 */
interface IFragment {

    fun provideCache(): Cache<String, Any?>

    fun onFragmentCreate()

    fun onFragmentResume()

    fun onFragmentPause()

    fun getContentLayout(): Int

    fun isCreateIndependentViewModel(): Boolean
}