package com.example.deltaproject;

import static com.example.deltaproject.MainActivity6.channelid;
import static com.example.deltaproject.MainActivity6.notifid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.security.Provider;

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Build and show the notification
        showCustomNotification();

        // Return START_STICKY to ensure the service restarts if it's killed by the system
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Intent notificationIntent = new Intent(getApplicationContext() ,  MainActivity6. class ) ;
        notificationIntent.putExtra( "fromNotification" , true ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        PendingIntent pendingIntent = PendingIntent. getActivity ( this, 0 , notificationIntent , 0 ) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , channelid) ;
        mBuilder.setContentTitle( "My Notification" ) ;
        mBuilder.setContentIntent(pendingIntent) ;
        mBuilder.setContentText( "Notification Listener Service Example" ) ;
        mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        mBuilder.setAutoCancel( true ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel(channelid , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId(channelid) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
        throw new UnsupportedOperationException( "Not yet implemented" ) ;
    }


    private void showCustomNotification() {
        /*
        createNotificationChannel();
        Intent buttonIntent = new Intent(this, MyBroadcastReceiver.class);
        buttonIntent.setAction("actionbtn"); // Replace with your desired action name
        PendingIntent buttonPendingIntent = PendingIntent.getBroadcast(this, 0, buttonIntent, PendingIntent.FLAG_MUTABLE);
        Intent notificationIntent = new Intent(this, MyBroadcastReceiver.class);
        notificationIntent.setAction("shownotification"); // Replace with your desired action name
        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(this, notifid, notificationIntent, PendingIntent.FLAG_MUTABLE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notif);
        remoteViews.setTextViewText(R.id.reminder, "Custom Notification Text");
        remoteViews.setOnClickPendingIntent(R.id.closenotif, buttonPendingIntent);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelid)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContent(remoteViews)
                .setAutoCancel(true);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            PendingIntent notificationIntent2 = PendingIntent.getBroadcast(this, notifid, buttonIntent, PendingIntent.FLAG_MUTABLE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationIntent);
        }*/
        createNotificationChannel();

        // Intent for the custom button click
        Intent buttonIntent = new Intent(this, MyBroadcastReceiver.class);
        buttonIntent.setAction("actionbtn"); // Replace with your desired action name
        PendingIntent buttonPendingIntent = PendingIntent.getBroadcast(this, 0, buttonIntent, PendingIntent.FLAG_MUTABLE);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notif);
        remoteViews.setTextViewText(R.id.reminder, "Custom Notification Text");
        remoteViews.setOnClickPendingIntent(R.id.closenotif, buttonPendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelid)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContent(remoteViews)
                .setAutoCancel(true);
        // Set the main notification click PendingIntent
        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notifid, builder.build());

        // Schedule the alarm to show the notification at the specified time

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = channelid; // Replace with your desired channel ID
            String channelName = "Your Channel Name"; // Replace with your desired channel name
            String channelDescription = "Your Channel Description"; // Replace with your desired channel description
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
