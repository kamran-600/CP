package com.example.collegeproject.Contacts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.collegeproject.Assignment.CreateAssignmentActivity;
import com.example.collegeproject.HomeActivity;
import com.example.collegeproject.R;

import java.util.List;
import java.util.Objects;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolde> {

    private List<ContactModel> userList;
    private int lastPosition=-1;

    Intent intent;


    public ContactAdapter(List<ContactModel> userList) {
        this.userList = userList;

    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_single_row,parent,false);
        return new ViewHolde(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        int resource = userList.get(position).getImage();
        String stuName = userList.get(position).getStuName();
        String stuRNumber = userList.get(position).getStuRNumber();
        String stuCNumber = userList.get(position).getStuCNumber();

        holder.setData(resource,stuName,stuRNumber,stuCNumber);

        setAnimation(holder.itemView,position);

        holder.call.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+ stuCNumber));
            activity.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolde extends  RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView rNumber;
        private TextView cNumber;
        ImageView call;
        private ImageView msg;



        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.stuName);
            rNumber = itemView.findViewById(R.id.stuRNumber);
            cNumber = itemView.findViewById(R.id.stuCNumber);
            call = itemView.findViewById(R.id.call);
            msg = itemView.findViewById(R.id.msg);


        }

        public void setData(int resource, String stuName, String stuRNumber, String stuCNumber) {
            image.setImageResource(resource);
            name.setText(stuName);
            rNumber.setText(stuRNumber);
            cNumber.setText(stuCNumber);

        }

    }
    private void setAnimation (View viewToAnimate, int position){
        if (position > lastPosition) {
            Animation slideIn = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(slideIn);
            lastPosition = position;
        }

    }

}