package com.example.collegeproject.Chat;

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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolde> {

    private final List<ChatModel> userList;
    private int lastPosition=-1;


    public  ChatAdapter (List<ChatModel>userList){this.userList = userList;}



    @NonNull
    @Override
    public ChatAdapter.ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_single_row,parent,false);
        return new ViewHolde(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolde holder, int position) {

        int resource = userList.get(position).getImage();
        String dname = userList.get(position).getdName();
        String ayear = userList.get(position).getaYear();
        String lmsg = userList.get(position).getLastMsg();
        String time = userList.get(position).getTime();
        holder.setData(resource,dname,ayear,lmsg,time);

        setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolde extends  RecyclerView.ViewHolder {
        private ImageView image;
        private TextView dName;
        private TextView aYear;
        private TextView lastMsg;
        private TextView mTime;


        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            dName = itemView.findViewById(R.id.departmentName);
            aYear = itemView.findViewById(R.id.aYear);
            lastMsg = itemView.findViewById(R.id.lastMessage);
            mTime = itemView.findViewById(R.id.mTime);

        }

        public void setData(int resource, String dname, String ayear, String lmsg, String time) {
            image.setImageResource(resource);
            dName.setText(dname);
            aYear.setText(ayear);
            lastMsg.setText(lmsg);
            mTime.setText(time);
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
