package com.patient.mokhtari.scanner.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.helper.PrefManager;
import com.patient.mokhtari.scanner.activities.walkthrough.WalkthroughStyle3Activity;

public class Main extends BaseActivity {
    public static String user_id;

    @SuppressWarnings("unused")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        PrefManager pm = new PrefManager(this.getBaseContext());

        String session = pm.getUserDetails().get("session");
        if (pm.getUserDetails().get("name") .equals("")) {
            Intent i = new Intent(this, WalkthroughStyle3Activity.class);
            startActivity(i);
            this.finish();
        } else {
            user_id = pm.getUserDetails().get("u_id");
        }
        if (savedInstanceState == null) {
            Fragment fragment = Frag_request_list.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }

  /*  @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();}
    }*/


}
