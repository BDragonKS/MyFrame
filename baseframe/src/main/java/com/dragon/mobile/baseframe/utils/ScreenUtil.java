package com.dragon.mobile.baseframe.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Objects;

/**
 * created by yhao on 2017/8/18.
 */


public class ScreenUtil {

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        Objects.requireNonNull(wm).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 计算RecycleView中ImageView的宽
     * @param context 上下文对象
     * @param imgNo 一行图片显示的张数
     * @return 宽
     */
    public static int calculationViewWidth(Context context,int imgNo) {
        int width = getScreenWidth(context);
        return (width - CalculationUtil.getInstance().dip2px(context, 18)) / imgNo;
    }
}
