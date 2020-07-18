package com.patient.mokhtari.scanner.activities.LoginRegistration;

import android.content.Context;

import com.patient.mokhtari.scanner.activities.webservice.ConnectToServer;
import com.patient.mokhtari.scanner.activities.webservice.VolleyCallback;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static com.patient.mokhtari.scanner.activities.webservice.URLs.URL_SIGNUP;

public class SignupPresenter {
    SignupActivity signupActivity;
    Context context;
    public SignupPresenter(SignupActivity signupActivity,Context context) {
        this.signupActivity=signupActivity;
        this.context=context;
    }
    public void signup(String username,String password,String name,String bday,String gender,String address,String introduction){

        Map<String, String> param = new HashMap<String, String>();
        param.put("username", username);
        param.put("password", password);
        param.put("name", name);
        param.put("bday", bday);
        param.put("gender", gender);
        param.put("address", address);
        param.put("introduction", introduction);

        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                signupActivity.signupSuccessful();
            }
        }, param, URL_SIGNUP);
    }



}
