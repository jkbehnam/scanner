package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterDocotrList;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.Doctor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag_doctor_list extends myFragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    // TODO: Rename and change types and number of parameters
    public static Frag_doctor_list newInstance() {
        Frag_doctor_list fragment = new Frag_doctor_list();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_doctor_list, container, false);
        ButterKnife.bind(this, rootView);


        setFragmentActivity(getActivity());

        setToolbar_notmain(rootView,"انتخاب پزشک");
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));


        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<Doctor> glist = new ArrayList<>();
        glist.add(new Doctor("محسن چاوشی", "پوست و مو", "",""));
        glist.add(new Doctor("حمید عسکری", "پوست و مو", "",""));
        glist.add(new Doctor("علی احمدی", "پوست و مو", "",""));

        adapterDocotrList madapter = new adapterDocotrList(glist);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterDocotrList.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                switch (position) {
                    case 0:
                        //   loadFragment(new BuyQuestionFragment());
                        break;
                    case 1:

                        break;
                    case 2:

                        //  loadFragment(new solvingListFragment());
                        break;
                    case 3:
                        //  loadFragment(new Fragment_ranking_list());
                        break;
                    case 4:
                        // loadFragment(new Fragment_source_list());
                        break;
                }
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
            case R.id.acount_btn:

                break;



        }
    }
}
