package com.example.collegeproject;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.collegeproject.BottomFragments.AssignmentFragment;
import com.example.collegeproject.BottomFragments.ChatsFragment;
import com.example.collegeproject.BottomFragments.ContactsFragment;
import com.example.collegeproject.BottomFragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.bReplace, new HomeFragment()).commit();
                        break;
                    case R.id.assignment:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.bReplace, new AssignmentFragment()).commit();
                        break;
                    case R.id.chats:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.bReplace, new ChatsFragment()).commit();
                        break;
                    case R.id.contacts:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.bReplace, new ContactsFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
}