package com.fengdadong.hightlightguidedialog;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import java.lang.reflect.Method;

public class Tools {



    public static int dp2px(Context context, int dp) {
        if (context == null) {
            return 0;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getDpi(Context context) {
        int dpi = 0;
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(" dpi ", "dpi " + dpi);//全屏高度
        return dpi;
    }

    /**
     * 得到屏幕总宽度
     *
     * @return
     */
    public static int getScreenWidth_phone(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
