package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterShowPhoto;
import com.patient.mokhtari.scanner.activities.BodyPart.view.BodyPointMain;
import com.patient.mokhtari.scanner.activities.CustomItems.RtlGridLayoutManager;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.New_request.AddSkinPhoto;
import com.patient.mokhtari.scanner.activities.New_request.AddTestPhoto;
import com.patient.mokhtari.scanner.activities.Objects.AddImage;
import com.patient.mokhtari.scanner.activities.Objects.ReqQuestions;
import com.patient.mokhtari.scanner.activities.Objects.Request;
import com.patient.mokhtari.scanner.activities.webservice.ConnectToServer;
import com.patient.mokhtari.scanner.activities.webservice.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.patient.mokhtari.scanner.activities.Frag_new_request.reqBodyPhotosArrayList;
import static com.patient.mokhtari.scanner.activities.Frag_new_request.reqTestPhotosArrayList;
import static com.patient.mokhtari.scanner.activities.utils.Utils.getDurationList;
import static com.patient.mokhtari.scanner.activities.utils.Utils.getPersianDate;
import static com.patient.mokhtari.scanner.activities.utils.Utils.getRequestState;
import static com.patient.mokhtari.scanner.activities.webservice.URLs.URL_GET_REQUEST_DETAIL;
import static com.yalantis.ucrop.UCropFragment.TAG;


public class Frag_request_details extends myFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    adapterShowPhoto madapter;
    adapterShowPhoto madapter2;
    ArrayList<AddImage> glist;
    @BindView(R.id.test_img_recycle)
    RecyclerView test_img_recycle;
    @BindView(R.id.scan_img_recycle)
    RecyclerView scan_img_recycle;
    @BindView(R.id.tv_body_part)
    TextView tv_body_part;
    @BindView(R.id.ReqDocName)
    TextView reqDocName;
    @BindView(R.id.ReqChat)
    TextView reqChat;
    @BindView(R.id.ReqProgress)
    TextView reqProgress;
    @BindView(R.id.ReqQuestions)
    TextView ReqQuestions;
    @BindView(R.id.ReqDate)
    TextView reqDate;
    @BindView(R.id.ReqDiagnosis)
    EditText ReqDiagnosis;
    @BindView(R.id.ReqTreatment)
    EditText ReqTreatment;
    @BindView(R.id.retryPhotoExam)
    TextView retryPhotoExam;
    @BindView(R.id.retryPhoto)
    TextView retryPhoto;
    @BindView(R.id.tv_duration)
    TextView tv_duration;
    int position;
    final Request request;
    JSONObject jsonObject;
    final ArrayList<AddImage> bodyphotos = new ArrayList<>();
    final ArrayList<AddImage> testphotos = new ArrayList<>();
    public ArrayList<ReqQuestions> reqQuestionsArrayList = new ArrayList<>();
    public static final ArrayList<BodyPointMain> reqBodyPoints2 = new ArrayList<>();
public static Frag_request_details frag_request_details;
    // TODO: Rename and change types and number of parameters
    public Frag_request_details(Request RequestId) {
        this.request = RequestId;
        getRequestDetail();
    }

    public static Frag_request_details newInstance(Request RequestId) {
        Frag_request_details fragment = new Frag_request_details(RequestId);
        frag_request_details=fragment;
        return fragment;
    }
    public static Frag_request_details getit(){

        return frag_request_details;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_details, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "خلاصه درخواست");
        //Toast.makeText(getActivity(), RequestId, Toast.LENGTH_SHORT).show();

        RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(getActivity(), 5);
        RtlGridLayoutManager layoutManager2 = new RtlGridLayoutManager(getActivity(), 5);

        test_img_recycle.setLayoutManager(layoutManager);
        scan_img_recycle.setLayoutManager(layoutManager2);

        reqDate.setText(getPersianDate(request.getRequest_date()));
        reqDocName.setText(request.getRequest_doctor());
        tv_duration.setText(getDurationList(request.getDuration()));
        reqProgress.setText(getRequestState(request.getRequest_state()));
        reqChat.setOnClickListener(this);
        ReqQuestions.setOnClickListener(this);
        tv_body_part.setOnClickListener(this);
        retryPhotoExam.setOnClickListener(this);
        retryPhoto.setOnClickListener(this);
        ReqDiagnosis.setText("تشخیص پزشک: " + request.getDiagnosis());
        ReqTreatment.setText("پلن درمانی: " + request.getTreatment());
        if (request.getReshot_test() == 0) {

            retryPhotoExam.setVisibility(View.INVISIBLE);
        }
        if (request.getReshot_body() == 0) {

            retryPhoto.setVisibility(View.INVISIBLE);
        }
        //  getRequestDetail();
        settitems(bodyphotos, testphotos);
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
            case R.id.tv_body_part:
                loadFragment(Frag_Body_part_2.newInstance());
                break;
            case R.id.ReqQuestions:
                loadFragment(Frag_questions_details.newInstance(jsonObject));
                break;
            case R.id.ReqChat:
                loadFragment(Frag_chat_ui.newInstance(request));
                break;
            case R.id.retryPhoto:
                reqBodyPhotosArrayList = new ArrayList<>();
                loadFragment(AddSkinPhoto.newInstance(request));
                break;
            case R.id.retryPhotoExam:
                reqTestPhotosArrayList = new ArrayList<>();
                loadFragment(AddTestPhoto.newInstance(request));
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


    public void getRequestDetail() {
        Map<String, String> param = new HashMap<>();
        param.put("request_id", request.getRequest_id());
        ConnectToServer.any_send(result -> reciveRequest(result), param, URL_GET_REQUEST_DETAIL);
    }

    public void reciveRequest(String response) throws JSONException {

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        // final Reader data = new InputStreamReader(LoginActivity.class.getResourceAsStream("user"), "UTF-8");
        JSONObject obj = new JSONObject(response);

        try {
            AddImage[] request = gson.fromJson(obj.getString("bodyphotos"), AddImage[].class);
            bodyphotos.addAll(Arrays.asList(request));
            request = gson.fromJson(obj.getString("testphotos"), AddImage[].class);
            testphotos.addAll(Arrays.asList(request));
            JSONArray ja = obj.getJSONArray("questions");
            if (ja.length() != 0)
                jsonObject = (JSONObject) ja.get(0);
            reqBodyPoints2.clear();
            BodyPointMain[] request2 = gson.fromJson(obj.getString("bodypoints"), BodyPointMain[].class);
            reqBodyPoints2.addAll(Arrays.asList(request2));
        } catch (Exception e) {
        }
        settitems(bodyphotos, testphotos);
    }

    public void settitems(ArrayList<AddImage> bodyphotos, ArrayList<AddImage> testphotos) {


        madapter = new adapterShowPhoto(bodyphotos);
        scan_img_recycle.setAdapter(madapter);
        madapter2 = new adapterShowPhoto(testphotos);
        test_img_recycle.setAdapter(madapter2);

        madapter.setOnCardClickListner((view, position) -> {
//                Intent intent = new Intent(getActivity(), imageSampleActivity.class);
//                getActivity().startActivity(intent);

            showphoto shortAnswerAlert = new showphoto();
            shortAnswerAlert.init_dialog(getActivity(), (bodyphotos.get(position).getAddress()));
            shortAnswerAlert.show();
        });
        madapter2.setOnCardClickListner((view, position) -> {
            // Intent intent = new Intent(getActivity(), imageSampleActivity.class);
            //  getActivity().startActivity(intent);

            showphoto shortAnswerAlert = new showphoto();
            shortAnswerAlert.init_dialog(getActivity(), (testphotos.get(position).getAddress()));
            shortAnswerAlert.show();
        });

    }


}
