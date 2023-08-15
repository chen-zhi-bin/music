package com.czb.module_ucenter.service;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.czb.module_base.RoutePath;
import com.czb.module_base.common.service.ucenter.IUcenterService;
import com.czb.module_ucenter.ui.fragment.UserFragment;


@Route(path = RoutePath.Ucenter.SERVICE_UCENTER)
public class UcenterServiceImpl implements IUcenterService {
    @Override
    public Fragment getFragment() {
        return new UserFragment();
    }

    @Override
    public void init(Context context) {

    }
}
