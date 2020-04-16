package com.patient.mokhtari.scanner.activities.LoginRegistration;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.BaseActivity;
import com.patient.mokhtari.scanner.activities.Dialoges.DialogTime;
import com.patient.mokhtari.scanner.activities.LoginRegistration.login.LoginActivity;
import com.patient.mokhtari.scanner.activities.Main;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends BaseActivity implements View.OnClickListener {
    AlertDialog ad;
    SignupPresenter signupPresenter;
    @BindView(R.id.tl)
    SegmentTabLayout segmentTabLayout;
    @BindView(R.id.lay_1)
    ConstraintLayout lay_1;
    @BindView(R.id.lay_2)
    ConstraintLayout lay_2;
    @BindView(R.id.btn_lay_1)
    CardView btn_lay_1;
    @BindView(R.id.btn_lay_2)
    CardView btn_lay_2;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_bday)
    EditText et_bday;
    @BindView(R.id.et_address)
    EditText et_address;

    private String[] mTitles = {"مرد", "زن"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        signupPresenter=new SignupPresenter(this,this);
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
        btn_lay_1.setOnClickListener(this);
        btn_lay_2.setOnClickListener(this);
        et_bday.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_lay_1:
                lay_1.setVisibility(View.GONE);
                lay_2.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_lay_2:
                signupPresenter.signup(et_username.getText().toString(),et_password.getText().toString(),et_name.getText().toString(),et_bday.getText().toString(),mTitles[segmentTabLayout.getCurrentTab()],et_address.getText().toString());
                break;
            case R.id.et_bday:
                showbdateDialog(view);
                break;
        }
    }

    private void showbdateDialog(final View view) {
        DialogTime dt = new DialogTime();
        ad = dt.qrcode_reader(this, view);
        ad.show();

    }
    public void signupSuccessful() {
        this.finish();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);


    }
}
