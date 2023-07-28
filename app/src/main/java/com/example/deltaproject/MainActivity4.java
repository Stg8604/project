package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.day;
import static com.example.deltaproject.MainActivity.thisusername;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity4 extends AppCompatActivity {
    RecyclerView sprecycle;
    private Button addtask;
    String tasknam;
    ArrayList<HashMap<String,String>> sptasks=new ArrayList<>();
    ArrayList<HashMap<String,String>> spitems=new ArrayList<>();
    ArrayList<HashMap<String,String>> spnotes=new ArrayList<>();
    static Context thus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        sprecycle=findViewById(R.id.specifictasklist);
        addtask=findViewById(R.id.addtaskbtn);
        thus=getApplicationContext();
        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog2();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<ArrayList<HashMap<String,String>>> call = service.getsptasks(thisusername);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                int status = response.code();
                if (response.isSuccessful()) {
                    sptasks=response.body();
                    sprecycle.setLayoutManager(new LinearLayoutManager(MainActivity4.this));
                    sprecycle.setAdapter(new SpAdapter(MainActivity4.this,sptasks));
                } else {
                    Toast.makeText(MainActivity4.this, String.valueOf(status), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                Log.d("API", "Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity4.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showdialog2(){
        getWindow().setEnterTransition(new Slide());
        final Dialog dialog = new Dialog(MainActivity4.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.addspdialog);
        TextView adtaskname=dialog.findViewById(R.id.adtaskname);
        TextView adslot=dialog.findViewById(R.id.adslot);
        EditText edttaskname=dialog.findViewById(R.id.edttasknam);
        EditText edtslot=dialog.findViewById(R.id.edtslot);
        EditText edtitem=dialog.findViewById(R.id.edtitem);
        EditText edtnote=dialog.findViewById(R.id.edtnote);
        Button spdone=dialog.findViewById(R.id.spdone);
        Button aditembtn=dialog.findViewById(R.id.aditmbtn);
        Button adnotebtn=dialog.findViewById(R.id.adnotebtn);
        Button close=dialog.findViewById(R.id.close);
        spdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                tasknam=edttaskname.getText().toString();
                Service service = retrofit.create(Service.class);
                Call<Void> call = service.addsptasks(thisusername,edttaskname.getText().toString(),edtslot.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                            edtitem.setVisibility(View.VISIBLE);
                            aditembtn.setVisibility(View.VISIBLE);
                            edtnote.setVisibility(View.VISIBLE);
                            adnotebtn.setVisibility(View.VISIBLE);
                            spdone.setVisibility(View.GONE);
                            close.setVisibility(View.VISIBLE);
                            edttaskname.setText("");
                            edtslot.setText("");

                        } else {
                            Toast.makeText(MainActivity4.this, String.valueOf(status), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity4.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        aditembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<Void> call = service.addspitems(thisusername,tasknam, edtitem.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                            edtitem.setText("");

                        } else {
                            Toast.makeText(MainActivity4.this, String.valueOf(status), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity4.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        adnotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<Void> call = service.addspnotes(thisusername,tasknam, edtnote.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                            edtnote.setText("");
                        } else {
                            Toast.makeText(MainActivity4.this, String.valueOf(status), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity4.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<ArrayList<HashMap<String,String>>> call = service.getsptasks(thisusername);
                call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                            sptasks=response.body();
                            sprecycle.setLayoutManager(new LinearLayoutManager(thus));
                            sprecycle.setAdapter(new SpAdapter(thus,sptasks));
                        } else {
                            Toast.makeText(MainActivity4.this, String.valueOf(status), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity4.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}