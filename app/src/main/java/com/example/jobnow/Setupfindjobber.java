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

import com.example.jobnow.Homefinfjobber.HomeFindJobber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Setupfindjobber extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    private Spinner spinner;
    private EditText NameFinder,phoneFinder;
    private Button buttonSubmit;

    String CurrentUserID;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setupfindjobber);

        mAuth= FirebaseAuth.getInstance();
        CurrentUserID= mAuth.getCurrentUser().getUid();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserID);

        spinner =(Spinner)findViewById(R.id.spinner);
        NameFinder= (EditText)findViewById(R.id.NameFinder);
        phoneFinder =(EditText)findViewById(R.id.phoneFinder);
        buttonSubmit =(Button)findViewById(R.id.buttonSubmit);

        loadingBar =new ProgressDialog(this);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence > adapter = ArrayAdapter.createFromResource(this, R.array.country, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  SaveAccountFidderJobberInfo();
            }
        });

    }

    private void SaveAccountFidderJobberInfo() {
        String fullname = NameFinder.getText().toString();
        String phonenumber= phoneFinder.getText().toString();
        String country= spinner.getSelectedItem().toString();
        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this, "please fill your full name", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(phonenumber)){
            Toast.makeText(this, "please fill your phone number", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(country)){
            Toast.makeText(this, "please fill your country", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Saving information");

            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap userMap = new HashMap();
            userMap.put("username",fullname);
            userMap.put("userPhone",phonenumber);
            userMap.put("country",country);
            userMap.put("status","bla bla bla" );
            userMap.put("gender","none");
            userMap.put("dob","");
            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                   if(task.isSuccessful()) {
                        SendUserToHomeFindJobber();
                       Toast.makeText(Setupfindjobber.this, "information saved successfully", Toast.LENGTH_SHORT).show();
                       loadingBar.dismiss();
                   }
                   else {
                       String massege = task.getException().getMessage();
                       Toast.makeText(Setupfindjobber.this, "Error"+ massege, Toast.LENGTH_SHORT).show();
                       loadingBar.dismiss();
                   }

                }
            });

        }
    }

    private void SendUserToHomeFindJobber() {
          Intent setupintent = new Intent(Setupfindjobber.this, HomeFindJobber.class);
        setupintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupintent);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
