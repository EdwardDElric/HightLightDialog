package com.fengdadong.hightlightguidedialog.hguide;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.fengdadong.hightlightguidedialog.R;
import com.fengdadong.hightlightguidedialog.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighLightGuideDialog implements PopupWindow.OnDismissListener {

    private Context mContext;
    private PopupWindow hGuide;

    public static final String H_GUIDE1 = "1";
    public static final String H_GUIDE2 = "2";
    public static final String H_GUIDE3 = "3";

    private final String GUIDE1 = "GUIDE1";
    private final String GUIDE2 = "GUIDE2";
    private final String GUIDE3 = "GUIDE3";
    private final String GUIDE4 = "GUIDE4";

    private String HTYPE;   //显示类型
    private String showType = GUIDE1;

    private Map<String, PopupWindow> hmap = new HashMap<>();
    private View view;
    private List<View> views;

    private View mParentView;

    public HighLightGuideDialog(Context context, View mParentView, String type, View view) {
        this.HTYPE = type;
        this.mParentView = mParentView;
        this.mContext = context;
        this.view = view;
        initView();
    }

    public HighLightGuideDialog(Context context, View mParentView, String type, List<View> views) {
        this.HTYPE = type;
        this.mParentView = mParentView;
        this.mContext = context;
        this.views = views;
        initView();
    }


    private void initView() {

        hmap = new HashMap<>();
        switch (HTYPE) {
            case H_GUIDE1:
                showType = GUIDE1;
                hmap.put(GUIDE1, createPopupWindow(R.layout.dialog_hguide_home_refresh, GUIDE1));
                break;
            case H_GUIDE2:
                showType = GUIDE2;
                hmap.put(GUIDE2, createPopupWindow(R.layout.dialog_hguide_two, GUIDE2));
                break;
            case H_GUIDE3:
                showType = GUIDE3;
                hmap.put(GUIDE3, createPopupWindow(R.layout.dialog_hguide_home_refresh, GUIDE3));
                hmap.put(GUIDE4, createPopupWindow(R.layout.dialog_hguide_home_refresh, GUIDE4));
                break;

        }
    }

    private PopupWindow createPopupWindow(int res, String key) {
        View view = LayoutInflater.from(mContext).inflate(res, null);
        ImageView iv_high_refresh_tips = null;
        if(!GUIDE2.equals(key)){
            iv_high_refresh_tips = view.findViewById(R.id.iv_hguide_tips);
        }
        HighLightGuideView highLightGuideView = view.findViewById(R.id.hight_light_view);
        setHighLView(highLightGuideView, iv_high_refresh_tips, key);

        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setClippingEnabled(false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    private void setHighLView(HighLightGuideView highLightGuideView, ImageView imageView, String key) {

        switch (key) {
            case GUIDE1:
                RegionCommon regionCommon = new RegionCommon(0,
                        0,
                        0,//width
                        0);//height
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.leftMargin = view.getLeft() * 2 + view.getWidth();
//                layoutParams.bottomMargin = (Tools.getDpi(mContext) - view.getTop()) / 2;
                layoutParams.bottomMargin = view.getHeight() /2;
                imageView.setLayoutParams(layoutParams);
                highLightGuideView.calView(view, regionCommon, HighLightGuideView.ShapeType.Circle, 0, 0);
                break;
            case GUIDE2:
                if (views == null || highLightGuideView == null) {
                    return;
                }
                List<HighLightGuideView.ShapeType> shapeTypeList = new ArrayList<>();
                for (int i = 0; i < views.size(); i++) {
                    shapeTypeList.add(HighLightGuideView.ShapeType.RoundRect);
                }
                highLightGuideView.calViews(views, shapeTypeList, 10, 10);
                break;
            case GUIDE3:
                RegionCommon regionCommon3 = new RegionCommon(Tools.dp2px(mContext, 10),
                        Tools.dp2px(mContext, 63),
                        Tools.getScreenWidth_phone(mContext) - Tools.dp2px(mContext, 10) * 2,
                        Tools.dp2px(mContext, 120));
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams3.topMargin = regionCommon3.getTop() + regionCommon3.getHeight() + Tools.dp2px(mContext, 30);
                imageView.setLayoutParams(layoutParams3);
                highLightGuideView.calView(null, regionCommon3, HighLightGuideView.ShapeType.RoundRect, 10, 10);
                break;
        }
    }

//    private void setHighLView(List<HighLightGuideView> highLightGuideViews) {
//
//        if (views == null || highLightGuideViews == null) {
//            return;
//        }
//
//        for (int i = 0; i < views.size(); i++) {
//            RegionCommon regionCommon = new RegionCommon(0, 0, 0, 0);
//
//            highLightGuideViews.get(i).calView(views.get(i), regionCommon, HighLightGuideView.ShapeType.RoundRect, 10, 10);
//        }
//    }

    public void start() {

        hGuide = hmap.get(showType);

        if (hGuide != null) {
            hGuide.setOnDismissListener(this);
            switch (showType) {
                case GUIDE1:
                    showType = GUIDE1;
                    hGuide.showAtLocation(mParentView, Gravity.NO_GRAVITY, 0, 0);
                    clear();
                    break;
                case GUIDE2:
                    showType = "";
                    hGuide.showAtLocation(mParentView, Gravity.NO_GRAVITY, 0, 0);
                    clear();
                    break;
                case GUIDE3:
                    showType = GUIDE4;
                    hGuide.showAtLocation(mParentView, Gravity.NO_GRAVITY, 0, 0);
                    break;
                case GUIDE4:
                    showType = "";
                    hGuide.showAtLocation(mParentView, Gravity.NO_GRAVITY, 0, 0);
                    clear();
                    break;
            }
        }

    }

    private void clear() {
        hGuide = null;
        hmap.clear();
    }

    @Override
    public void onDismiss() {

    }
}
