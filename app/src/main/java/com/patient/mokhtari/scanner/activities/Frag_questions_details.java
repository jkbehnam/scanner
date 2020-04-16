package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterPatientDetails;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.ReqQuestions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag_questions_details extends myFragment implements View.OnClickListener{
    ArrayList<ReqQuestions> reqQuestionsArrayList;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    // TODO: Rename and change types and number of parameters
    public static Frag_questions_details newInstance(ArrayList<ReqQuestions> reqQuestionsArrayList) {
        Frag_questions_details fragment = new Frag_questions_details( reqQuestionsArrayList);

        return fragment;
    }
public Frag_questions_details(ArrayList<ReqQuestions> reqQuestionsArrayList){
        this.reqQuestionsArrayList=reqQuestionsArrayList;
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_questions_details, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView,"مشخصات هویتی بیمار");


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));


        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<ReqQuestions> glist = new ArrayList<>();
        glist.add(new ReqQuestions("حسن حسن زاده", "(مرد)"));
        glist.add(new ReqQuestions("سن", "48/3/7   (50)"));
        glist.add(new ReqQuestions("محل زندگی", "خراسان رضوی_مشهد"));
        glist.add(new ReqQuestions("تعداد درخواست ها", "10"));

        adapterPatientDetails madapter = new adapterPatientDetails(reqQuestionsArrayList);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterPatientDetails.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

            }
        });


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



        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
