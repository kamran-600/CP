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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import de.hdodenhof.circleimageview.CircleImageView;

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
    List<Item> userList;
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

        userList = new ArrayList<>();

        // show the profile image

        db.collection("College_Project").document("student").collection("4th Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                StudentData data = rollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){

                                    if(data.getProfileImageBlob() != null){
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.profilePic.setImageBitmap(fullBitmap);

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

                                    if(data.getProfileImageBlob() != null){
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.profilePic.setImageBitmap(fullBitmap);

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

                                    if(data.getProfileImageBlob() != null){
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.profilePic.setImageBitmap(fullBitmap);

                                    }                                }
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

                                    if(data.getProfileImageBlob() != null){
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.profilePic.setImageBitmap(fullBitmap);
                                    }                                }
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

                                    if(data.getProfileImageBlob() != null){
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.profilePic.setImageBitmap(fullBitmap);
                                    }                                }
                            }
                        }
                    }
                });



        homeActivity = (HomeActivity) getActivity();
        homeActivity.setSupportActionBar(binding.topAppBar);
        homeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);

        binding.feedPost.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), FeedPostActivity.class));
        });

        // get data from db to show the feed

        db.collection("College_Project").document("feed").collection("feed_details")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                    TextImageFeedModel data = documentSnapshot.toObject(TextImageFeedModel.class);
                                    if(data!= null){
                                        if(data.getFeedImageByteBlob()==null && data.getFeedMsg() !=null){
                                            TextFeedModel textFeedModel = new TextFeedModel(data.getSenderName(), data.getFeedMsg(), data.getDate(), data.getTime(), data.getRole(), data.getRoll_number(), data.getEmail());
                                            userList.add(0,new Item(0, textFeedModel));
                                        } else if (data.getFeedMsg() == null && data.getFeedImageByteBlob() !=null) {
                                            ImageFeedModel imageFeedModel = new ImageFeedModel(data.getSenderName(), data.getDate(), data.getTime(), data.getRole(), data.getRoll_number(), data.getEmail(), data.getFeedImageByteBlob());
                                            userList.add(0,new Item(1,imageFeedModel));
                                        }
                                        else{
                                            userList.add(0,new Item(2, data));
                                        }
                                        feedAdapter = new FeedAdapter(userList);
                                        binding.feedRecyclerview.setAdapter(feedAdapter);
                                        feedAdapter.notifyDataSetChanged();
                                    }
                            }
                            if(userList.size() == 0){
                                Toast.makeText(getContext(), "No feed are available", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // get data from db to show the feed

                db.collection("College_Project").document("feed").collection("feed_details")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    int size = userList.size();
                                    userList.clear();
                                    if(size !=0){
                                        feedAdapter.notifyItemRangeRemoved(0, size);
                                    }
                                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                        TextImageFeedModel data = documentSnapshot.toObject(TextImageFeedModel.class);
                                        if(data!= null){
                                            if(data.getFeedImageByteBlob()==null && data.getFeedMsg() !=null){
                                                TextFeedModel textFeedModel = new TextFeedModel(data.getSenderName(), data.getFeedMsg(), data.getDate(), data.getTime(), data.getRole(), data.getRoll_number(), data.getEmail());
                                                userList.add(0,new Item(0, textFeedModel));
                                            } else if (data.getFeedMsg() == null && data.getFeedImageByteBlob() !=null) {
                                                ImageFeedModel imageFeedModel = new ImageFeedModel(data.getSenderName(), data.getDate(), data.getTime(), data.getRole(), data.getRoll_number(), data.getEmail(), data.getFeedImageByteBlob());
                                                userList.add(0,new Item(1,imageFeedModel));
                                            }
                                            else{
                                                userList.add(0,new Item(2, data));
                                            }
                                            feedAdapter = new FeedAdapter(userList);
                                            binding.feedRecyclerview.setAdapter(feedAdapter);
                                            feedAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    if(userList.size() == 0){
                                        Toast.makeText(getContext(), "No feed are available", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

                binding.swipeRefresh.setRefreshing(false);


            }


        });

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

}