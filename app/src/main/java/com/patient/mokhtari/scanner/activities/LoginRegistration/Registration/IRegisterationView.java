package com.patient.mokhtari.scanner.activities.LoginRegistration.Registration;

public interface IRegisterationView {
    void showLoading();
    void hideLoading();
    void startTimer();
    void EnterCodePage();
    void EnterphonePage();
void optVerified();
    void showEndTimerDialog();
    void numberNotExist();
    void toastSuccessMessage(String message);
    void toastFailMessage(String message);
    void gotoNextPage();
     void uniqueUsername() ;
    void repetitiousUsername();
}
