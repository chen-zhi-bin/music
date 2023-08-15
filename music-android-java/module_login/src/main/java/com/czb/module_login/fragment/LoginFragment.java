package com.czb.module_login.fragment;

import android.view.View;
import android.widget.Button;

import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_base.view.CodeEditView;
import com.czb.module_base.view.LoadingDialog;
import com.czb.module_base.view.LoginEditView;
import com.czb.module_login.R;
import com.czb.module_login.callback.ILoginCallback;
import com.czb.module_login.model.bean.User;
import com.czb.module_login.presenter.ILoginPresenter;
import com.czb.module_login.uitls.PresenterManager;


public class LoginFragment extends BaseFragment implements ILoginCallback {


    private LoginEditView mEditPhone;
    private LoginEditView mEditPsw;
    private CodeEditView mEditCode;
    private Button mBtnLogin;
    private ILoginPresenter mLoginPresenter;
    private LoginFragmentListener loginFragmentListener=null;
    private LoadingDialog mLoadingDialog;

    @Override
    protected int getRootViewResId() {
        return R.layout.login_fragment_login;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        setupState(State.SUCCESS);
        mEditPhone = rootView.findViewById(R.id.edit_phone);
        mEditPsw = rootView.findViewById(R.id.edit_password);
        mEditCode = rootView.findViewById(R.id.edit_turing_code);
        mBtnLogin = rootView.findViewById(R.id.btn_login);
        mLoadingDialog = new LoadingDialog(getContext());
    }

    @Override
    protected void initPresenter() {
        mLoginPresenter = PresenterManager.getInstance().getLoginPresenter();
        mLoginPresenter.registerViewCallback(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = mEditCode.getKeyCode();
                String code = mEditCode.getValue();
                String phone = mEditPhone.getValue();
                String psw = mEditPsw.getValue();
                if (psw.isEmpty()){
                    ToastUtils.showToast("请输入密码");
                    return;
                }else if (code.isEmpty()){
                    ToastUtils.showToast("请输入验证码");
                    return;
                }

                mLoadingDialog.showDialog(getContext());
                User user = new User(mEditPhone.getValue().toString(),mEditPsw.getValue().toString());
                mLoginPresenter.getLogin(code,user,mEditCode.getKey());
//                mLoginPresenter.getLogin(code,user,key);
            }
        });

    }

    @Override
    protected void relese() {
        super.relese();
        mLoginPresenter.unregisterViewCallback(this);
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

    public void setLoginFragmentListener(LoginFragmentListener loginFragmentListener){
        this.loginFragmentListener = loginFragmentListener;
    }

    @Override
    public void onLoginError() {
        mLoadingDialog.dismissDialog();
        ToastUtils.showToast("网络错误");
    }

    @Override
    public void onLoginSuccess() {
        mLoadingDialog.dismissDialog();
        loginFragmentListener.onCallbackBack();
    }

    public interface LoginFragmentListener{
        void onCallbackBack();
    }
}
