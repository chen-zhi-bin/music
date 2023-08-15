package com.czb.module_login.fragment;

import android.view.View;
import android.widget.Button;

import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_base.view.CodeEditView;
import com.czb.module_base.view.LoadingDialog;
import com.czb.module_base.view.LoginEditView;
import com.czb.module_login.R;
import com.czb.module_login.callback.IRegisterCallback;
import com.czb.module_login.presenter.IRegisterPresenter;
import com.czb.module_login.uitls.PresenterManager;


public class RegisterFragment extends BaseFragment implements CodeEditView.PhoneCodeListener, IRegisterCallback {

    private CodeEditView mEditTuringCode;
    private LoginEditView mEditPhone;
    private CodeEditView mEditPhoneCode;
    private LoginEditView mEditNickName;
    private LoginEditView mEditPsw;
    private Button mBtnRegister;
    private IRegisterPresenter mRegisterPresenter;
    private LoadingDialog mLoadingDialog;


    @Override
    protected int getRootViewResId() {
        return R.layout.moudlelogin_fragment_register;
    }

    @Override
    protected void initView(View rootView) {
        setupState(State.SUCCESS);
        mEditTuringCode = rootView.findViewById(R.id.edit_turing_code);
        mEditPhone = rootView.findViewById(R.id.edit_phone);
        mEditPhoneCode = rootView.findViewById(R.id.edit_phone_code);
        mEditNickName = rootView.findViewById(R.id.edit_nickname);
        mEditPsw = rootView.findViewById(R.id.edit_password);
        mBtnRegister = rootView.findViewById(R.id.btn_register);

//        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
//                .setMessage("请求中...")
//                .setCancelable(true)//返回键是否可点击
//                .setCancelOutside(false);//窗体外是否可点击
        mLoadingDialog = new LoadingDialog(BaseApplication.getAppContext());

        mEditPhoneCode.setPhoneCodeListener(this);
    }

    @Override
    protected void initListener() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditPhone.getValue().equals("")||mEditPhone.getValue()==null){
                    ToastUtils.showToast("请输入邮箱号");
                    return;
                }
                if (mEditNickName.getValue().equals("")||mEditNickName.getValue()==null){
                    ToastUtils.showToast("请输入名字");
                    return;
                }
                if (mEditPsw.getValue().equals("")||mEditPsw.getValue()==null){
                    ToastUtils.showToast("请输入密码");
                    return;
                }

                mLoadingDialog.show();
//                mRegisterPresenter.postRegister(new UserR(mEditPhone.getValue(),mEditPsw.getValue(),mEditNickName.getValue()),mEditPhoneCode.getValue());
            }
        });
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mRegisterPresenter = PresenterManager.getInstance().getRegisterPresenter();
        mRegisterPresenter.registerViewCallback(this);
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
        if (mEditPhone.getValue().equals("")||mEditPhone.getValue()==null){
            ToastUtils.showToast("请输入手机号");
            return;
        }
        if (mEditTuringCode.getValue().equals("")||mEditTuringCode.getValue()==null){
            ToastUtils.showToast("请输入图灵验证码");
            return;
        }
        mLoadingDialog.show();
        String keyCode = mEditTuringCode.getKeyCode();
//        mRegisterPresenter.getSmsCode(new SendSmsVo(mEditPhone.getValue(),mEditTuringCode.getValue()),keyCode);

    }

//    @Override
//    public void setSmsCode(BaseResponseBean data) {
//        mLoadingDialog.dismiss();
//        ToastUtils.showToast(data.getMessage());
//    }
//
//    @Override
//    public void setRegister(BaseResponseBean data) {
//        mLoadingDialog.dismiss();
////        if (!data.getSuccess()){
//            ToastUtils.showToast("身份验证失败");
////        }
//    }

    @Override
    protected void relese() {
        super.relese();
        mRegisterPresenter.unregisterViewCallback(this);
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
