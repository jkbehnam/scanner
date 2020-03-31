package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterShowPhoto;
import com.patient.mokhtari.scanner.activities.CustomItems.RtlGridLayoutManager;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.AddImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yalantis.ucrop.UCropFragment.TAG;


public class Frag_request_details extends myFragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    adapterShowPhoto madapter;
    ArrayList<AddImage> glist;
    @BindView(R.id.test_img_recycle)
    RecyclerView test_img_recycle;
    @BindView(R.id.scan_img_recycle)
    RecyclerView scan_img_recycle;
    @BindView(R.id.pat_det_tv)
    TextView pat_det_tv;
    @BindView(R.id.tv_body_part)
    TextView tv_body_part;
    int position;

    // TODO: Rename and change types and number of parameters
    public static Frag_request_details newInstance() {
        Frag_request_details fragment = new Frag_request_details();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_request_details, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView,"خلاصه درخواست");


        RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(getActivity(), 5);
        RtlGridLayoutManager layoutManager2 = new RtlGridLayoutManager(getActivity(), 5);

        test_img_recycle.setLayoutManager(layoutManager);
        scan_img_recycle.setLayoutManager(layoutManager2);
        glist = new ArrayList<>();
        glist.add(new AddImage(""));
        glist.add(new AddImage(""));
        glist.add(new AddImage(""));
        glist.add(new AddImage(""));
        glist.add(new AddImage(""));

        madapter = new adapterShowPhoto(glist);
        test_img_recycle.setAdapter(madapter);
        scan_img_recycle.setAdapter(madapter);

madapter.setOnCardClickListner(new adapterShowPhoto.OnCardClickListner() {
    @Override
    public void OnCardClicked(View view, int position) {
        Intent intent=new Intent(getActivity(),imageSampleActivity.class);
        getActivity().startActivity(intent);
    }
});

pat_det_tv.setOnClickListener(this::onClick);
        tv_body_part.setOnClickListener(this::onClick);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  if (context instanceof OnFragmentInteractionListener) {
      //      mListener = (OnFragmentInteractionListener) context;
     //   } else {
       //     throw new RuntimeException(context.toString()
      //              + " must implement OnFragmentInteractionListener");
     //   }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acount_btn:

                break;
            case R.id.pat_det_tv:
                break;
            case R.id.tv_body_part:
                loadFragment(Frag_Body_part_2.newInstance());
                break;

        }
    }
    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
        glist.get(position).setAddress(url);
        madapter.notifyDataSetChanged();
        madapter.notifyItemChanged(position);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
