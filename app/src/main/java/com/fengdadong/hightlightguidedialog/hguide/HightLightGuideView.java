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

import androidx.annotation.Nullable;

public class HightLightGuideView extends View {


    private Paint mbgPaint;
    private Path mPath;
    private RectF rectF;
    private Context context;

    private ShapeType LIGHT_TYPE;

    private float radius;
    private float rx;
    private float ry;

    private int paintColor = Color.parseColor("#B3000000");


    public HightLightGuideView(Context context) {
        super(context);
    }

    public HightLightGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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

        mPath.reset();
        mPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CCW);
        if (rectF != null) {
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
        }
        canvas.drawPath(mPath, mbgPaint);
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
//
//            rectF.left = location[0];
//            rectF.top = location[0];
            int dpi = 0;
            Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
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
            Log.e(" dpi " ,"location 0 "+location[0]);//
            Log.e(" dpi " ,"location 1 "+location[1]);//
            Log.e(" dpi " ,"getTop  "+view.getTop());//
            Log.e(" dpi " ,"getBottom  "+view.getBottom());//
            Log.e(" dpi " ,"dpi "+dpi);//全屏高度
            Log.e(" ScreenHeight " ,"getHeight  "+ view.getLayoutParams().height); //去除虚拟键
            /**
             * 全面屏
             */
//            rectF.left = regionCommon.getLeft();
//            rectF.right = rectF.left + regionCommon.getWidth();
//            rectF.bottom = Tools.getScreenHeight_phone(context) ;
//            rectF.top = rectF.bottom + view.getHeight();

//            rectF.left = regionCommon.getLeft();
//            rectF.right = rectF.left + regionCommon.getWidth();
////            rectF.top = Tools.getScreenHeight_phone(context)  -  view.getLayoutParams().height;
//            rectF.top = Tools.getScreenHeight_phone(context) - view.getLayoutParams().height/ 2;
//            rectF.bottom = rectF.top + regionCommon.getHeight();

            rectF.left = regionCommon.getLeft();
            rectF.right = rectF.left + regionCommon.getWidth();
            rectF.top = view.getTop();
            rectF.bottom = rectF.top + regionCommon.getHeight();

        } else {
            rectF.left = regionCommon.getLeft();
            rectF.top =  regionCommon.getTop();
            rectF.right = rectF.left + regionCommon.getWidth();
            rectF.bottom = rectF.top + regionCommon.getHeight();
        }

        this.radius = (rectF.right - rectF.left) / 2 + 20;
        invalidate();
    }
}
