package com.netanel.iaiforme.manager.fragments.actions;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.mtp.MtpConstants;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.firebase_cloud_messaging.Constants;
import com.netanel.iaiforme.firebase_cloud_messaging.MyNotificationManager;
import com.netanel.iaiforme.manager.activities.ManagerMainActivity;
import com.netanel.iaiforme.pojo.Noti;

import java.util.HashMap;
import java.util.Map;

public class SendFcmActivity extends AppCompatActivity {
    EditText etFcmTitle, etFcmDescription;
    private CollectionReference fcmSendMessageRef = FirebaseFirestore.getInstance().collection("Fcm");
    Button sendFcmToAllBtn;
    private Map<String, Object> fcmText = new HashMap<>();
    private Noti noti = new Noti();
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_send_fcm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("שליחת הודעה לכל העובדים");
        setUpViews();
        sendMessageToAll();


    }

    //Setup views
    public void setUpViews() {
        etFcmTitle = findViewById(R.id.et_fcm_title);
        etFcmDescription = findViewById(R.id.et_fcm_description);
        sendFcmToAllBtn = findViewById(R.id.send_fcm_to_all_btn);
    }

    //Send message to all users using MAP
    public void sendMessageToAll() {
        sendFcmToAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fcmTitleText = etFcmTitle.getText().toString();
                String fcmDescriptionText = etFcmDescription.getText().toString();

                noti.setTitle(fcmTitleText);
                noti.setDescription(fcmDescriptionText);
//                fcmText.put("title", fcmTitleText);
//                fcmText.put("description", fcmDescriptionText);
                fcmSendMessageRef.add(noti);
                Toast.makeText(SendFcmActivity.this, "הודעה נשלחה בהצלחה!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SendFcmActivity.this, ManagerMainActivity.class);
                MyNotificationManager.getInstance(getApplicationContext()).displayNotification(fcmTitleText , fcmDescriptionText);
                startActivity(intent);
                finish();
            }
        });
    }

}