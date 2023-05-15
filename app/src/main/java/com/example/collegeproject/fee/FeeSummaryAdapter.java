package com.example.collegeproject.fee;

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
        int resource = userList.get(position).getImage();
        String name = userList.get(position).getName();
        String rno = userList.get(position).getRno();
        String total = userList.get(position).getTotal();
         String submit = userList.get(position).getSubmit();
        holder.setData(resource, name, rno, total, submit);

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

        private ImageView image;
        private TextView Name;
        private TextView rno;
        private TextView total;
        private TextView submit;

        public ViewHolde(View view) {
            super(view);
            image = itemView.findViewById(R.id.image);
            Name = itemView.findViewById(R.id.stuName);
            rno = itemView.findViewById(R.id.stuRNumber);
            total = itemView.findViewById(R.id.totalFee);
            submit = itemView.findViewById(R.id.submitFee);
        }

        public void setData( int uImage,String name, String rnum, String totalFee, String submit1) {
            image.setImageResource(uImage);
            Name.setText(name);
            rno.setText(rnum);
            total.setText(totalFee);
            submit.setText(submit1);
        }
    }
}
