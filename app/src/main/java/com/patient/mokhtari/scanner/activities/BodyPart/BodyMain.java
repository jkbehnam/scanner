package com.patient.mokhtari.scanner.activities.BodyPart;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.flyco.tablayout.SegmentTabLayout;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.BaseActivity;
import com.patient.mokhtari.scanner.activities.BodyPart.region.RegionView;
import com.patient.mokhtari.scanner.activities.BodyPart.view.HumanBodyWidget;
import com.patient.mokhtari.scanner.activities.BodyPart.view.WaveEffectLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BodyMain extends BaseActivity {
    @BindView(R.id.t2)
    SegmentTabLayout segmentTabLayout;

    private WaveEffectLayout container;
    private HumanBodyWidget bodyWidget;
    private ImageView manIv, womanIv;
    private TextView manTv, womanTv, flipFrontTv, flipBackTv;
    private String[] mTitles = {"جلو","پشت" };
    private ArrayList<MyTouchListener> mTouchListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setTitle("انتخاب محل ضایعه");
        initViews(savedInstanceState);

    }

    public void initViews(Bundle savedInstanceState){
        setContentView(R.layout.activity_bodypart);
        ButterKnife.bind(this);
        container = (WaveEffectLayout) findViewById(R.id.container);
        manIv = (ImageView) findViewById(R.id.man_icon);
        manTv = (TextView) findViewById(R.id.man_text);
        womanIv = (ImageView) findViewById(R.id.woman_icon);
        womanTv = (TextView) findViewById(R.id.woman_text);
        flipFrontTv = (TextView) findViewById(R.id.flipFront);
        flipBackTv = (TextView) findViewById(R.id.flipBack);
        segmentTabLayout.setTabData(mTitles);
        bodyWidget = new HumanBodyWidget(this, container, savedInstanceState);
        container.setRegionView(new RegionView(container, this));

        Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("انتخاب محل ضایعه");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "font/vazirbold.ttf");
        txttoolbar.setTypeface(typeface3, Typeface.BOLD);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    public void registerTouchListener(MyTouchListener listener){
        mTouchListeners.add(listener);
    }

    public void unRegisterTouchListener(MyTouchListener listener){
        mTouchListeners.remove(listener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        for(MyTouchListener listener : mTouchListeners){
            listener.onTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

    public void genderClick(View view){
        switch (view.getId()){
            case R.id.man_btn:
                if(bodyWidget.toggleBodyGenderImage(true)) {
                    findViewById(R.id.man_btn).setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                    findViewById(R.id.woman_btn).setBackgroundColor(Color.TRANSPARENT);
                    manIv.setImageResource(R.mipmap.icon_man_pressed);
                    manTv.setTextColor(Color.WHITE);
                    womanIv.setImageResource(R.mipmap.icon_woman);
                    womanTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                }
                break;
            case R.id.woman_btn:
                if(bodyWidget.toggleBodyGenderImage(false)) {
                    findViewById(R.id.man_btn).setBackgroundColor(Color.TRANSPARENT);
                    findViewById(R.id.woman_btn).setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                    manIv.setImageResource(R.mipmap.icon_man);
                    manTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                    womanIv.setImageResource(R.mipmap.icon_woman_pressed);
                    womanTv.setTextColor(Color.WHITE);
                }
                break;
        }
    }

    public void sideClick(View view){

        switch (view.getId()){
            case R.id.flipFront:
                if(bodyWidget.flipBody(false)) {
                    flipFrontTv.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                    flipBackTv.setBackgroundColor(Color.TRANSPARENT);
                    flipFrontTv.setTextColor(Color.WHITE);
                    flipBackTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                }
                break;
            case R.id.flipBack:
                if(bodyWidget.flipBody(true)) {
                    flipFrontTv.setBackgroundColor(Color.TRANSPARENT);
                    flipBackTv.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                    flipFrontTv.setTextColor(getResources().getColor(R.color.colorLightBlue));
                    flipBackTv.setTextColor(Color.WHITE);
                }
                break;
        }
    }

    public interface MyTouchListener
    {
        void onTouchEvent(MotionEvent event);
    }
}
