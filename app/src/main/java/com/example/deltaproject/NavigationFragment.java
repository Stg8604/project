package com.example.deltaproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
public class NavigationFragment extends Fragment {
    private FragmentListener call;
    public interface FragmentListener{
        void moveactivity();
        void todayactivity();
        void mylistsactivity();
        void reminderactivity();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            call = (FragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement ");
        }
    }

    private TextView username,dailytasks,today,mylists,reminders;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.threeline,container,false);
        username=v.findViewById(R.id.username);
        dailytasks=v.findViewById(R.id.daily);
        today=v.findViewById(R.id.today);
        mylists=v.findViewById(R.id.mylists);
        reminders=v.findViewById(R.id.ogreminders);
        dailytasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.moveactivity();
            }
        });
        dailytasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.todayactivity();
            }
        });
        mylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.mylistsactivity();
            }
        });
        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.reminderactivity();
            }
        });
        return v;
    }
}
