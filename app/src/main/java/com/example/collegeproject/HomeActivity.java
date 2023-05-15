package com.example.collegeproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.collegeproject.BottomFragments.AssignmentFragment;
import com.example.collegeproject.BottomFragments.ChatsFragment;
import com.example.collegeproject.BottomFragments.ContactsFragment;
import com.example.collegeproject.BottomFragments.HomeFragment;
import com.example.collegeproject.Progress.ProgressFragment;
import com.example.collegeproject.databinding.ActivityHomeBinding;
import com.example.collegeproject.studentData.Remark.RemarkFragment;
import com.example.collegeproject.attendance.ClassFragment;
import com.example.collegeproject.fee.FeeFragment;
import com.example.collegeproject.profile.ProfileActivity;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class HomeActivity extends AppCompatActivity {

    static final float END_SCALE = 0.7f;
    ActivityResultLauncher<String> requestLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ContactsFragment()).commit();
            } else {
                Toast.makeText(HomeActivity.this, "Call Permission Denied\nTo Allow Permission go to\n Setting < App Manager / App Permission", Toast.LENGTH_SHORT).show();
            }
        }
    });
    private ActivityHomeBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    /* *****************************************
                   Drawer Animation
       ***************************************** */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        animateNavDrawer(); //calling for animation

        Menu menu = binding.navigationView.getMenu(); //taking reference of logout menu option
        MenuItem menuItem = menu.findItem(R.id.logout);
        menu.findItem(R.id.progress).setVisible(false);


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


        binding.navigationView.setCheckedItem(R.id.home);

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
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        Toast.makeText(HomeActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finishAffinity();
                        break;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START,true);
                return true;
            }
        });

         /* *****************************************
                 goto to profile activity
           ***************************************** */
        View headerView = binding.navigationView.getHeaderView(0);
        ImageView edit = headerView.findViewById(R.id.edit);
        TextView name = headerView.findViewById(R.id.name);
        TextView role = headerView.findViewById(R.id.role);



        // student

        db.collection("College_Project").document("student").collection("4th Year").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                        StudentData data = rollNo.toObject(StudentData.class);
                                        if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                            name.setText(data.getFull_name());
                                            role.setText(data.getRole());
                                            menu.findItem(R.id.fee).setVisible(false);
                                        }
                                    }
                                }
                            }
                        });

        db.collection("College_Project").document("student").collection("3rd Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                StudentData data = rollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    name.setText(data.getFull_name());
                                    role.setText(data.getRole());
                                    menu.findItem(R.id.fee).setVisible(false);
                                }
                            }
                        }
                    }
                });

        db.collection("College_Project").document("student").collection("2nd Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                StudentData data = rollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    name.setText(data.getFull_name());
                                    role.setText(data.getRole());
                                    menu.findItem(R.id.fee).setVisible(false);
                                }
                            }
                        }
                    }
                });

        db.collection("College_Project").document("student").collection("1st Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    name.setText(data.getFull_name());
                                    role.setText(data.getRole());
                                    menu.findItem(R.id.fee).setVisible(false);
                                }
                            }
                        }
                    }
                });


        // teacher

        db.collection("College_Project").document("teacher").collection("teacher_details").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    name.setText(data.getFull_name());
                                    role.setText(data.getRole());
                                }
                            }
                        }
                    }
                });


/*
        db.collection("College_Project").document("C.S.E/student/2nd Year/"+mAuth.getCurrentUser().getUid()+"/"+mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    StudentData data = task.getResult().toObject(StudentData.class);
                    if(data !=null) {
                        name.setText(data.getFull_name());
                        role.setText("Student");
                    }
                }
            }
        });

        db.collection("College_Project").document("C.S.E/student/1st Year/"+mAuth.getCurrentUser().getUid()+"/"+mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    StudentData data = task.getResult().toObject(StudentData.class);
                    if(data !=null) {
                        name.setText(data.getFull_name());
                        role.setText("Student");
                    }
                }
            }
        });


        // teacher

        db.collection("College_Project").document("C.S.E/teacher/"+mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    TeacherData data = task.getResult().toObject(TeacherData.class);
                    if(data !=null) {
                        name.setText(data.getFull_name());
                        role.setText(data.getRole());
                    }
                }
            }
        });*/




        //1
        /*db.collection("College_Project").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot department : task.getResult()){

                        db.collection("College_Project").document(department.getId()).collection("student")
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot academicYear : task.getResult()){
                                                System.out.println(academicYear.getId());
                                                db.collection("College_Project").document(department.getId()).collection("student")
                                                        .document(academicYear.getId()).collection(mAuth.getCurrentUser().getUid()).document(mAuth.getCurrentUser().getEmail())
                                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if(task.isSuccessful()){
                                                                    StudentData data = task.getResult().toObject(StudentData.class);

                                                                    System.out.println(data.getFull_name());
                                                                    name.setText(data.getFull_name());

                                                                    name.setSelected(true);
                                                                    role.setText("Student");
                                                                    role.setSelected(true);

                                                                }
                                                                else
                                                                    Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }
                                        else {
                                            Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            System.out.println(task.getException().getMessage());
                                        }                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                }
                else {
                    Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(task.getException().getMessage());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/


       /* db.collection("College_Project").document("student").collection("student_details")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                StudentData data = document.toObject(StudentData.class);
                                if (data != null) {
                                    db.collection("College_Project").document("student").collection("student_details")
                                            .document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful() && mAuth.getCurrentUser().getEmail().equals(task.getResult().getData().get("e_mail"))) {
                                                        // Toast.makeText(ProfileActivity.this, "data Found", Toast.LENGTH_SHORT).show();

                                                        name.setText(data.getFull_name());
                                                        name.setSelected(true);
                                                        role.setText("Student");
                                                        role.setSelected(true);
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                */


        //1
        /*db.collection("College_Project").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot department : task.getResult()) {
                        if (department != null) {
                            db.collection("College_Project").document(department.getId()).collection("teacher").document(mAuth.getCurrentUser().getEmail())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                TeacherData data = task.getResult().toObject(TeacherData.class);
                                                name.setText(data.getFull_name());
                                                name.setSelected(true);
                                                role.setText(data.getRole());
                                                name.setSelected(true);
                                            }
                                            else
                                                Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/






        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("name", name.getText().toString().trim());
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
                    if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // ActivityCompat.requestPermissions(CreateAssignmentActivity.this, new String[]{Manifest.permission.CAMERA},1);  //line 136-151
                        requestLauncher.launch(Manifest.permission.CALL_PHONE);
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ContactsFragment()).commit();
                    }
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
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                binding.bReplace.setScaleX(offsetScale);
                binding.bReplace.setScaleY(offsetScale);
                // translate the view accounting of the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = binding.bReplace.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                binding.bReplace.setTranslationX(xTranslation);
                binding.bottom.setVisibility(View.GONE);
            }


            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                binding.bottom.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                    binding.bottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                    binding.drawerLayout.openDrawer(GravityCompat.START,true);

        }
        return super.onOptionsItemSelected(item);
    }
}