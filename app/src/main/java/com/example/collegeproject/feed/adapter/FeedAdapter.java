package com.example.collegeproject.feed.adapter;





import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.databinding.ImageFeedBinding;
import com.example.collegeproject.databinding.TextFeedBinding;
import com.example.collegeproject.databinding.TextImageFeedBinding;
import com.example.collegeproject.feed.models.ImageFeedModel;
import com.example.collegeproject.feed.models.Item;
import com.example.collegeproject.feed.models.TextFeedModel;
import com.example.collegeproject.feed.models.TextImageFeedModel;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Item> items;

    public FeedAdapter(List<Item> items) {
        this.items = items;
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
            TextFeedModel textFeedModel = (TextFeedModel) items.get(position).getObject();
            ((TextFeedViewHolder) holder).setTextFeed(textFeedModel);
        }
        else if(getItemViewType(position) == 1){
            ImageFeedModel imageFeedModel = (ImageFeedModel) items.get(position).getObject();
            ((ImageFeedViewHolder) holder).setImageFeed(imageFeedModel);
        }
        else {
            TextImageFeedModel textImageFeedModel = (TextImageFeedModel) items.get(position).getObject();
            ((TextFeedImageViewHolder) holder).setTextImage(textImageFeedModel);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    static class TextFeedViewHolder extends RecyclerView.ViewHolder{
        TextFeedBinding binding;

        public TextFeedViewHolder(@NonNull TextFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setTextFeed(TextFeedModel data){
            binding.profileImage.setImageResource(data.getProfileImage());
            binding.userName.setText(data.getUserName());
            binding.userOnlyText.setText(data.getUserText());
        }
    }

    static class ImageFeedViewHolder extends RecyclerView.ViewHolder{
        ImageFeedBinding binding;
        public ImageFeedViewHolder(@NonNull ImageFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void  setImageFeed(ImageFeedModel data){
            binding.profileImage.setImageResource(data.getProfileImage());
            binding.userName.setText(data.getUserName());
            binding.userPostImage.setImageResource(data.getUserPostImage());
        }
    }

    static  class TextFeedImageViewHolder extends RecyclerView.ViewHolder{
        TextImageFeedBinding binding;
        public TextFeedImageViewHolder(@NonNull TextImageFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void setTextImage(TextImageFeedModel data){
            binding.profileImage.setImageResource(data.getProfileImage());
            binding.userName.setText(data.getUserName());
            binding.userOnlyText.setText(data.getUserText());
            binding.userPostImage.setImageResource(data.getUserPostImage());
        }
    }





}
