package com.fengdadong.hightlightguidedialog.hguide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.fengdadong.hightlightguidedialog.Tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class HighLightGuideView extends View {


    private Paint mbgPaint;
    private Path mPath;
    private RectF rectF;
    private Context context;

    private ShapeType LIGHT_TYPE;

    private float radius;
    private float rx;
    private float ry;

    private int paintColor = Color.parseColor("#B3000000");

    private List<RectF> rectFList;
    private List<ShapeType> shapeTypeList;

    public HighLightGuideView(Context context) {
        super(context);
        init(context);
    }

    public HighLightGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        mbgPaint = new Paint();
        mbgPaint.setAntiAlias(true);
        mbgPaint.setColor(paintColor);
        mPath = new Path();
        setWillNotDraw(false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(rectFList!=null && rectFList.size()>0){
//            canvas.drawRect(0,0,getWidth(), getHeight(),mbgPaint);
            mPath.reset();
            mPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CCW);
            for(int i =0;i<rectFList.size();i++){
                RectF rectF = rectFList.get(i);
                switch (shapeTypeList.get(i)) {
                    case RoundRect://圆角矩形
                        mPath.addRoundRect(rectF, rx, ry, Path.Direction.CW);
                        break;
                    case Oval:   //椭圆
                        mPath.addOval(rectF, Path.Direction.CW);
                        break;
                    case Circle: //圆形
                        float cX = (rectF.right + rectF.left) / 2;
                        float cY = (rectF.bottom + rectF.top) / 2;
                        mPath.addCircle(cX, cY, radius, Path.Direction.CW);
                        break;
                    default:     //矩形
                        mPath.addRect(rectF, Path.Direction.CW);
                        break;
                }
//                canvas.drawRect(rectF,mbgPaint);
            }
            canvas.drawPath(mPath, mbgPaint);
        }else if(rectF!=null){
            mPath.reset();
            mPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CCW);
            switch (LIGHT_TYPE) {
                case RoundRect://圆角矩形
                    mPath.addRoundRect(rectF, rx, ry, Path.Direction.CW);
                    break;
                case Oval:   //椭圆
                    mPath.addOval(rectF, Path.Direction.CW);
                    break;
                case Circle: //圆形
                    float cX = (rectF.right + rectF.left) / 2;
                    float cY = (rectF.bottom + rectF.top) / 2;
                    mPath.addCircle(cX, cY, radius, Path.Direction.CW);
                    break;
                default:     //矩形
                    mPath.addRect(rectF, Path.Direction.CW);
                    break;
            }
            canvas.drawPath(mPath, mbgPaint);
        }
    }

    public enum ShapeType {
        Rectangle(0), Oval(1), Circle(2), RoundRect(3);

        private int type;

        ShapeType(int i) {
            type = i;
        }

        public int getType() {
            return type;
        }
    }

    public void setColor(int color) {
        this.paintColor = color;
        mbgPaint.setColor(paintColor);
        invalidate();
    }

    public void calView(View view, RegionCommon regionCommon, ShapeType shapeType, int rx, int ry) {
        this.LIGHT_TYPE = shapeType;
        this.rx = rx;
        this.ry = ry;

        rectF = new RectF();

        if (view != null) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            Tools.getDpi(context);
            Log.e(" dpi ", "location 0 " + location[0]);
            Log.e(" dpi ", "location 1 " + location[1]);
            Log.e(" dpi ", "getTop  " + view.getTop());
            Log.e(" dpi ", "getBottom  " + view.getBottom());

//            rectF.left = regionCommon.getLeft();
//            rectF.right = rectF.left + regionCommon.getWidth();
//            rectF.top = view.getTop();
//            rectF.bottom = rectF.top + regionCommon.getHeight();

            rectF.left = location[0] ;
            rectF.top = location[1];
            rectF.right = rectF.left + view.getWidth();
            rectF.bottom = rectF.top + view.getHeight();

        } else {
            rectF.left = regionCommon.getLeft();
            rectF.top = regionCommon.getTop();
            rectF.right = rectF.left + regionCommon.getWidth();
            rectF.bottom = rectF.top + regionCommon.getHeight();
        }

        this.radius = (rectF.right - rectF.left) / 2 + 20;
        invalidate();
    }

    public void calViews(List<View> viewList,List<ShapeType> shapeTypeList,int rx ,int ry){
        this.rx = rx;
        this.ry = ry;
        this.shapeTypeList = shapeTypeList;
        this.rectFList = new ArrayList<>();
        if (viewList != null) {
            for(int i=0;i<viewList.size();i++){
                int[] location = new int[2];

                View view = viewList.get(i);
                view.getLocationOnScreen(location);

                RectF rectF = new RectF();
                rectF.left = location[0] ;
                rectF.top = location[1];
                rectF.right = rectF.left + view.getWidth();
                rectF.bottom = rectF.top + view.getHeight();

                rectFList.add(rectF);

                if(shapeTypeList!=null && shapeTypeList.size()>i && (shapeTypeList.get(i) == ShapeType.Circle)){
                    radius = (rectF.right - rectF.left) / 2 + 20;
                }

                Tools.getDpi(context);
                Log.e(" dpi ", "location 0 " + location[0]);
                Log.e(" dpi ", "location 1 " + location[1]);
                Log.e(" dpi ", "getTop  " + view.getTop());
                Log.e(" dpi ", "getBottom  " + view.getBottom());
            }
        }

        invalidate();
    }
}
