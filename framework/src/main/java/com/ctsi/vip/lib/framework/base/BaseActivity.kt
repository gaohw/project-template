package com.ctsi.vip.lib.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils
import com.ctsi.vip.lib.framework.base.delegate.IActivity
import com.ctsi.vip.lib.framework.http.state.NetState
import com.ctsi.vip.lib.framework.http.state.NetworkStateHelper
import com.ctsi.vip.lib.framework.utils.cache.Cache
import com.ctsi.vip.lib.framework.utils.cache.CacheFactory
import com.ctsi.vip.lib.framework.utils.cache.CacheType
import com.ctsi.vip.lib.framework.widget.dialog.LoadingDialog
import com.ctsi.vip.lib.framework.widget.dialog.Status
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity(), IActivity {

    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: VB

    private var mCache: Cache<String, Any?>? = null
    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)

        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            createViewModel(type.actualTypeArguments[0])
            createViewDataBinding(type.actualTypeArguments[1])
        } else {
            throw IllegalArgumentException("Generic error")
        }

        mViewModel.mLoadingStatus.observe(this) { showLoadingDialog(it) }
        NetworkStateHelper.addObserver(this) { onNetworkStateChanged(it) }

        onActivityCreate(savedInstanceState)
    }

    override fun onDestroy() {
        NetworkStateHelper.removeObserver(this)
        super.onDestroy()
    }

    private fun createViewModel(type: Type) {
        val vmClass = type as Class<VM>
        mViewModel = ViewModelProvider(this).get(vmClass)
        lifecycle.addObserver(mViewModel)
    }

    private fun createViewDataBinding(type: Type) {
        val clazz = type as Class<*>
        when {
            //VM类型是ViewDataBinding的子类(不包含本身)
            ViewDataBinding::class.java.isAssignableFrom(clazz) && clazz != ViewDataBinding::class.java -> {
                require(getContentLayout() != 0) {
                    throw  IllegalArgumentException("Using DataBinding requires overriding method layoutId")
                }
                mBinding = DataBindingUtil.setContentView(this, getContentLayout())
                (mBinding as ViewDataBinding).lifecycleOwner = this
            }
            //VM类型是ViewBinding的子类(不包含本身) ps.单独使用ViewBinding需将gradle tools升级至3.6.1以上
            ViewBinding::class.java.isAssignableFrom(clazz) && clazz != ViewBinding::class.java -> {
                clazz.getDeclaredMethod("inflate", LayoutInflater::class.java).run {
                    @Suppress("UNCHECKED_CAST")
                    mBinding = invoke(null, layoutInflater) as VB
                    setContentView(mBinding.root)
                }
            }
            else -> if (getContentLayout() != 0) {
                setContentView(getContentLayout())
            }
        }
    }

    private fun showLoadingDialog(status: Status) {
        when (status) {
            is Status.Show -> {
                if (mLoadingDialog == null) {
                    mLoadingDialog = LoadingDialog.Builder(this).build()
                }
                status.msg?.let { mLoadingDialog?.setMessage(it) }
                mLoadingDialog?.show()
            }
            is Status.Dismiss -> mLoadingDialog?.dismiss()
        }
    }

    override fun provideCache(): Cache<String, Any?> {
        return mCache ?: CacheFactory.build<String, Any?>(CacheType.ACTIVITY_CACHE).also { mCache = it }
    }

    override fun useFragment(): Boolean = false

    override fun getContentLayout(): Int = 0

    override fun onNetworkStateChanged(state: NetState) {
        //网络状态发生改变
    }

    abstract override fun onActivityCreate(savedInstanceState: Bundle?)
}