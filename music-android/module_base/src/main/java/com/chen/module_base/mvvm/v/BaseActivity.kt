package com.chen.module_base.mvvm.v

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.chen.module_base.callback.EmptyCallback
import com.chen.module_base.callback.ErrorCallback
import com.chen.module_base.callback.PlaceHolderCallback
import com.chen.module_base.mvvm.vm.BaseViewModel
import com.chen.module_base.net.RetrofitFactory
import com.chen.module_base.net.State
import com.chen.module_base.net.StateType
import com.chen.module_base.utils.*
import com.chen.module_base.utils.network.AutoRegisterNetListener
import com.chen.module_base.utils.network.NetworkTypeEnum
import com.chen.module_base.view.LoadingDialog
import com.hjq.toast.ToastUtils
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.chen.module_base.utils.EventBusRegister
import com.zwb.lib_base.utils.EventBusUtils

abstract class BaseActivity<VB: ViewBinding,VM: BaseViewModel>:AppCompatActivity() ,
        FrameView<VB>, NetworkStateChangeListener {

    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }

    private lateinit var mLoadingDialog: LoadingDialog
    protected abstract val mViewModel: VM

    private var loadMap: HashMap<String, LoadService<*>> = HashMap()

    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> {
                        showSuccess(it.urlKey)
                        innerDismissLoading(it.message)
                    }
                    StateType.ERROR -> {
                        showError(it.urlKey)
                        innerDismissLoading(it.message)
                    }
                    StateType.NETWORK_ERROR -> {
                        showError(it.urlKey)
                        innerDismissLoading(it.message)
                    }
                    StateType.EMPTY -> showEmpty(it.urlKey)
                    else -> showSuccess(it.urlKey)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(mBinding.root)
        ARouter.getInstance().inject(this)
        mLoadingDialog = LoadingDialog(this, false)

        mViewModel.loadState.observe(this, observer)

        // 注册EventBus
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)

        setStatusBar()
        mBinding.initView()
        initNetworkListener()
        initObserve()
        initRequestData()
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "ActivityStack: ${ActivityStackManager.activityStack}")
    }

    /**
     * 初始化网络状态监听
     * @return Unit
     */
    private fun initNetworkListener() {
        lifecycle.addObserver(AutoRegisterNetListener(this))
    }

    /**
     * 设置状态栏
     * 子类需要自定义时重写该方法即可
     * @return Unit
     */
    open fun setStatusBar() {
        StatusBarUtil.immersive(this)
        StatusBarUtil.darkMode(this,true)
    }

    /**
     * 网络类型更改回调
     * @param type Int 网络类型
     * @return Unit
     */
    override fun networkTypeChange(type: NetworkTypeEnum) {}


    /**
     * 网络连接状态更改回调
     * @param isConnected Boolean 是否已连接
     * @return Unit
     */
    override fun networkConnectChange(isConnected: Boolean) {
        Toast.makeText(this,if (isConnected) "网络已连接" else "网络已断开", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.unRegister(
            this
        )
        super.onDestroy()
        // 解决某些特定机型会触发的Android本身的Bug
        AndroidBugFixUtils().fixSoftInputLeaks(this)
    }

    fun setPlaceHolderLoad(view: View, resId:Int, key: String) {
        if(loadMap[key] == null){
            val loadSir = LoadSir.Builder()
                .addCallback(PlaceHolderCallback(resId))
                .addCallback(EmptyCallback())
                .addCallback(ErrorCallback())
                .setDefaultCallback(PlaceHolderCallback::class.java)
                .build()
            val loadService = loadSir.register(view) {
                initRequestData()
            }
            loadMap[key] = loadService
        }
    }
    fun setDefaultLoad(view: View, key: String) {
        if(loadMap[key] == null){
            val loadService = LoadSir.getDefault().register(view) {
                initRequestData()
            }
            loadMap[key] = loadService!!
        }
    }




    /**
     * 是否登录拦截 根据具体的业务判断
     */
    fun isLoginIntercept(toLogin: Boolean): Boolean{
        val sobToken = SpUtils.getString(RetrofitFactory.USER_TOKEN,"")
        // 已经登录
        if(!TextUtils.isEmpty(sobToken)){
            return true
        }
        if(toLogin){
            ARouter.getInstance()
                .build("/login/LoginActivity")
                .navigation()
        }
        return false
    }

    private fun showSuccess(key: String) {
        loadMap.remove(key)?.showCallback(SuccessCallback::class.java)
    }

    private fun showEmpty(key: String) {
        loadMap.remove(key)?.showCallback(EmptyCallback::class.java)
    }

    private fun showError(key: String) {
        loadMap.remove(key)?.showCallback(ErrorCallback::class.java)
    }

    private fun innerDismissLoading(msg:String) {
        Handler().postDelayed({
            dismissLoading()
        },1000)
        if(!TextUtils.isEmpty(msg)) toast(msg)
    }

    /**
     * show 加载中
     */
    fun showLoading() {
        mLoadingDialog.showDialog(this, false)
    }

    /**
     * dismiss loading dialog
     *
     */
    fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }

    fun toast(msg: String){
        ToastUtils.show(msg)
    }
}