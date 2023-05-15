package com.example.collegeproject.BottomFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Assignment.AssignmentOpenActivity;
import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;
import com.example.collegeproject.databinding.FragmentHomeBinding;
import com.example.collegeproject.feed.FeedPostActivity;
import com.example.collegeproject.feed.adapter.FeedAdapter;
import com.example.collegeproject.feed.models.ImageFeedModel;
import com.example.collegeproject.feed.models.Item;
import com.example.collegeproject.feed.models.TextFeedModel;
import com.example.collegeproject.feed.models.TextImageFeedModel;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    HomeActivity homeActivity;
    List<Item> items;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FeedAdapter feedAdapter;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        db.collection("College_Project").document("student").collection("4th Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                StudentData data = rollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.profilePic.setImageBitmap(fullBitmap);
                                                    binding.profilePic.setOnClickListener(v -> {
                                                        Intent intent = new Intent(getContext(), AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(binding.profilePic, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
                                                }
                                            }
                                        });
                                    }
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

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.profilePic.setImageBitmap(fullBitmap);
                                                    binding.profilePic.setOnClickListener(v -> {
                                                        Intent intent = new Intent(getContext(), AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(binding.profilePic, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
                                                }
                                            }
                                        });
                                    }
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

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.profilePic.setImageBitmap(fullBitmap);
                                                    binding.profilePic.setOnClickListener(v -> {
                                                        Intent intent = new Intent(getContext(), AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(binding.profilePic, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
                                                }
                                            }
                                        });
                                    }
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

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.profilePic.setImageBitmap(fullBitmap);
                                                    binding.profilePic.setOnClickListener(v -> {
                                                        Intent intent = new Intent(getContext(), AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(binding.profilePic, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
                                                }
                                            }
                                        });
                                    }
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

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.profilePic.setImageBitmap(fullBitmap);
                                                    binding.profilePic.setOnClickListener(v -> {
                                                        Intent intent = new Intent(getContext(), AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(binding.profilePic, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                });



        homeActivity = (HomeActivity) getActivity();
        homeActivity.setSupportActionBar(binding.topAppBar);
        homeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);

        binding.feedPost.setOnClickListener(v -> {
            intentActivityResultLauncher.launch(new Intent(getContext(), FeedPostActivity.class) );
           // startActivity(new Intent(getContext(), FeedPostActivity.class));
        });

        items = new ArrayList<>();

        TextFeedModel textFeedModel = new TextFeedModel("Ram Singh","hii everyone...",R.drawable.a2);
        items.add(new Item(0,textFeedModel));

        ImageFeedModel imageFeedModel = new ImageFeedModel("Kamran Alam",R.drawable.a0,R.drawable.a2);
        items.add(new Item(1,imageFeedModel));

        TextImageFeedModel textImageFeedModel = new TextImageFeedModel("Kamran Alam","Good Morning",R.drawable.a4,R.drawable.a5);
        items.add(new Item(2,textImageFeedModel));


        feedAdapter = new FeedAdapter(items);

        binding.feedRecyclerview.setAdapter(feedAdapter);

           /* *****************************************
                          hide bottom bar
            ***************************************** */
        binding.feedRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                HomeActivity homeActivity = (HomeActivity) getActivity();
                // scroll down

                if (dy > 0 && homeActivity.findViewById(R.id.bottom).getVisibility() == View.VISIBLE) {
                   // binding.feedPost.setVisibility(View.GONE);
                    homeActivity.findViewById(R.id.bottom).setVisibility(View.GONE);
                    TranslateAnimation animate = new TranslateAnimation(0, 0, 0, homeActivity.findViewById(R.id.bottom).getHeight());
                    animate.setDuration(400);
                    homeActivity.findViewById(R.id.bottom).startAnimation(animate);

                }
                // scroll up

                if (dy < -5 && homeActivity.findViewById(R.id.bottom).getVisibility() == View.GONE) {
                    //binding.feedPost.setVisibility(View.GONE);
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

    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                items.add(new Item(0,new TextFeedModel(mAuth.getCurrentUser().getEmail(), result.getData().getStringExtra("feedText"), R.drawable.a0)));
              //  items.add(new Item(2,new TextImageFeedModel(mAuth.getCurrentUser().getEmail(), result.getData().getStringExtra("feedText"), result.getData().getParcelableExtra("feedImage") , result.getData().getParcelableExtra("feedImage"))));
                feedAdapter.notifyDataSetChanged();
            }
        }
    });

}