package com.patient.mokhtari.scanner.activities.Dialoges;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.patient.mokhtari.scanner.R;
import com.sasanebrahimi.persiandatepicker.PersianDatePicker;



public class DialogTime {
    Context context;
    AlertDialog.Builder builder;
    View view_alert_dialog_exit;
    AlertDialog ald_exit;
    Button btn_ok;
    PersianDatePicker pdp;

    @SuppressLint("ResourceAsColor")
    public AlertDialog qrcode_reader(Context context, View view1) {
        this.context = context;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        ald_exit = builder.create();
        view_alert_dialog_exit = LayoutInflater.from(context).inflate(R.layout.dialog_time, null);
        ald_exit.setView(view_alert_dialog_exit);
        ald_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ald_exit.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_ok = view_alert_dialog_exit.findViewById(R.id.alert_btn_ok);
        pdp = view_alert_dialog_exit.findViewById(R.id.pdp);
        btn_ok.setOnClickListener(view -> {
            ((EditText) view1).setText(pdp.getDateString("/"));
            ald_exit.hide();
        });
        return ald_exit;
    }

    public void show() {
        ald_exit.show();


    }


}

