package com.example.btl_android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String time = intent.getStringExtra("thoigian");
        String ten = intent.getStringExtra("ten");

        Log.d("TAG", "onReceive: ádsdasds");
        if(intent.getAction().equals("MyAction")){

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("201","ChannelAlarm",NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Mieuta");
                notificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"201")
                    .setContentTitle("Thông Báo " +time)
                    .setContentText(ten)
                    .setSmallIcon(R.drawable.ic_noti)
                    .setColor(Color.RED)
                    .setCategory(NotificationCompat.CATEGORY_ALARM);
            notificationManager.notify(getNotificationId(),builder.build());
            Intent intent1 = new Intent(context, Music.class);
            context.startService(intent1);
        }


    }

    private  int getNotificationId(){
        int time = (int) new Date().getTime();
        return time;
    }

}
