package com.patient.mokhtari.scanner.activities.LoginRegistration;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.BaseActivity;
import com.patient.mokhtari.scanner.activities.Dialoges.DialogTime;
import com.patient.mokhtari.scanner.activities.LoginRegistration.login.LoginActivity;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.patient.mokhtari.scanner.activities.utils.Utils.faToEn;

public class SignupActivity extends BaseActivity implements View.OnClickListener {
    AlertDialog ad;
    SignupPresenter signupPresenter;
    @BindView(R.id.tl)
    SegmentTabLayout segmentTabLayout;
    @BindView(R.id.lay_1)
    ConstraintLayout lay_1;
    @BindView(R.id.lay_2)
    ConstraintLayout lay_2;
    @BindView(R.id.rel_lay_2)
    RelativeLayout rel_lay_2;

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
    @BindView(R.id.main_activity_multi_line_radio_group)
    MultiLineRadioGroup mrg;
    private final String[] mTitles = {"مرد", "زن"};
    String EnUsername;
    String EnPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        signupPresenter = new SignupPresenter(this, this);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView txttoolbar = findViewById(R.id.txttoolbar);
        txttoolbar.setText("ثبت نام");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "font/vazirbold.ttf");
        txttoolbar.setTypeface(typeface3, Typeface.BOLD);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        segmentTabLayout.setTabData(mTitles);
        btn_lay_1.setOnClickListener(this);
        btn_lay_2.setOnClickListener(this);
        et_bday.setOnClickListener(this);

        mrg.setMaxInRow(2);
        mrg.addButtons(0, "بروشور");
        mrg.addButtons(1, "مصاحبه گفتگو");
        mrg.addButtons(2, "شبکه های اجتماعی");
        mrg.checkAt(0);
        setLoading(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_lay_1:
                EnUsername= faToEn(et_username.getText().toString());
                EnPassword=faToEn(et_password.getText().toString());
                if (signupPresenter.userPassValidator(EnUsername, EnPassword))
                    signupPresenter.checkUnique(EnUsername);
                break;

            case R.id.btn_lay_2:
                signupPresenter.signup(EnUsername, EnPassword, et_name.getText().toString(), et_bday.getText().toString(), mTitles[segmentTabLayout.getCurrentTab()], et_address.getText().toString(), mrg.getCheckedRadioButtonText().toString());
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

    public void usernameIsUnique() {
        lay_1.setVisibility(View.GONE);
        rel_lay_2.setVisibility(View.VISIBLE);
    }
    public void setUsernameError(String error){
et_username.setError(error);
    }
    public void setPasswordError(String error){
        et_password.setError(error);
    }
}
