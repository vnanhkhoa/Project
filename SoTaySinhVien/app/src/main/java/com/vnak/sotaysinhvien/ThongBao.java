package com.vnak.sotaysinhvien;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.vnak.sotaysinhvien.Task.LichHocTask;
import com.vnak.sotaysinhvien.fragment.HomeFragment;
import com.vnak.sotaysinhvien.model.CalendarST;
import com.vnak.sotaysinhvien.model.CalendarSubject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class ThongBao extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (intent.getAction() == Intent.ACTION_DATE_CHANGED) {
            if (connectivityManager.getActiveNetworkInfo() != null){
                SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
                if (preferences != null) {
                    String msv = preferences.getString("MSV",null);
                    if (msv != null) {
                        String urlservice = new Service().URLSERVER+"api/calendartoday/"+msv;
                        System.out.println("SERVER "+urlservice);
                        CalendarToday calendarToday = new CalendarToday(context);
                        calendarToday.execute(urlservice);
                    }
                }
            }
        }
    }
    private void xulyNotification(Context context) {
        @SuppressLint("ResourceAsColor") NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setContentTitle("Thông báo lịch học")
                .setContentText("Hôm nay bạn có lịch học")
                .setColor(R.color.colorPrimary)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification.build());
    }

    class CalendarToday extends LichHocTask {
        Context context;

        public CalendarToday(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<CalendarSubject> calendarSubjects) {
            super.onPostExecute(calendarSubjects);
            if (calendarSubjects.size() > 0) {
                xulyNotification(context);
            }
        }
    }

}
