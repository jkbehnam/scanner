
package com.patient.mokhtari.scanner.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.patient.mokhtari.scanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MenuFrag extends BottomSheetDialogFragment implements View.OnClickListener {


    @BindView(R.id.acount_btn)
    LinearLayout acount_btn;


   // List<Languages> langList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_setting_sectioned, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        //setToolbar(rootView, "صفحه شخصی");

        acount_btn.setOnClickListener(this);

        return rootView;
    }


    public static MenuFrag newInstance() {
        MenuFrag fragment = new MenuFrag();
        Bundle bundle = new Bundle();
        //  bundle.putSerializable(EVENT_KEY, event);
        fragment.setArguments(bundle);
        return fragment;
    }

  /*  private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acount_btn:

                break;

        }
    }

}