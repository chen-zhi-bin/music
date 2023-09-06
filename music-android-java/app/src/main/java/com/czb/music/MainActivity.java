package com.czb.music;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.callback.LoadingCallback;
import com.czb.module_base.common.Constants;
import com.czb.module_base.common.service.home.wrap.HomeServiceWrap;
import com.czb.module_base.common.service.moyu.wrap.MoyuServiceWrap;
import com.czb.module_base.common.service.ucenter.wrap.UcenterServiceWrap;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.SharedPreferencesUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.lzx.starrysky.StarrySky;

public class MainActivity extends BaseActivity {
    private FragmentManager mFm;
    private BaseFragment mHomeFragment =null;
    private BaseFragment mMoyuFragment =null;
    private BaseFragment mUserFragment = null;
    private boolean isLogin = false;            //是否登录
    private boolean isNeedtoFragmentUser=false;
    private SharedPreferencesUtils mSharedPreferencesUtils;
    private BaseFragment mFragment;
    private BottomNavigationView mNavigationView;

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initEvent() {
        initListener();
    }

    @Override
    protected void initView() {
//        mNavigationView = findViewById(R.id.main_navigation_bar);
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(BaseApplication.getAppContext());
        mNavigationView = this.findViewById(R.id.main_navigation_bar);
        initFragments();
    }




    private void initFragments() {
        mFm = getSupportFragmentManager();
        if (mHomeFragment == null){
            mHomeFragment = (BaseFragment) HomeServiceWrap.Singletion.INSTANCE.getHolder().getFragment();
        }
//        mRecommendFragment = new RecommendFragment();
        if (mMoyuFragment==null){
            mMoyuFragment = (BaseFragment) MoyuServiceWrap.Singletion.INSTANCE.getHolder().getFragment();
        }
        if (mUserFragment==null){
            mUserFragment = (BaseFragment) UcenterServiceWrap.Singletion.INSTANCE.getHolder().getFragment();
        }
//        mUserFragment = new UserFragment();
        //默认选中推荐，不然会出现空白页
        switchFragment(mHomeFragment);
    }



    private void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                isLogin = mSharedPreferencesUtils.contains(SharedPreferencesUtils.USER_TOKEN_COOKIE);
                LogUtils.d("test","isLogin ="+isLogin);
                LogUtils.d("test","test");
                if (item.getItemId()==R.id.home){
                    LogUtils.d(MainActivity.this,"切换到首页");
//                        mRecommendFragment = new RecommendFragment();
                    switchFragment(mHomeFragment);
                    return true;
                }else if (item.getItemId()==R.id.moyu){
                    LogUtils.d(MainActivity.this,"切换到社区");
                    switchFragment(mMoyuFragment);
                    return true;
                } else if (item.getItemId()==R.id.user){
                    LogUtils.d(MainActivity.this,"切换到个人");
                    if (isLogin){
//                        LogUtils.d("test",UcenterServiceWrap.Singletion.INSTANCE.getHolder().getFragment().toString()+"");
                        if (mUserFragment ==null){

                            mUserFragment = (BaseFragment) UcenterServiceWrap.Singletion.INSTANCE.getHolder().getFragment();
                        }
                        LogUtils.d("test","logined");
                        switchFragment(mUserFragment);
                        return true;
                    }else {
                        ARouter.getInstance()
                                .build(RoutePath.Login.PATH_lOGIN)
//                                .withString(RoutePath.PATH,RoutePath.Ucenter.FRAGMENT_UCENTER)
                                .navigation();
                        LogUtils.d("test","to login");
                        isNeedtoFragmentUser=true;
                        String token = mSharedPreferencesUtils.getString(SharedPreferencesUtils.USER_TOKEN_COOKIE, "");
                        LogUtils.d("test","logined");
                        switchFragment(lastOneFragment);
                        return false;
                    }
                }
                return true;
            }
        });

    }

    /**
     * 上一次显示的Fragment
     */
    private BaseFragment lastOneFragment = mHomeFragment;

    private void switchFragment(BaseFragment targetFragment) {
        //如果上一个Fragment和当前要切换的fragment是同一个，那么不需要切换
        if (lastOneFragment == targetFragment){
            return;
        }
        //修改成add和hide的方式来控制Fragment
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        //如果没有添加就添加
        if (!targetFragment.isAdded()) {
            fragmentTransaction.add(R.id.main_page_container,targetFragment);
        }else {
            fragmentTransaction.show(targetFragment);
        }
        if (lastOneFragment!=null){
            fragmentTransaction.hide(lastOneFragment);
        }
        fragmentTransaction.commit();
        lastOneFragment = targetFragment;

//        //开启事务
//        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
//        fragmentTransaction.replace(R.id.main_page_container,targetFragment);
//        //提交事务
//        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        LogUtils.d("onActivityResult","requestCode ="+requestCode+",resultCode="+resultCode+",data = "+data.toString());
        if (resultCode == Constants.RETURN_TO_HMOE){
            switchFragment(mHomeFragment);
            mNavigationView.setSelectedItemId(mNavigationView.getMenu().getItem(Constants.NAVIGATION_VIEW_MENU_RECOMMEND_ITEM_ID_INDEX).getItemId());
        }else if (resultCode==Constants.RETURN_TO_USER){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedtoFragmentUser) {
            isLogin = mSharedPreferencesUtils.contains(SharedPreferencesUtils.USER_TOKEN_COOKIE);
            if (isLogin) {
                if (mUserFragment == null){
                    mUserFragment = (BaseFragment) UcenterServiceWrap.Singletion.INSTANCE.getHolder().getFragment();
                }
                switchFragment(mUserFragment);
                mNavigationView.setSelectedItemId(mNavigationView.getMenu().getItem(Constants.NAVIGATION_VIEW_MENU_USER_ITEM_ID_INDEX).getItemId());
            }else {
                switchFragment(mHomeFragment);
                mNavigationView.setSelectedItemId(mNavigationView.getMenu().getItem(0).getItemId());
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    /**
     * 返回键的事件
     */
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode== KeyEvent.KEYCODE_HOME){
            return true;
        } else if( keyCode== KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()- exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                StarrySky.closeNotification();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}