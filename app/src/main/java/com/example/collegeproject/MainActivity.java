package com.example.collegeproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.example.collegeproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
             //   ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, Pair.create(binding.logo , "ImageTransition"));
                startActivity(intent);
                finish();
                overridePendingTransition(0,0);
            }
        },1000);
    }
}