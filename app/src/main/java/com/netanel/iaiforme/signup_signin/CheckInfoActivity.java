package com.netanel.iaiforme.signup_signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.manager.activities.ManagerMainActivity;
import com.netanel.iaiforme.pojo.User;
import com.netanel.iaiforme.worker.activities.WorkerMainActivity;

public class CheckInfoActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info);


        checkUserStatus();
    }

    //Check user status (Worker or Manager)
    public void checkUserStatus(){

        userRef.document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);

                Snackbar snackbar = Snackbar.make(findViewById(R.id.title_please_wait),
                        "הנך מחובר בתור: " + user.getName() + " " + user.getLast(), Snackbar.LENGTH_LONG);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (user.getStatus().equals("Worker")){
                            Intent intent = new Intent(CheckInfoActivity.this, WorkerMainActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (user.getStatus().equals("Manager")){
                            Intent intent = new Intent(CheckInfoActivity.this, ManagerMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, 2000);



            }

        });
    }
}