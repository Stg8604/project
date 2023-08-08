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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity3 extends AppCompatActivity {
    private RecyclerView recycleforget,recycleschedule;
    private EditText edtnote,edtside;
    ArrayList<HashMap<String,String>> forget=new ArrayList<>();
    ArrayList<HashMap<String,String>> schedule=new ArrayList<>();
    static ArrayList<HashMap<String,String>> notes=new ArrayList<>();
    static String task;
    static Context content;
    private Button addnotes,addsidenote,todaynotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        recycleforget=findViewById(R.id.dontforgetlist);
        recycleschedule=findViewById(R.id.schedulelist);
        addnotes=findViewById(R.id.add);
        todaynotes=findViewById(R.id.todaybtn);
        edtnote=findViewById(R.id.addnote);
        edtside=findViewById(R.id.addside);
        addsidenote=findViewById(R.id.ad);
        task=getIntent().getStringExtra("Task");
        content=getApplicationContext();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service=retrofit.create(Service.class);
        Call<ArrayList<HashMap<String,String>>> call=service.getitems(thisusername,day,task);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                int status=response.code();
                if(response.isSuccessful()){
                    forget=response.body();
                    recycleforget.setLayoutManager(new LinearLayoutManager(content));
                    recycleforget.setAdapter(new ForgetAdapter(content,forget));
                }
                else{
                    Toast.makeText(MainActivity3.this,"Cant Show Tasks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                Log.d("API","Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity3.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Call<ArrayList<HashMap<String,String>>> call2=service.getschedule(thisusername,day,task);
        call2.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                int status=response.code();
                if(response.isSuccessful()){
                    schedule=response.body();
                    recycleschedule.setLayoutManager(new LinearLayoutManager(content));
                    recycleschedule.setAdapter(new ScheduleAdapter(content,schedule));
                }
                else{
                    Toast.makeText(MainActivity3.this,"Cant Show Schedule", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                Log.d("API","Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity3.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<Void> call = service.addnotes(thisusername, day, task, edtnote.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                        } else {
                            Toast.makeText(MainActivity3.this, "Enter Notes Appropriately", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity3.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                edtnote.setText("");
            }
        });
        todaynotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog1();
            }
        });
        addsidenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<Void> call = service.addsidenotes(thisusername, day, task, edtside.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {

                        } else {
                            Toast.makeText(MainActivity3.this, "Add Sidenote", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity3.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void showdialog1(){
        getWindow().setEnterTransition(new Slide());
        final Dialog dialog = new Dialog(MainActivity3.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.notesfile);
        RecyclerView recyclenotes=dialog.findViewById(R.id.noteslist);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service=retrofit.create(Service.class);
        Call<ArrayList<HashMap<String,String>>> call=service.getnotes(thisusername,day,task);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                int status=response.code();
                if(response.isSuccessful()){
                    notes=response.body();
                    if(notes.isEmpty()){
                        Toast.makeText(content, "No notes set up for the day", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        recyclenotes.setLayoutManager(new LinearLayoutManager(content));
                        recyclenotes.setAdapter(new notesadapter(content, notes));
                    }
                    dialog.show();
                }
                else{
                    Toast.makeText(MainActivity3.this,String.valueOf(status), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                Log.d("API","Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity3.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}