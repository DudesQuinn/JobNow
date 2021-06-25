package com.example.jobnow.Homefinfjobber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.jobnow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFindJobber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homefindjobber);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomnav= findViewById(R.id.bottom_navigation1);
        bottomnav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddJobFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment =null;
            switch (menuItem.getItemId()){
                case R.id.navAddJob:
                    selectedFragment =new AddJobFragment();
                    break;
                case R.id.navInbox:
                    selectedFragment =new InboxFragment();
                    break;
                case R.id.navReview:
                    selectedFragment =new ReviewFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };
}
