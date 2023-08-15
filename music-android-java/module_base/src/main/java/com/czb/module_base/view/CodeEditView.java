package com.czb.module_base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.czb.module_base.CodeIntercepter;
import com.czb.module_base.R;
import com.czb.module_base.utils.LogUtils;
import com.czb.module_base.utils.UIUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CodeEditView extends LinearLayout implements View.OnFocusChangeListener {

    private String KeyCode;
    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Glide.with(context).load((Bitmap) msg.obj).apply(mRequestOptions).into(mIvTuring);
                    break;
                case 2:
                    Toast.makeText(context,"错误,无法获取验证码",Toast.LENGTH_SHORT).show();
                    Glide.with(context).load(R.mipmap.ic_close).apply(mRequestOptions).into(mIvTuring);
                    break;
            }
        }
    };

    public String getKeyCode() {
        return KeyCode;
    }

    private Random mRandom = new Random();
    private String key = (10000000+mRandom.nextInt(10000000))+""+(mRandom.nextInt(1000000)+1000000)+"";
    private String codeUrl = "http://10.0.2.2:8090/user/captcha?captcha_key="+key;
    private String RegisterCodeUrl = "https://api.sunofbeaches.com/uc/ut/join/send-sms";
    private final Context context;
    private int leftIcon = R.mipmap.ic_phone_code;
    private String mHint;
    private boolean mIsPhoneCode;
    private TextView mBtnMsgCode;
    private ImageView mIvTuring;
    private EditText mEditInput;
    private ConstraintLayout mInputLayout;
    private PhoneCodeListener mPhoneCodeListener = null;
    private String mUrl;
    private RequestOptions mRequestOptions;

    public String getKey() {
        return key;
    }

    public CodeEditView(Context context) {
        this(context,null);
    }

    public CodeEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CodeEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initattrs(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.modulebase_base_view_code_edit,this,true);
        initView();
        initListener();
    }

    private void initListener() {
        LogUtils.d("test","init");
        mEditInput.setOnFocusChangeListener(this);
        mBtnMsgCode.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                mPhoneCodeListener.getSms();
            }
        });
        mIvTuring.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initTuringCode();
            }
        });

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        LogUtils.d("test","onFocusChange");
        int bgResId = R.drawable.shape_login_input;
        int leftIconColor = R.color.grey_light;
        LogUtils.d("test","b=="+b);
        if (b){
            bgResId = R.drawable.shape_login_input_focus;
            leftIconColor = R.color.colorPrimary;
            mInputLayout.setBackgroundResource(R.drawable.shape_login_input_focus);
            swithViewStyle(bgResId,leftIconColor);
        }
    }

    private void swithViewStyle(int bgResId, int leftIconColor) {
        mInputLayout.setBackgroundResource(bgResId);
        Drawable[] compoundDrawables = mEditInput.getCompoundDrawables();
        Drawable drawable =
                UIUtils.tintDrawable(compoundDrawables[0], ColorStateList.valueOf(ContextCompat.getColor(context,leftIconColor)));
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mEditInput.setCompoundDrawables(drawable,null,null,null);
    }

    public String getValue() {
        if (mEditInput.getText()!=null){
            return mEditInput.getText().toString().trim();
        }else {
            return "";
        }
    }

    private void initView() {
        mBtnMsgCode = this.findViewById(R.id.btn_msg_code);
        mIvTuring = this.findViewById(R.id.iv_turing);
        mEditInput = this.findViewById(R.id.edit_input);
        mInputLayout = this.findViewById(R.id.input_layout);
        if (mIsPhoneCode){
            mBtnMsgCode.setVisibility(View.VISIBLE);
            mIvTuring.setVisibility(View.GONE);
            initTimer();
        }else {
            mBtnMsgCode.setVisibility(View.GONE);
            mIvTuring.setVisibility(View.VISIBLE);
            initTuringCode();
        }
        mEditInput.setHint(mHint);
        //设置设置drawableLeft
        Drawable drawable = getResources().getDrawable(leftIcon);
//        Drawable[] compoundDrawables = mEditInput.getCompoundDrawables();
//        drawable = compoundDrawables[0];
        LogUtils.d("CodeEditView","drawable ="+drawable.isVisible());
        setDrawableLeft(drawable);
    }

    private void setDrawableLeft(Drawable drawable) {
        Log.d("test","width ="+drawable.getMinimumHeight()+"=="+drawable.getMinimumWidth());
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mEditInput.setCompoundDrawables(drawable,null,null,null);
    }

    private void initTuringCode() {
        mRequestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE);

//        Glide.with(context).load(codeUrl + Math.random() * 100).apply(requestOptions).into(mIvTuring);
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().addInterceptor(new CodeIntercepter());
        LogUtils.d("test",codeUrl);
        Request build = new Request.Builder().url(codeUrl).build();
        Call call = client.newCall(build);
        Bitmap bitmaps;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what=2;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message message = new Message();
                message.what=1;
                message.obj = bitmap;
                mHandler.sendMessage(message);
//                String code = response.headers().get("l_c_i");
//                KeyCode = code;
//                Log.d("test","code="+code);
            }
        });

//        Glide.with(context).load().apply(requestOptions).into(mIvTuring);
        Log.d("test","requestOptions=="+ mRequestOptions);

    }


    private void initTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(1000 * 6, 1000) {
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.M)
            @Override
            public void onTick(long l) {
                mBtnMsgCode.setText(l / 1000+"秒后重发");
                mBtnMsgCode.setEnabled(false);
                mBtnMsgCode.setTextColor(ContextCompat.getColor(context,R.color.grey_light));
                if ((l/1000) ==0L){
                    reseText();
                }
            }

            @Override
            public void onFinish() {

            }
        };

    }

    private void reseText() {
        mBtnMsgCode.setEnabled(true);
        mBtnMsgCode.setText("获取验证码");
        mBtnMsgCode.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initattrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CodeEditView);
        leftIcon = a.getResourceId(R.styleable.CodeEditView_codeLeftIcon,this.leftIcon);
        mHint = a.getString(R.styleable.CodeEditView_codeHint);
        mIsPhoneCode = a.getBoolean(R.styleable.CodeEditView_isPhoneCode, false);
        LogUtils.d("test","leftIcon ="+leftIcon);
        LogUtils.d("test","mHint ="+mHint);
        LogUtils.d("test","mIsPhoneCode ="+mIsPhoneCode);
        a.recycle();
    }

    public void setPhoneCodeListener(PhoneCodeListener phoneCodeListener){
        this.mPhoneCodeListener = phoneCodeListener;
    }

    public interface PhoneCodeListener{
        String sendMessage();
        boolean isPreContented();
        void getSms();
    }

}
