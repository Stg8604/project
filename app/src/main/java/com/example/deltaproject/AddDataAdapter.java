package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.thisusername;
import static com.example.deltaproject.MainActivity5.senddata;

import android.app.Dialog;
import android.content.Context;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddDataAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<HashMap<String,String>> data;
    String dattype;
    static String curlistname;
    ArrayList<HashMap<String,String>> datamylist=new ArrayList<>();
    ArrayList<HashMap<String,Object>> collectdata=new ArrayList<>();
    public AddDataAdapter(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemlist,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        curlistname=data.get(position).get("listname").toString();
        holder.listnames.setText(data.get(position).get("listname").toString());
        holder.itemcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.getitemlist);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                TextView getitlistname=dialog.findViewById(R.id.getitlistname);
                RecyclerView recycleitem=dialog.findViewById(R.id.getitemrecycle);
                Button getitmclose=dialog.findViewById(R.id.getitmclose);
                Call<ArrayList<HashMap<String,String>>> call2 = service.namemy(thisusername);
                call2.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                            datamylist=response.body();
                            getitlistname.setText(data.get(position).get("listname"));
                            recycleitem.setLayoutManager(new LinearLayoutManager(context));
                            recycleitem.setAdapter(new ItemColumnRecycle(context,datamylist));
                            getitmclose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
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
        });
        holder.adlistdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.newlist);
                Button newdata=dialog.findViewById(R.id.adddata);
                senddata=new ArrayList<>();
                RecyclerView recylecolumn=dialog.findViewById(R.id.recycleaddlist);
                Button clearbtn=dialog.findViewById(R.id.clearbtn);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<ArrayList<HashMap<String,String>>> call2 = service.mylistcolumn(data.get(position).get("listname").toString());
                call2.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                            datamylist=response.body();
                            recylecolumn.setLayoutManager(new LinearLayoutManager(context));
                            recylecolumn.setAdapter(new MyListAdapter(context,datamylist));
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
                newdata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://localhost:8000/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Service service = retrofit.create(Service.class);
                        Call<Void> call = service.getmylist(thisusername,data.get(position).get("listname").toString(),senddata);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int status = response.code();
                                if (response.isSuccessful()) {
                                    senddata=new ArrayList<>();
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
                dialog.show();
                clearbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        holder.deletelistdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<Void> call = service.delete(curlistname);
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
    private void showdialognew(){

    }
}
