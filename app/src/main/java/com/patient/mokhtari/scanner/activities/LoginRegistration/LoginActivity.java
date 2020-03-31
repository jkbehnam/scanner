package com.patient.mokhtari.scanner.activities.LoginRegistration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.BaseActivity;
import com.patient.mokhtari.scanner.activities.Main;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    public static Context maincontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        maincontext = this;
        Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("ورود");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "font/vazirbold.ttf");
        txttoolbar.setTypeface(typeface3, Typeface.BOLD);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin2:
                Intent i=new Intent(this, Main.class);
                startActivity(i);
                // Toast.makeText(this, "Button Sign In clicked!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
