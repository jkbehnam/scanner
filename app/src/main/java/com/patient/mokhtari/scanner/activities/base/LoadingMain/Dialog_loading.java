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

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Dialog_loading {
    Context context;
    private static String[] array_title_place = {
            "آنتونی جی. دی آنجلو: اشتیاق\u200Cتان برای یادگیری را توسعه دهید، تا هرگز در راه رشد و تعالی متوقف نشوید.",
            "آلبرت اینشتین: تنها چیزی که در یادگیری من تداخل ایجاد می\u200Cکند، تحصیلاتم است.",
            "لوسییوس آنائوس سنکا: تا زمانی که زندگی می\u200Cکنید، یاد بگیرید که چگونه زندگی کنید.",
            "ویکتور هوگو: هر کس درب مدرسه ای را باز کند، درب زندانی را بسته است.",
            "آلبرت اینشتین: هنر عالی معلم ترغیب لذت در بیان خلاق و دانش است.",
            "ارسطو: ریشه های آموزش تلخ، اما میوه هایش شیرین است.",
            "پوبلیوس سیروس: نادان کسی است که آموزش را حقیر بشمارد.",
            "بنجامین دیسرایلی: دیدن زیاد، درد و رنج زیاد و مطالعه زیاد سه ستون یادگیری هستند.",
            "مارک تواین: هرگز اجازه ندهید آموزش و پرورش رسمی، راه اصلی یادگیری شما شود.",
            "تئودور روزولت: آموزش انسان\u200Cها بدون اصول اخلاقی، تهدیدی برای جامعه است.",
            "اسکار وایلد: همه آنچه ارزش دانستن دارد، آموزش دادنی نیست.",
            "افلاطون: دانشی که همراه با اجبار کسب شود در ذهن نمی\u200Cماند.",
            "ماری کوری: در زندگی چیزی برای ترسیدن وجود ندارد، بلکه باید درک شود.",
            "برایان تریسی: مشکلات ما را متوقف نمی\u200Cکنند، بلکه به ما آموزش می\u200Cدهند.",


    };
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

        final int min = 0;
        final int max = 13;
        final int random = new Random().nextInt((max - min) + 1) + min;
        loading_text.setText(array_title_place[random]);
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

