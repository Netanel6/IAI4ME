package com.netanel.iaiforme.signup_signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;
import com.netanel.iaiforme.pojo.User;

public class SignupActivity extends AppCompatActivity  {
    static FirebaseAuth mAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference userRef = db.collection("Users");
    private EditText emailEt, passwordEt, nameEt, lastEt, phoneEt, personalNumberEt;
    private String email, password, name, last, phone, personalNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email_signup_et);
        passwordEt = findViewById(R.id.password_signup_et);
        nameEt = findViewById(R.id.name_et);
        lastEt = findViewById(R.id.last_et);
        phoneEt = findViewById(R.id.phone_et);
        personalNumberEt = findViewById(R.id.personal_number_et);
        findViewById(R.id.signup_btn).setOnClickListener(v -> {
            email = emailEt.getText().toString();
            password = passwordEt.getText().toString();
            name = nameEt.getText().toString();
            last = lastEt.getText().toString();
            phone = phoneEt.getText().toString();
            personalNumber = personalNumberEt.getText().toString();

            if (email.isEmpty()) {
                emailEt.setError("אנא הכנס מייל");
                emailEt.requestFocus();
            } else if (password.isEmpty()) {
                passwordEt.setError("אנא הכנס סיסמא");
                passwordEt.requestFocus();
            } else if (phone.length() < 9 || phone.length() > 10) {
                phoneEt.setError("אנא הכנס מספר טלפון מלא");
                phoneEt.requestFocus();
            } else if (name.isEmpty()) {
                nameEt.setError("אנא הכנס שם");
                nameEt.requestFocus();
            } else if (last.isEmpty()) {
                lastEt.setError("אנא הכנס שם משפחה");
                lastEt.requestFocus();
            } else if (personalNumber.length() < 5 || personalNumber.contains("-")) {
                personalNumberEt.setError("אנא הכנס מספר אישי ללא דש");
                personalNumberEt.requestFocus();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUser(v);
                    } else {
                        toast();
                    }
                    Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                    intent.putExtra("mail" ,email);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                    finish();
                });
            }
        });
        findViewById(R.id.to_signin).setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(intent);
        });
    }

    public void saveUser(View v) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User user = new User(uid, email, password, name, last, phone, personalNumber, "Worker", "pic" , "");
        userRef.document(uid).set(user);
    }

    private void toast() {
        Toast.makeText(this, "הרשמה נכשלה!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
    }
}
