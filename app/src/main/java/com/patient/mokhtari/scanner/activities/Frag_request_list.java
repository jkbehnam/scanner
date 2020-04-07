package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterRcycleMain2;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.requests;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag_request_list extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.bottomAppBar)
    BottomAppBar bottomAppBar;
    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;
    @BindView(R.id.btnNewRequest)
    CardView btnNewRequest;
    @BindView(R.id.iv_empty_state)
    ImageView iv_empty_state;
    @BindView(R.id.tv_empty_state)
    TextView tv_empty_state;

    // TODO: Rename and change types and number of parameters
    public static Frag_request_list newInstance() {
        Frag_request_list fragment = new Frag_request_list();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_list, container, false);
        ButterKnife.bind(this, rootView);

        setFragmentActivity(getActivity());
        setToolbar(rootView, "لیست درخواست ها");
        //  ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        //   ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnNewRequest.setOnClickListener(this);
        bottomAppBar.setOnClickListener(this);
        //((AppCompatActivity)getActivity()).setSupportActionBar(bottomAppBar);
        /*bottomAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
                MenuFrag fragment = MenuFrag.newInstance();

                MenuFrag dialogFragment = new MenuFrag();
                dialogFragment.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "tag");
            //    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //  fragment.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), fragment.getTag());
            }
        });*/
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<requests> glist = new ArrayList<>();
        glist.add(new requests("پشت دست", "97/3/2", "دکتر یوسفی", "در انتظار بررسی", "hand"));
        glist.add(new requests("پشت شانه", "97/3/3", "دکتر یوسفی", "دریافت پاسخ", "sholder"));
        // glist.add(new requests("پشت دست", "97/3/2", "دکتر یوسفی", "دریافت پاسخ", "transaction"));

        settitems(glist);

        View myLayout = rootView.findViewById(R.id.toolbar); // root View id from that link
        ImageView myView = (ImageView) myLayout.findViewById(R.id.iv_chat);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(Frag_chat_lists.newInstance());
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

    public void settitems(ArrayList<requests> glist) {
        if (glist.size() != 0) {
            tv_empty_state.setVisibility(View.GONE);
            iv_empty_state.setVisibility(View.GONE);
        }
        adapterRcycleMain2 madapter = new adapterRcycleMain2(glist);
        mainActivity_recycle.setAdapter(madapter);
        madapter.setOnCardClickListner(new adapterRcycleMain2.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {


                // Intent i=new Intent(Mainskin.this, question.class);
                //startActivity(i);
                loadFragment(Frag_request_details.newInstance());


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
                Fragment fragment = Frag_new_request.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.bottomAppBar:
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();


                MenuFrag dialogFragment = new MenuFrag();
                dialogFragment.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "tag");
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
