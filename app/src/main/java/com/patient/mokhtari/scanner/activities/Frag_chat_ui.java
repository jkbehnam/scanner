package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class Frag_chat_ui extends myFragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

@BindView(R.id.chat_view)
ChatView chatView;
    ArrayList<ChatMessage> chat_list;
    // TODO: Rename and change types and number of parameters
    public static Frag_chat_ui newInstance() {
        Frag_chat_ui fragment = new Frag_chat_ui();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_chat_ui, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView,"ارتباط با پزشک");

chatView.addMessage(new ChatMessage("سلام",1548570748, ChatMessage.Type.SENT,"دکتر یوسفی"));
            chatView.addMessage(new ChatMessage("سلام",1548550748, ChatMessage.Type.RECEIVED,"حسن حسن زاده"));
        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                // perform actual message sending
                return true;
            }
        });
        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));




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
