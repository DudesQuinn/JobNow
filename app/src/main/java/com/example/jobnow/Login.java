package com.example.jobnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobnow.Homefinfjobber.HomeFindJobber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {
    private TextView textView3;
    private EditText emailLog,passwordLog;
    private Button buttonLog;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth =FirebaseAuth.getInstance();

        emailLog =(EditText)findViewById(R.id.emailLog);
        passwordLog =(EditText)findViewById(R.id.passwordLog);
        buttonLog =(Button)findViewById(R.id.buttonLog);
        textView3=(TextView) findViewById(R.id.textView3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensignup();
            }
        });
        loadingBar =new ProgressDialog(this);

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogIn();
            }
        });
    }

    private void AllowUserToLogIn() {

        String email = emailLog.getText().toString();
        String password = passwordLog.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login...");
            loadingBar.setMessage("please wait while login to your account");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendUserToHome();
                        Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else {
                        String message =task.getException().getMessage();
                        Toast.makeText(Login.this, "Error"+message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void SendUserToHome() {
        Intent setupIntent =new Intent(Login.this, HomeFindJobber.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }


    public void opensignup(){
        Intent intent2 = new Intent(this,Signup.class);
        startActivity(intent2);
    {
}}}

