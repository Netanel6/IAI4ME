package com.netanel.iaiforme.signup_signin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.netanel.iaiforme.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button resetPassword;
    EditText emailAddressEt;
    String emailAddress = "user@example.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        resetPassword();
    }

    public void resetPassword(){
        //Reset password
        resetPassword = findViewById(R.id.btn_reset_password);
        emailAddressEt = findViewById(R.id.email_forgotpassword_et);
        resetPassword.setOnClickListener(view -> {
            emailAddress = emailAddressEt.getText().toString().toLowerCase().trim();
            if (emailAddress.isEmpty()){
                Toast.makeText(ForgotPasswordActivity.this, "הכנס מייל", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "איפוס סיסמא נישלח למייל: " + emailAddress, Toast.LENGTH_SHORT).show();
                        }
                        goToSignin();
                    });
        });
    }
    public void goToSignin() {
        Intent intent = new Intent(ForgotPasswordActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToSignin();
    }
}