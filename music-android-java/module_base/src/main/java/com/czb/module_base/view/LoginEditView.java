package com.czb.module_base.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.czb.module_base.R;
import com.czb.module_base.utils.UIUtils;


public class LoginEditView extends LinearLayout {

    private Context context=null;
    private int leftIcon = R.mipmap.ic_phone;
    private String mHint=null;
    // 只有密码框才用到
    // 不可见密码： InputType.TYPE_TEXT_VARIATION_PASSWORD
    // 可见密码：   InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_CLASS_TEXT
    private boolean mIsPassword;
    private ImageView mClearIv;
    private ImageView mCloseIv;
    private ImageView mEyeIv;
    private EditText mEditText;
    private boolean isPsdVisible = false;
    private ConstraintLayout mInputLayout;

    public LoginEditView(Context context) {
        this(context,null);
    }

    public LoginEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoginEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initattrs(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.modulebase_login_input_layout,this);
        initView();

        initListener();
    }

    private void initListener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()&&mEditText.hasFocus()){
                    mClearIv.setVisibility(View.VISIBLE);
                }else {
                    mClearIv.setVisibility(View.GONE);

                }
            }
        });
        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                 int bgResId = R.drawable.shape_login_input;
                 int leftIconColor = R.color.grey_light;
                if (hasFocus()) {
                    bgResId = R.drawable.shape_login_input_focus;
                    leftIconColor = R.color.colorPrimary;
                    mInputLayout.setBackgroundResource(R.drawable.shape_login_input_focus);
                }
                switchViewStyle(bgResId,leftIconColor);
            }

        });

        mEyeIv.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                if (isPsdVisible){
                    //密文
                    isPsdVisible = false;
//                    mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mEyeIv.setImageResource(R.mipmap.ic_invisible);
                    mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());  //密码不可见
                }else {
                    //明文
                    isPsdVisible=true;
//                    mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mEyeIv.setImageResource(R.mipmap.ic_visible);
                    mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                mEditText.setSelection(mEditText.getText().toString().length());
            }
        });

        mClearIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                mClearIv.setVisibility(View.GONE);
            }
        });
    }

    private void switchViewStyle(int bgResId, int leftIconColor) {
        mInputLayout.setBackgroundResource(bgResId);
        Drawable[] compoundDrawables = mEditText.getCompoundDrawables();
        Drawable drawable =
                UIUtils.tintDrawable(compoundDrawables[0], ColorStateList.valueOf(ContextCompat.getColor(context,leftIconColor)));
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mEditText.setCompoundDrawables(drawable,null,null,null);
    }

    private void initView() {
        mClearIv = this.findViewById(R.id.iv_clear);
//        mCloseIv = this.findViewById(R.id.iv_close);
        mEyeIv = this.findViewById(R.id.iv_eye);
        mEditText = this.findViewById(R.id.edit_input);
        mInputLayout = this.findViewById(R.id.input_layout);
//        mEyeIv.setVisibility(mIsPassword?View.VISIBLE:View.GONE);
        mClearIv.setVisibility(View.GONE);
        mEditText.setHint(mHint);

        //设置editText左侧的图标
        Drawable drawable = getResources().getDrawable(leftIcon);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mEditText.setCompoundDrawables(drawable,null,null,null);

        if (mIsPassword){
            mEyeIv.setVisibility(View.VISIBLE);
//            mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPsdVisible = false;
        }else {
            mEyeIv.setVisibility(View.GONE);
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    private void initattrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoginEditView);
        leftIcon = a.getResourceId(R.styleable.LoginEditView_inputLeftIcon, leftIcon);
        mHint = a.getString(R.styleable.LoginEditView_inputHint);
        mIsPassword = a.getBoolean(R.styleable.LoginEditView_isPassword,false);
//        isPsdVisible = !mIsPassword;
        a.recycle();

    }



    public String getValue(){
        if (mEditText.getText()!=null){
            return mEditText.getText().toString();
        }else {
            return null;
        }
    }

}
