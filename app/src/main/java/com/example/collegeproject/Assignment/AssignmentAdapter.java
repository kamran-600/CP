package com.example.collegeproject.Assignment;

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


public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private List<AssignmentModal> userList;
    private int lastPosition = -1;

    public AssignmentAdapter(List<AssignmentModal> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_single_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.ViewHolder holder, int position) {

        int resource = userList.get(position).getImage();
        String tName = userList.get(position).gettName();
        String className = userList.get(position).getClassName();
        String desc = userList.get(position).getDesc();
        String duedate = userList.get(position).getDueDate();
        String time = userList.get(position).getTime();

        holder.setData(resource,tName,className,desc,duedate,time);
        setAnimation(holder.itemView,position);

        // intent to Show Assignment
        holder.itemView.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.startActivity(new Intent(activity,AssignmentShowActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView tName;
        private TextView className;
        private TextView desc;
        private TextView dueDate;
        private TextView time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tName = itemView.findViewById(R.id.tName);
            className = itemView.findViewById(R.id.className);
            desc = itemView.findViewById(R.id.desc);
            dueDate = itemView.findViewById(R.id.dueDate);
            time = itemView.findViewById(R.id.time);

        }
        public void setData(int resource, String tName1, String className1, String desc1, String duedate1, String time1) {
            image.setImageResource(resource);
            tName.setText(tName1);
            className.setText(className1);
            desc.setText(desc1);
            desc.setSelected(true);
            dueDate.setText(duedate1);
            time.setText(time1);
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