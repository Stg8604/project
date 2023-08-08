package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.day;
import static com.example.deltaproject.MainActivity.thisusername;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity5 extends AppCompatActivity {
    RecyclerView recyclemy;
    Button addmybtn;
    List<String> columnlist=new ArrayList<>();
    String dattype;
    String lstnaming;
    static ArrayList<Object> senddata=new ArrayList<>();
    ArrayList<HashMap<String,String>> datamylist=new ArrayList<>();
    ArrayList<HashMap<String,String>> collectdata=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        recyclemy=findViewById(R.id.recyclemy);
        addmybtn=findViewById(R.id.addmy);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<ArrayList<HashMap<String,String>>> call = service.names(thisusername);
        call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                int status = response.code();
                if (response.isSuccessful()) {
                    collectdata=response.body();
                    recyclemy.setLayoutManager(new LinearLayoutManager(MainActivity5.this));
                    recyclemy.setAdapter(new AddDataAdapter(MainActivity5.this,collectdata));
                } else {
                    Toast.makeText(MainActivity5.this,"Could Not Load My Lists", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                Log.d("API", "Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity5.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addmybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shownewdialog();
            }
        });
    }
    private void shownewdialog(){
        getWindow().setEnterTransition(new Slide());
        final Dialog dialog = new Dialog(MainActivity5.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.mylistpopup);
        EditText edtgpname=dialog.findViewById(R.id.addgpname);
        Button confirm=dialog.findViewById(R.id.confirmbtn);
        EditText edtgpcolumn=dialog.findViewById(R.id.addgpcolumn);
        Button addgpbtn=dialog.findViewById(R.id.addgpbtn);
        Button donethis=dialog.findViewById(R.id.crtable);
        TextView gpcolumn= dialog.findViewById(R.id.gpcolumn);
        TextView dataselect=dialog.findViewById(R.id.dataselect);
        Spinner dataaspinner=dialog.findViewById(R.id.dataspinner);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service=retrofit.create(Service.class);
                if(edtgpname.getText().toString().equals("")){
                    Toast.makeText(MainActivity5.this, "Enter Group Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    Call<Void> call = service.addlist(thisusername,edtgpname.getText().toString());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int status = response.code();
                            if (response.isSuccessful()) {
                                gpcolumn.setVisibility(View.VISIBLE);
                                addgpbtn.setVisibility(View.VISIBLE);
                                edtgpcolumn.setVisibility(View.VISIBLE);
                                dataselect.setVisibility(View.VISIBLE);
                                dataaspinner.setVisibility(View.VISIBLE);
                                confirm.setVisibility(View.GONE);
                                donethis.setVisibility(View.VISIBLE);
                                lstnaming=edtgpname.getText().toString();
                                final ArrayList<String> dataitmspinner=new ArrayList<>();
                                dataitmspinner.add("String");
                                dataitmspinner.add("Integer");
                                dataitmspinner.add("Floating Point");
                                ArrayAdapter<String> newadapter=new ArrayAdapter<>(MainActivity5.this, android.R.layout.simple_spinner_dropdown_item,dataitmspinner);
                                dataaspinner.setAdapter(newadapter);
                                dataaspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String selectedItem = parent.getItemAtPosition(position).toString();
                                        dattype = selectedItem;
                                        }
                                        public void onNothingSelected (AdapterView < ? > parent)
                                        {
                                        }
                                });
                            } else {
                                Toast.makeText(MainActivity5.this,"Could Not Add", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("API", "Error");
                            System.out.println(t.getMessage());
                            Toast.makeText(MainActivity5.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });

                }
            }
        });
        addgpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                String text = "";
                if (dattype.equals("String")) {
                    text = "varchar(30)";
                } else if (dattype.equals("Integer")) {
                    text = "int";
                } else {
                    text = "double";
                }
                Service service = retrofit.create(Service.class);
                Call<Void> call = service.createlist(edtgpname.getText().toString(), edtgpcolumn.getText().toString(), text);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                        } else {
                            Toast.makeText(MainActivity5.this,"Table Could not be created", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity5.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
        donethis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                shownewdialog2();
            }
        });
    }
    private void shownewdialog2(){
        getWindow().setEnterTransition(new Slide());
        final Dialog dialog = new Dialog(MainActivity5.this);
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
        Call<ArrayList<HashMap<String,String>>> call2 = service.mylistcolumn(lstnaming);
        call2.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                int status = response.code();
                if (response.isSuccessful()) {
                    datamylist=response.body();
                    recylecolumn.setLayoutManager(new LinearLayoutManager(MainActivity5.this));
                    recylecolumn.setAdapter(new MyListAdapter(MainActivity5.this,datamylist));
                } else {
                    Toast.makeText(MainActivity5.this, "Data could not be added", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                Log.d("API", "Error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity5.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Call<Void> call = service.getmylist(thisusername,lstnaming,senddata);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                            senddata=new ArrayList<>();
                        } else {
                            Toast.makeText(MainActivity5.this, "Enter Correctly", Toast.LENGTH_SHORT).show();
                            senddata=new ArrayList<>();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity5.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://localhost:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<ArrayList<HashMap<String,String>>> call = service.names(thisusername);
                call.enqueue(new Callback<ArrayList<HashMap<String,String>>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HashMap<String,String>>> call, Response<ArrayList<HashMap<String,String>>> response) {
                        int status = response.code();
                        if (response.isSuccessful()) {
                            collectdata=response.body();
                        } else {
                            Toast.makeText(MainActivity5.this,"Error in clearing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                        Log.d("API", "Error");
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity5.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                recyclemy.setLayoutManager(new LinearLayoutManager(MainActivity5.this));
                recyclemy.setAdapter(new AddDataAdapter(MainActivity5.this,collectdata));
            }
        });
    }
}