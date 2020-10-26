package com.netanel.iaiforme.firebase_cloud_messaging;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.netanel.iaiforme.R;
import com.netanel.iaiforme.shared.home.HomeFragment;

public class MyNotificationManager {

    private final Context context;
    @SuppressLint("StaticFieldLeak")
    private static MyNotificationManager instance;

    public MyNotificationManager(Context context) {
        this.context = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (instance == null) {
            instance = new MyNotificationManager(context);
        }
        return instance;
    }

    public void displayNotification(String title, String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.israel_aerospace_industries_logo)
                .setContentTitle(title)
                .setContentText(body);

        Intent intent = new Intent(context, HomeFragment.class);
        int NOTI_RC = 1;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTI_RC, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(Constants.CHANNEL_ID, "NOTIFICATION_CHANNEL_ID", importance);
            builder.setChannelId(Constants.CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }
}
