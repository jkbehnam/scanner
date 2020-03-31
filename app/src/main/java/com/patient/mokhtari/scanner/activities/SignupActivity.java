package com.patient.mokhtari.scanner.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.patient.mokhtari.scanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends BaseActivity {
    @BindView(R.id.tl)
    SegmentTabLayout segmentTabLayout;
    private String[] mTitles = {"مرد", "زن"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);
        Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("ثبت نام");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "font/vazirbold.ttf");
        txttoolbar.setTypeface(typeface3, Typeface.BOLD);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        segmentTabLayout.setTabData(mTitles);

    }
}
