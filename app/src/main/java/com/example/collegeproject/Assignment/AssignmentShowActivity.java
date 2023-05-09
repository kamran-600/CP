package com.example.collegeproject.Assignment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityAssignmentShowBinding;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssignmentShowActivity extends AppCompatActivity {

    ActivityAssignmentShowBinding binding;
    List<AssignmentSubmitModal> userList;
    LinearLayoutManager layoutManager;
    AssignmentSubmitAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();


        String url = getIntent().getStringExtra("url");

        storageReference = storage.getReferenceFromUrl(url);
        storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                binding.docTitle.setText(storageMetadata.getName());
                Double i = Double.valueOf(storageMetadata.getSizeBytes());
                if (i < 900000) {
                    i /= Math.pow(10, 3);
                    binding.docSize.setText(String.format("%.2f", i) + " KB");
                } else {
                    i /= Math.pow(10, 6);
                    binding.docSize.setText(String.format("%.2f", i) + " MB");
                }
                binding.docType.setText(storageMetadata.getContentType().split("/")[1]);

                binding.dueDate.setText(getIntent().getStringExtra("dueDate"));
                binding.desc.setText(getIntent().getStringExtra("desc"));
            }
        });

            storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, new ByteArrayOutputStream());
                    binding.docImage.setImageBitmap(bitmap);
                    binding.docImage.setOnClickListener(v -> {
                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                        intent.putExtra("byte", bytes);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(AssignmentShowActivity.this, Pair.create(binding.docImage, "ImageTransition"));
                        startActivity(intent, optionsCompat.toBundle());
                    });
                }
            });



        // teacher

        db.collection("College_Project").document("teacher").collection("teacher_details")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.listOfStudentText.setVisibility(View.VISIBLE);
                                    binding.recyclerview.setVisibility(View.VISIBLE);

                                }
                            }
                            if(binding.listOfStudentText.getVisibility() == View.GONE){
                                binding.AttachAssignmentBtnStudent.setVisibility(View.VISIBLE);
                                binding.submit.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        binding.AttachAssignmentBtnStudent.setOnClickListener(v -> {
            docLauncher.launch("application/pdf");
        });

        binding.clear.setOnClickListener(v -> {
            binding.AssignmentCardStudent.setVisibility(View.GONE);
            binding.submit.setEnabled(false);
        });

        binding.submit.setOnClickListener(view -> {
            binding.submit.setEnabled(false);
            Snackbar.make(view, "Assignment Submitted Successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressed();
                }
            }, 1000);

        });


        initData();
        initRecyclerView();


    }

    ActivityResultLauncher<String> docLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result != null){
                getTitleAndSize(result);
            }
        }
    });

    private void getTitleAndSize(Uri result) {

        Cursor cursor = getContentResolver().query(result, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        binding.CompletedAssignmentName.setText(cursor.getString(nameIndex));
        cursor.close();

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

        binding.AssignmentCardStudent.setVisibility(View.VISIBLE);
        binding.submit.setEnabled(true);
    }

    private void initData() {
        userList = new ArrayList<>();

        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Kamran", "12/01/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Aftab", "13/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Alam", "14/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Arif", "15/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Arshad", "16/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Rehan", "24/12/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Kamran", "12/01/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Aftab", "13/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Alam", "14/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Arif", "15/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Arshad", "16/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Rehan", "24/12/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Kamran", "12/01/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Aftab", "13/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Alam", "14/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Arif", "15/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Arshad", "16/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Rehan", "24/12/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Kamran", "12/01/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Aftab", "13/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Alam", "14/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Arif", "15/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Arshad", "16/10/2023", "12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon, "Rehan", "24/12/2023", "12:10AM"));

    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new AssignmentSubmitAdapter(userList);
        binding.recyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.recyclerview.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();

    }
}