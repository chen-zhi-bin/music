package com.czb.module_home.ui.fragment;

import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.bean.TitleMultiBean;
import com.czb.module_base.callback.EmptyCallback;
import com.czb.module_base.callback.ErrorCallback;
import com.czb.module_base.callback.LoadingCallback;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_home.R;
import com.czb.module_home.adapter.HomeAdapter;
import com.czb.module_home.adapter.HomeBannerAdapter;
import com.czb.module_home.callback.IHomeMainFragmentCallback;
import com.czb.module_home.model.bean.BannerBean;
import com.czb.module_home.model.bean.RecommendMusicBean;
import com.czb.module_home.model.bean.TopMusicBean;
import com.czb.module_home.presenter.IHomeMainFragmentPresenter;
import com.czb.module_home.utils.PresenterManager;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import kotlin.collections.CollectionsKt;

public class HomeMainFragment extends BaseFragment implements IHomeMainFragmentCallback {

    public static final String key = "HomeMainFragment";
    private HomeAdapter mAdapter;
    private RecyclerView mRvList;
//    private HomeListFragmentPresenterImpl mHomeListFragmentPresenter;
    private SmartRefreshLayout mRefreshLayout;

    private Banner mBanner;
    private IHomeMainFragmentPresenter mHomeMainFragmentPresenter;
    private List<MultiItemEntity> mTopMusicList = CollectionsKt.mutableListOf();
    private List<MultiItemEntity> mRecommendMusicList = CollectionsKt.mutableListOf();
    private int currentRecommendPage = 2;
    @Override
    protected int getRootViewResId() {
        return R.layout.home_main_fragment;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        setupState(State.SUCCESS);
        mRvList = rootView.findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(BaseApplication.getAppContext()));
        mAdapter = new HomeAdapter();
        mRvList.setAdapter(mAdapter);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        LayoutInflater from = LayoutInflater.from(BaseApplication.getAppContext());
        View inflate = from.inflate(R.layout.modulehome_home_banner_layout, null);
        mAdapter.addHeaderView(inflate);
        mBanner = inflate.findViewById(R.id.banner);

        StatusBarUtil.immersive(requireActivity());
        StatusBarUtil.darkMode(requireActivity(),true);
        StatusBarUtil.setPaddingSmart(requireContext(),mRefreshLayout);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> initData());
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mHomeMainFragmentPresenter.getRecommendMore();
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initPresenter() {
        mHomeMainFragmentPresenter = PresenterManager.getInstance().getHomeMainFragmentPresenter();
        mHomeMainFragmentPresenter.registerViewCallback(this);
        initData();
    }

    private void initData() {
        mHomeMainFragmentPresenter.getBanner();
        mHomeMainFragmentPresenter.getTopMusic(1);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public LifecycleTransformer<Object> TobindToLifecycle() {
        BehaviorSubject<Object> objectBehaviorSubject = BehaviorSubject.create();
        return  RxLifecycle.bind(objectBehaviorSubject);
    }

    @Override
    public void setRequestError(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void setBanner(BannerBean data) {
        LogUtils.d("test","banner == "+data.toString());
        if (data != null) {
            HomeBannerAdapter bannerAdapter = new HomeBannerAdapter(data.getData().getList());
            mBanner.setAdapter(bannerAdapter);
            mBanner.addBannerLifecycleObserver(this);
            //画廊效果
            mBanner.setBannerGalleryEffect(16,6,0.8f);

            mBanner.setIndicator(new CircleIndicator(requireContext()));
            mBanner.setIndicatorSelectedColor(
                    ContextCompat.getColor(
                            getContext(),
                            R.color.colorPrimary
                    )
            );
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    //点击事件，目前没有
                }
            });
        }
    }

    @Override
    public void setTopMusic(TopMusicBean data) {
        mTopMusicList.clear();
        mTopMusicList.add(new TitleMultiBean("顶置音乐"));
        List<TopMusicBean.DataBean.ListBean> topList = data.getData().getList();
        for (TopMusicBean.DataBean.ListBean listBean : topList) {
            mTopMusicList.add(listBean);
        }
        mAdapter.addData(mTopMusicList);
        mHomeMainFragmentPresenter.getRecommend(currentRecommendPage);
    }

    @Override
    public void setRecommendMusic(RecommendMusicBean data) {
        finishRefersh();
        mRecommendMusicList.clear();
        mRecommendMusicList.add(new TitleMultiBean("推荐音乐"));
        List<RecommendMusicBean.DataBean.ListBean> topList = data.getData().getList();
        for (RecommendMusicBean.DataBean.ListBean listBean : topList) {
            mRecommendMusicList.add(listBean);
        }
        mAdapter.addData(mRecommendMusicList);
    }

    private void finishRefersh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void setRecommendMusicMore(RecommendMusicBean data) {
        finishLoadMore();
        if (data.getData().getList()==null||data.getData().getList().size()==0){
            ToastUtils.showToast("暂无更多");
            return;
        }
        List<RecommendMusicBean.DataBean.ListBean> topList = data.getData().getList();
        for (RecommendMusicBean.DataBean.ListBean listBean : topList) {
            mRecommendMusicList.add(listBean);
        }
        mAdapter.addData(mRecommendMusicList);
    }

    private void finishLoadMore() {
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
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
}
