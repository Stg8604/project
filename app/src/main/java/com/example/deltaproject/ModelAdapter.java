package com.example.deltaproject;


import static com.example.deltaproject.MainActivity.thisusername;
import static com.example.deltaproject.MainActivity2.datalist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ModelAdapter implements RemoteViewsService.RemoteViewsFactory {

        private Context context;

        public ModelAdapter(Context context, Intent intent) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public RemoteViews getViewAt(int position) {

            if(datalist==null || position>=datalist.size()){
                return null;
            }
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.checkboxlay);
            views.setTextViewText(R.id.checkboxitem,datalist.get(position).get("task"));
            views.setTextViewText(R.id.widtimestart,datalist.get(position).get("timestart")+"-"+datalist.get(position).get("timeend"));
            return views;
        }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public void onDataSetChanged() {
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
                        Toast.makeText(context,String.valueOf(status), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<HashMap<String,String>>> call, Throwable t) {
                    Log.d("API","Error");
                    System.out.println(t.getMessage());
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    @Override
    public void onDestroy() {

    }

    // Other methods like getItemId, hasStableIds, etc.
}


