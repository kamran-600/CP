package com.example.collegeproject.fee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Assignment.AssignmentOpenActivity;
import com.example.collegeproject.R;
import com.google.firebase.firestore.Blob;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

public class FeeSummaryAdapter extends RecyclerView.Adapter<FeeSummaryAdapter.ViewHolde> {

    private final List<FeeSummaryModel> userList;
    private int lastPosition = -1;

    public FeeSummaryAdapter(List<FeeSummaryModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public FeeSummaryAdapter.ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fee_summary_single_row, parent, false);
        return new FeeSummaryAdapter.ViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeSummaryAdapter.ViewHolde holder, int position) {
        Blob resource = userList.get(position).getImageBlob();
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
        String name = userList.get(position).getName();
        String rno = userList.get(position).getRno();
        String total = userList.get(position).getTotal();
        if (Objects.equals(userList.get(position).getSubmit(), "") || userList.get(position).getSubmit() == null)
            holder.submit.setText("N/A");
        else holder.submit.setText(userList.get(position).getSubmit());
        holder.setData(name, rno, total);

        setAnimation(holder.itemView, position);
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

        private final ImageView image;
        private final TextView Name;
        private final TextView rno;
        private final TextView total;
        private final TextView submit;

        public ViewHolde(View view) {
            super(view);
            image = itemView.findViewById(R.id.image);
            Name = itemView.findViewById(R.id.stuName);
            rno = itemView.findViewById(R.id.stuRNumber);
            total = itemView.findViewById(R.id.totalFee);
            submit = itemView.findViewById(R.id.submitFee);
        }

        public void setData(String name, String rnum, String totalFee) {

            Name.setText(name);
            rno.setText(rnum);
            total.setText(totalFee);
        }
    }
}
