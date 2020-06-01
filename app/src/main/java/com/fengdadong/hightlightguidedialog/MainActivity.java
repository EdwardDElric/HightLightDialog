package com.fengdadong.hightlightguidedialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fengdadong.hightlightguidedialog.hguide.HighLightGuideDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View contentView  = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        setContentView(contentView);
        final TextView tv_reFresh = findViewById(R.id.tv_reFresh);
        final TextView tv_two1 = findViewById(R.id.tv_two1);
        final Button btn_two2 = findViewById(R.id.btn_two2);


        tv_reFresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighLightGuideDialog dialog = new HighLightGuideDialog(MainActivity.this, contentView, HighLightGuideDialog.H_GUIDE1, tv_reFresh);
                dialog.start();
            }
        });

        final List<View> views = new ArrayList<>();
        views.add(tv_two1);
        views.add(btn_two2);

        tv_two1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighLightGuideDialog dialog = new HighLightGuideDialog(MainActivity.this, contentView, HighLightGuideDialog.H_GUIDE2, views);
                dialog.start();
            }
        });

        btn_two2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighLightGuideDialog dialog = new HighLightGuideDialog(MainActivity.this, contentView, HighLightGuideDialog.H_GUIDE2, views);
                dialog.start();
            }
        });

    }
}
