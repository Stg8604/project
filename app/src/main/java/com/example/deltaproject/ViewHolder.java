package com.example.deltaproject;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView itemname,taskname1,slot,side,itmnam,slotname,slottime,notename,sptaskname,spslot,item1,listnames,getitlistname,txtcolumn,txtvalue,tskname,timeslot;
    CardView cardView,itemcard;
    EditText lstaddedit;
    CheckBox checkboxitem;
    Button select,done,spforget,spnotes,forgotselect,clear,lstaddname,adlistdata,deletelistdata,getitmclose;
    RecyclerView recycleitem,columnrecycle;
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
        clear=itemView.findViewById(R.id.clear);
        lstaddedit=itemView.findViewById(R.id.lstaddedit);
        lstaddname=itemView.findViewById(R.id.lstaddname);
        listnames=itemView.findViewById(R.id.listnames);
        adlistdata=itemView.findViewById(R.id.adlistdata);
        deletelistdata=itemView.findViewById(R.id.deletelistdata);
        itemcard=itemView.findViewById(R.id.itemlistcard);
        getitlistname=itemView.findViewById(R.id.getitlistname);
        recycleitem=itemView.findViewById(R.id.getitemrecycle);
        getitmclose=itemView.findViewById(R.id.getitmclose);
        txtcolumn=itemView.findViewById(R.id.txtcolumn);
        txtvalue=itemView.findViewById(R.id.txtvalue);
        columnrecycle=itemView.findViewById(R.id.columnrecycle);
        tskname=itemView.findViewById(R.id.tskname);
        timeslot=itemView.findViewById(R.id.timeslot);
        checkboxitem=itemView.findViewById(R.id.checkboxitem);
    }
}
