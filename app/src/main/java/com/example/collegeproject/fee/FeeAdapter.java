package com.example.collegeproject.fee;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;

import java.util.List;

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.ViewHolde> {
    private List<FeeModel> userList;
    private int lastPosition=-1;

    public  FeeAdapter (List<FeeModel> userList){
        this.userList = userList;

    }
    @NonNull
    @Override
    public FeeAdapter.ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fee_single_row,parent,false);
        return new FeeAdapter.ViewHolde(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FeeAdapter.ViewHolde holder, int position) {

        int resource = userList.get(position).getImage();
        String dname = userList.get(position).getdName();
        String ayear = userList.get(position).getaYear();
        holder.setData(resource,dname,ayear);
        setAnimation(holder.itemView,position);
        /* ********************************************
                   onclick perform on objects.
           ********************************************
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.startActivity(new Intent(activity, FeeSummaryActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    /* *****************************************
               Animate the RecyclerView
     ***************************************** */
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation slideIn = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(slideIn);
            lastPosition = position;
        }

    }

    public class ViewHolde extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView dName;
        private TextView aYear;


        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            dName = itemView.findViewById(R.id.departmentName);
            aYear = itemView.findViewById(R.id.aYear);
        }

        public void setData(int resource, String dname1, String ayear) {
            image.setImageResource(resource);
            dName.setText(dname1);
            aYear.setText(ayear);
        }
    }
}
