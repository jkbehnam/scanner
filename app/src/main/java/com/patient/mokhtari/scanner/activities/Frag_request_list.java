package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterRcycleMain2;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Objects.Request;
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

import static com.patient.mokhtari.scanner.activities.Main.user_id;
import static com.patient.mokhtari.scanner.activities.webservice.URLs.URL_GET_REQUEST_LIST;


public class Frag_request_list extends myFragment implements View.OnClickListener {
    public static ArrayList<Request> requests = new ArrayList<>();
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
    @BindView(R.id.reqListSwipeContainer)
    SwipeRefreshLayout reqListSwipeContainer;

    // TODO: Rename and change types and number of parameters
    public static Frag_request_list newInstance() {
        return new Frag_request_list();
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
        btnNewRequest.setOnClickListener(this);
        bottomAppBar.setOnClickListener(this);
        bottomAppBar.setOnMenuItemClickListener(menuItem -> false);

        reqListSwipeContainer.setOnRefreshListener(() -> {

            requests=new ArrayList<>();
            getRequestList();
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mainActivity_recycle.setLayoutManager(layoutManager);
        View myLayout = rootView.findViewById(R.id.toolbar); // root View id from that link
        ImageView myView = myLayout.findViewById(R.id.iv_chat);
        myView.setOnClickListener(view -> loadFragment(Frag_chat_lists.newInstance()));
        if (requests.size() == 0) {
            getRequestList();
        } else {
            settitems(requests);
        }
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
                Frag_new_request.reqQuestionsArrayList.clear();
                Frag_new_request.reqTestPhotosArrayList.clear();
                Frag_new_request.reqBodyPhotosArrayList.clear();
                Frag_new_request.reqDuration = "";
                Frag_new_request.reqDoctor = "";
                Frag_new_request.useDrug="";
                Frag_new_request.reqBodyPoints.clear();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.bottomAppBar:
                MenuFrag dialogFragment = new MenuFrag();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "tag");
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

    public void getRequestList() {
        showLoading_base();
        Map<String, String> param = new HashMap<>();
        param.put("user_id", user_id);
        ConnectToServer.any_send(result -> {
            if(reqListSwipeContainer.isRefreshing()){reqListSwipeContainer.setRefreshing(false);}

            reciveRequest(result);
            hideLoading_base();
        }, param, URL_GET_REQUEST_LIST);
    }

    public void reciveRequest(String response) throws JSONException {

        final GsonBuilder builder = new GsonBuilder();

        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);

        try {
            final Request[] request = gson.fromJson(obj.getString("requests"), Request[].class);
            requests.addAll(Arrays.asList(request));
        } catch (Exception e) {
            String s = e.getLocalizedMessage();

        }
        settitems(requests);
    }

    public void settitems(ArrayList<Request> glist) {
        if (glist.size() != 0) {
            tv_empty_state.setVisibility(View.GONE);
            iv_empty_state.setVisibility(View.GONE);
        }
        adapterRcycleMain2 madapter = new adapterRcycleMain2(glist);
        mainActivity_recycle.setAdapter(madapter);
        madapter.setOnCardClickListner((view, position, req) -> loadFragment(Frag_request_details.newInstance(req)));

    }

}
