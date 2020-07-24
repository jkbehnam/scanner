package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterRcycleMain;
import com.patient.mokhtari.scanner.activities.BodyPart.view.BodyPointMain;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.New_request.AddSkinPhoto;
import com.patient.mokhtari.scanner.activities.New_request.AddTestPhoto;
import com.patient.mokhtari.scanner.activities.Objects.MainList;
import com.patient.mokhtari.scanner.activities.Objects.ReqPhoto;
import com.patient.mokhtari.scanner.activities.Objects.ReqQuestions;
import com.patient.mokhtari.scanner.activities.webservice.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.patient.mokhtari.scanner.activities.Main.user_id;
import static com.patient.mokhtari.scanner.activities.webservice.URLs.URL_UPLOAD_REQUEST;


public class Frag_new_request extends myFragment implements View.OnClickListener {
    public static final ArrayList<ReqQuestions> reqQuestionsArrayList = new ArrayList<>();
    public static ArrayList<ReqPhoto> reqTestPhotosArrayList = new ArrayList<>();
    public static ArrayList<ReqPhoto> reqBodyPhotosArrayList = new ArrayList<>();
    public static String reqDuration = "";
    public static String reqDoctor = "";
    public static String useDrug = "";
    public static final ArrayList<BodyPointMain> reqBodyPoints = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;
    @BindView(R.id.btnSendRequest)
    CardView btnSendRequest;

    // TODO: Rename and change types and number of parameters
    public static Frag_new_request newInstance() {

        return new Frag_new_request();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
    @SuppressWarnings("unused")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @SuppressWarnings("unused")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_new_request, container, false);
        ButterKnife.bind(this, rootView);

        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "درخواست جدید");
        btnSendRequest.setOnClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);

        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<MainList> glist = new ArrayList<>();
        glist.add(new MainList("انتخاب محل ضایعه", "skin", (reqBodyPoints.size() != 0 && !reqDuration.equals(""))));
        glist.add(new MainList("پاسخ به سوالات", "ques", (reqQuestionsArrayList.size() != 0)));
        glist.add(new MainList("ارسال تصویر آزمایش", "test", (reqTestPhotosArrayList.size() != 0)));
        glist.add(new MainList("ارسال تصویر ضایعه", "skin", (reqBodyPhotosArrayList.size() != 0)));
        glist.add(new MainList("انتخاب پزشک", "doc", !reqDoctor.equals("")));

        adapterRcycleMain madapter = new adapterRcycleMain(glist);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner((view, position) -> {

            switch (position) {
                case 0:
                    loadFragment(new Frag_Body_part());
                    break;
                case 1:
                    loadFragment(Frag_Question_list.newInstance());
                    break;
                case 2:
                    loadFragment(AddTestPhoto.newInstance());
                    break;
                case 3:
                    loadFragment(AddSkinPhoto.newInstance());
                    break;
                case 4:
                    loadFragment(Frag_doctor_list.newInstance());

                    break;
            }
        });
        if(useDrug.equals("")){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("آیا این درخواست را بعد از مصرف دارو انجام می\u200Cدهید؟");
            builder.setCancelable(false);
            builder.setNegativeButton("خیر", (dialog, which) -> {
                useDrug="false";
                dialog.cancel();
            });

            builder.setPositiveButton("بله، مصرف کرده\u200Cام", (dialog, which) -> {
                useDrug="true";
                dialog.cancel();


            });

            AlertDialog alert = builder.create();
            alert.show();
            Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(getResources().getColor(R.color.black));
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(getResources().getColor(R.color.allOkButton));

        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            reqQuestionsArrayList.clear();
            reqTestPhotosArrayList.clear();
            reqBodyPhotosArrayList.clear();
            reqDuration = "";
            reqDoctor = "";
            reqBodyPoints.clear();
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
            case R.id.btnSendRequest:
                uploadBitmap();
                break;

        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    private void uploadBitmap() {

        int bodyPhotoSize = reqBodyPhotosArrayList.size();
        int testPhotoSize = reqTestPhotosArrayList.size();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_UPLOAD_REQUEST,
                response -> {
                    hideLoading_base();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                   // String s = new String(response.data);
                    Toast.makeText(getActivity(), "درخواست با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("questions", arrToJsonReqQuestions(reqQuestionsArrayList));
                params.put("api_key", user_id);
                params.put("bodyPhotoSize", String.valueOf(bodyPhotoSize));
                params.put("testPhotoSize", String.valueOf(testPhotoSize));
                params.put("doctor", reqDoctor);
                params.put("duration", reqDuration);
                params.put("useDrug", useDrug);
                params.put("bodyPoint", arrToJsonBodyPointMain(reqBodyPoints));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                for (int i = 0; i < bodyPhotoSize; i++) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(reqBodyPhotosArrayList.get(i).getUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String uniqueID = UUID.randomUUID().toString();
                    try {
                        params.put("picbody" + i, new DataPart(uniqueID + ".jpg", getFileDataFromDrawable(bitmap)));
                    } catch (Exception e) {
                    }

                }
                for (int i = 0; i < testPhotoSize; i++) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(reqTestPhotosArrayList.get(i).getUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String uniqueID = UUID.randomUUID().toString();
                    try {
                        params.put("pictest" + i, new DataPart(uniqueID + ".jpg", getFileDataFromDrawable(bitmap)));
                    } catch (Exception e) {
                    }

                }

                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
        showLoading_base();
    }

    public String arrToJsonReqQuestions(ArrayList<ReqQuestions> req) {
        JSONObject JSONcontacts = new JSONObject();
        for (int i = 0; i < req.size(); i++) {
            try {
                JSONcontacts.put("Count:" + (i + 1),req.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        return gson.toJson(
                req,
                new TypeToken<ArrayList<ReqQuestions>>() {
                }.getType());
    }

    public String arrToJsonBodyPointMain(ArrayList<BodyPointMain> req) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        return gson.toJson(
                req,
                new TypeToken<ArrayList<BodyPointMain>>() {
                }.getType());
    }

    public String arrToJsonReqPhoto(ArrayList<ReqPhoto> req) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        return gson.toJson(
                req,
                new TypeToken<ArrayList<ReqPhoto>>() {
                }.getType());
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
