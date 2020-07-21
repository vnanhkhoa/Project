package com.vnak.sotaysinhvien;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static android.content.Context.ALARM_SERVICE;


public class ThongBao extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null){
            if (intent.getAction() == Intent.ACTION_DATE_CHANGED) {
                System.out.println("Anhkhoa1201");
                xulyNotification(context);

            }
        } else {
            System.out.println("Anhkhoa121");
        }
    }

    private void xulyNotification(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setContentTitle("Lịch Học")
                .setContentText("Đi Học")
                .setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification.build());
    }

}
