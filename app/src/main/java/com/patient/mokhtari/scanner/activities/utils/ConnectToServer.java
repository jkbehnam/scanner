package com.patient.mokhtari.scanner.activities.utils;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.patient.mokhtari.scanner.activities.webservice.VolleyCallback;

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
                        Toast.makeText(homecontext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {






                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

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
}
