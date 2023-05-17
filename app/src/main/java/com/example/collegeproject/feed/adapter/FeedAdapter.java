package com.example.collegeproject.feed.adapter;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Assignment.AssignmentOpenActivity;
import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ImageFeedBinding;
import com.example.collegeproject.databinding.TextFeedBinding;
import com.example.collegeproject.databinding.TextImageFeedBinding;
import com.example.collegeproject.feed.models.ImageFeedModel;
import com.example.collegeproject.feed.models.Item;
import com.example.collegeproject.feed.models.TextFeedModel;
import com.example.collegeproject.feed.models.TextImageFeedModel;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Item> userList;

    public FeedAdapter(List<Item> items) {
        this.userList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here 0 is text only ,1 is only image ,3 is image and text both.
        if (viewType == 0){

            TextFeedBinding binding = TextFeedBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return  new TextFeedViewHolder(binding);
        }
        else if (viewType == 1){

            ImageFeedBinding binding1 = ImageFeedBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
            return new ImageFeedViewHolder(binding1);
        }
        else {

            TextImageFeedBinding binding2 = TextImageFeedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new TextFeedImageViewHolder(binding2);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == 0)
        {
            TextFeedModel textFeedModel = (TextFeedModel) userList.get(position).getObject();
            ((TextFeedViewHolder) holder).setTextFeed(textFeedModel);
        }
        else if(getItemViewType(position) == 1){
            ImageFeedModel imageFeedModel = (ImageFeedModel) userList.get(position).getObject();
            ((ImageFeedViewHolder) holder).setImageFeed(imageFeedModel);
        }
        else {
            TextImageFeedModel textImageFeedModel = (TextImageFeedModel) userList.get(position).getObject();
            ((TextFeedImageViewHolder) holder).setTextImage(textImageFeedModel);
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return userList.get(position).getType();
    }

    static class TextFeedViewHolder extends RecyclerView.ViewHolder{
        TextFeedBinding binding;

        public TextFeedViewHolder(@NonNull TextFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setTextFeed(TextFeedModel data1){

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // set profile image (Student perspective)
            if(data1.getRoll_number() != null){
                // student
                db.collection("College_Project").document("student").collection("4th Year").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                        StudentData data = stuRollNo.toObject(StudentData.class);
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
                                }
                            }
                        });

            }

            // set profile image (Teacher Perspective)
            if(data1.getEmail() != null){

                //teacher

                db.collection("College_Project").document("teacher").collection("teacher_details")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                        TeacherData data = teacherEmail.toObject(TeacherData.class);
                                        if(data.getEmail().equals(data1.getEmail())){

                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }
                                    }
                                }
                            }
                        });

            }

            binding.userName.setText(data1.getSenderName());
            binding.userOnlyText.setText(data1.getFeedMsg());
            binding.role.setText(data1.getRole());
            String dataTime = data1.getTime()+", "+data1.getDate();
            binding.dateTime.setText(dataTime);
        }
    }

    static class ImageFeedViewHolder extends RecyclerView.ViewHolder{
        ImageFeedBinding binding;
        public ImageFeedViewHolder(@NonNull ImageFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void  setImageFeed(ImageFeedModel data1){

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // set profile image (Student perspective)
            if(data1.getRoll_number() != null){
                // student
                db.collection("College_Project").document("student").collection("4th Year").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                        StudentData data = stuRollNo.toObject(StudentData.class);
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
                                }
                            }
                        });

            }

            // set profile image (Teacher Perspective)
            if(data1.getEmail() != null){

                //teacher

                db.collection("College_Project").document("teacher").collection("teacher_details")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                        TeacherData data = teacherEmail.toObject(TeacherData.class);
                                        if(data.getEmail().equals(data1.getEmail())){

                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }
                                    }
                                }
                            }
                        });

            }

            binding.userName.setText(data1.getSenderName());

            Bitmap feedImageBitmap = BitmapFactory.decodeByteArray(data1.getFeedImageByteBlob().toBytes(), 0, data1.getFeedImageByteBlob().toBytes().length);
            feedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());

            binding.userPostImage.setImageBitmap(feedImageBitmap);
            binding.userPostImage.setOnClickListener(v -> {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent intent = new Intent(activity, AssignmentOpenActivity.class);
                intent.putExtra("byte", data1.getFeedImageByteBlob().toBytes());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation( activity, Pair.create(binding.userPostImage, "ImageTransition"));
                activity.startActivity(intent, optionsCompat.toBundle());
            });
            binding.role.setText(data1.getRole());
            String dataTime = data1.getTime()+", "+ data1.getDate();
            binding.dateTime.setText(dataTime);
        }
    }

    static  class TextFeedImageViewHolder extends RecyclerView.ViewHolder{
        TextImageFeedBinding binding;
        public TextFeedImageViewHolder(@NonNull TextImageFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void setTextImage(TextImageFeedModel data1){

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // set profile image (Student perspective)
            if(data1.getRoll_number() != null){
                // student
                db.collection("College_Project").document("student").collection("4th Year").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                        StudentData data = stuRollNo.toObject(StudentData.class);
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
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
                                        if( data != null && data.getRoll_number().equals(data1.getRoll_number())){
                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }                                    }
                                }
                            }
                        });

            }

            // set profile image (Teacher Perspective)
            if(data1.getEmail() != null){

                //teacher

                db.collection("College_Project").document("teacher").collection("teacher_details")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                        TeacherData data = teacherEmail.toObject(TeacherData.class);
                                        if(data.getEmail().equals(data1.getEmail())){

                                            if(data.getProfileImageBlob() != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                binding.profileImage.setImageBitmap(fullBitmap);
                                            }
                                            else binding.profileImage.setImageResource(R.drawable.cartoon);
                                        }
                                    }
                                }
                            }
                        });

            }

            binding.userName.setText(data1.getSenderName());

            Bitmap feedImageBitmap = BitmapFactory.decodeByteArray(data1.getFeedImageByteBlob().toBytes(), 0, data1.getFeedImageByteBlob().toBytes().length);
            feedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());

            binding.userPostImage.setImageBitmap(feedImageBitmap);
            binding.userPostImage.setOnClickListener(v -> {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent intent = new Intent(activity, AssignmentOpenActivity.class);
                intent.putExtra("byte", data1.getFeedImageByteBlob().toBytes());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation( activity, Pair.create(binding.userPostImage, "ImageTransition"));
                activity.startActivity(intent, optionsCompat.toBundle());
            });
            binding.userOnlyText.setText(data1.getFeedMsg());
            binding.role.setText(data1.getRole());
            String dataTime = data1.getTime()+", "+ data1.getDate();
            binding.dateTime.setText(dataTime);
        }
    }





}
