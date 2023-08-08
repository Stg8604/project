package com.example.deltaproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<HashMap<String,String>> data;

    public ReminderAdapter(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.reminderitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tskname.setText(data.get(position).get("name"));
        holder.timeslot.setText(data.get(position).get("hour")+":"+data.get(position).get("minute"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
