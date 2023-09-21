package com.example.collegeproject.Contacts;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolde> {

    private final List<ContactModel> userList;
    Intent intent;
    private int lastPosition = -1;


    public ContactAdapter(List<ContactModel> userList) {
        this.userList = userList;
    }


    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_single_row, parent, false);
        return new ViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
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
        String stuName = userList.get(position).getStuName();
        String stuRNumber = userList.get(position).getStuRNumber();
        String stuCNumber = userList.get(position).getStuCNumber();

        holder.setData(stuName, stuRNumber, stuCNumber);
        setAnimation(holder.itemView, position);

        if (Patterns.EMAIL_ADDRESS.matcher(stuRNumber).matches()) {


            SpannableString email = new SpannableString(holder.rNumber.getText());

            ForegroundColorSpan g = new ForegroundColorSpan(Color.parseColor("#FF1E88E5"));
            ForegroundColorSpan m = new ForegroundColorSpan(Color.RED);
            ForegroundColorSpan a = new ForegroundColorSpan(Color.parseColor("#DC3C31"));
            ForegroundColorSpan i = new ForegroundColorSpan(Color.parseColor("#FF9800"));
            ForegroundColorSpan l = new ForegroundColorSpan(Color.parseColor("#237927"));

            email.setSpan(g, email.length() - 9, email.length() - 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            email.setSpan(m, email.length() - 8, email.length() - 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            email.setSpan(a, email.length() - 7, email.length() - 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            email.setSpan(i, email.length() - 6, email.length() - 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            email.setSpan(l, email.length() - 5, email.length() - 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            holder.rNumber.setText(email);

            holder.rNumber.setOnClickListener(v -> {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                /*TypedValue typedValue = new TypedValue();
                activity.getTheme().resolveAttribute(android.R.attr.selectableItemBackground,typedValue, true );
                holder.rNumber.setBackgroundResource(typedValue.resourceId);*/
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + stuRNumber));
                activity.startActivity(intent);
            });
        }
        /*else {
              holder.rNumber.setBackgroundResource(0);
        }*/


        holder.call.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + stuCNumber));
            activity.startActivity(intent);

        });

        holder.msg.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            String uri = "https://api.whatsapp.com/send?phone=" + "+91" + stuCNumber;
            try {
                PackageManager pm = activity.getPackageManager();
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(uri));
                activity.startActivity(i);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(activity, "Whatsapp app not installed in your phone", Toast.LENGTH_LONG).show();
            }
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

    public class ViewHolde extends RecyclerView.ViewHolder {
        private final ImageView call;
        private final ImageView msg;
        private final ImageView image;
        private final TextView name;
        private final TextView rNumber;
        private final TextView cNumber;


        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.stuName);
            rNumber = itemView.findViewById(R.id.stuRNumber);
            cNumber = itemView.findViewById(R.id.stuCNumber);
            call = itemView.findViewById(R.id.call);
            msg = itemView.findViewById(R.id.msg);
        }


        public void setData(String stuName, String stuRNumber, String stuCNumber) {

            name.setText(stuName);
            rNumber.setText(stuRNumber);
            cNumber.setText(stuCNumber);

        }

    }


}