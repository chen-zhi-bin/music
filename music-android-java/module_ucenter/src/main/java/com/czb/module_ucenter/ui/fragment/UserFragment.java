package com.czb.module_ucenter.ui.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.common.Constants;
import com.czb.module_base.common.service.ucenter.wrap.UcenterServiceWrap;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.SharedPreferencesUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_ucenter.R;
import com.czb.module_ucenter.callback.IUserFragmentCallback;
import com.czb.module_ucenter.model.bean.UserInfoBean;
import com.czb.module_ucenter.persenter.IUserFragmentPresenter;
import com.czb.module_ucenter.utils.PresenterManager;
import com.hjq.bar.TitleBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class UserFragment extends BaseFragment implements IUserFragmentCallback {

    private IUserFragmentPresenter mUserFragmentPresenter;
    private TitleBar mTitleBar;
    private TextView mNameTv;
    private TextView mUserMsgTv;
    private RoundedImageView mAvatarIv;
    private SuperTextView mSetTv;
    private SuperTextView mHistoryTv;
    private SuperTextView mCollectionTv;
    private SharedPreferencesUtils mSharedPreferencesUtils;
    private String mUserId = null;

    @Override
    protected int getRootViewResId() {
        return R.layout.ucenter_main_fragment;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        setupState(State.SUCCESS);
        mTitleBar = rootView.findViewById(R.id.user_titlebar);
        mTitleBar.setLeftIcon(null);

        mNameTv = rootView.findViewById(R.id.et_nickname);
        mUserMsgTv = rootView.findViewById(R.id.tv_header_mes);
        mAvatarIv = rootView.findViewById(R.id.iv_avatar);
        mHistoryTv = rootView.findViewById(R.id.tv_history);
        mCollectionTv = rootView.findViewById(R.id.tv_collection);
        mSetTv = rootView.findViewById(R.id.tv_set);
        setStatusBar();
    }

    private void setStatusBar() {
        StatusBarUtil.immersive(getActivity());
        StatusBarUtil.darkMode(getActivity(),true);
        StatusBarUtil.setPaddingSmart(getContext(),mTitleBar);
    }

    @Override
    protected void initPresenter() {
        mUserFragmentPresenter = PresenterManager.getInstance().getUserFragmentPresenter();
        mSharedPreferencesUtils = SharedPreferencesUtils.getInstance(getActivity());
        mUserId = mSharedPreferencesUtils.getString(SharedPreferencesUtils.USER_ID);
        mUserFragmentPresenter.registerViewCallback(this);
        mUserFragmentPresenter.getUserInfo();
    }

    @Override
    protected void initListener() {
        mSetTv.setOnClickListener(v -> UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchSetting());
        mHistoryTv.setOnClickListener(v -> UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchHistory());
        mCollectionTv.setOnClickListener(v -> {
            if (mUserId != null) {
                UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchCollection(mUserId);
            }
        });
    }

    @Override
    public void onError() {
        setupState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    @Override
    public void setNotLogin() {
        ToastUtils.showToast("尚未登录");
    }

    @Override
    public LifecycleTransformer<Object> TobindToLifecycle() {
        BehaviorSubject<Object> objectBehaviorSubject = BehaviorSubject.create();
        return RxLifecycle.bind(objectBehaviorSubject);
    }

    @Override
    public void setRequestError(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void setUserInfo(UserInfoBean data) {
        LogUtils.d("test",data.toString());
        UserInfoBean.DataBean.InfoBean info = data.getData().getInfo();
        if (info != null) {
            Glide.with(mAvatarIv.getContext())
                    .load(Constants.BASE_URL_IMAGE+info.getAvatar())
                    .placeholder(R.mipmap.ic_default_avatar)
                    .circleCrop()       //圆角
                    .into(mAvatarIv);
            mNameTv.setText(info.getUserName());
            mUserMsgTv.setText(info.getSign());
        }
    }
}
