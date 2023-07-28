package com.example.deltaproject;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView itemname,taskname1,slot,side,itmnam,slotname,slottime,notename,sptaskname,spslot,item1;
    CardView cardView;
    Button select,done,spforget,spnotes,forgotselect;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemname=itemView.findViewById(R.id.itemname);
        taskname1=itemView.findViewById(R.id.taskname1);
        slot=itemView.findViewById(R.id.slot);
        side=itemView.findViewById(R.id.side);
        itmnam=itemView.findViewById(R.id.itmnam);
        select=itemView.findViewById(R.id.select);
        slotname=itemView.findViewById(R.id.slotname);
        slottime=itemView.findViewById(R.id.slottime);
        done=itemView.findViewById(R.id.done);
        cardView=itemView.findViewById(R.id.cardview1);
        notename=itemView.findViewById(R.id.noteitem);
        sptaskname=itemView.findViewById(R.id.spetaskname);
        spslot=itemView.findViewById(R.id.spslot);
        spforget=itemView.findViewById(R.id.spforget);
        spnotes=itemView.findViewById(R.id.spnotes);
        item1=itemView.findViewById(R.id.item1);
        forgotselect=itemView.findViewById(R.id.forgetselect);
    }
}
