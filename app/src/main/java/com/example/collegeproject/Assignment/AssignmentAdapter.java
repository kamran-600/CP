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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_single_row, parent, false);
//        AssignmentSingleRowBinding binding = AssignmentSingleRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
//        return new ViewHolder(binding);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int resource = userList.get(position).getImage();
        String tName = userList.get(position).gettName();
        String className = userList.get(position).getClassName();
        String desc = userList.get(position).getDesc();
        String duedate = userList.get(position).getDueDate();
        String time = userList.get(position).getTime();
        String date = userList.get(position).getDate();
        String url = userList.get(position).getAssignmentUrl();

        holder.setData(resource, tName, className, desc, duedate,date, time);
        setAnimation(holder.itemView, position);

        // intent to Show Assignment
        holder.itemView.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Intent intent = new Intent(activity, AssignmentShowActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("postedDate",date+", "+time);
            intent.putExtra("dueDate", duedate);
            intent.putExtra("desc", desc);
            intent.putExtra("id", userList.get(position).getId());
            activity.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation slideIn = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(slideIn);
            lastPosition = position;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView tName;
        private TextView className;
        private TextView desc;
        private TextView dueDate;
        private TextView time;
        private TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tName = itemView.findViewById(R.id.tName);
            className = itemView.findViewById(R.id.className);
            desc = itemView.findViewById(R.id.desc);
            dueDate = itemView.findViewById(R.id.dueDate);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);

        }

        public void setData(int resource, String tName1, String className1, String desc1, String dueDate1, String date1, String time1) {
            image.setImageResource(resource);
            tName.setText(tName1);
            className.setText(className1);
            desc.setText(desc1);
            desc.setSelected(true);
            dueDate.setText(dueDate1);
            date.setText(date1);
            time.setText(time1);
        }
    }

}