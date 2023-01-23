package com.example.collegeproject.attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<AttendanceModel> userList;
    private int lastPosition=-1;


    public AttendanceAdapter(List<AttendanceModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_take_attendance,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
        int resource = userList.get(position).getImage();
        String stuName = userList.get(position).getStuName();
        String roll = userList.get(position).getRoll();
        holder.setData(resource,stuName,roll);
        setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView stuName;
        private TextView roll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            stuName = itemView.findViewById(R.id.stuName);
            roll = itemView.findViewById(R.id.roll);

        }
        public void setData(int resource, String stuName1, String roll1) {
            image.setImageResource(resource);
            stuName.setText(stuName1);
            roll.setText(roll1);

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
