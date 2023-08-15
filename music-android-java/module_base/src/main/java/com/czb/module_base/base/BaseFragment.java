package com.czb.module_base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.czb.module_base.R;
import com.czb.module_base.callback.EmptyCallback;
import com.czb.module_base.callback.ErrorCallback;
import com.czb.module_base.callback.LoadingCallback;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private State currentState = State.NONE;
//    private LoadService mLoadService;
        private View mLoadingView;
    private View mSuccessView;
    private View mErrorView;
    private View mEmptyView;

    public enum State{
        NONE,LOADING,SUCCESS,ERROR,EMPTY
    }

    private Unbinder mBind;
    private FrameLayout mBaseContainer;


    //    @OnClick(R.id.network_error_tips)
    public void retry(){
        //点击重新加载
//        LogUtils.d(this,"on retry..");
//        ToastUtils.showToast("刷新");
        onRetryClick();
    }

    /**
     *如果子fragment需要知道网络错误之后的点击，那么覆盖方法
     */
    protected void onRetryClick() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建view
        View rootView = loadRootView(inflater,container);
        mBaseContainer = rootView.findViewById(R.id.base_container);
        loadStaterView(inflater,container);
        mBind = ButterKnife.bind(this, rootView);
//        View rootView = View.inflate(getActivity(), getRootViewResId(), null);
//        mLoadService = LoadSir.getDefault().register(getActivity(), new Callback.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
//                // 重新加载逻辑
//            }
//        });
//        LoadSir loadSir = new LoadSir.Builder()
//                .addCallback(new ErrorCallback())//添加各种状态页
//                .addCallback(new EmptyCallback())
//                .addCallback(new LoadingCallback())
//                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
//                .setDefaultCallback(LoadingCallback.class)
//                .build();
//        mLoadService = loadSir.register(rootView, new Callback.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
////                loadService.showCallback(LoadingCallback.class);
//                // 重新加载逻辑
//            }
//        });

        initView(rootView);
        initListener();
        initPresenter();
        loadData();
        return rootView;
    }

    /*
        如果子类需要设置相关的事件就覆写
         */
    protected void initListener(){

    }

    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.modulebase_base_fragment_layout, container,false);
    }

    /**
     * 加载各种状态的view
     * @param inflater
     * @param container
     */
    private void loadStaterView(LayoutInflater inflater, ViewGroup container) {

        //成功的View
        mSuccessView = loadSuccessView(inflater, container);
        mBaseContainer.addView(mSuccessView);

        //Loading的View
        mLoadingView = loadLoadingView(inflater,container);
        mBaseContainer.addView(mLoadingView);

        //错误页面
        mErrorView = loadErrorView(inflater, container);
        LinearLayout error = mErrorView.findViewById(R.id.network_error_tips);
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry();
            }
        });
        mBaseContainer.addView(mErrorView);

        //内容为空的页面
        mEmptyView = loadEmptyView(inflater, container);
        mBaseContainer.addView(mEmptyView);

        setupState(State.NONE);
    }

    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.modulebase_base_fragment_base_error,container,false);
    }

    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.modulebase_base_fragment_base_empty,container,false);
    }

    /**
     * 子类通过这个方法来切换状态页面即可
     * @param state
     */
    public void setupState(State state){
        this.currentState=state;

        mSuccessView.setVisibility(currentState== State.SUCCESS?View.VISIBLE:View.GONE);
        mLoadingView.setVisibility(currentState== State.LOADING?View.VISIBLE:View.GONE);

        mErrorView.setVisibility(currentState == State.ERROR? View.VISIBLE: View.GONE);
        mEmptyView.setVisibility(currentState == State.EMPTY? View.VISIBLE: View.GONE);
    }

    /**
     * 加载loading
     * @param inflater
     * @param container
     * @return
     */
    private View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.modulebase_base_fragment_base_loading,container,false);
    }

    protected void initView(View rootView) {
//        mLoadService.showCallback(LoadingCallback.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
        }
        relese();
    }

    protected void relese() {
        //释放资源

    }

    protected void initPresenter() {
        //创建Presenter

    }

    protected void loadData() {
        //加载数据

    }

    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container){
        //得到id
        int resId = getRootViewResId();
        //返回view
        return inflater.inflate(resId, container, false);
    }

    protected abstract int getRootViewResId();
}
