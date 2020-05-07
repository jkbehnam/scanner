package com.patient.mokhtari.scanner.activities.webservice;

import android.content.Context;
import android.widget.Toast;

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

import org.json.JSONException;

import java.util.Map;

import static com.patient.mokhtari.scanner.activities.base.Application.homecontext;


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
