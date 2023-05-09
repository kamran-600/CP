package com.example.collegeproject.Assignment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityAssignmentCheckBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Locale;

public class AssignmentCheckActivity extends AppCompatActivity {

    ActivityAssignmentCheckBinding binding;
    ActivityResultLauncher<String> docLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
                getTitleOfDocument(result);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.submit.setOnClickListener(v -> {
            binding.submit.setEnabled(false);
            Snackbar.make(v, "Assignment Checked", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressed();
                }
            }, 1000);
        });

        binding.clear.setOnClickListener(v -> {
            binding.expendableLayout2.setVisibility(View.GONE);
            binding.submit.setEnabled(false);
        });

        Intent intent = getIntent();
        binding.stuName.setText(intent.getStringExtra("stuName"));
        binding.image.setImageResource(intent.getIntExtra("image", 0));
        binding.submissionDate.setText(intent.getStringExtra("time"));

        binding.submitCheckedCopy.setOnClickListener(v -> docLauncher.launch("application/pdf"));


    }

    private void getTitleOfDocument(Uri result) {

        Cursor cursor = getContentResolver().query(result, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        binding.AssignmentNameChecked.setText(cursor.getString(nameIndex));
        binding.expendableLayout2.setVisibility(View.VISIBLE);
        binding.submit.setEnabled(true);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH)+1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        if(hour == 0)
            hour = 12;
        final int minutes = calendar.get(Calendar.MINUTE);
        final String am_pm = calendar.get(Calendar.AM_PM)==Calendar.AM ? " AM" : " PM";

        final String currentDate = String.format(Locale.ENGLISH,"%02d/"+"%02d/",day,month)+year;
        final String currentTime = String.format(Locale.ENGLISH,"%02d:"+"%02d",hour,minutes)+am_pm;

        binding.time2.setText(currentTime);
        cursor.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}