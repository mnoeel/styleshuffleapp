package com.example.styleshuffle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


//PASSWORD REST WILL OCCUR HERE
public class ForgotPassword extends AppCompatActivity {
    private EditText emailEditText;
    private Button restPasswordBtn;
    private ProgressBar progressBar;

    private TextView back;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.ForgotEmail);
        restPasswordBtn = findViewById(R.id.resetPass);
        progressBar = findViewById(R.id.progBar);
        back = findViewById(R.id.backLog);

        auth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        restPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restPassword();
            }
        });
    }

    private void restPassword() {
        String emailAddress = emailEditText.getText().toString().trim();

        if (emailAddress.isEmpty()) {
            Toast.makeText(ForgotPassword.this, "Please input email", Toast.LENGTH_SHORT).show();
        } else {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Email sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPassword.this, "Please input email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

}

