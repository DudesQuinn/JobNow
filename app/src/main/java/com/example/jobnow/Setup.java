package com.example.jobnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Setup extends AppCompatActivity {
    private Button Jobber,findJobber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Jobber=(Button) findViewById(R.id.Jobber);
        Jobber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetupjobber();
            }
        });
        findJobber=(Button)findViewById(R.id.findJobber);
        findJobber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetupfindjobber();
            }
        });

    }

    private void openSetupfindjobber() {
        Intent intent1 = new Intent(this, Setupfindjobber.class);
        startActivity(intent1);
    }

    private void openSetupjobber() {
        Intent intent =new Intent(this,Setupjobber.class);
        startActivity(intent);
    }
}
