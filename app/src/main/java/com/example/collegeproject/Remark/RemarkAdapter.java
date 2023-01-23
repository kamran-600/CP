package com.example.collegeproject.Remark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.attendance.AttendanceModel;

import java.util.List;

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {
    private List<AttendanceModel> userList;
    private int lastPosition=-1;


    public RemarkAdapter(List<AttendanceModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RemarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_remark,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemarkAdapter.ViewHolder holder, int position) {
        int resource = userList.get(position).getImage();
        String stuName = userList.get(position).getStuName();
        String roll = userList.get(position).getRoll();

        holder.setData(resource, stuName, roll);
        setAnimation(holder.itemView, position);

        /* *****************************************
                     Perform Rating
           ***************************************** */
        holder.RatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                holder.rating_no.setVisibility(View.VISIBLE);
                holder.rating_no.setText(v + "/" + ratingBar.getNumStars());
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView stuName;
        private final TextView roll;
        private final TextView rating_no;
        private AppCompatRatingBar RatingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            stuName = itemView.findViewById(R.id.stuName);
            roll = itemView.findViewById(R.id.roll);
            rating_no = itemView.findViewById(R.id.rating_no);
            RatingBar = itemView.findViewById(R.id.ratingBar);

        }

        public void setData(int resource, String stuName1, String roll1) {
            image.setImageResource(resource);
            stuName.setText(stuName1);
            roll.setText(roll1);

        }
    }
}
