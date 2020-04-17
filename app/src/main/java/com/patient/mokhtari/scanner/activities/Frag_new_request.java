package com.patient.mokhtari.scanner.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.patient.mokhtari.scanner.activities.utils.VolleyMultipartRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.zelory.compressor.Compressor;

import static com.patient.mokhtari.scanner.activities.Main.user_id;
import static com.patient.mokhtari.scanner.activities.utils.URLs.URL_UPLOAD_REQUEST;
import static io.objectbox.BoxStore.context;


public class Frag_new_request extends myFragment implements View.OnClickListener {
    public static ArrayList<ReqQuestions> reqQuestionsArrayList = new ArrayList<>();
    public static ArrayList<ReqPhoto> reqTestPhotosArrayList = new ArrayList<>();
    public static ArrayList<ReqPhoto> reqBodyPhotosArrayList = new ArrayList<>();
    public static String reqDuration = "";
    public static String reqDoctor = "";
    public static String useDrug = "";
    public static ArrayList<BodyPointMain> reqBodyPoints = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;
    @BindView(R.id.btnSendRequest)
    CardView btnSendRequest;

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
        View rootView = inflater.inflate(R.layout.fragment_new_request, container, false);
        ButterKnife.bind(this, rootView);



        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "درخواست جدید");
        btnSendRequest.setOnClickListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        // rcleView.setLayoutManager(layoutManager);
     /*   rouchuan.circlelayoutmanager.CircleLayoutManager circleLayoutManager = new rouchuan.circlelayoutmanager.CircleLayoutManager(getActivity());
        rcleView.setLayoutManager(circleLayoutManager);
        rcleView.addOnScrollListener(new rouchuan.circlelayoutmanager.CenterScrollListener());
        */
        //  rcleView.setLayoutManager(new HiveLayoutManager(HiveLayoutManager.VERTICAL));


        mainActivity_recycle.setLayoutManager(layoutManager);
        ArrayList<MainList> glist = new ArrayList<>();
        glist.add(new MainList("انتخاب محل ضایعه", "skin", (reqBodyPoints.size() != 0 && !reqDuration.equals(""))));
        glist.add(new MainList("پاسخ به سوالات", "qu", (reqQuestionsArrayList.size() != 0)));
        glist.add(new MainList("ارسال تصویر آزمایش", "test", (reqTestPhotosArrayList.size() != 0)));
        glist.add(new MainList("ارسال تصویر ضایعه", "skin", (reqBodyPhotosArrayList.size() != 0)));
        glist.add(new MainList("انتخاب پزشک", "doc", !reqDoctor.equals("")));

        if((reqBodyPoints.size() != 0 && !reqDuration.equals(""))&&(reqQuestionsArrayList.size() != 0)&&(reqTestPhotosArrayList.size() != 0)
        &&(reqBodyPhotosArrayList.size() != 0)&&!reqDoctor.equals("")){
            btnSendRequest.setClickable(true);
        }else { btnSendRequest.setClickable(false);}


        adapterRcycleMain madapter = new adapterRcycleMain(glist);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner(new adapterRcycleMain.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

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
            }
        });
        if(useDrug.equals("")){
        SweetAlertDialog a = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
        a.setTitleText("آیا این درخواست را بعد از مصرف دارو انجام می\u200Cدهید؟");
        a.setConfirmText("بله، مصرف کرده\u200Cام");
        a.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                useDrug="true";
                sDialog.dismissWithAnimation();
            }
        });
        a.setCancelButton("خیر", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                useDrug="false";
                sDialog.dismissWithAnimation();
            }
        });
        a.setCancelable(false);
        a.show();}

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
        transaction.commit();
    }

    private void uploadBitmap() {

        int bodyPhotoSize = reqBodyPhotosArrayList.size();
        int testPhotoSize = reqTestPhotosArrayList.size();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_UPLOAD_REQUEST,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        hideLoading_base();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack();
                       // String s = new String(response.data);
                        Toast.makeText(getActivity(), "درخواست با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
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
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String x = gson.toJson(
                req,
                new TypeToken<ArrayList<Object>>() {
                }.getType());
        return x;
    }

    public String arrToJsonBodyPointMain(ArrayList<BodyPointMain> req) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String x = gson.toJson(
                req,
                new TypeToken<ArrayList<Object>>() {
                }.getType());
        return x;
    }

    public String arrToJsonReqPhoto(ArrayList<ReqPhoto> req) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String x = gson.toJson(
                req,
                new TypeToken<ArrayList<Object>>() {
                }.getType());
        return x;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
