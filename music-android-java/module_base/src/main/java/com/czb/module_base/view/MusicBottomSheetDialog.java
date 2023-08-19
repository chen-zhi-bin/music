package com.czb.module_base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.czb.module_base.R;
import com.czb.module_base.adapter.SongInfoAdapter;
import com.czb.module_base.base.BaseApplication;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.ToastUtils;
import com.czb.module_base.utils.UIUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lzx.starrysky.SongInfo;
import com.lzx.starrysky.StarrySky;

import java.util.List;

public class MusicBottomSheetDialog extends BottomSheetDialog {

    private final Activity activity;
    private int lastTimekeyboardHeight = 0;
    private int mKeyBoardHeight = 0;

    private View rootView = null;
    private final View mDialogBinding;
    private ConstraintLayout mRootView;
    private TextView mTvTitle;
    private RecyclerView mMusicListRv;
    private SongInfoAdapter mSongInfoAdapter;

    private View lastMusicView= null;

    public MusicBottomSheetDialog(@NonNull Activity context, int theme) {
        super(context, theme);
        this.activity = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mDialogBinding = inflater.inflate(R.layout.modulebase_common_dialog_music_list, null);
    }

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (mRootView != null) {
                handleOnGlobalLayout();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            if (window.getDecorView() != null) {
                window.getDecorView().setPadding(0,0,0,0);
            }
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.BOTTOM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            setContentView(mDialogBinding);
            mRootView = mDialogBinding.findViewById(R.id.layout_reply);
            mTvTitle = mDialogBinding.findViewById(R.id.tv_title);
            mTvTitle.setText("音乐列表");
            mMusicListRv = mDialogBinding.findViewById(R.id.dialog_music_list);
            mSongInfoAdapter = new SongInfoAdapter();
            mMusicListRv.setLayoutManager(new LinearLayoutManager(activity));
            mMusicListRv.setAdapter(mSongInfoAdapter);

            List<SongInfo> playList = StarrySky.with().getPlayList();
            mSongInfoAdapter.getData().clear();
            mSongInfoAdapter.addData(playList);
            mSongInfoAdapter.notifyDataSetChanged();


            mSongInfoAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                    SongInfo songInfo = (SongInfo)adapter.getData().get(position);
                    StarrySky.with().playMusicByInfo(songInfo);
                   if (lastMusicView!=null){
                       lastMusicView.setBackground(ContextCompat.getDrawable(BaseApplication.getAppContext(),R.drawable.shape_linearlayout_white));
                       mSongInfoAdapter.notifyDataSetChanged();
                   }else {
                       mSongInfoAdapter.notifyDataSetChanged();
                   }
                    view.setBackground(ContextCompat.getDrawable(BaseApplication.getAppContext(),R.drawable.shape_linearlayout_blue));
                    lastMusicView = view;
                }
            });
        }
    }


    @Override
    public void show() {
        super.show();
        if (mRootView != null) {
            if (mRootView.getViewTreeObserver() != null) {
                mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
            }
        }
        refreshData();
    }

    public void refreshData(){
        List<SongInfo> playList = StarrySky.with().getPlayList();
        mSongInfoAdapter.getData().clear();
        mSongInfoAdapter.addData(playList);
        mSongInfoAdapter.notifyDataSetChanged();

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void handleOnGlobalLayout(){
        Point screenSize = new Point();
        Window window = getWindow();
        if (window != null) {
            window.getWindowManager().getDefaultDisplay().getSize(screenSize);
        }
        Rect rect = new Rect();
        if (mRootView != null) {
            mRootView.getWindowVisibleDisplayFrame(rect);
        }

        // 可以用手机的全屏尺寸来改变这一点
        // 并且还使用手机的状态栏和导航条高度来计算

        int orientation = getScreenOrientation();
        int keyboardHeight = screenSize.y - rect.bottom;
        if (lastTimekeyboardHeight == keyboardHeight){
            return;
        }else {
            lastTimekeyboardHeight = keyboardHeight;
        }
//        if (keyboardHeight==0){
//            notifyKeyboardHeightChanged(0,orientation);
//        }else if (orientation == Configuration.ORIENTATION_PORTRAIT){
//            notifyKeyboardHeightChanged(keyboardHeight,orientation);
//        }else {
//            notifyKeyboardHeightChanged(keyboardHeight,orientation);
//        }
    }



    public void setRootPaddingBottom(int bottom){
        if (mRootView != null) {
            mRootView.setPadding(
                    UIUtils.dp2px(10f),
                    UIUtils.dp2px(10f),
                    UIUtils.dp2px(10f),
                    bottom
            );
        }
    }

    //Get the screen orientation
    private int getScreenOrientation(){
        return activity.getResources().getConfiguration().orientation;
    }

}
