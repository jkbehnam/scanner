package com.patient.mokhtari.scanner.activities.LoginRegistration.login;


import com.patient.mokhtari.scanner.activities.utils.ErrorCode;
import com.patient.mokhtari.scanner.activities.webservice.model.response.ResponseLogin;

/**
 * Created by cstudioo on 05/01/17.
 */

public interface ILoginView {

    void showLoading();
    void hideLoading();
    void setEmailError(ErrorCode code);
    void setPasswordError(ErrorCode code);
    void loginSuccess(ResponseLogin user);
    void loginFailure(ErrorCode code);
    void loginFailure(String errMsg);
}
