package com.netanel.iaiforme.manager.fragments.actions.send_fcm;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
//import com.netanel.iaiforme.firebase_cloud_messaging.MyNotificationManager;
import com.netanel.iaiforme.firebase_cloud_messaging.MyNotificationManager;
import com.netanel.iaiforme.manager.activities.ManagerMainActivity;
import com.netanel.iaiforme.pojo.Noti;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendFcmActivity extends AppCompatActivity {
    EditText etFcmTitle, etFcmDescription;
    private CollectionReference fcmSendMessageRef = FirebaseFirestore.getInstance().collection("Fcm");
    Button sendFcmToAllBtn;
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
                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
                String date = dateFormat.format(new Date());
                DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String time = timeFormat.format(new Date());
                String timeDate = date + " " + time;


                String fcmTitleText = etFcmTitle.getText().toString();
                String fcmDescriptionText = etFcmDescription.getText().toString();
                noti.setDate(timeDate);
                noti.setTitle(fcmTitleText);
                noti.setDescription(fcmDescriptionText);
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