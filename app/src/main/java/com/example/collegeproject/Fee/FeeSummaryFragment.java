package com.example.collegeproject.Fee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collegeproject.Chat.ChatAdapter;
import com.example.collegeproject.Chat.ChatModel;
import com.example.collegeproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeeSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeeSummaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeeSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeeSummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeeSummaryFragment newInstance(String param1, String param2) {
        FeeSummaryFragment fragment = new FeeSummaryFragment();
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
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<FeeSummaryModel> userList;
    FeeSummaryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fee_summary, container, false);
        recyclerView = v.findViewById(R.id.recyclerview);

        initData();
        initRecyclerView();

        return v;
    }

    private void initRecyclerView() {


        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FeeSummaryAdapter(userList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        userList = new ArrayList<>();

        userList.add(new FeeSummaryModel(R.drawable.a0, "Ram", "1904310100075", "85000",
                "50000"));
        userList.add(new FeeSummaryModel(R.drawable.a1, "Sita", " 19043101000759", "90000",
                "56000"));
        userList.add(new FeeSummaryModel(R.drawable.a5, "Mohan", "1904310100065", "89000",
                "50000"));
        userList.add(new FeeSummaryModel(R.drawable.a1, "Kamran", " 19043101000706", "98000",
                "55000"));
        userList.add(new FeeSummaryModel(R.drawable.a4, "Tanveer", "1904310100075", "85000",
                "50000"));
        userList.add(new FeeSummaryModel(R.drawable.a1, "Mark", " 19043101000756", "90000",
                "55000"));
        userList.add(new FeeSummaryModel(R.drawable.a5, "Pinku", "1904310100075", "85000",
                "50000"));
        userList.add(new FeeSummaryModel(R.drawable.a2, "Tony", " 19043101000756", "90000",
                "55000"));
    }
}