package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.thisusername;
import static com.example.deltaproject.MainActivity2.cont;
import static com.example.deltaproject.MainActivity6.notifid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBroadcastReceiver extends BroadcastReceiver {
    ArrayList<HashMap<String,String>> newrem=new ArrayList<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()!=null && intent.getAction().equals("actionbtn")) {
            Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancel(notifid);
        }
    }
}
