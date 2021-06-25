package com.example.jobnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText emailSign,passwordSign,passwordCheck;
    private Button buttonCreate;

    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth =FirebaseAuth.getInstance();

        emailSign =(EditText)findViewById(R.id.emailSign);
        passwordSign =(EditText)findViewById(R.id.passwordSign);
        passwordCheck =(EditText)findViewById(R.id.passwordCheck);
        buttonCreate = (Button)findViewById(R.id.buttonCreate);

        loadingBar =new ProgressDialog(this);




        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {

        String email2 = emailSign.getText().toString();
        String password = passwordSign.getText().toString();
        String passwordConfirm =passwordCheck.getText().toString();


         if(TextUtils.isEmpty(email2)){
            Toast.makeText(this, "please write email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please write password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(passwordConfirm)){
            Toast.makeText(this, "please write password again", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(passwordConfirm)){
            Toast.makeText(this, "your password do not match", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Creating new account");
            loadingBar.setMessage("please wait while creating your account");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email2,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        SendUserToSetupActivity();
                        Toast.makeText(Signup.this, "registered successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else{
                        String message = task.getException().getMessage();
                        Toast.makeText(Signup.this, "Error:"+ message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }


    }

    private void SendUserToSetupActivity() {
        Intent setupintent = new Intent(Signup.this,Setup.class);
        setupintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupintent);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
