package com.example.collegeproject;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.collegeproject.Attendance.ClassFragment;
import com.example.collegeproject.BottomFragments.AssignmentFragment;
import com.example.collegeproject.BottomFragments.ChatsFragment;
import com.example.collegeproject.BottomFragments.ContactsFragment;
import com.example.collegeproject.BottomFragments.HomeFragment;
import com.example.collegeproject.Fee.FeeFragment;
import com.example.collegeproject.Fee.FeeSummaryFragment;
import com.example.collegeproject.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    ActionBarDrawerToggle toggle;
    static final float END_SCALE = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toggle=new ActionBarDrawerToggle(HomeActivity.this,binding.drawerLayout,binding.topAppBar,R.string.open,R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        animateNavDrawer();


        Menu menu = binding.navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.logout);

        menuItem.setTitle("  Logout  ");
        SpannableString logout = new SpannableString(menuItem.getTitle());
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.bloodRed));
        logout.setSpan(fcs, 0,logout.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        menuItem.setTitle(logout);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bReplace, new HomeFragment()).commit();

        binding.bottom.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.bReplace, new HomeFragment()).commit();
                    binding.topAppBar.setTitle("Home");
                    break;
                case R.id.assignment:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.bReplace, new AssignmentFragment()).commit();
                    binding.topAppBar.setTitle("Assignment");
                    break;
                case R.id.chats:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.bReplace, new ChatsFragment()).commit();
                    binding.topAppBar.setTitle("Chats");
                    break;
                case R.id.contacts:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.bReplace, new ContactsFragment()).commit();
                    binding.topAppBar.setTitle("Contacts");
                    break;
            }
            return true;
        });
    }
    private void animateNavDrawer() {
        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //Scale the view based on current slide offset
                final float diffScaledOffset = slideOffset * (1-END_SCALE);
                final float offsetScale = 1-diffScaledOffset;
                binding.bReplace.setScaleX(offsetScale);
                binding.bReplace.setScaleY(offsetScale);
                // translate the view accounting of the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = binding.bReplace.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                binding.bReplace.setTranslationX(xTranslation);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        /* *************************************
                 Navigation item access
           *************************************
        */

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.bReplace, new FeeFragment()).commit();
                        break;
                    case R.id.fee:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.bReplace, new FeeSummaryFragment()).commit();
                        break;
                    case R.id.attendence:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.bReplace, new ClassFragment()).commit();
                        break;
                    case R.id.logout:
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        finishAffinity();
                        break;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });

    }
}