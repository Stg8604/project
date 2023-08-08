package com.example.deltaproject;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class RemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ModelAdapter(this.getApplicationContext(), intent);
    }
}
