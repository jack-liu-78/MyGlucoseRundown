package com.example.marty.myglucoserundown;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by marty on 12/13/2017.
 * //This activity is what is used to make the notification repeat daily and make the notification have the appropriate look
 */

public class Notification_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeatingIntent = new Intent(context, homeScreen.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentIntent(pendingIntent).setSmallIcon(R.drawable.mgr_icon)
                .setContentTitle("Glucose Rundown")
                .setContentText("Take a Reading")
                .setSound(soundUri)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        notificationManager.notify(100,builder.build());
    }
}
