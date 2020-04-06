package com.fengdadong.hightlightguidedialog;

import android.content.Context;

public class Tools {



    public static int dp2px(Context context, int dp) {
        if (context == null) {
            return 0;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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
