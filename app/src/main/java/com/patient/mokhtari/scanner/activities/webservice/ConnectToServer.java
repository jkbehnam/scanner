package com.patient.mokhtari.scanner.activities.webservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.patient.mokhtari.scanner.activities.Objects.ReqPhoto;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.patient.mokhtari.scanner.activities.Main.user_id;
import static com.patient.mokhtari.scanner.activities.base.Application.homecontext;
import static com.patient.mokhtari.scanner.activities.webservice.URLs.URL_UPLOAD_Photo;
import static com.patient.mokhtari.scanner.activities.webservice.URLs.URL_UPLOAD_REQUEST;


public class ConnectToServer {

    public static void any_send(VolleyCallback callback, Map<String, String> params, String url) {

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            String s = new String(response.data);
                            callback.onSuccess(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        show_error_warning(error, homecontext);
                        error.getMessage();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
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
        Volley.newRequestQueue(homecontext).add(volleyMultipartRequest);
    }

    public static void uploadBitmap(VolleyCallback callback, ArrayList<ReqPhoto> PhotosArrayList, com.patient.mokhtari.scanner.activities.Objects.Request request,String type) {

        int PhotoSize = PhotosArrayList.size();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_UPLOAD_Photo,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            String s = new String(response.data);
                            callback.onSuccess(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(homecontext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("request_id", request.getRequest_id());
                params.put("PhotoSize", String.valueOf(PhotoSize));
                params.put("type", type);

                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                for (int i = 0; i < PhotoSize; i++) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(homecontext.getContentResolver(), Uri.parse(PhotosArrayList.get(i).getUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String uniqueID = UUID.randomUUID().toString();
                    try {
                        params.put("picbody" + i, new DataPart(uniqueID + ".jpg", getFileDataFromDrawable(bitmap)));
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
        Volley.newRequestQueue(homecontext).add(volleyMultipartRequest);

    }
    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public static void show_error_warning(VolleyError error, Context context) {

        String message = null;
        if (error instanceof NetworkError) {
          //  message = "Cannot connect to Internet...Please check your connection!";
            message = "اشکار در اتصال به اینترنت...لطفا وضعیت اتصال خود را بررسی کنید";
        } else if (error instanceof ServerError) {
         //   message = "The server could not be found. Please try again after some time!!";
            message = "سرور یافت نشد. لطفا بهد از چند لحظه دوباره تلاش کنید";
        } else if (error instanceof AuthFailureError) {
           // message = "Cannot connect to Internet...Please check your connection!";
            message = "اشکار در اتصال به اینترنت...لطفا وضعیت اتصال خود را بررسی کنید";
        } else if (error instanceof ParseError) {
           // message = "Parsing error! Please try again after some time!!";
            message = "اشکار در اتصال به اینترنت...لطفا وضعیت اتصال خود را بررسی کنید";
        } else if (error instanceof NoConnectionError) {
            message = "اشکار در اتصال به اینترنت...لطفا وضعیت اتصال خود را بررسی کنید";
        } else if (error instanceof TimeoutError) {
           // message = "Connection TimeOut! Please check your internet connection.";
            message = "اشکار در اتصال به اینترنت...لطفا وضعیت اتصال خود را بررسی کنید";
        }


         Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
