package com.example.collegeproject.Assignment;

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

import com.example.collegeproject.R;
import com.example.collegeproject.studentData.StudentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AssignmentSubmitAdapter extends RecyclerView.Adapter<AssignmentSubmitAdapter.Viewholder> {


    List<AssignmentSubmitModal> userList;
    private int lastPosition = -1;
    String id;

    public AssignmentSubmitAdapter(List<AssignmentSubmitModal> userList, String id) {
        this.userList = userList;
        this.id = id;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_assignment_submit, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String rollNo = userList.get(position).getRoll_number();

        final Blob[] imageBlob = new Blob[1];
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // student
        db.collection("College_Project").document("student").collection("4th Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if( data != null && data.getRoll_number().equals(rollNo)){
                                    if(data.getProfileImageBlob() != null){
                                        imageBlob[0] = data.getProfileImageBlob();
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        holder.image.setImageBitmap(fullBitmap);
                                    }
                                    else holder.image.setImageResource(R.drawable.cartoon);
                                }
                            }
                        }
                    }
                });

        db.collection("College_Project").document("student").collection("3rd Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if( data != null && data.getRoll_number().equals(rollNo)){
                                    if(data.getProfileImageBlob() != null){
                                        imageBlob[0] = data.getProfileImageBlob();
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        holder.image.setImageBitmap(fullBitmap);

                                    }
                                    else holder.image.setImageResource(R.drawable.cartoon);

                                }
                            }
                        }
                    }
                });


        db.collection("College_Project").document("student").collection("2nd Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if( data != null && data.getRoll_number().equals(rollNo)){
                                    if(data.getProfileImageBlob() != null){
                                        imageBlob[0] = data.getProfileImageBlob();
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        holder.image.setImageBitmap(fullBitmap);
                                    }
                                    else holder.image.setImageResource(R.drawable.cartoon);
                                }
                            }
                        }
                    }
                });


        db.collection("College_Project").document("student").collection("1st Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if( data != null && data.getRoll_number().equals(rollNo)){
                                    if(data.getProfileImageBlob() != null){
                                        imageBlob[0] = data.getProfileImageBlob();
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        holder.image.setImageBitmap(fullBitmap);
                                    }
                                    else holder.image.setImageResource(R.drawable.cartoon);
                                }
                            }
                        }
                    }
                });

        String stuName = userList.get(position).getStudentName();
        String submitDate = userList.get(position).getDate();
        String submitTime = userList.get(position).getTime();
        holder.setData( stuName, submitDate, submitTime);
        setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Intent intent = new Intent(activity, AssignmentCheckActivity.class);
            if(imageBlob[0] != null){
                intent.putExtra("imageByte", imageBlob[0].toBytes());
            }
            else intent.putExtra("resource", R.drawable.a2);
            intent.putExtra("stuName", stuName);
            intent.putExtra("date", submitDate);
            intent.putExtra("time", submitTime);
            intent.putExtra("url", userList.get(position).getSubmittedAssignmentUrl());
            intent.putExtra("id", id);
            // OR use below commented code
            //  ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(holder.dName,"dnameTransition"),Pair.create(holder.image,"imageTransition"));
            Pair[] pairs = new Pair[4];
            pairs[0] = new Pair<View, String>(holder.stuName, "stuNameTransition");
            pairs[1] = new Pair<View, String>(holder.image, "imageTransition");
            pairs[2] = new Pair<View, String>(holder.submitDate, "dateTransition");
            pairs[3] = new Pair<View, String>(holder.submitTime, "timeTransition");
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

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation slideIn = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(slideIn);
            lastPosition = position;
        }

    }

    static class Viewholder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView stuName, submitDate,submitTime;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            stuName = itemView.findViewById(R.id.stuName);
            submitDate = itemView.findViewById(R.id.submissionDate);
            submitTime = itemView.findViewById(R.id.submissionTime);
        }

        public void setData( String stuName1, String submitDate1, String submitTime1) {
            stuName.setText(stuName1);
            submitDate.setText(submitDate1);
            submitTime.setText(submitTime1);
        }
    }

}
