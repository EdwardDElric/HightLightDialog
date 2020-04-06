package com.fengdadong.hightlightguidedialog.hguide;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.fengdadong.hightlightguidedialog.R;
import com.fengdadong.hightlightguidedialog.Tools;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HightLightGuideDialog implements PopupWindow.OnDismissListener {

    private Context mContext;
    private PopupWindow hGuide;

    public static final String H_HOME = "0";
    public static final String H_GUIDE1 = "1";
    public static final String H_GUIDE2 = "2";
    public static final String H_GUIDE3 = "3";

    private final String HomeRefresh = "homeRefresh";
    private final String GUIDE1 = "GUIDE1";
    private final String GUIDE2 = "GUIDE2";
    private final String GUIDE3 = "GUIDE3";

    private String HTYPE;   //显示类型
    private String showType = HomeRefresh;

    private Map<String, PopupWindow> hmap = new HashMap<>();
    private View view;

    private View mParentView;

    public HightLightGuideDialog(Context context, View mParentView, String type, View view) {
        this.HTYPE = type;
        this.mParentView = mParentView;
        this.mContext = context;
        this.view = view;
        initView();
    }


    private void initView() {

        hmap = new HashMap<>();
        switch (HTYPE) {
            case H_HOME:
                showType = HomeRefresh;
                hmap.put(HomeRefresh, createPopupWindow(R.layout.dialog_hguide_home_refresh, HomeRefresh));
                break;
            case H_GUIDE1:
                showType = GUIDE1;
                hmap.put(GUIDE1, createPopupWindow(R.layout.dialog_hguide_home_refresh, GUIDE1));
                break;
            case H_GUIDE2:
                showType = GUIDE2;
                hmap.put(GUIDE2, createPopupWindow(R.layout.dialog_hguide_home_refresh, GUIDE1));
                break;
            case H_GUIDE3:

        }
    }


    private PopupWindow createPopupWindow(int res, String key) {
        View view = LayoutInflater.from(mContext).inflate(res, null);
        HightLightGuideView hightLightGuideView = view.findViewById(R.id.hight_light_view);
        ImageView iv_hight_refresh_tips = view.findViewById(R.id.iv_hguide_tips);

        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setClippingEnabled(false);

        setHightLView(hightLightGuideView, iv_hight_refresh_tips, key);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    private void setHightLView(HightLightGuideView hightLightGuideView, ImageView imageView, String key) {

        switch (key) {
            case HomeRefresh:
                RegionCommon regionCommon = new RegionCommon((int) (view.getWidth() / 13.5),
                        0,
//                        Tools.getScreenHeight_phone(mContext) - Tools.dp2px(mContext, 47),
                        Tools.dp2px(mContext, 39),
                        Tools.dp2px(mContext, 39));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.leftMargin = regionCommon.getLeft() * 2 + regionCommon.getWidth();
                layoutParams.bottomMargin = (getDpi() - view.getTop()) / 2;
                imageView.setLayoutParams(layoutParams);
                hightLightGuideView.calView(view, regionCommon, HightLightGuideView.ShapeType.Circle, 0, 0);
                break;
            case GUIDE1:
                RegionCommon regionCommon2 = new RegionCommon(Tools.dp2px(mContext, 10),
                        Tools.dp2px(mContext, 63),
                        Tools.getScreenWidth_phone(mContext) - Tools.dp2px(mContext, 10) * 2,
                        Tools.dp2px(mContext, 120));
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams2.topMargin = regionCommon2.getTop() + regionCommon2.getHeight() + Tools.dp2px(mContext, 30);
                imageView.setLayoutParams(layoutParams2);
                hightLightGuideView.calView(null, regionCommon2, HightLightGuideView.ShapeType.RoundRect, 10, 10);
                break;
            case GUIDE2:
                RegionCommon regionCommon3 = new RegionCommon(Tools.dp2px(mContext, 38),
                        Tools.dp2px(mContext, 26),
                        Tools.getScreenWidth_phone(mContext) - Tools.dp2px(mContext, 38) * 2,
                        Tools.dp2px(mContext, 42));
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams3.topMargin = regionCommon3.getTop() + regionCommon3.getHeight() + Tools.dp2px(mContext, 23);
                imageView.setLayoutParams(layoutParams3);
                hightLightGuideView.calView(null, regionCommon3, HightLightGuideView.ShapeType.RoundRect, 10, 10);
                break;
        }
    }


    public void start() {

        hGuide = hmap.get(showType);

        if (hGuide != null) {
            hGuide.setOnDismissListener(this);
            switch (showType) {
                case HomeRefresh:
                    showType = GUIDE1;
                    hGuide.showAtLocation(mParentView, Gravity.NO_GRAVITY, 0, 0);
                    break;
                case GUIDE2:
                    showType = "";
                    hGuide.showAtLocation(mParentView, Gravity.NO_GRAVITY, 0, 0);
                    clear();
                    break;
                case GUIDE3:
                    showType = "";
                    hGuide.showAtLocation(mParentView, Gravity.NO_GRAVITY, 0, 0);
                    clear();
                    break;
            }
        }

    }

    public void clear() {
        hGuide = null;
        hmap.clear();
    }

    private int getDpi() {

        int dpi = 0;
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
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
        return dpi;
    }

    @Override
    public void onDismiss() {

    }
}
