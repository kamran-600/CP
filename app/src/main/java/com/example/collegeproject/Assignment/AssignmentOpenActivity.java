package com.example.collegeproject.Assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityAssignmentOpenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class AssignmentOpenActivity extends AppCompatActivity {

    ActivityAssignmentOpenBinding binding;
    ScaleGestureDetector scaleGestureDetector;
    private float FACTOR = 1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentOpenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.postBar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        byte[] bytes = getIntent().getByteArrayExtra("byte");
        if(bytes != null){
            binding.image.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            binding.image.setImageBitmap(bitmap);
        }
        else {
            binding.progressBar.setVisibility(View.VISIBLE);
            //Snackbar.make(this, binding.pdfView, "wait a while", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            Toast.makeText(this, "wait a while", Toast.LENGTH_SHORT).show();
            String url = getIntent().getStringExtra("url");
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
            File file;
            try {
                file = File.createTempFile(getIntent().getStringExtra("name"),"");
                storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AssignmentOpenActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.GONE);
                        binding.pdfView.setVisibility(View.VISIBLE);
                        binding.pdfView.fromFile(file).show();
                    }
                });
            } catch (IOException e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(@NonNull ScaleGestureDetector detector) {
                FACTOR *= detector.getScaleFactor();
                FACTOR = Math.max(0.1f, Math.min(FACTOR, 10.f));
                binding.image.setScaleX(FACTOR);
                binding.image.setScaleY(FACTOR);
                return true;
            }
        });






    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}