package com.example.collegeproject.BottomFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.collegeproject.Contacts.ContactAdapter;
import com.example.collegeproject.Contacts.ContactModel;
import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;

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

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ContactModel> userList;
    ContactAdapter adapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView = v.findViewById(R.id.recyclerview);

        initData();
        initRecyclerView();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                HomeActivity homeActivity = (HomeActivity) getActivity();
                // scroll down
                if(dy > 15 ){
                    homeActivity.findViewById(R.id.appbarLayout).setVisibility(View.GONE);
                    homeActivity.findViewById(R.id.bottom).setVisibility(View.GONE);
                }
                // scroll up
                if(dy < -10 ){
                    homeActivity.findViewById(R.id.appbarLayout).setVisibility(View.VISIBLE);
                    homeActivity.findViewById(R.id.bottom).setVisibility(View.VISIBLE);
                }
            }
        });

     /*   searchView  = v.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);

             return true;
            }
        });


      */

        return v;
    }

  /*  private void filterList(String text) {
        List<ContactModel> filteredList = new ArrayList<>();
        for (ContactModel item : userList){
            if (item.getStuName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            if (filteredList.isEmpty()){
                Toast.makeText(getContext(), "No Student Find", Toast.LENGTH_SHORT).show();
            }else{
                ContactAdapter.setFilterdList(filteredList);
            }
        }
    }
   */


    private void initData () {

        userList = new ArrayList<>();

        userList.add(new ContactModel(R.drawable.a0, "Ram", "9568324125745", "9876543210"));
        userList.add(new ContactModel(R.drawable.a5, "Mark", "9568326352745", "9876536210"));
        userList.add(new ContactModel(R.drawable.a3, "Komal", "9568320005745", "98765363210"));
        userList.add(new ContactModel(R.drawable.a4, "Mahima", "9568300125745", "98766523210"));
        userList.add(new ContactModel(R.drawable.a5, "Soni", "9568324105745", "9876543000"));
        userList.add(new ContactModel(R.drawable.a3, "Hari", "9568324195745", "98765432650"));
        userList.add(new ContactModel(R.drawable.a3, "Kamran", "956832825745", "9876543220"));
        userList.add(new ContactModel(R.drawable.a5, "Mohan", "9568324665745", "9876543560"));
        userList.add(new ContactModel(R.drawable.a4, "Sita", "9568324165745", "9876543216"));
        userList.add(new ContactModel(R.drawable.a5, "Ganesh", "9568329625745", "98765432210"));
        userList.add(new ContactModel(R.drawable.a5, "Pappu", "9568324335745", "9876543213"));
        userList.add(new ContactModel(R.drawable.a0, "Sidharth", "9568334125745", "9876544210"));
        userList.add(new ContactModel(R.drawable.a3, "Yash", "9568324129745", "9876543217"));
        userList.add(new ContactModel(R.drawable.a4, "Ambika", "956832435745", "9876543610"));
        userList.add(new ContactModel(R.drawable.a3, "Babu Rao", "9568323125745", "9876443210"));
        userList.add(new ContactModel(R.drawable.a5, "Shyam", "9568324125795", "9876543610"));
        userList.add(new ContactModel(R.drawable.a4, "Raju Rastogi", "9568024125745", "8876543210"));
        userList.add(new ContactModel(R.drawable.a5, "Om Puri", "9568624125745", "9876563210"));
        userList.add(new ContactModel(R.drawable.a3, "Tanveer", "9568327125745", "9876546210"));
        userList.add(new ContactModel(R.drawable.a0, "Yash", "9568324155745", "9876543215"));
        userList.add(new ContactModel(R.drawable.a5, "Ram", "9568324126745", "9876543219"));
        userList.add(new ContactModel(R.drawable.a0, "Mark", "9568322525745", "9876543280"));
        userList.add(new ContactModel(R.drawable.a5, "Kamran", "9568354125745", "9876548210"));

    }

    private void initRecyclerView () {

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactAdapter(userList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();

    }
}