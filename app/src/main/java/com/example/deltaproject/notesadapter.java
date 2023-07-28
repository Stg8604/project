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

public class notesadapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<HashMap<String,String>> data;

    public notesadapter(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.notesitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.notename.setText(data.get(position).get("note"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
