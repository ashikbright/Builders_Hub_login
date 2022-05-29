package com.example.bh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView registeruser,frgtpassword;
    private EditText mail,password;
    private Button loginuser;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail=findViewById(R.id.txtemail);
        password=findViewById(R.id.txtpassword);
        loginuser=findViewById(R.id.btnlogin);
        registeruser=findViewById(R.id.rgtr);
        progressBar=findViewById(R.id.prgbarlogin);
        mAuth=FirebaseAuth.getInstance();
        frgtpassword=findViewById(R.id.frgtpass);

        frgtpassword.setOnClickListener(MainActivity.this);
        registeruser.setOnClickListener(MainActivity.this);
        loginuser.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View view) {

            switch(view.getId()){
                case R.id.rgtr:
                    startActivity(new Intent(MainActivity.this,register.class));
                    break;
                case R.id.btnlogin:
                    loginac();
                    break;
                case R.id.frgtpass:
                    startActivity(new Intent(MainActivity.this,forgot_password.class));
                    break;
            }
    }

    private void loginac(){
        String em=mail.getText().toString();
        String pass=password.getText().toString();


        if(em.isEmpty()){
            mail.setError("Enter Valid Email Address!!!");
            mail.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError("Password is Required!!!");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(em,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()){
                                //redirect to users home page
                                startActivity(new Intent(MainActivity.this,UsersHome.class));
                            }
                               else{
                                    user.sendEmailVerification();
                                    Toast.makeText(MainActivity.this, "Check Your email to verify your account....", Toast.LENGTH_SHORT).show();
                                }
                           }
                        else {
                            Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}