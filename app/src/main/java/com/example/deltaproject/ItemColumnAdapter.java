package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.day;
import static com.example.deltaproject.MainActivity.thisusername;
import static com.example.deltaproject.MainActivity3.task;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemColumnAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    HashMap<String,String> data;


    public ItemColumnAdapter(Context context, HashMap<String,String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.columnitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> keysList = new ArrayList<>(data.keySet());
        List<Object> valuesList = new ArrayList<>(data.values());
        holder.txtcolumn.setText(keysList.get(position).toString());
        holder.txtvalue.setText(valuesList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
