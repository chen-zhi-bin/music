package com.czb.module_search.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.czb.module_base.base.BaseFragment;
import com.czb.module_base.common.Constants;
import com.czb.module_base.utils.EventBusUtils;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.StatusBarUtil;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_search.R;
import com.czb.module_search.adapter.SearchListAdapter;
import com.czb.module_search.callback.ISearchListFragmentCallback;
import com.czb.module_search.model.bean.MusicInfoByLyricsBean;
import com.czb.module_search.model.bean.MusicInfoByNameBean;
import com.czb.module_search.presenter.ISearchListFragmentPresenter;
import com.czb.module_search.utils.PresenterManager;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.PlayerControl;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class SearchListFragment extends BaseFragment implements ISearchListFragmentCallback {
    String regExPre = "<span\\s*[^>]*>"; //去掉<span></span>
    String regExOff = "</span>";
    private String mType;
    private String mKeyword = "";
    private ISearchListFragmentPresenter mSearchListFragmentPresenter;
    private RecyclerView mRvList;
    private SmartRefreshLayout mRefreshLayout;
    private SearchListAdapter mAdapter;
    private PlayerControl mControl;

    @Override
    protected int getRootViewResId() {
        return R.layout.search_fragment_list;
    }

    @Override
    protected void initPresenter() {
        mSearchListFragmentPresenter = PresenterManager.getInstance().getSearchListFragmentPresenter();
        mSearchListFragmentPresenter.registerViewCallback(this);
    }

    @Subscribe
    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        setupState(State.SUCCESS);
        mControl = StarrySky.with();
        if (!EventBusUtils.INSTANCE.isRegistered(this)) {
            EventBusUtils.INSTANCE.register(this);
        }
        mType = getArguments().getString(Constants.SearchType.SEARCH_TYPE);
        mKeyword = getArguments().getString("keyword");
        LogUtils.d("test", "type = " + mType);
        mAdapter = new SearchListAdapter();
        mRvList = rootView.findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvList.setAdapter(mAdapter);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        initStatusBar();
    }

    private void initStatusBar() {
        StatusBarUtil.immersive(requireActivity());
        StatusBarUtil.darkMode(requireActivity(), true);
        StatusBarUtil.setPaddingSmart(requireContext(), mRefreshLayout);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRefreshLayout.setOnRefreshListener(refreshLayout -> search());
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> loadMore());
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Object item = adapter.getItem(position);
                int id = view.getId();
                SongInfo songInfo = new SongInfo();
                if (id == R.id.search_layout_item) {
                    MusicInfoByNameBean.DataBean.ListBean searchByNameItem = (MusicInfoByNameBean.DataBean.ListBean) item;
                    LogUtils.d("Test",searchByNameItem.getName());
                    songInfo.setSongUrl(Constants.BASE_URL_MUSIC + searchByNameItem.getUrl());
                    String singerName = searchByNameItem.getSingerName();
                    if (singerName == null || singerName.equals("") || singerName.equals("null")) {
                        singerName = "未知歌手";
                    }
                    songInfo.setArtist(singerName);
                    songInfo.setSongId(searchByNameItem.getId());
                    String name = searchByNameItem.getName();
                    Pattern compile = Pattern.compile(regExPre, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = compile.matcher(name);
                    name = matcher.replaceAll("");
//                    name = name.replace(regExPre,"");
                    name = name.replace(regExOff, "");
                    songInfo.setSongName(name);
                    String picId = searchByNameItem.getPicId();
                    if (picId == null || picId.equals("") || picId.equals("null")) {

                    } else {
                        songInfo.setSongCover(Constants.BASE_URL_IMAGE + picId);
                    }
                    songInfo.setDuration(Long.parseLong(searchByNameItem.getDuration()));
                    mControl.addSongInfo(songInfo);
                    mControl.playMusicByInfo(songInfo);
                } else if (id == R.id.search_lyric_layout_item) {
                    MusicInfoByLyricsBean.DataBean.ListBean searchByLyricItem = (MusicInfoByLyricsBean.DataBean.ListBean) item;
                    LogUtils.d("Test",searchByLyricItem.getName());
                    String singerName = searchByLyricItem.getSingerName();
                    if (singerName == null || singerName.equals("") || singerName.equals("null")) {
                        singerName = "未知歌手";
                    }
                    songInfo.setSongUrl(Constants.BASE_URL_MUSIC + searchByLyricItem.getUrl());
                    songInfo.setArtist(singerName);
                    songInfo.setSongId(searchByLyricItem.getId());
                    songInfo.setSongName(searchByLyricItem.getName());
                    songInfo.setDuration(Long.parseLong(searchByLyricItem.getDuration()));
                    String picId = searchByLyricItem.getPicId();
                    if (picId == null || picId.equals("") || picId.equals("null")) {

                    } else {
                        songInfo.setSongCover(Constants.BASE_URL_IMAGE + picId);
                    }
                    mControl.addSongInfo(songInfo);
                    mControl.playMusicByInfo(songInfo);
                }
            }
        });
    }

    /**
     * SearchActivity 输入框改变是通知这
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshSearch(String s) {
        if (s != null && !s.equals("")) {
            mKeyword = s;
            search();
        }
    }

    private void search() {
        mRefreshLayout.setEnableLoadMore(true);
        setupState(State.LOADING);
        mSearchListFragmentPresenter.getSearchList(mKeyword, mType);
    }

    private void loadMore() {
        mSearchListFragmentPresenter.getSearchListMore(mKeyword, mType);
    }

    @Override
    public void onDestroy() {
        if (EventBusUtils.INSTANCE.isRegistered(this)) {
            EventBusUtils.INSTANCE.unRegister(this);
        }
        super.onDestroy();
    }

    @Override
    public String getKey() {
        return mType;
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
    public void setMusicByNameData(MusicInfoByNameBean data) {
        setupState(State.SUCCESS);
        finishRefresh();
        List<MusicInfoByNameBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null && list.size() > 0) {
            mAdapter.getData().clear();
            mAdapter.addData(list);
        } else {
            onEmpty();
        }
    }

    @Override
    public void setMusicByNameDataMore(MusicInfoByNameBean data) {
        finishLoadMore();
        List<MusicInfoByNameBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null && list.size() > 0) {
            mAdapter.addData(list);
        } else {
            ToastUtils.showToast("暂无更多");
            mRefreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void setMusicByLyricData(MusicInfoByLyricsBean data) {
        setupState(State.SUCCESS);
        finishRefresh();
        List<MusicInfoByLyricsBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null && list.size() > 0) {
            mAdapter.getData().clear();
            mAdapter.addData(list);
        } else {
           onEmpty();
        }
    }

    @Override
    public void setMusicByLyricDataMore(MusicInfoByLyricsBean data) {
        finishLoadMore();
        List<MusicInfoByLyricsBean.DataBean.ListBean> list = data.getData().getList();
        if (list != null && list.size() > 0) {
            mAdapter.addData(list);
        } else {
            ToastUtils.showToast("暂无更多");
            mRefreshLayout.setEnableLoadMore(false);
        }
    }

    private void finishRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
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
