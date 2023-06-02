package com.example.collegeproject.attendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.Assignment.AssignmentOpenActivity;
import com.example.collegeproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<AttendanceModel> userList;
    String year;
    private int lastPosition = -1;


    public AttendanceAdapter(List<AttendanceModel> userList, String year) {
        this.userList = userList;
        this.year = year;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_take_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {

        Blob resource = userList.get(position).getProfileImageBlob();
        if(resource != null){

            Bitmap fullBitmap = BitmapFactory.decodeByteArray(resource.toBytes(), 0, resource.toBytes().length);
            fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
            holder.image.setImageBitmap(fullBitmap);
            holder.image.setOnClickListener(v -> {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Intent intent = new Intent(activity, AssignmentOpenActivity.class);
                intent.putExtra("byte", resource.toBytes());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(holder.image, "ImageTransition"));
                activity.startActivity(intent, optionsCompat.toBundle());
            });
        }
        else{
            holder.image.setImageResource(R.drawable.cartoon);
        }
        String stuName = userList.get(position).getFull_name();
        String roll = userList.get(position).getRoll_number();
        holder.setData(stuName, roll);
        setAnimation(holder.itemView, position);

       /* FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> hm = new HashMap<>();
        holder.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String dateStamp = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(new Date());
                hm.put("name", holder.stuName);
                hm.put("roll_number", holder.roll);
                hm.put("profileImageBlob", resource);
                hm.put("isChecked", isChecked);

                db.collection("College_Project").document("student").collection(year).document("attendance")
                        .collection("attendance_details").document(dateStamp).set(hm, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                }
                            }
                        });
            }
        });
  */  }

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
        private TextView stuName;
        private TextView roll;
        private SwitchCompat switchButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            stuName = itemView.findViewById(R.id.stuName);
            roll = itemView.findViewById(R.id.roll);
            switchButton = itemView.findViewById(R.id.switchButton);

        }

        public void setData(String stuName1, String roll1) {

            stuName.setText(stuName1);
            roll.setText(roll1);

        }
    }
}
