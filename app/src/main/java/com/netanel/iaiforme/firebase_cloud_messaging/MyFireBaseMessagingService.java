package com.netanel.iaiforme.firebase_cloud_messaging;


import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        MyNotificationManager.getInstance(getApplicationContext()).displayNotification(title, body);


        Log.d("onMessageRecieved", "onMessageReceived: " + remoteMessage);
    }

    @Override
    public void onNewToken(String s) {
        Log.d("onNewToken", "onNewToken: " + s);
    }
}
