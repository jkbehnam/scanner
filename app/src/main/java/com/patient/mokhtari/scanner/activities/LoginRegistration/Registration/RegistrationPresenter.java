package com.patient.mokhtari.scanner.activities.LoginRegistration.Registration;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


import com.patient.mokhtari.scanner.activities.utils.ConnectToServer;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationPresenter {
    IRegisterationView mlIRegisterationView;
    Context context;

    public RegistrationPresenter(IRegisterationView IRV) {
        this.mlIRegisterationView = IRV;
        this.context = (Context) IRV;
    }

    void sendPhoneNumber(String phone) {
        mlIRegisterationView.showLoading();
      //  ConnectToServer.sendSms(this, phone);
    }

    String validatePhoneNumber(String phone) {
 /*       String regEx = "^[0-9]{11}$";
        if (TextUtils.isEmpty(phone)) {
            //edit text empty error method
            return "editTextEmpty";
        } else if (phone.length() < 10 && !phone.matches(regEx)) {
            return "PhoneNumberNotValid";
            // phone number not valid
        } else {
            return "ok";
            //ok method
            //goto next step...manage layouts
            // ارسال کد به سمت سرور
        }*/
 return "";
    }

    public void sendOptCode(String code, String phone) {
     /*     mlIRegisterationView.showLoading();
        ConnectToServer.sendOtp(this, code, phone);
*/
    }

    public void smsRequestRecived(String response) {
        mlIRegisterationView.hideLoading();
        JSONObject responseObj = null;
        try {
            responseObj = new JSONObject(response);
            boolean error = responseObj.getBoolean("error");
            if (!error) {
                mlIRegisterationView.toastSuccessMessage("کد ارسال شد");
                mlIRegisterationView.numberNotExist();
            } else if (responseObj.getString("message").equals("Mobile number already existed!")) {
                mlIRegisterationView.toastFailMessage("این شماره قبلا در سیستم ثبت شده است");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void optRequestRecived(String response) {
        mlIRegisterationView.hideLoading();
        JSONObject responseObj = null;
        try {
            responseObj = new JSONObject(response);
            boolean error = responseObj.getBoolean("error");
            if (!error) {
                //mlIRegisterationView.toastSuccessMessage("کد دریافت شد");
                if (responseObj.getString("message").equals("User created successfully!")) {
                    mlIRegisterationView.optVerified();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "wrong", Toast.LENGTH_SHORT).show();
        }

        // Parsing json object response
        // response will be a json object

    }

    public void setUserPass(String username, String Password, String passwordRep, String phone) {

        // checkUserName();
        if (passwordRep.equals(Password)) {


           /* ConnectToServer.check_username(username, context, new checkUserName() {
                @Override
                public void exist() {
                    mlIRegisterationView.toastFailMessage("این نام کاربری در سیستم موجود است. لطفا نام دیگری را انتخاب کنید.");
                }

                @Override
                public void notexist() {
                    mlIRegisterationView.toastSuccessMessage("نام کاربری یکتا است");

                    Gamer g = null;
                    g = new Gamer(username, phone, HashedPassword.create(passwordRep, "dfdf").toString(), "sdfds");
                    connectToServer.sendGamerData(g, context);
                    mlIRegisterationView.gotoNextPage();
                }
            });*/
        } else {
            mlIRegisterationView.toastFailMessage("رمز عبور و تکرار آن یکسان نیستند.");
        }

    }

    public void username_check(String username) {

  /*      ConnectToServer.check_username(username, context, new checkUserName() {
            @Override
            public void exist() {
                mlIRegisterationView.toastFailMessage("این نام کاربری در سیستم موجود است. لطفا نام دیگری را انتخاب کنید.");
                mlIRegisterationView.repetitiousUsername();
            }

            @Override
            public void notexist() {
                mlIRegisterationView.toastSuccessMessage("نام کاربری یکتا است");
                mlIRegisterationView.uniqueUsername();
            }
        });*/
    }

    public interface checkUserName {
        void exist();

        void notexist();
    }

}
