package com.netanel.iaiforme.signup_signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;

public class SplashScreenActivity extends AppCompatActivity {


    private FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference userName = FirebaseFirestore.getInstance().collection("Users");

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        showSplash();
    }

    public void showSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SplashScreenActivity.this, CheckInfoActivity.class);
                    startActivity(intent);

                    finish();
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, SigninActivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);
    }
}