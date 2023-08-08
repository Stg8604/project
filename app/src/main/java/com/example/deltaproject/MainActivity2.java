package com.example.deltaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    private Button newuser,chooseuser;
    ArrayList<String> userslist;
    static Context cont;
    static String accesstoken;
    static ArrayList<HashMap<String,String>> datalist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        newuser=findViewById(R.id.newuserbtn);
        chooseuser=findViewById(R.id.chooseuser);
        cont=getApplicationContext();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service=retrofit.create(Service.class);
        Call<ArrayList<HashMap<String,String>>> call=service.getdays("michael","monday");
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                int status=response.code();
                if(response.isSuccessful()){
                    datalist=response.body();

                }
                else{
                    Toast.makeText(MainActivity2.this,"Error Occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                Log.d("API","Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog1();
            }
        });
        chooseuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog2();
            }
        });
    }
    private void showdialog1(){
        getWindow().setEnterTransition(new Slide());
        final Dialog dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.newuser);
        EditText edtuser=dialog.findViewById(R.id.createedt);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service=retrofit.create(Service.class);
        Call<Void> call=service.newusers(edtuser.getText().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int status=response.code();
                if(response.isSuccessful()){
                }
                else{
                    Toast.makeText(MainActivity2.this,"Enter Appropriate Username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("API","Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    private void showdialog2(){
        getWindow().setEnterTransition(new Slide());
        final Dialog dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.userlist);
        Context context=getBaseContext();
        RecyclerView recyclelist=dialog.findViewById(R.id.userlistview);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service=retrofit.create(Service.class);
        Call<ArrayList<String>> call=service.getUsers();
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                int status=response.code();
                if(response.isSuccessful()){
                    userslist=response.body();
                    recyclelist.setLayoutManager(new LinearLayoutManager(context));
                    recyclelist.setAdapter(new ListAdapter(context,userslist));
                }
                else{
                    Toast.makeText(MainActivity2.this,"UserList error occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.d("API","Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }




}