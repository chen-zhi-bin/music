package com.chen.music.activity

import android.util.SparseArray
import android.view.MenuItem
import com.chen.module_base.mvvm.v.BaseActivity
import com.chen.music.MainViewModel
import com.chen.music.databinding.ActivityMainBinding
import com.chen.module_base.utils.EventBusRegister
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.chen.module_base.common.constant.RoutePath
import com.chen.module_base.common.constant.SpKey
import com.chen.module_base.common.event.StringEvent
import com.chen.module_base.common.service.community.wrap.CommunityServiceWrap
import com.chen.music.R
import com.chen.module_base.common.service.home.wrap.HomeServiceWrap
import com.chen.module_base.common.service.home.wrap.HomeServiceWrapJava
import com.chen.module_base.common.service.ucenter.wrap.UcenterServiceWrap
import com.chen.module_base.utils.LogUtils
import com.chen.module_base.utils.SpUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@EventBusRegister
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(){
    override val mViewModel by viewModels<MainViewModel>()

    private var mLastIndex: Int = -1
    private val mFragmentSparseArray = SparseArray<Fragment>()
    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    override fun ActivityMainBinding.initView() {
        switchFragment(HOME)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        mBinding.navView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    switchFragment(HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_community -> {
                    switchFragment(COMMUNITY)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_ucenter -> {
                    val isLogin = SpUtils.getBoolean(SpKey.IS_LOGIN, false)

                    if(isLogin == true){
                        switchFragment(UCENTER)
                        return@setOnNavigationItemSelectedListener true
                    }else{
                        ARouter.getInstance()
                            .build(RoutePath.Login.PAGE_LOGIN)
                            .withString(RoutePath.PATH, RoutePath.Ucenter.FRAGMENT_UCENTER)
                            .navigation()
//                        LogUtils.d("MainActivity","isLogin ==> "+isLogin)
//                        ARouter.getInstance().build(RoutePath.Login.PAGE_LOGIN).navigation()
                        return@setOnNavigationItemSelectedListener false
                    }
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commitAllowingStateLoss()
        mLastIndex = index
    }

    private fun getFragment(index: Int): Fragment {
        var fragment: Fragment? = mFragmentSparseArray.get(index)
        if (fragment == null) {
            when (index) {
                HOME -> fragment = HomeServiceWrap.instance.getFragment()
//                HOME -> fragment = HomeServiceWrapJava.Singletion.INSTANCE.holder.fragment
                COMMUNITY -> fragment = CommunityServiceWrap.instance.getFragment()
                UCENTER -> fragment = UcenterServiceWrap.instance.getFragment()
            }
            mFragmentSparseArray.put(index, fragment)
        }
        return fragment!!
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventSwitchTab(event: StringEvent){
        when (event.event) {
            StringEvent.Event.SWITCH_HOME -> mBinding.navView.selectedItemId = R.id.menu_home
            StringEvent.Event.SWITCH_UCENTER -> mBinding.navView.selectedItemId = R.id.menu_ucenter
        }
    }

    override fun initObserve() {
//        TODO("Not yet implemented")
    }

    override fun initRequestData() {
//        TODO("Not yet implemented")
    }


    companion object{
        const val HOME = 0
        const val COMMUNITY = 1
        const val UCENTER = 2
    }
}