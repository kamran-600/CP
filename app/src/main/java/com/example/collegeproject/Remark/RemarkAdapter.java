package com.example.collegeproject.Remark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Assignment.AssignmentOpenActivity;
import com.example.collegeproject.R;
import com.example.collegeproject.attendance.AttendanceModel;
import com.google.firebase.firestore.Blob;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {
    private final List<AttendanceModel> userList;
    private int lastPosition = -1;


    public RemarkAdapter(List<AttendanceModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RemarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_remark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemarkAdapter.ViewHolder holder, int position) {
        Blob resource = userList.get(position).getProfileImageBlob();
        if (resource != null) {
            Bitmap fullBitmap = BitmapFactory.decodeByteArray(resource.toBytes(), 0, resource.toBytes().length);
            fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
            holder.image.setImageBitmap(fullBitmap);
            holder.image.setOnClickListener(v -> {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent intent = new Intent(activity, AssignmentOpenActivity.class);
                intent.putExtra("byte", resource.toBytes());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(holder.image, "ImageTransition"));
                activity.startActivity(intent, optionsCompat.toBundle());
            });
        } else {
            holder.image.setImageResource(R.drawable.cartoon);
        }
        String stuName = userList.get(position).getFull_name();
        String roll = userList.get(position).getRoll_number();
        holder.setData(stuName, roll);
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

        private final TextView roll;
        private final TextView rating_no;
        private final ImageView image;
        private final TextView stuName;
        private final AppCompatRatingBar RatingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            stuName = itemView.findViewById(R.id.stuName);
            roll = itemView.findViewById(R.id.roll);
            rating_no = itemView.findViewById(R.id.rating_no);
            RatingBar = itemView.findViewById(R.id.ratingBar);

        }

        public void setData(String stuName1, String roll1) {
            stuName.setText(stuName1);
            roll.setText(roll1);

        }
    }
}
