package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.day;
import static com.example.deltaproject.MainActivity.thisusername;
import static com.example.deltaproject.MainActivity3.task;
import static com.example.deltaproject.MainActivity4.thus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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

public class SpAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    static String sptask;
    List<HashMap<String,String>> data;
    ArrayList<HashMap<String,String>> noteslist=new ArrayList<>();
    ArrayList<HashMap<String,String>> newitems=new ArrayList<>();
    public SpAdapter(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.specifictaskitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sptaskname.setText(data.get(position).get("task"));
        holder.spslot.setText(data.get(position).get("slot"));
        holder.spforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.getWindow().setEnterTransition(new Slide());
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setContentView(R.layout.forgetitemlist);
                    RecyclerView recyclerView=dialog.findViewById(R.id.splistforget);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://localhost:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Service service = retrofit.create(Service.class);
                    Call<ArrayList<HashMap<String,String>>> call = service.getspitems(thisusername,data.get(position).get("task"));
                    call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
                        @Override
                        public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                            int status = response.code();
                            if (response.isSuccessful()) {
                                sptask=data.get(position).get("task");
                                newitems=response.body();
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                recyclerView.setAdapter(new SpForgetAdapter(context,newitems));
                            } else {
                                Toast.makeText(context, String.valueOf(status), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                            Log.d("API", "Error");
                            System.out.println(t.getMessage());
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
            }
        });
        holder.spnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.getWindow().setEnterTransition(new Slide());
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setContentView(R.layout.spnoteslist);
                    Button done=dialog.findViewById(R.id.donenotes);
                    RecyclerView recyclerView=dialog.findViewById(R.id.sprecyclenotes);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://localhost:8000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Service service = retrofit.create(Service.class);
                    Call<ArrayList<HashMap<String,String>>> call = service.getspnotes(thisusername,data.get(position).get("task"));
                    call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
                        @Override
                        public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                            int status = response.code();
                            if (response.isSuccessful()) {
                                noteslist=response.body();
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                recyclerView.setAdapter(new notesadapter(context,noteslist));
                            } else {
                                Toast.makeText(context, String.valueOf(status), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                            Log.d("API", "Error");
                            System.out.println(t.getMessage());
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
