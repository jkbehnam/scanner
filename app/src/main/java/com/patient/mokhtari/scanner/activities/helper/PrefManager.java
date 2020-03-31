package com.patient.mokhtari.scanner.activities.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Created by Ravi on 08/07/15.
 */
public class PrefManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "myTrivia";

    // All Shared Preferences Keys
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";
    private static final String KEY_MOBILE_NUMBER = "mobile_number";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_NAME = "name";
    private static final String KEY_USER_ID = "u_id";
    private static final String KEY_MOBILE = "mobile";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsWaitingForSms(boolean isWaiting) {
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaiting);
        editor.commit();
    }

    public boolean isWaitingForSms() {
        return pref.getBoolean(KEY_IS_WAITING_FOR_SMS, false);
    }

    public void setMobileNumber(String mobileNumber) {
        editor.putString(KEY_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return pref.getString(KEY_MOBILE_NUMBER, null);
    }

    public void createLogin(String name ,String mobile,String u_id) {
        editor.putString(KEY_NAME, name);

        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_USER_ID, u_id);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }
    public void user_details(String user) {
        editor.putString("user_detail", user);
        editor.commit();
    }
    public void set_prof_toman(String prof_toman) {
        editor.putString("prof_toman", prof_toman);

        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
    public HashMap<String, String> get_user() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put("user_detail", pref.getString("user_detail", ""));

        return profile;
    }

    public HashMap<String, String> get_setting() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put("prof_toman", pref.getString("prof_toman", ""));

        return profile;
    }
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put("name", pref.getString(KEY_NAME, null));
        profile.put("mobile", pref.getString(KEY_MOBILE, null));
        profile.put("u_id", pref.getString(KEY_USER_ID, null));
        return profile;
    }
}
