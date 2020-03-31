package com.patient.mokhtari.scanner.activities.CustomItems;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.patient.mokhtari.scanner.R;


public abstract class myFragment extends Fragment {
    FragmentActivity a;



    public void setFragmentActivity(FragmentActivity a){
this.a=a;
    }
    public void setToolbar(View rootView,String title){
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.txttoolbar);

        Typeface typeface3 = Typeface.createFromAsset(a.getAssets(), "font/vazirbold.ttf");
        tvToolbarTitle.setTypeface(typeface3, Typeface.BOLD);
        tvToolbarTitle.setText(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).onBackPressed();
            }
        });
    }
    public void setToolbar_notmain(View rootView,String title){
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.txttoolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).onBackPressed();
            }
        });
        Typeface typeface3 = Typeface.createFromAsset(a.getAssets(), "font/vazirbold.ttf");
        tvToolbarTitle.setTypeface(typeface3, Typeface.BOLD);
        tvToolbarTitle.setText(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void toast(String s){
        Toast.makeText(a, s, Toast.LENGTH_SHORT).show();
    }


}
