package com.example.deltaproject;

import static com.example.deltaproject.MainActivity.context;
import static com.example.deltaproject.MainActivity6.channelid;
import static com.example.deltaproject.MainActivity6.notifid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        showReminderNotification(context);
    }

    private void showReminderNotification(Context context) {
        createNotificationChannel();

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notif);
        remoteViews.setTextViewText(R.id.reminder, "Reminder Title");

        // Set up the button click intent
        Intent snoozeIntent = new Intent(context, SnoozeReceiver.class);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.closenotif, snoozePendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelid)
                .setCustomContentView(remoteViews)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        Toast.makeText(context, "doneyes", Toast.LENGTH_SHORT).show();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notifid, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "reminder_channel_id";
            CharSequence channelName = "Reminder Channel";
            String channelDescription = "Channel for reminder notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH; // Adjust as needed

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.setDescription(channelDescription);
            // Configure other properties like sound, vibration, light color, etc.

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
}


