package com.czb.module_base.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.czb.module_base.R;


public class LoadingDialog extends Dialog {

    private LoadingDialog mLoadingDialog ;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.layout.base_dialog_loading_view);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.LoadingDialog);
        setContentView(themeResId);
        ImageView imageView = findViewById(R.id.iv_image);
        RotateAnimation animation = new RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        animation.setDuration(2000l);
        animation.setRepeatCount(10);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }

    public void showDialog(Context context){
        if (context instanceof Activity){
            if (((Activity) context).isFinishing()){
                return;
            }
        }
        if (mLoadingDialog==null){
            mLoadingDialog =new LoadingDialog(context);
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void showDialog(){
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void dismissDialog(){
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
