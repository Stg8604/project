package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.day;
import static com.example.deltaproject.MainActivity.thisusername;
import static com.example.deltaproject.MainActivity2.cont;
import static com.example.deltaproject.MainActivity3.content;
import static com.example.deltaproject.MainActivity3.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<HashMap<String,String>> data;

    public ForgetAdapter(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemselect,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.itmnam.setText(data.get(position).get("item"));
            holder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text;
                    if (holder.select.getText().toString().equals("Select")) {
                        text="selected";
                    }else{
                        text="select";
                    }
                        holder.select.setText(text);
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://localhost:8000/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Service service = retrofit.create(Service.class);
                        Call<Void> call = service.selects(thisusername, day, task,text,data.get(position).get("item"));
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int status = response.code();
                                if (response.isSuccessful()) {
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, String.valueOf(status), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("API", "Error");
                                System.out.println(t.getMessage());
                                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
