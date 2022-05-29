package com.example.bh;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView login;
    private EditText mail,password,cpassword;
    private Button create;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mail=findViewById(R.id.frgttxtemail);
        password=findViewById(R.id.ctxtpassword);
        cpassword=findViewById(R.id.ctxtconfpassword);
        login=findViewById(R.id.txtlogin);
        create=findViewById(R.id.btncreateaccount);
        progressBar=findViewById(R.id.prgbar);
        create.setOnClickListener(register.this);

    }

    @Override
    public void onClick(View view) {
            switch(view.getId()){
                case R.id.txtlogin:
                    startActivity(new Intent(register.this,MainActivity.class));
                    break;
                case R.id.btncreateaccount:
                    registerac();
                    break;
            }
    }
    private void registerac(){
        String em=mail.getText().toString();
        String pass=password.getText().toString();
        String cpass=cpassword.getText().toString();

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
        if(cpass.isEmpty()){
            cpassword.setError("Confirm Password field is Required!!!");
            cpassword.requestFocus();
            return;
        }
        if(pass.length()<8){
            password.setError("Enter atleast 8 Characters");
            password.requestFocus();
            return;
        }
        if(cpass.length()<8){
            cpassword.setError("Enter atleast 8 Characters");
            cpassword.requestFocus();
            return;
        }
        //if(pass!=cpass){
        //    cpassword.setError("Password Doesnot match!!");
        //    return;
       // }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(em,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user user=new user(em);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(register.this, "User Logged In Successfully...", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.VISIBLE);
                                            }
                                            else{
                                                Toast.makeText(register.this, "Failed To Register...", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(register.this, "Failed To Register...Try Again!!!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}