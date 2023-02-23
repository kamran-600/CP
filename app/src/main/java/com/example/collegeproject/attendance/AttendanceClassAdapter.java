package com.example.collegeproject.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.collegeproject.R;

import java.util.List;

public class AttendanceClassAdapter extends ArrayAdapter<AttendanceModelClass> {
    private Context context;
    private int mResource;

    public AttendanceClassAdapter(@NonNull Context context, int resource, @NonNull List<AttendanceModelClass> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(mResource, parent, false);

        ImageView classLogo = convertView.findViewById(R.id.image);
        TextView className = convertView.findViewById(R.id.className);
        TextView section = convertView.findViewById(R.id.section);

        classLogo.setImageResource(getItem(position).getClassLogo());
        className.setText(getItem(position).getClassName());
        section.setText(getItem(position).getSection());

        return convertView;
    }
}

