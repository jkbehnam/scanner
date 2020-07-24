package com.patient.mokhtari.scanner.activities.walkthrough;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.BaseActivity;
import com.patient.mokhtari.scanner.activities.LoginRegistration.login.LoginActivity;
import com.patient.mokhtari.scanner.activities.LoginRegistration.SignupActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WalkthroughStyle3Activity extends BaseActivity implements View.OnClickListener {
    private View indicator1;
    private View indicator2;
    private View indicator3;
    private View indicator4;
@BindView(R.id.btnSignUp)
    CardView btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walkthrough3_layout);
        ButterKnife.bind(this);

        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        indicator4 = findViewById(R.id.indicator4);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new WizardPageChangeListener());
        btnSignUp.setOnClickListener(this);
        updateIndicators(0);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new WalkthroughStyle3Fragment(position);
        }

        @Override
        public int getCount() {
            int WIZARD_PAGES_COUNT = 4;
            return WIZARD_PAGES_COUNT;
        }

    }

    private class WizardPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int position) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            updateIndicators(position);
        }
    }

    public void updateIndicators(int position) {
        switch (position) {
            case 0:
                indicator1.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot));
                indicator2.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator3.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator4.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                break;
            case 1:
                indicator1.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator2.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot));
                indicator3.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator4.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                break;
            case 2:
                indicator1.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator2.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator3.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot));
                indicator4.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                break;
            case 3:
                indicator1.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator2.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator3.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot_grey));
                indicator4.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_dot));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignIn:
                Intent i=new Intent(this, LoginActivity.class);
                startActivity(i);
               // Toast.makeText(this, "Button Sign In clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSignUp:
                Intent i2=new Intent(this, SignupActivity.class);
                startActivity(i2);
                break;
            default:
                break;
        }
    }
}
