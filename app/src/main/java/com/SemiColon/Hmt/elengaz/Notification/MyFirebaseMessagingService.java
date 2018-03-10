package com.SemiColon.Hmt.elengaz.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.SemiColon.Hmt.elengaz.Activities.Client_Response_Orders;
import com.SemiColon.Hmt.elengaz.Activities.ServiceProvider_Home;
import com.SemiColon.Hmt.elengaz.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by elashry on 2/11/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> map = remoteMessage.getData();
        String n_message= map.get("message");
        String n_title  = map.get("title");
        String n_ids     = map.get("id");

        SharedPreferences pref = getSharedPreferences("user_id",MODE_PRIVATE);
        String user_id = pref.getString("id","");
        String user_type = pref.getString("user_type","");
        String session = pref.getString("session","");

        if (session.equals("logged_in"))
        {
            if (!TextUtils.isEmpty(user_type))
            {
                switch (user_type)
                {
                    case "client":
                        try {
                            JSONArray jsonArray = new JSONArray(n_ids);
                            List<String> mList = new ArrayList<>();
                            for (int index=0;index<jsonArray.length();index++)
                            {
                                if (jsonArray.get(index).toString().equals(user_id))
                                {
                                    mList.add(jsonArray.get(index).toString());
                                }
                            }

                            if (mList.size()>0)
                            {
                                sendNotification(n_title,n_message,"client",user_id);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "office":
                        try {
                            JSONArray jsonArray = new JSONArray(n_ids);
                            List<String> mList = new ArrayList<>();
                            for (int index=0;index<jsonArray.length();index++)
                            {
                                if (jsonArray.get(index).toString().equals(user_id))
                                {
                                    mList.add(jsonArray.get(index).toString());
                                }
                            }

                            if (mList.size()>0)
                            {
                                sendNotification(n_title,n_message,"office",user_id);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }

        Log.e("sexxxxxxxxxxxxx",user_id);

        /*if (!TextUtils.isEmpty(user_id))
        {

            sendNotification(n_message,n_title);
            for (String key :map.keySet())
            {
                Log.e("keys",key);
                Log.e("data",map.get(key));
            }

        }*/


    }
    private void sendNotification(String title, String messageBody,String user_type,String user_id) {


        if (user_type.equals("client"))
        {
            Intent intent = new Intent(this, Client_Response_Orders.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("client_id",user_id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.logo_icon)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }else if (user_type.equals("office"))
        {
            Intent intent = new Intent(this, ServiceProvider_Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("office_id",user_id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.logo_icon)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }





    }


}