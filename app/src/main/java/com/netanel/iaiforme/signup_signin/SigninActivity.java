package com.netanel.iaiforme.signup_signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netanel.iaiforme.R;


public class SigninActivity extends AppCompatActivity {
    TextView toSignUp , forgotPassword;
    Button signIN;
    String emailFromSignUp;

     FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static String email, password;
    private EditText emailEt, passwordEt;
    FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(SigninActivity.this, "אנא התחבר מחדש", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_signin);

        signIN();
        signUP();
        forgotPassword();
    }

    //SignIN
    public void signIN(){
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            emailFromSignUp = extras.getString("mail");
        }

        emailEt = findViewById(R.id.email_signin_et);
        emailEt.setText(emailFromSignUp);
        passwordEt = findViewById(R.id.password_signin_et);
        signIN = findViewById(R.id.signin_btn);
        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEt.getText().toString();
                password = passwordEt.getText().toString();


                if (email.isEmpty() || password.isEmpty()) {
                    signIN.setTextColor(getResources().getColor(R.color.redSignIn));
                }

                if (password.isEmpty()) {
                    passwordEt.setError("אנא הכנס סיסמא");
                    passwordEt.requestFocus();
                } else if (email.isEmpty()) {
                    emailEt.setError("אנא הכנס מייל");
                    passwordEt.requestFocus();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                signIN.setTextColor(getResources().getColor(R.color.greenSignIn));
                                Intent intent = new Intent(SigninActivity.this, CheckInfoActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                toast("ההתחברות נכשלה, נסה שנית");
                            }
                        }
                    });
                }
            }
        });
    }

    //SignUP
    public void signUP(){
        toSignUp = findViewById(R.id.to_signup);
        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Forgot Pass (RESET)
    public void forgotPassword(){
        forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    //Toast
    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
    }
}
