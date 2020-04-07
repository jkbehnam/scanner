package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterChatList;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.requests;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag_chat_lists extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    @BindView(R.id.iv_empty_state)
    ImageView iv_empty_state;

    @BindView(R.id.tv_empty_state)
    TextView tv_empty_state;

    // TODO: Rename and change types and number of parameters
    public static Frag_chat_lists newInstance() {
        Frag_chat_lists fragment = new Frag_chat_lists();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, rootView);

        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView,"ارتباط با پزشک");

        //  ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        //   ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<requests> glist = new ArrayList<>();
        glist.add(new requests("پشت دست", "97/3/2", "دکتر حمید یوسفی", "2", "hand"));
        glist.add(new requests("پشت شانه", "97/3/3", "دکتر کامران جعفری", "3", "sholder"));

        // glist.add(new requests("پشت دست", "97/3/2", "دکتر یوسفی", "دریافت پاسخ", "transaction"));

        settitems(glist);


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void settitems(ArrayList<requests> glist) {
        if (glist.size() != 0) {
            tv_empty_state.setVisibility(View.GONE);
            iv_empty_state.setVisibility(View.GONE);
        }
        adapterChatList madapter = new adapterChatList(glist);
        mainActivity_recycle.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapterChatList.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

loadFragment(Frag_chat_ui.newInstance());
                // Intent i=new Intent(Mainskin.this, question.class);
                //startActivity(i);
                //   loadFragment(new BuyQuestionFragment());


            }
        });

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

            case R.id.btnNewRequest:

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
