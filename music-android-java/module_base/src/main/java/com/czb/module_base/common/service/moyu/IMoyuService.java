package com.czb.module_base.common.service.moyu;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IMoyuService extends IProvider {
    Fragment getFragment();
}
