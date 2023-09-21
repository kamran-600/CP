package com.example.collegeproject.BottomFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Chat.ChatAdapter;
import com.example.collegeproject.Chat.ChatModel;
import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ChatModel> userList;
    ChatAdapter adapter;
    HomeActivity homeActivity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatsFragment newInstance(String param1, String param2) {
        ChatsFragment fragment = new ChatsFragment();
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
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = v.findViewById(R.id.recyclerview);

        initData();
        initRecyclerView();

        homeActivity = (HomeActivity) getActivity();
        homeActivity.setSupportActionBar(v.findViewById(R.id.topAppBar));
        homeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);


         /* *****************************************
                          hide bottom bar
            ***************************************** */

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        return v;
    }

    /* *****************************************
            initialize the data for adapter
       ***************************************** */
    private void initData() {

        userList = new ArrayList<>();

        userList.add(new ChatModel(R.drawable.cse, "CS/IT Department", "First Year", "Good Morning Everyone",
                "7:05 am"));
        userList.add(new ChatModel(R.drawable.c, "CS/IT Department", "Second Year", "Good Night Everyone",
                "8:05 pm"));
        userList.add(new ChatModel(R.drawable.cs2, "CS/IT Department", "Third Year", "Good Afternoon Everyone",
                "2:05 pm"));
        userList.add(new ChatModel(R.drawable.cse, "CS/IT Department", "First Year", "Good Morning Everyone",
                "7:05 am"));
        userList.add(new ChatModel(R.drawable.c, "CS/IT Department", "Second Year", "Good Night Everyone",
                "8:05 pm"));
        userList.add(new ChatModel(R.drawable.cs2, "CS/IT Department", "Third Year", "Good Afternoon Everyone",
                "2:05 pm"));
        userList.add(new ChatModel(R.drawable.cse, "CS/IT Department", "First Year", "Good Morning Everyone",
                "7:05 am"));
        userList.add(new ChatModel(R.drawable.c, "CS/IT Department", "Second Year", "Good Night Everyone",
                "8:05 pm"));
        userList.add(new ChatModel(R.drawable.cs2, "CS/IT Department", "Third Year", "Good Afternoon Everyone",
                "2:05 pm"));
        userList.add(new ChatModel(R.drawable.cse, "CS/IT Department", "First Year", "Good Morning Everyone",
                "7:05 am"));
        userList.add(new ChatModel(R.drawable.c, "CS/IT Department", "Second Year", "Good Night Everyone",
                "8:05 pm"));
        userList.add(new ChatModel(R.drawable.cs2, "CS/IT Department", "Third Year", "Good Afternoon Everyone",
                "2:05 pm"));
        userList.add(new ChatModel(R.drawable.cse, "CS/IT Department", "First Year", "Good Morning Everyone",
                "7:05 am"));
        userList.add(new ChatModel(R.drawable.c, "CS/IT Department", "Second Year", "Good Night Everyone",
                "8:05 pm"));
        userList.add(new ChatModel(R.drawable.cs2, "CS/IT Department", "Third Year", "Good Afternoon Everyone",
                "2:05 pm"));
        userList.add(new ChatModel(R.drawable.cse, "CS/IT Department", "First Year", "Good Morning Everyone",
                "7:05 am"));
        userList.add(new ChatModel(R.drawable.c, "CS/IT Department", "Second Year", "Good Night Everyone",
                "8:05 pm"));
        userList.add(new ChatModel(R.drawable.cs2, "CS/IT Department", "Third Year", "Good Afternoon Everyone",
                "2:05 pm"));
        userList.add(new ChatModel(R.drawable.cse, "CS/IT Department", "First Year", "Good Morning Everyone",
                "7:05 am"));
        userList.add(new ChatModel(R.drawable.c, "CS/IT Department", "Second Year", "Good Night Everyone",
                "8:05 pm"));
        userList.add(new ChatModel(R.drawable.cs2, "CS/IT Department", "Third Year", "Good Afternoon Everyone",
                "2:05 pm"));
    }

    /* *****************************************
                set data to adapter
       ***************************************** */
    private void initRecyclerView() {

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
