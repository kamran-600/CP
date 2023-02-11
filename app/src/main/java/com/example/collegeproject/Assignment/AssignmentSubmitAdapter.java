package com.example.collegeproject.Assignment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Chat.ConversationActivity;
import com.example.collegeproject.R;

import java.util.List;

public class AssignmentSubmitAdapter extends RecyclerView.Adapter<AssignmentSubmitAdapter.Viewholder> {


    List<AssignmentSubmitModal> userList;
    private int lastPosition = -1;

    public AssignmentSubmitAdapter(List<AssignmentSubmitModal> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public AssignmentSubmitAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_assignment_submit,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentSubmitAdapter.Viewholder holder, int position) {
        int resource = userList.get(position).getImage();
        String stuName = userList.get(position).getStuName();
        String submitDate = userList.get(position).getSubmitDate();
        //String submitTime = userList.get(position).getSubmitTime();
        holder.setData(resource, stuName, submitDate);
        setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Intent intent = new Intent(activity, AssignmentCheckActivity.class);
            intent.putExtra("image", resource);
            intent.putExtra("stuName", stuName);
            intent.putExtra("time",submitDate);
            // OR use below commented code
            //  ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(holder.dName,"dnameTransition"),Pair.create(holder.image,"imageTransition"));
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(holder.stuName, "stuNameTransition");
            pairs[1] = new Pair<View, String>(holder.image, "imageTransition");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
            activity.startActivity(intent, options.toBundle());
        });

/*
        boolean isExpanded = userList.get(position).isExpanded();

        holder.linearLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.relativeLayout.setOnClickListener(v -> {
                userList.get(position).setExpanded(!userList.get(position).isExpanded());
                notifyItemChanged(position);
        });

        holder.submitCheckedCopy.setOnClickListener(v -> {
            docLauncher.launch("application/pdf");
        });
    }

 */
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView stuName,submitDate;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            stuName = itemView.findViewById(R.id.stuName);
            submitDate = itemView.findViewById(R.id.submissionDate);

        }
        public void setData(int resource,String stuName1,String submitDate1) {
            image.setImageResource(resource);
            stuName.setText(stuName1);
            submitDate.setText(submitDate1);
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
