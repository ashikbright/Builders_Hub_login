package com.example.bh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
    private EditText email;
    private Button resetbtn;
    private ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email=findViewById(R.id.frgttxtemail);
        resetbtn=findViewById(R.id.btnreset);
        progressBar=findViewById(R.id.frgtprogress);
        auth=FirebaseAuth.getInstance();
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }
        });

    }
    private void resetpassword(){
        String mail=email.getText().toString().trim();
        if(mail.isEmpty()){
            email.setError("Enter Valid Email!!!");
            email.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgot_password.this, "Check Your Email to Reset Password...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(forgot_password.this, "Failed To Reset Password...", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}