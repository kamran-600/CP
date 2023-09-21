package com.example.collegeproject.BottomFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Contacts.ContactAdapter;
import com.example.collegeproject.Contacts.ContactModel;
import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;
import com.example.collegeproject.databinding.FragmentContactsBinding;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String roll, name, phoneNo;
    LinearLayoutManager layoutManager;
    List<ContactModel> userList;
    ContactAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FragmentContactsBinding binding;
    HomeActivity homeActivity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        userList = new ArrayList<>();


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        homeActivity = (HomeActivity) getActivity();
        homeActivity.setSupportActionBar(binding.topAppBar);
        homeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);


        Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.recyclerview, R.layout.contacts_single_row, 10);
        skeleton.showSkeleton();

        // teacher

        db.collection("College_Project").document("teacher").collection("teacher_details").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot teacherEmail : task.getResult().getDocuments()) {
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {

                                    binding.topAppBar.inflateMenu(R.menu.years_menu);

                                    db.collection("College_Project").document("student").collection("4th Year").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        skeleton.showOriginal();
                                                        binding.topAppBar.setTitle("Contacts of 4th Year");
                                                        userList.clear();
                                                        for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                                            if (data != null) {
                                                                phoneNo = data.getPersonal_phone();
                                                                name = data.getFull_name();
                                                                roll = data.getRoll_number();
                                                                userList.add(new ContactModel(data.getProfileImageBlob(), name, roll, phoneNo));
                                                                adapter = new ContactAdapter(userList);
                                                                binding.recyclerview.setAdapter(adapter);
                                                                adapter.notifyDataSetChanged();
                                                            }

                                                        }
                                                        if (userList.size() == 0) {
                                                            Toast.makeText(getContext(), "No Student enrolled in 4th Year", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });

                                    binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {
                                            skeleton.showSkeleton();
                                            switch (item.getItemId()) {
                                                case R.id.first:
                                                    db.collection("College_Project").document("student").collection("1st Year").get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        binding.topAppBar.setTitle("Contacts of 1st Year");
                                                                        int size = userList.size();
                                                                        userList.clear();
                                                                        if (size != 0) {
                                                                            adapter.notifyItemRangeRemoved(0, size);
                                                                        }
                                                                        skeleton.showOriginal();
                                                                        for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                                                            if (data != null) {
                                                                                phoneNo = data.getPersonal_phone();
                                                                                name = data.getFull_name();
                                                                                roll = data.getRoll_number();
                                                                                userList.add(new ContactModel(data.getProfileImageBlob(), name, roll, phoneNo));
                                                                                adapter = new ContactAdapter(userList);
                                                                                binding.recyclerview.setAdapter(adapter);
                                                                                adapter.notifyDataSetChanged();
                                                                            }

                                                                        }
                                                                        if (userList.size() == 0) {
                                                                            Toast.makeText(getContext(), "No Student enrolled in 1st Year", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    }
                                                                }
                                                            });
                                                    break;

                                                case R.id.second:
                                                    db.collection("College_Project").document("student").collection("2nd Year").get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        binding.topAppBar.setTitle("Contacts of 2nd Year");
                                                                        int size = userList.size();
                                                                        userList.clear();
                                                                        if (size != 0) {
                                                                            adapter.notifyItemRangeRemoved(0, size);
                                                                        }
                                                                        skeleton.showOriginal();
                                                                        for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                                                            if (data != null) {
                                                                                phoneNo = data.getPersonal_phone();
                                                                                name = data.getFull_name();
                                                                                roll = data.getRoll_number();
                                                                                userList.add(new ContactModel(data.getProfileImageBlob(), name, roll, phoneNo));
                                                                                adapter = new ContactAdapter(userList);
                                                                                binding.recyclerview.setAdapter(adapter);
                                                                                adapter.notifyDataSetChanged();
                                                                            }

                                                                        }
                                                                        if (userList.size() == 0) {
                                                                            Toast.makeText(getContext(), "No Student enrolled in 2nd Year", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    }
                                                                }
                                                            });
                                                    break;

                                                case R.id.third:
                                                    db.collection("College_Project").document("student").collection("3rd Year").get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        binding.topAppBar.setTitle("Contacts of 3rd Year");
                                                                        int size = userList.size();
                                                                        userList.clear();
                                                                        if (size != 0) {
                                                                            adapter.notifyItemRangeRemoved(0, size);
                                                                        }
                                                                        skeleton.showOriginal();
                                                                        for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                                                            if (data != null) {
                                                                                phoneNo = data.getPersonal_phone();
                                                                                name = data.getFull_name();
                                                                                roll = data.getRoll_number();
                                                                                userList.add(new ContactModel(data.getProfileImageBlob(), name, roll, phoneNo));
                                                                                adapter = new ContactAdapter(userList);
                                                                                binding.recyclerview.setAdapter(adapter);
                                                                                adapter.notifyDataSetChanged();
                                                                            }

                                                                        }
                                                                        if (userList.size() == 0) {
                                                                            Toast.makeText(getContext(), "No Student enrolled in 3rd Year", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                    break;

                                                case R.id.fourth:
                                                    db.collection("College_Project").document("student").collection("4th Year").get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        binding.topAppBar.setTitle("Contacts of 4th Year");
                                                                        int size = userList.size();
                                                                        userList.clear();
                                                                        if (size != 0) {
                                                                            adapter.notifyItemRangeRemoved(0, size);
                                                                        }
                                                                        skeleton.showOriginal();
                                                                        for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                                                            if (data != null) {
                                                                                phoneNo = data.getPersonal_phone();
                                                                                name = data.getFull_name();
                                                                                roll = data.getRoll_number();
                                                                                userList.add(new ContactModel(data.getProfileImageBlob(), name, roll, phoneNo));
                                                                                adapter = new ContactAdapter(userList);
                                                                                binding.recyclerview.setAdapter(adapter);
                                                                                adapter.notifyDataSetChanged();
                                                                            }

                                                                        }
                                                                        if (userList.size() == 0) {
                                                                            Toast.makeText(getContext(), "No Student enrolled in 4th Year", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                    break;
                                            }

                                            return true;
                                        }


                                    });

                                }
                            }
                        }
                    }
                });


        // student

        db.collection("College_Project").document("student").collection("1st Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    db.collection("College_Project").document("teacher").collection("teacher_details").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        skeleton.showOriginal();
                                                        for (DocumentSnapshot teacherEmail : task.getResult().getDocuments()) {
                                                            TeacherData data1 = teacherEmail.toObject(TeacherData.class);
                                                            if (data1 != null) {
                                                                phoneNo = data1.getPhone_no();
                                                                name = data1.getFull_name();

                                                                userList.add(new ContactModel(data1.getProfileImageBlob(), name, data1.getEmail(), phoneNo));
                                                                adapter = new ContactAdapter(userList);
                                                                binding.recyclerview.setAdapter(adapter);
                                                                adapter.notifyDataSetChanged();
                                                            }

                                                        }
                                                        if (userList.size() == 0) {
                                                            Toast.makeText(getContext(), "No Teacher details are available", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });

        db.collection("College_Project").document("student").collection("2nd Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    db.collection("College_Project").document("teacher").collection("teacher_details").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        skeleton.showOriginal();
                                                        for (DocumentSnapshot teacherEmail : task.getResult().getDocuments()) {
                                                            TeacherData data1 = teacherEmail.toObject(TeacherData.class);
                                                            if (data1 != null) {
                                                                phoneNo = data1.getPhone_no();
                                                                name = data1.getFull_name();

                                                                userList.add(new ContactModel(data1.getProfileImageBlob(), name, data1.getEmail(), phoneNo));
                                                                adapter = new ContactAdapter(userList);
                                                                binding.recyclerview.setAdapter(adapter);
                                                                adapter.notifyDataSetChanged();
                                                            }

                                                        }
                                                        if (userList.size() == 0) {
                                                            Toast.makeText(getContext(), "No Teacher details are available", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });

        db.collection("College_Project").document("student").collection("3rd Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    db.collection("College_Project").document("teacher").collection("teacher_details").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        skeleton.showOriginal();
                                                        for (DocumentSnapshot teacherEmail : task.getResult().getDocuments()) {
                                                            TeacherData data1 = teacherEmail.toObject(TeacherData.class);
                                                            if (data1 != null) {
                                                                phoneNo = data1.getPhone_no();
                                                                name = data1.getFull_name();

                                                                userList.add(new ContactModel(data1.getProfileImageBlob(), name, data1.getEmail(), phoneNo));
                                                                adapter = new ContactAdapter(userList);
                                                                binding.recyclerview.setAdapter(adapter);
                                                                adapter.notifyDataSetChanged();
                                                            }

                                                        }
                                                        if (userList.size() == 0) {
                                                            Toast.makeText(getContext(), "No Teacher details are available", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });

        db.collection("College_Project").document("student").collection("4th Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    db.collection("College_Project").document("teacher").collection("teacher_details").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        skeleton.showOriginal();
                                                        for (DocumentSnapshot teacherEmail : task.getResult().getDocuments()) {
                                                            TeacherData data1 = teacherEmail.toObject(TeacherData.class);
                                                            if (data1 != null) {
                                                                phoneNo = data1.getPhone_no();
                                                                name = data1.getFull_name();

                                                                userList.add(new ContactModel(data1.getProfileImageBlob(), name, data1.getEmail(), phoneNo));
                                                                adapter = new ContactAdapter(userList);
                                                                binding.recyclerview.setAdapter(adapter);
                                                                adapter.notifyDataSetChanged();
                                                            }

                                                        }
                                                        if (userList.size() == 0) {
                                                            Toast.makeText(getContext(), "No Teacher details are available", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });



          /* *****************************************
                          hide bottom bar
             ***************************************** */

        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                HomeActivity homeActivity = (HomeActivity) getActivity();
                // scroll down

                if (dy > 0 && homeActivity.findViewById(R.id.bottom).getVisibility() == View.VISIBLE) {

                    homeActivity.findViewById(R.id.bottom).setVisibility(View.GONE);
                    TranslateAnimation animate = new TranslateAnimation(0, 0, 0, homeActivity.findViewById(R.id.bottom).getHeight());
                    animate.setDuration(400);
                    homeActivity.findViewById(R.id.bottom).startAnimation(animate);

                }
                // scroll up
                if (dy < -10 && homeActivity.findViewById(R.id.bottom).getVisibility() == View.GONE) {
                    homeActivity.findViewById(R.id.bottom).setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(0, 0, homeActivity.findViewById(R.id.bottom).getHeight(), 0);
                    // duration of animation
                    animate.setDuration(200);
                    animate.setFillAfter(true);
                    homeActivity.findViewById(R.id.bottom).startAnimation(animate);
                }
            }
        });


        return binding.getRoot();
    }

}