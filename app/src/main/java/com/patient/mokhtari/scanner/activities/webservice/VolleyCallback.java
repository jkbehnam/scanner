package com.patient.mokhtari.scanner.activities.webservice;

import org.json.JSONException;

public interface VolleyCallback{
    void onSuccess(String result) throws JSONException;
}