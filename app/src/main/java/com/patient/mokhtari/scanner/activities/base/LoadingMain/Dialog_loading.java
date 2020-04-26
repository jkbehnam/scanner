package com.patient.mokhtari.scanner.activities.base.LoadingMain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.patient.mokhtari.scanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Dialog_loading {
    Context context;

    AlertDialog.Builder builder;
    View view_alert_dialog_exit;
    AlertDialog ald_exit;
    @BindView(R.id.loading_text)
    TextView loading_text;

    @SuppressLint("ResourceAsColor")
    public AlertDialog qrcode_reader(Context context) {
        this.context = context;
        // ald_insert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        ald_exit = builder.create();
        view_alert_dialog_exit = LayoutInflater.from(context).inflate(R.layout.alert_loading, null);
        ald_exit.setView(view_alert_dialog_exit);
        ald_exit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, view_alert_dialog_exit);
        //loading_text = (TextView) ald_exit.findViewById(R.id.loading_text);
     /*   dialog = new Dialog(context, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_location_searching);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.primarydark_dark));
        }
*/
        //  ald_insert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return ald_exit;
    }

    public void show() {
        ald_exit.show();


    }


}

