package com.czb.module_base.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.czb.module_base.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mBind = ButterKnife.bind(this);
        initView();
        initStatusBar();
        initEvent();
        initPresenter();
    }

    protected void initStatusBar(){
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
    }

    protected abstract void initPresenter();

    protected abstract void initEvent();

    protected abstract void initView();

    public abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        this.relese();
    }

    /**
     * 释放资源
     */
    protected void relese() {

    }


}
