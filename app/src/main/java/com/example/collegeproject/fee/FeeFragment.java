package com.example.collegeproject.fee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;
import com.example.collegeproject.databinding.FragmentFeeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentFeeBinding binding;
    String department, year;
    LinearLayoutManager layoutManager;
    List<FeeModel> userList;
    FeeAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    HomeActivity homeActivity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeeFragment newInstance(String param1, String param2) {
        FeeFragment fragment = new FeeFragment();
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
        binding = FragmentFeeBinding.inflate(inflater, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        initData();
        initRecyclerView();

        homeActivity = (HomeActivity) getActivity();
        homeActivity.setSupportActionBar(binding.topAppBar);
        homeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);


        /* *****************************************
                        Hide Bottom Bar
           ***************************************** */
        binding.recyclerview1.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

                if (dy < -5 && homeActivity.findViewById(R.id.bottom).getVisibility() == View.GONE) {

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

    private void initRecyclerView() {

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerview1.setLayoutManager(layoutManager);
        adapter = new FeeAdapter(userList);
        binding.recyclerview1.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void initData() {

        userList = new ArrayList<>();

        userList.add(new FeeModel(R.drawable.cse, "C.S.E Department", "1st Year"));
        userList.add(new FeeModel(R.drawable.cse, "C.S.E Department", "2nd Year"));
        userList.add(new FeeModel(R.drawable.cse, "C.S.E Department", "3rd Year"));
        userList.add(new FeeModel(R.drawable.cse, "C.S.E Department", "4th Year"));

    }
}