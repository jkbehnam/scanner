package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterdurationList;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.Duration;
import static com.patient.mokhtari.scanner.activities.Frag_new_request.reqDuration;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag_duration_list extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    @BindView(R.id.btn_period)
    CardView btn_period;
    // TODO: Rename and change types and number of parameters
    public static Frag_duration_list newInstance() {
        Frag_duration_list fragment = new Frag_duration_list();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_duration_list, container, false);
        ButterKnife.bind(this, rootView);

        setFragmentActivity(getActivity());


        setToolbar_notmain(rootView,"انتخاب محل ضایعه");
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));


        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<Duration> glist = new ArrayList<>();
        glist.add(new Duration("کمتر از ۶ ماه","Less6month"));
        glist.add(new Duration("کمتر از یک روز","LessDay"));
        glist.add(new Duration("کمتر از یک هفته","LessWeek"));
        glist.add(new Duration("کمتر از یک سال","LessMonth"));
        glist.add(new Duration("کمتر از یک ماه","LessYear"));
        glist.add(new Duration("بیشتر از یک سال","MoreYear"));
        glist.add(new Duration("از بدو تولد","FromBDay"));
        glist.add(new Duration("از کودکی","FromChildhood"));
        glist.add(new Duration("از بلوغ","FromMaturity"));


        adapterdurationList madapter = new adapterdurationList(glist);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterdurationList.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                reqDuration =String.valueOf(glist.get(position).getId());
            }
        });

        btn_period.setOnClickListener(this);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            reqDuration ="";
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_period:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                fm.popBackStack();
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
