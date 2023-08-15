package com.czb.module_home.ui.fragment;

import android.view.View;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.callback.EmptyCallback;
import com.czb.module_base.callback.ErrorCallback;
import com.czb.module_base.callback.LoadingCallback;
import com.czb.module_home.R;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

public class HomeMainFragment extends BaseFragment {
    @Override
    protected int getRootViewResId() {
        return R.layout.home_main_fragment;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        setupState(State.SUCCESS);
    }
}
