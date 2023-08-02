package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.thisusername;
import static com.example.deltaproject.MainActivity5.senddata;
import static com.example.deltaproject.SpAdapter.sptask;

import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
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

public class MyListAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<HashMap<String,String>> data;

    public MyListAdapter(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleraddlist,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lstaddname.setText("Add "+data.get(position).get("column"));
        String txtl;
        if(data.get(position).get("datatype").equals("varchar(30)")){
            txtl="String";
        }else if(data.get(position).get("datatype").equals("int")){
            txtl="Integer";
        }else{
            txtl="Floating Point";
        }
        holder.lstaddedit.setHint(txtl);
        holder.lstaddname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.lstaddedit.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Properly", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(data.get(position).get("datatype").equals("varchar(30)")){
                        senddata.add(holder.lstaddedit.getText().toString());
                    }else if(data.get(position).get("datatype").equals("int")){
                        senddata.add(Integer.parseInt(holder.lstaddedit.getText().toString()));
                    }else{
                        senddata.add(Float.parseFloat(holder.lstaddedit.getText().toString()));
                    }
                holder.lstaddedit.setText("");
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
}
