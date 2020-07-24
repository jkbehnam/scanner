package com.patient.mokhtari.scanner.activities.CustomItems;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.base.LoadingMain.Dialog_loading;


public abstract class myFragment extends Fragment {
    FragmentActivity a;


    AlertDialog ad;

    public void showLoading_base() {
        ad.show();

    }
    public void showLoading_base(String message) {
        ad.show();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) -> {
            //Catch your exception
            // Without System.exit() this will not work.
            System.exit(2);
        });
    }

    public void hideLoading_base() {
       /* if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();*/
        ad.hide();

    }

    public void setFragmentActivity(FragmentActivity a) {
        this.a = a;
        Dialog_loading aa;
        aa = new Dialog_loading();
        ad = aa.qrcode_reader(a);
    }

    public void setToolbar(View rootView, String title) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = rootView.findViewById(R.id.txttoolbar);

        Typeface typeface3 = Typeface.createFromAsset(a.getAssets(), "font/vazirbold.ttf");
        tvToolbarTitle.setTypeface(typeface3, Typeface.BOLD);
        tvToolbarTitle.setText(title);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    public void setToolbar_notmain(View rootView, String title) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = rootView.findViewById(R.id.txttoolbar);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        Typeface typeface3 = Typeface.createFromAsset(a.getAssets(), "font/vazirbold.ttf");
        tvToolbarTitle.setTypeface(typeface3, Typeface.BOLD);
        tvToolbarTitle.setText(title);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void toast(String s) {
        Toast.makeText(a, s, Toast.LENGTH_SHORT).show();
    }


}
