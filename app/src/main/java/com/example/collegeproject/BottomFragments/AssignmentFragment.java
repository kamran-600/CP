package com.example.collegeproject.BottomFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Assignment.AssignmentAdapter;
import com.example.collegeproject.Assignment.AssignmentModal;
import com.example.collegeproject.Assignment.CreateAssignmentActivity;
import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;
import com.example.collegeproject.databinding.FragmentAssignmentBinding;

import java.util.ArrayList;
import java.util.List;

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
    LinearLayoutManager layoutManager;
    List<AssignmentModal> userList;
    AssignmentAdapter adapter;

    private FragmentAssignmentBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAssignmentBinding.inflate(inflater, container, false);


        binding.extendedFab.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), CreateAssignmentActivity.class));

        });

        initData();
        initRecyclerView();

         /* *****************************************
                          hide bottom bar
            ***************************************** */
        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                HomeActivity homeActivity = (HomeActivity) getActivity();
                // scroll down
                if (dy > 0 && binding.extendedFab.isExtended()) {
                    homeActivity.findViewById(R.id.bottom).setVisibility(View.GONE);
                    binding.extendedFab.shrink();
                }
                // scroll up
                if (dy < -10) {
                    homeActivity.findViewById(R.id.bottom).setVisibility(View.VISIBLE);
                    binding.extendedFab.extend();
                }
            }
        });

        return binding.getRoot();
    }

    /* *****************************************
                  set data to adapter
       ***************************************** */
    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new AssignmentAdapter(userList);
        binding.recyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        binding.recyclerview.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();

    }

    /* *****************************************
            initialize the data for adapter
       ***************************************** */
    private void initData() {
        userList = new ArrayList<>();

        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));


    }
}