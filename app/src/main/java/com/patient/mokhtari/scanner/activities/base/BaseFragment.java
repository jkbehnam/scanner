package com.patient.mokhtari.scanner.activities.base;


import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.base.LoadingMain.Dialog_loading;


public abstract class BaseFragment extends Fragment {
    FragmentActivity a;
    AlertDialog ad;
    public void showLoading_base() {
        try {
           ad.show();
        }catch (Exception e){}


     /*   mProgressDialog.setTitle(null);
        mProgressDialog.setMessage(getResources().getString(R.string.activity_login_loading_msg));
        mProgressDialog.show();*/
    }

    public void hideLoading_base() {
       /* if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();*/
        try {
            ad.hide();
        }catch (Exception e){}
    }
    public void setFragmentActivity(FragmentActivity a){
        Dialog_loading aa;
        aa = new Dialog_loading();
        ad = aa.qrcode_reader(a);
     //   mProgressDialog =new ProgressDialog(a);
        this.a=a;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setToolbar(View rootView, String title){
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
     //   TextView tvToolbarTitle = rootView.findViewById(R.id.tvToolbarAllPage);
       //Typeface typeface = ResourcesCompat.getFont(rootView.getContext(),R.font.aarith);
      //  tvToolbarTitle.setTypeface(typeface, Typeface.BOLD);
    //    tvToolbarTitle.setTextSize(20);
     //   tvToolbarTitle.setText(title);
    }
    public void setToolbar_notmain(View rootView,String title){
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
       // TextView tvToolbarTitle = (TextView) rootView.findViewById(R.id.tvToolbarAllPage);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).onBackPressed();
            }
        });

        //tvToolbarTitle.setText(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void toast(String s){
        Toast.makeText(a, s, Toast.LENGTH_SHORT).show();
    }


}
