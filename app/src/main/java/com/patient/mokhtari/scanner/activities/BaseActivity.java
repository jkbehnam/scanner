package com.patient.mokhtari.scanner.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.MenuItem;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.base.LoadingMain.Dialog_loading;

import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity2";
    private FirebaseAnalytics mFirebaseAnalytics;


    public void showLoading_base() {

    }
    public void hideLoading_base() {
       /* if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();*/


    }
    public void setLoading(Context context){

    }
    @Override
    protected void attachBaseContext(Context base) {
      //  App.localeManager = new LocaleManager(base);
       // super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        super.attachBaseContext(ViewPumpContextWrapper.wrap(base));

        Log.d(TAG, "attachBaseContext");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) -> Log.e("Alert","Lets See if it Works !!!"));

    /*    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/iran_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/iran_sans.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.bottombar));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //   TextView tv = findViewById(R.id.cache);
        //  tv.setText(Utility.getTitleCache());
    }




    public void setToolbar(String title) {
     //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   this.setSupportActionBar(toolbar);
     // //  TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarAllPage);
      //  tvToolbarTitle.setText(title);
    }



    private Context updateBaseContextLocale(Context context) {
        // String language = SharedPrefUtils.getSavedLanguage(); // Helper method to get saved language from SharedPreferences
        Locale locale = new Locale("fa");
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale);
        }

        return updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
}