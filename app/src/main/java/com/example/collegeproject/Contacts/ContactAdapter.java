package com.example.collegeproject.Contacts;


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

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolde> {

    private final List<ContactModel> userList;
    private int lastPosition=-1;

   public ContactAdapter (List<ContactModel> userList){this.userList =userList;}


    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_single_row,parent,false);
        return new ViewHolde(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        int resource = userList.get(position).getImage();
        String stuName = userList.get(position).getStuName();
        String stuRNumber = userList.get(position).getStuRNumber();
        String stuCNumber = userList.get(position).getStuCNumber();
        holder.setData(resource, stuName, stuRNumber, stuCNumber);
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
        private TextView name;
        private TextView rNumber;
        private TextView cNumber;
        private final ImageView call;
        private final ImageView msg;


        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.stuName);
            rNumber = itemView.findViewById(R.id.stuRNumber);
            cNumber = itemView.findViewById(R.id.stuCNumber);
            call = itemView.findViewById(R.id.call);
            msg = itemView.findViewById(R.id.msg);
        }

        public void setData(int resource, String stuName, String stuRNumber, String stuCNumber) {
            image.setImageResource(resource);
            name.setText(stuName);
            rNumber.setText(stuRNumber);
            cNumber.setText(stuCNumber);

        }
    }
}