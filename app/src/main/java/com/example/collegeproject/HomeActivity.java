package com.example.collegeproject;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.collegeproject.BottomFragments.AssignmentFragment;
import com.example.collegeproject.BottomFragments.ChatsFragment;
import com.example.collegeproject.BottomFragments.ContactsFragment;
import com.example.collegeproject.BottomFragments.HomeFragment;
import com.example.collegeproject.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
}