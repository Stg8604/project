package com.example.deltaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> itemList;

    public CheckBoxAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkboxlay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.itemCheckBox.setText(item);
        holder.itemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox itemCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            itemCheckBox = itemView.findViewById(R.id.checkboxitem);
        }
    }
}
