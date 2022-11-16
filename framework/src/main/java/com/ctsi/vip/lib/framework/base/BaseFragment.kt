package com.ctsi.vip.lib.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ctsi.vip.lib.framework.base.delegate.IFragment
import com.ctsi.vip.lib.framework.utils.cache.Cache
import com.ctsi.vip.lib.framework.utils.cache.CacheFactory
import com.ctsi.vip.lib.framework.utils.cache.CacheType
import com.ctsi.vip.lib.framework.widget.dialog.LoadingDialog
import com.ctsi.vip.lib.framework.widget.dialog.Status
import java.lang.reflect.ParameterizedType

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment(), IFragment {

    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: VB

    protected var isFirstVisible = true
    protected var isCurrentVisible = false
    private val isParentVisible: Boolean
        get() {
            val parent = parentFragment
            return if (parent is BaseFragment<*, *>) {
                parent.isCurrentVisible
            } else {
                true
            }
        }

    private var mCache: Cache<String, Any?>? = null
    private var mLoadingDialog: LoadingDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createViewDataBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()

        if (isCreateIndependentViewModel()) {
            mViewModel.mLoadingStatus.observe(viewLifecycleOwner) { showLoadingDialog(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isHidden && !isCurrentVisible) {
            dispatchUserVisibleHint(true)
        }
    }

    override fun onPause() {
        super.onPause()
        if (isCurrentVisible) {
            dispatchUserVisibleHint(false)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            dispatchUserVisibleHint(false)
        } else {
            dispatchUserVisibleHint(true)
        }
    }

    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val argument = type.actualTypeArguments[0]
            val vmClass = argument as Class<VM>
            //使用activity作为owner，保证fragment获取的ViewModel和activity是同一个，便于两者之间进行通信
            mViewModel = ViewModelProvider(if (isCreateIndependentViewModel()) this else requireActivity())
                .get(vmClass)
            lifecycle.addObserver(mViewModel)
        } else {
            throw IllegalArgumentException("Generic error")
        }
    }

    private fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        val generic = javaClass.genericSuperclass
        if (generic is ParameterizedType) {
            val clazz = generic.actualTypeArguments[1] as Class<*>
            return when {
                ViewDataBinding::class.java.isAssignableFrom(clazz) && clazz != ViewDataBinding::class.java -> {
                    require(getContentLayout() != 0) {
                        throw  IllegalArgumentException("Using DataBinding requires overriding method layoutId")
                    }
                    mBinding =
                        DataBindingUtil.inflate(inflater, getContentLayout(), container, false)
                    (mBinding as ViewDataBinding).lifecycleOwner = this
                    mBinding.root
                }
                ViewBinding::class.java.isAssignableFrom(clazz) && clazz != ViewBinding::class.java -> {
                    clazz.getDeclaredMethod("inflate", LayoutInflater::class.java).run {
                        @Suppress("UNCHECKED_CAST")
                        mBinding = invoke(null, inflater) as VB
                        mBinding.root
                    }
                }
                else -> {
                    require(getContentLayout() != 0) {
                        throw  IllegalArgumentException("Using DataBinding requires overriding method layoutId")
                    }
                    inflater.inflate(getContentLayout(), container, false)
                }
            }
        } else {
            throw IllegalArgumentException("Generic error")
        }
    }

    private fun showLoadingDialog(status: Status) {
        when (status) {
            is Status.Show -> {
                if (mLoadingDialog == null) {
                    mLoadingDialog = LoadingDialog.Builder(requireContext()).build()
                }
                status.msg?.let { mLoadingDialog?.setMessage(it) }
                mLoadingDialog?.show()
            }
            is Status.Dismiss -> mLoadingDialog?.dismiss()
        }
    }

    private fun dispatchUserVisibleHint(visible: Boolean) {
        if (visible && !isParentVisible) {
            return
        }
        if (isCurrentVisible == visible) {
            return
        }
        isCurrentVisible = visible
        if (visible) {
            if (view == null) return
            if (isFirstVisible) {
                onFragmentCreate()
                isFirstVisible = false
            }
            onFragmentResume()
        } else {
            if (!isFirstVisible) {
                onFragmentPause()
            }
        }
    }

    /* 懒加载首次可见 */
    abstract override fun onFragmentCreate()

    //resume
    override fun onFragmentResume() {

    }

    //pause
    override fun onFragmentPause() {

    }

    override fun getContentLayout(): Int = 0

    override fun provideCache(): Cache<String, Any?> {
        return mCache ?: CacheFactory.build<String, Any?>(CacheType.FRAGMENT_CACHE).also { mCache = it }
    }

    /**
     * 是否创建独立的ViewModel
     * 返回true则创建owner为此fragment的ViewModel，否则与activity共用
     *
     * @param boolean
     */
    override fun isCreateIndependentViewModel(): Boolean = false
}