package com.patient.mokhtari.scanner.activities.base;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.patient.mokhtari.scanner.activities.utils.ObjectBox;


public class Application extends android.app.Application {

    public static Application application;

    @Override
    public void onCreate() {

        application=this;
        createNotificationChannel();
        ObjectBox.init(this);

        super.onCreate();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager  manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel("1234");
            if(channel == null) {
                channel = new NotificationChannel("1234",
                        "examp",
                        NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
        }
    }

}

}