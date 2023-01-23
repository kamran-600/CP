package com.example.collegeproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.collegeproject.BottomFragments.AssignmentFragment;
import com.example.collegeproject.BottomFragments.ChatsFragment;
import com.example.collegeproject.BottomFragments.ContactsFragment;
import com.example.collegeproject.BottomFragments.HomeFragment;
import com.example.collegeproject.Progress.ProgressFragment;
import com.example.collegeproject.Remark.RemarkFragment;
import com.example.collegeproject.attendance.ClassFragment;
import com.example.collegeproject.databinding.ActivityHomeBinding;
import com.example.collegeproject.fee.FeeFragment;
import com.example.collegeproject.profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    static final float END_SCALE = 0.7f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animateNavDrawer(); //calling for animation

        Menu menu = binding.navigationView.getMenu(); //taking reference of logout menu option
        MenuItem menuItem = menu.findItem(R.id.logout);

        /* *****************************************
                 for Coloring the logout option
           ***************************************** */

        SpannableString logout = new SpannableString(menuItem.getTitle());
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.bloodRed));
        logout.setSpan(fcs, 0, logout.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        menuItem.setTitle(logout);
        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new HomeFragment()).commit();

        /* *****************************************
                     Drawer OnCLick Perform
           ***************************************** */

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new HomeFragment()).commit();
                        break;
                    case R.id.attendence:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ClassFragment()).commit();
                        break;
                    case R.id.fee:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new FeeFragment()).commit();
                        break;
                    case R.id.remark:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new RemarkFragment()).commit();
                        break;
                    case R.id.progress:
                        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ProgressFragment()).commit();
                        break;
                    case R.id.logout:
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finishAffinity();
                        break;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

         /* *****************************************
                 goto to profile activity
           ***************************************** */
        View headerView = binding.navigationView.getHeaderView(0);
        ImageView edit = (ImageView) headerView.findViewById(R.id.edit);
        TextView name = (TextView) headerView.findViewById(R.id.name);
        TextView role = (TextView) headerView.findViewById(R.id.role);
        name.setText("Mark");
        name.setSelected(true);
        role.setText("H.O.D");
        role.setSelected(true);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("name", name.getText().toString().trim());
                intent.putExtra("role", role.getText().toString().trim());
                startActivity(intent);
            }
        });



        /* *****************************************
               Bottom Navigation OnCLick Perform
           ***************************************** */

        binding.bottom.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new HomeFragment()).commit();
                    break;
                case R.id.assignment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new AssignmentFragment()).commit();
                    break;
                case R.id.chats:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ChatsFragment()).commit();
                    break;
                case R.id.contacts:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ContactsFragment()).commit();
                    break;
            }

            return true;
        });

    }

    /* *****************************************
                   Drawer Animation
       ***************************************** */
    private void animateNavDrawer() {
        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //Scale the view based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
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


    }


}