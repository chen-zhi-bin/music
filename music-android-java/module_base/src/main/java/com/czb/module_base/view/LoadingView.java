package com.czb.module_base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.czb.module_base.R;


public class LoadingView extends AppCompatImageView {

    private float mDegrees =30;
    private boolean mNeetRotate=true;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeetRotate=true;
        //开始转
        startRotate();
    }

    private void startRotate() {
        post(new Runnable() {
            @Override
            public void run() {
                mDegrees+=10;
                if (mDegrees>=360){
                    mDegrees=0;
                }
                invalidate();
//                LogUtils.d(LoadingView.this,"LoadingView Loading......");
                //判断是否要继续旋转
                //如果是不可见或者已经DetachedFromWindow就不再转动
                if (getVisibility()!=VISIBLE&&!mNeetRotate){
                    removeCallbacks(this);
                }else {
                    postDelayed(this,10);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRotate();
    }

    private void stopRotate() {
        mNeetRotate=false;
    }

    //转起来
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(mDegrees,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
    }
}
