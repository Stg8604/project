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

public class DayAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<HashMap<String,String>> data;

    public DayAdapter(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.taskitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.taskname1.setText(data.get(position).get("task"));
            holder.slot.setText(data.get(position).get("timestart")+"-"+data.get(position).get("timeend"));
            holder.side.setText(data.get(position).get("sidenote"));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,MainActivity3.class);
                    intent.putExtra("Task",data.get(position).get("task"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
