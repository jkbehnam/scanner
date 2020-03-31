package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterRcycleMain;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.New_request.AddSkinPhoto;
import com.patient.mokhtari.scanner.activities.New_request.AddTestPhoto;
import com.patient.mokhtari.scanner.activities.Objects.MainList;
import com.patient.mokhtari.scanner.activities.Objects.ReqQuestions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag_new_request extends myFragment implements View.OnClickListener{
static ArrayList<ReqQuestions> reqQuestionsArrayList=new ArrayList<>();
    //static ArrayList<ReqQuestions> reqQuestionsArrayList=new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    // TODO: Rename and change types and number of parameters
    public static Frag_new_request newInstance() {
        Frag_new_request fragment = new Frag_new_request();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_new_request, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView,"درخواست جدید");


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));


        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<MainList> glist = new ArrayList<>();
        glist.add(new MainList("انتخاب محل ضایعه", "skin", true));
        glist.add(new MainList("پاسخ به سوالات", "qu", false));
        glist.add(new MainList("ارسال تصویر آزمایش", "test", false));
        glist.add(new MainList("ارسال تصویر ضایعه", "skin", false));
        glist.add(new MainList("انتخاب پزشک", "doc", false));

        adapterRcycleMain madapter = new adapterRcycleMain(glist);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterRcycleMain.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                switch (position) {
                    case 0:
                       // Intent i=new Intent(getActivity(), BodyMain.class);
                       /// startActivity(i);
                           loadFragment(new Frag_Body_part());
                        break;
                    case 1:
                      //  Intent i2=new Intent(getActivity(), question.class);
                      //  startActivity(i2);

                        loadFragment(Frag_Question_list.newInstance());
                        break;
                    case 2:
                        loadFragment(AddTestPhoto.newInstance());

                        break;
                    case 3:
                        loadFragment(AddSkinPhoto.newInstance());
                        //  loadFragment(new Fragment_ranking_list());
                        break;
                    case 4:
                        loadFragment(Frag_doctor_list.newInstance());
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

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
