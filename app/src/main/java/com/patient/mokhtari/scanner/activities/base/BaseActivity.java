package com.patient.mokhtari.scanner.activities.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.base.LoadingMain.Dialog_loading;
import com.patient.mokhtari.scanner.activities.utils.App;
import com.patient.mokhtari.scanner.activities.utils.Utils;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {
    AlertDialog ad;
    private static final String TAG = "BaseActivity";

    @Override
    protected void attachBaseContext(Context base) {
       // App.localeManager = new LocaleManager(base);
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        Dialog_loading aa;
        aa = new Dialog_loading();
        ad = aa.qrcode_reader(this);
        //   mProgressDialog =new ProgressDialog(a);
        Log.d(TAG, "onCreate");
        Utils.resetActivityTitle(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/iran_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.bottonNav));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white)); //status bar or the time bar at the top

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
        showResourcesInfo();

        //   TextView tv = findViewById(R.id.cache);
        //  tv.setText(Utility.getTitleCache());
    }

    private void showResourcesInfo() {
        Resources topLevelRes = Utils.getTopLevelResources(this);
        // updateInfo("Top level  ", findViewById(R.id.tv1), topLevelRes);

        Resources appRes = getApplication().getResources();
        //  updateInfo("Application  ", findViewById(R.id.tv2), appRes);

        Resources actRes = getResources();
        //  updateInfo("Activity  ", findViewById(R.id.tv3), actRes);

        //  TextView tv4 = findViewById(R.id.tv4);
        String defLanguage = Locale.getDefault().getLanguage();
        //  tv4.setText(String.format("Locale.getDefault() - %s", defLanguage));
        //  tv4.setCompoundDrawablesWithIntrinsicBounds(null, null, getLanguageDrawable(defLanguage), null);
    }
/*
    private void updateInfo(String title, TextView tv, Resources res) {
        Locale l = LocaleManager.getLocale(res);
        // tv.setText(title + Utility.hexString(res) + String.format(" - %s", l.getLanguage()));
        //  Drawable icon = getLanguageDrawable(l.getLanguage());
        // tv.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }*/

   /* private Drawable getLanguageDrawable(String language) {
        switch (language) {
            case LANGUAGE_ENGLISH:
                return ContextCompat.getDrawable(this, R.drawable.language_en);
            case LANGUAGE_FARSI:
                return ContextCompat.getDrawable(this, R.drawable.language_uk);
            default:
                Log.w(TAG, "Unsupported language");
                return null;
        }
    }*/

    public abstract int getLayout();

    public abstract void init();

    public void setToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
       // TextView tvToolbarTitle = findViewById(R.id.tvToolbarAllPage);
       // tvToolbarTitle.setText(title);
    }

    public void show_loading() {
        try {
            ad.show();
        }catch (Exception e){}

    }

    public void hide_Loading() {
       /* if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();*/

        try {
            ad.hide();
        }catch (Exception e){}
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

    @SuppressWarnings("deprecation")
    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
}