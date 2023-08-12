package com.example.deltaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationFragment.FragmentListener {
    private Spinner spinner;
    private ImageView menu,settings;
    static String thisusername;
    RecyclerView daysrecycler;
    ArrayList<HashMap<String,String>> tasklist;
    static Context context;
    static String day;
    public NavigationFragment naviFragment;
    private static String changetag="change";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=findViewById(R.id.datspinner);
        menu=findViewById(R.id.menu);
        settings=findViewById(R.id.settings);
        final ArrayList<String> daysspinner=new ArrayList<>();
        daysspinner.add("Monday");
        daysspinner.add("Tuesday");
        daysspinner.add("Wednesday");
        daysspinner.add("Thursday");
        daysspinner.add("Friday");
        daysspinner.add("Saturday");
        daysspinner.add("Sunday");
        context=getApplicationContext();
        thisusername=getIntent().getStringExtra("username");
        daysrecycler=findViewById(R.id.recycletask);
        FragmentManager fragmentManager=getSupportFragmentManager();
        naviFragment=(NavigationFragment) fragmentManager.findFragmentByTag(changetag);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().add(R.id.slidefragment,naviFragment,changetag).commit();
            }
        });
        ArrayAdapter<String> dayadapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,daysspinner);
        spinner.setAdapter(dayadapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                day=selectedItem;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service=retrofit.create(Service.class);
                Call<ArrayList<HashMap<String,String>>> call=service.getdays(thisusername,selectedItem);
                call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                        int status=response.code();
                        if(response.isSuccessful()){
                            tasklist=response.body();
                            daysrecycler.setLayoutManager(new LinearLayoutManager(context));
                            daysrecycler.setAdapter(new DayAdapter(context,tasklist));
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                        Log.d("API","Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });


    }
    private void showdialog1(){
        getWindow().setEnterTransition(new Slide());
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.threeline);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.LEFT;
        layoutParams.y=100;
        ImageView usericon=dialog.findViewById(R.id.usericon);
        TextView username=dialog.findViewById(R.id.username);
        TextView daily=dialog.findViewById(R.id.daily);
        TextView today=dialog.findViewById(R.id.today);
        TextView MyLists=dialog.findViewById(R.id.mylists);
        TextView reminders=dialog.findViewById(R.id.ogreminders);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainActivity4.class);
                startActivity(intent);
            }
        });
        MyLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainActivity5.class);
                startActivity(intent);
            }
        });
        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainActivity6.class);
                startActivity(intent);
            }
        });
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }

    @Override
    public void moveactivity() {

    }

    @Override
    public void todayactivity() {
        Intent intent=new Intent(MainActivity.this,MainActivity3.class);
        startActivity(intent);
    }

    @Override
    public void mylistsactivity() {
        Intent intent=new Intent(MainActivity.this,MainActivity4.class);
        startActivity(intent);
    }

    @Override
    public void reminderactivity() {
        Intent intent=new Intent(MainActivity.this,MainActivity5.class);
        startActivity(intent);
    }
}