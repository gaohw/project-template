package com.ctsi.vip.lib.framework.base.delegate

import android.os.Bundle
import com.ctsi.vip.lib.framework.http.state.NetState
import com.ctsi.vip.lib.framework.utils.cache.Cache


/**
 * Class : IActivity
 * Create by GaoHW at 2022-10-13 10:39.
 * Description:
 */
interface IActivity {

    fun provideCache(): Cache<String, Any?>

    fun getContentLayout(): Int

    fun onNetworkStateChanged(state: NetState)

    fun onActivityCreate(savedInstanceState: Bundle?)

    fun useFragment(): Boolean
}