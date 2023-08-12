package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.context;
import static com.example.deltaproject.MainActivity.thisusername;

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
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity6 extends AppCompatActivity {
    TextView reminders;
    RecyclerView recyclereminder;
    Button createreminder;
    ArrayList<HashMap<String,String>> getrem=new ArrayList<>();
    public static final String channelid = "my_notification_channel";
    static int notifid=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        recyclereminder=findViewById(R.id.reminderrecycle);
        createreminder=findViewById(R.id.remindercreate);
        recyclereminder.setLayoutManager(new LinearLayoutManager(context));
        recyclereminder.setAdapter(new ReminderAdapter(context,getrem));
        scheduleNotification(15,44);
        createreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialognew();
            }
        });
    }
    private void showdialognew(){
        getWindow().setEnterTransition(new Slide());
        final Dialog dialog = new Dialog(MainActivity6.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.remindernewdialog);
        EditText edtrem=dialog.findViewById(R.id.edtremname);
        EditText edthour=dialog.findViewById(R.id.edthour);
        EditText edtminute=dialog.findViewById(R.id.edtminute);
        Button adreminder=dialog.findViewById(R.id.adreminder);
        adreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service=retrofit.create(Service.class);
                Call<Void> call=service.rem(thisusername,edtrem.getText().toString(),Integer.parseInt(edthour.getText().toString()),Integer.parseInt(edtminute.getText().toString()));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int status=response.code();
                        if(response.isSuccessful()){
                            scheduleNotification(Integer.parseInt(edthour.getText().toString()),Integer.parseInt(edtminute.getText().toString()));
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://localhost:8000/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            Service service=retrofit.create(Service.class);
                            Call<ArrayList<HashMap<String,String>>> call2=service.getrem(thisusername);
                            call2.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
                                @Override
                                public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                                    int status=response.code();
                                    if(response.isSuccessful()){
                                        getrem=response.body();
                                        recyclereminder.setLayoutManager(new LinearLayoutManager(context));
                                        recyclereminder.setAdapter(new ReminderAdapter(context,getrem));
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(MainActivity6.this,"Could not load reminders", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                                    Log.d("API","Error");
                                    System.out.println(t.getMessage());
                                    Toast.makeText(MainActivity6.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(MainActivity6.this,"Enter details Correctly", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("API","Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity6.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }
    private void scheduleNotification(int hour,int minute) {
        // Get the current time
        /*
        Calendar now = Calendar.getInstance();

        // Set the desired time for the notification
        Calendar notificationTime = Calendar.getInstance();
        notificationTime.set(Calendar.HOUR_OF_DAY, hour);
        Toast.makeText(this, String.valueOf(hour), Toast.LENGTH_SHORT).show();
        notificationTime.set(Calendar.MINUTE, minute);
        Toast.makeText(this, String.valueOf(minute), Toast.LENGTH_SHORT).show();
        notificationTime.set(Calendar.SECOND, 0);
        if (now.after(notificationTime)) {
            notificationTime.add(Calendar.DAY_OF_YEAR, 1);
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notificationTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
        }*/
        Intent myIntent = new Intent(getApplicationContext() , AlarmReciever. class ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE ) ;
        PendingIntent pendingIntent = PendingIntent. getService ( this, 0 , myIntent , PendingIntent.FLAG_MUTABLE ) ;
        Calendar calendar = Calendar. getInstance () ;
        calendar.set(Calendar. SECOND , 15 ) ;
        calendar.set(Calendar. MINUTE , 55 ) ;
        calendar.set(Calendar. HOUR , 0 ) ;
        calendar.set(Calendar. AM_PM , Calendar. AM ) ;
        calendar.add(Calendar. DAY_OF_MONTH , 1 ) ;
        alarmManager.setRepeating(AlarmManager. RTC_WAKEUP , calendar.getTimeInMillis() , 1000 * 60 * 60 * 24 , pendingIntent) ;
    }
    }


