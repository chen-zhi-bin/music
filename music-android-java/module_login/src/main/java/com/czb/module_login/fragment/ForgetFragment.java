package com.czb.module_login.fragment;

import android.view.View;
import android.widget.Button;

import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_base.view.CodeEditView;
import com.czb.module_base.view.LoadingDialog;
import com.czb.module_base.view.LoginEditView;
import com.czb.module_login.R;
import com.czb.module_login.callback.IForgetCallback;
import com.czb.module_login.presenter.IForgetPresenter;
import com.czb.module_login.uitls.PresenterManager;


public class ForgetFragment extends BaseFragment implements CodeEditView.PhoneCodeListener, IForgetCallback {

    private IForgetPresenter mForgetPresenter;
    private CodeEditView mEtTuringCode;
    private LoginEditView mEtPhone;
    private CodeEditView mEtPhoneCode;
    private Button mBtnForget;
    private LoginEditView mEtNewPassword;
    private LoadingDialog mLoadingDialog;

    @Override
    protected int getRootViewResId() {
        return R.layout.login_fragment_forget;
    }

    @Override
    protected void initView(View rootView) {
        setupState(State.SUCCESS);
        mEtTuringCode = rootView.findViewById(R.id.edit_turing_code);
        mEtPhone = rootView.findViewById(R.id.edit_phone);
        mEtPhoneCode = rootView.findViewById(R.id.edit_phone_code);
        mBtnForget = rootView.findViewById(R.id.btn_forget);
        mEtNewPassword = rootView.findViewById(R.id.edit_password);
        mEtPhoneCode.setPhoneCodeListener(this);
        mLoadingDialog = new LoadingDialog(getContext());
//        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
//                .setMessage("请求中...")
//                .setCancelable(true)//返回键是否可点击
//                .setCancelOutside(false);//窗体外是否可点击
//        mLoadingDialog = builder.create();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToastUtils.showToast("dianji");
                if (mEtPhone.getValue().equals("")||mEtPhone.getValue()==null){
                    ToastUtils.showToast("请输入账号");
                    return;
                }
                if (mEtNewPassword.getValue().equals("")||mEtNewPassword.getValue()==null){
                    ToastUtils.showToast("请输入新密码");
                    return;
                }
                if (mEtPhoneCode.getValue().equals("")||mEtPhoneCode.getValue()==null){
                    ToastUtils.showToast("请输入验证码");
                    return;
                }
                mLoadingDialog.show();
//                mForgetPresenter.toForget(new UserVo(mEtPhone.getValue(),mEtNewPassword.getValue()),
//                        mEtPhoneCode.getValue());
            }
        });
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mForgetPresenter = PresenterManager.getInstance().getForgetPresenter();
        mForgetPresenter.registerViewCallback(this);
    }

    @Override
    public String sendMessage() {
        return null;
    }

    @Override
    public boolean isPreContented() {
        return false;
    }

    @Override
    public void getSms() {
        if (mEtTuringCode.getValue().equals("")||mEtTuringCode.getValue()==null){
            ToastUtils.showToast("请输入图灵验证码");
        }
        if (mEtPhone.getValue().equals("")||mEtPhone.getValue()==null){
            ToastUtils.showToast("请输入手机号");
            return;
        }
        mLoadingDialog.show();
//        mForgetPresenter.toSendSms(
//                new SendSmsVo(mEtPhone.getValue(),mEtTuringCode.getValue()),
//                mEtTuringCode.getKeyCode()
//        );
    }

//    @Override
//    public void setForgetSmsCode(BaseResponseBean data) {
//        mLoadingDialog.dismiss();
//        ToastUtils.showToast(data.getMessage());
//    }
//
//    @Override
//    public void setForget(BaseResponseBean data) {
//        mLoadingDialog.dismiss();
//        ToastUtils.showToast(data.getMessage());
//    }

    @Override
    protected void relese() {
        super.relese();
        mForgetPresenter.unregisterViewCallback(this);
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
}
