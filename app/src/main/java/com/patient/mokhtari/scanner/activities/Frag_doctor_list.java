package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterDocotrList;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.Doctor;
import com.patient.mokhtari.scanner.activities.webservice.ConnectToServer;
import com.patient.mokhtari.scanner.activities.webservice.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.patient.mokhtari.scanner.activities.Frag_new_request.reqDoctor;
import static com.patient.mokhtari.scanner.activities.webservice.URLs.URL_GET_DOC_LIST;

public class Frag_doctor_list extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;

    @BindView(R.id.btn_doc)
    CardView btn_doc;

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
        View rootView = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        ButterKnife.bind(this, rootView);
        btn_doc.setOnClickListener(this);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "انتخاب پزشک");
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);

        mainActivity_recycle.setLayoutManager(layoutManager);

        getEventList();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            reqDoctor ="";
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
            case R.id.btn_doc:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                break;

        }
    }

    public void getEventList() {
        Map<String, String> param = new HashMap<String, String>();
        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                reciveRequeset(result);
            }
        }, param, URL_GET_DOC_LIST);
    }
    public void reciveRequeset(String response) throws JSONException {

        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);
        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            final Doctor[] events = gson.fromJson(obj.getString("doctors"), Doctor[].class);
            doctors.addAll(Arrays.asList(events));
        } catch (Exception e) {
        }
        setitem(doctors);
    }

    public void setitem(ArrayList<Doctor> doctors){

        adapterDocotrList madapter = new adapterDocotrList(doctors);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterDocotrList.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                reqDoctor =doctors.get(position).getId();
            }
        });
    }
}
