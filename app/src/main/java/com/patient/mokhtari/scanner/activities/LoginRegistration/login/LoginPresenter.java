package com.patient.mokhtari.scanner.activities.LoginRegistration.login;


import com.patient.mokhtari.scanner.activities.LoginRegistration.LoginActivity;
import com.patient.mokhtari.scanner.activities.helper.PrefManager;
import com.patient.mokhtari.scanner.activities.utils.ConnectToServer;
import com.patient.mokhtari.scanner.activities.utils.URLs;
import com.patient.mokhtari.scanner.activities.webservice.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginPresenter {
    LoginActivity loginActivity;
    String url;
    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;

    }

    public void login(String username, String password) {
        Map<String, String> param = new HashMap<String, String>();
        url = URLs.URL_LOGIN;
        param.put("Username", username);
        param.put("Password", password);

        ConnectToServer.any_send(new VolleyCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                reciveRequeset(result);
            }
        }, param, url);
    }

    public void reciveRequeset(String result) throws JSONException {
        JSONObject obj = new JSONObject(result);
        if (obj.getString("message").equals("Login successfull")) {

            obj = new JSONObject(obj.getString("user"));
            PrefManager pm = new PrefManager(loginActivity.getBaseContext());
            pm.createLogin(obj.getString("u_id"), obj.getString("phone"), obj.getString("Name"));

            loginActivity.loginSuccessful();
        }


    }
}
