package com.czb.module_base.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.graphics.drawable.DrawableCompat;

public class UIUtils {

    private static final DisplayMetrics sMetrics = Resources.getSystem().getDisplayMetrics();

    public static int getScreenWidth() {
            return sMetrics != null ? sMetrics.widthPixels : 0;
        }

    public static int getScreenHeeight(){
        return sMetrics!=null?sMetrics.heightPixels:0;
    }

    public static Point getScreenSize(Context context){
        Point point = new Point();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        return point;
    }

    public static int dp2px(float dipValue) {
        final float scale = sMetrics != null ? sMetrics.density : 1;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int dip2px(float dpValue) {
        float scale = sMetrics.density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 改变图片颜色
     * @param drawable Drawable
     * @param colors color
     * @return Drawable
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * @param str                  要修改的字符串
     * @param absoluteSizeSpan1    修改特定位置字符串的字号
     * @param foregroundColorSpan1 修改特定位置字符串的颜色
     * @param i                    要修改的字符串的起始位置
     * @param j                    要修改的字符串的结束位置
     * @return
     */
    public static CharSequence setTextViewContentStyle(String str,
                                                       AbsoluteSizeSpan absoluteSizeSpan1,
                                                       ForegroundColorSpan foregroundColorSpan1, int i, int j) {
        // Auto-generated method stub
        SpannableString spStr = new SpannableString(str);
        if (foregroundColorSpan1 != null) {
            spStr.setSpan(foregroundColorSpan1, i, j,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (absoluteSizeSpan1 != null) {
            spStr.setSpan(absoluteSizeSpan1, i, j,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spStr;
    }
}
