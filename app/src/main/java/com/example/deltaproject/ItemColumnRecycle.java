package com.example.deltaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemColumnRecycle extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<HashMap<String,String>> data;

    public ItemColumnRecycle(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.columnitemrecycle,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        data.get(position).remove("username");
        holder.columnrecycle.setLayoutManager(new LinearLayoutManager(context));
        holder.columnrecycle.setAdapter(new ItemColumnAdapter(context,data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
