package com.example.collegeproject.BottomFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;
import com.example.collegeproject.databinding.FragmentHomeBinding;
import com.example.collegeproject.databinding.TextFeedBinding;
import com.example.collegeproject.feed.FeedPostActivity;
import com.example.collegeproject.feed.adapter.FeedAdapter;
import com.example.collegeproject.feed.models.ImageFeedModel;
import com.example.collegeproject.feed.models.Item;
import com.example.collegeproject.feed.models.TextFeedModel;
import com.example.collegeproject.feed.models.TextImageFeedModel;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        homeActivity = (HomeActivity) getActivity();
        homeActivity.setSupportActionBar(binding.topAppBar);
        homeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);

        binding.feedPost.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), FeedPostActivity.class));
        });

        List<Item> items = new ArrayList<>();

        ImageFeedModel imageFeedModel = new ImageFeedModel("Kamran Alam",R.drawable.a0,R.drawable.a2);
        items.add(new Item(1,imageFeedModel));

        TextFeedModel textFeedModel = new TextFeedModel("Ram Singh","hii everyone...",R.drawable.a2);
        items.add(new Item(0,textFeedModel));

        TextImageFeedModel textImageFeedModel = new TextImageFeedModel("Kamran Alam","Good Morning",R.drawable.a4,R.drawable.a5);
        items.add(new Item(2,textImageFeedModel));


        binding.feedRecyclerview.setAdapter(new FeedAdapter(items));

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