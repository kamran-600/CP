package com.example.collegeproject.BottomFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.collegeproject.Assignment.AssignmentAdapter;
import com.example.collegeproject.Assignment.AssignmentModal;
import com.example.collegeproject.Assignment.AssignmentShowActivity;
import com.example.collegeproject.Assignment.CreateAssignmentActivity;
import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;
import com.example.collegeproject.databinding.FragmentAssignmentBinding;
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
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayoutManager layoutManager;
    List<AssignmentModal> userList;
    AssignmentAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AssignmentFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssignmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssignmentFragment newInstance(String param1, String param2) {
        AssignmentFragment fragment = new AssignmentFragment();
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
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private FragmentAssignmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAssignmentBinding.inflate(inflater, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userList = new ArrayList<>();


        homeActivity = (HomeActivity) getActivity();
        homeActivity.setSupportActionBar(binding.topAppBar);
        homeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);


        // show the fab button in (Teacher Perspective)

        db.collection("College_Project").document("teacher").collection("teacher_details")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.extendedFab.setVisibility(View.VISIBLE);

                                }
                            }
                        }
                    }
                });


        // show shimmer
        Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.recyclerview, R.layout.assignment_single_row, 10);
        skeleton.showSkeleton();

        //  show the assignment in recyclerview (Teacher perspective)

        db.collection("College_Project").document("teacher").collection("assignments")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            skeleton.showOriginal();
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                if(documentSnapshot!=null){
                                    AssignmentModal data = documentSnapshot.toObject(AssignmentModal.class);
                                    if(data !=null){
                                        userList.add(0,new AssignmentModal(data.getTeacherName(),documentSnapshot.getId(), data.getClassName(), data.getDesc(), data.getDueDate(), data.getDate(), data.getTime(),data.getAssignmentUrl(), data.getEmail()));
                                    }
                                }
                            }
                            if(userList.size() ==0){
                                Toast.makeText(getContext(), "No assignment is available", Toast.LENGTH_SHORT).show();
                            }
                            adapter = new AssignmentAdapter(userList);
                            binding.recyclerview.setAdapter(adapter);
                            if(getContext() != null){
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                                binding.recyclerview.addItemDecoration(dividerItemDecoration);
                                adapter.notifyDataSetChanged();
                            }



                        }
                    }
                });

        // pull to refresh
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // show shimmer
                skeleton.showSkeleton();

                //  show the assignment in recyclerview (Teacher perspective)

                db.collection("College_Project").document("teacher").collection("assignments")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    int size = userList.size();
                                    userList.clear();
                                    if(size !=0){
                                        adapter.notifyItemRangeRemoved(0, size);
                                    }
                                    skeleton.showOriginal();
                                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                        if(documentSnapshot!=null){
                                            AssignmentModal data = documentSnapshot.toObject(AssignmentModal.class);
                                            if(data !=null){
                                                userList.add(0,new AssignmentModal(data.getTeacherName(),documentSnapshot.getId(), data.getClassName(), data.getDesc(), data.getDueDate(), data.getDate(), data.getTime(),data.getAssignmentUrl(), data.getEmail()));
                                            }
                                        }
                                    }
                                    if(userList.size() ==0){
                                        Toast.makeText(getContext(), "No assignment is available", Toast.LENGTH_SHORT).show();
                                    }

                                    adapter = new AssignmentAdapter(userList);
                                    binding.recyclerview.setAdapter(adapter);
                                    if(getContext() != null){
                                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                                        binding.recyclerview.addItemDecoration(dividerItemDecoration);
                                        adapter.notifyDataSetChanged();
                                    }

                                }
                            }
                        });
                binding.swipeRefresh.setRefreshing(false);

            }
        });



        binding.extendedFab.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), CreateAssignmentActivity.class));
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
                    binding.extendedFab.shrink();
                }
                // scroll up

                if (dy < -5 && homeActivity.findViewById(R.id.bottom).getVisibility() == View.GONE) {

                    homeActivity.findViewById(R.id.bottom).setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(0, 0, homeActivity.findViewById(R.id.bottom).getHeight(), 0);
                    // duration of animation
                    animate.setDuration(200);
                    animate.setFillAfter(true);
                    homeActivity.findViewById(R.id.bottom).startAnimation(animate);
                    binding.extendedFab.extend();
                }
            }
        });

        return binding.getRoot();
    }



}