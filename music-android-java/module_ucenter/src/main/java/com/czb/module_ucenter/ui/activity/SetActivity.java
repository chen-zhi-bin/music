package com.czb.module_ucenter.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.czb.module_base.RoutePath;
import com.czb.module_base.base.BaseActivity;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_ucenter.R;
import com.czb.module_ucenter.callback.ISetActivityCallback;
import com.czb.module_ucenter.model.bean.LogoutBean;
import com.czb.module_ucenter.persenter.ISetActivityPresenter;
import com.czb.module_ucenter.utils.PresenterManager;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Route(path = RoutePath.Ucenter.PAGE_USER_SET)
public class SetActivity extends BaseActivity implements ISetActivityCallback {

    private ISetActivityPresenter mSetActivityPresenter;
    private TextView mLogoutTv;

    @Override
    protected void initPresenter() {
        mSetActivityPresenter = PresenterManager.getInstance().getSetActivityPresenter();
        mSetActivityPresenter.registerViewCallback(this);
    }

    @Override
    protected void initEvent() {
        mLogoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetActivityPresenter.doLogout();
            }
        });
    }

    @Override
    protected void initView() {
        mLogoutTv = this.findViewById(R.id.logout);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.ucenter_activity_set;
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    public LifecycleTransformer<Object> TobindToLifecycle() {
        BehaviorSubject<Object> objectBehaviorSubject = BehaviorSubject.create();
        return RxLifecycle.bind(objectBehaviorSubject);
    }

    @Override
    public void setReturnMessage(LogoutBean data) {
        if (data.getCode()== Constants.SUCCESS) {
            ToastUtils.showToast(data.getMessage());
        }else {
            ToastUtils.showToast("退出登录");
        }
        finish();
    }
}