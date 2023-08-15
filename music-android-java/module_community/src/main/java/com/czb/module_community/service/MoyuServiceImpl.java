package com.czb.module_community.service;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.czb.module_base.RoutePath;
import com.czb.module_base.common.service.moyu.IMoyuService;
import com.czb.module_community.ui.fragment.MoyuMainFragment;


@Route(path = RoutePath.Moyu.SERVICE_MOYU)
public class MoyuServiceImpl implements IMoyuService {

    @Override
    public Fragment getFragment() {
        return new MoyuMainFragment();
    }

    @Override
    public void init(Context context) {

    }
}
