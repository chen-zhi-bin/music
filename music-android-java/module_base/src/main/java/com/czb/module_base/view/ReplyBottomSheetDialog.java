package com.czb.module_base.view;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.czb.module_base.R;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.UIUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class ReplyBottomSheetDialog extends BottomSheetDialog {

    private final Activity activity;
    private int lastTimekeyboardHeight = 0;
    private int mKeyBoardHeight = 0;

    private View rootView = null;
    private final View mDialogBinding;
    private ConstraintLayout mRootView;
    private EditText mEditReply;
    private TextView mBtnSend;
    private TextView mTvTitle;
    private TextView mTvNum;

    public ReplyBottomSheetDialog(@NonNull Activity context, int theme) {
        super(context, theme);
        this.activity = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mDialogBinding = inflater.inflate(R.layout.base_common_dialog_reply, null);
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
            mEditReply = mDialogBinding.findViewById(R.id.edit_reply);
            mBtnSend = mDialogBinding.findViewById(R.id.btn_send);
            mTvTitle = mDialogBinding.findViewById(R.id.tv_title);
            mTvNum = mDialogBinding.findViewById(R.id.tv_num_s);
            mEditReply.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    mTvNum.setText(editable.length());
                }
            });

            mEditReply.setFocusable(true);
            mEditReply.setFocusableInTouchMode(true);
            mEditReply.requestFocus();
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
    }

    @Override
    public void dismiss() {
        mEditReply.setText("");
        mTvTitle.setText("发表评论");
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
        // 键盘高度。
        int orientation = getScreenOrientation();
        int keyboardHeight = screenSize.y - rect.bottom;
        if (lastTimekeyboardHeight == keyboardHeight){
            return;
        }else {
            lastTimekeyboardHeight = keyboardHeight;
        }
        if (keyboardHeight==0){
            notifyKeyboardHeightChanged(0,orientation);
        }else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            notifyKeyboardHeightChanged(keyboardHeight,orientation);
        }else {
            notifyKeyboardHeightChanged(keyboardHeight,orientation);
        }
    }

    private void notifyKeyboardHeightChanged(int height,int origintation){
        if (mKeyBoardHeight==height){
            return;
        }else {
            mKeyBoardHeight=height;
        }
        LogUtils.e("======","height == mKeyBoardHeight ="+height);
        if (mKeyBoardHeight<=0){
            //键盘收起
            setRootPaddingBottom(UIUtils.dp2px(10f));
        }else {
            //键盘打开
            setRootPaddingBottom(mKeyBoardHeight + UIUtils.dp2px(10f)+mBtnSend.getHeight());
        }
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

    public void setReplyTitle(String title){
        if (title.contains("@")){
            mTvTitle.setText(
                    UIUtils.setTextViewContentStyle(
                            title,
                            new AbsoluteSizeSpan(UIUtils.dip2px(14f)),
                            new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.colorPrimary)),
                            title.indexOf("@"),
                            title.length()
                    )
            );
        }else {
            mTvTitle.setText(title);
        }
    }

    public void sendListener(OnSendListener listener){
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mEditReply.getText())){
                    listener.onSend(mEditReply.getText().toString());
                    mEditReply.setText("");
                }
            }
        });
    }

    public interface OnSendListener{
        void onSend(String v);
    }
}
