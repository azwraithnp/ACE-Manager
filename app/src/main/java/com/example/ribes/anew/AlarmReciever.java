package com.example.ribes.anew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Avinash Mishra on 7/2/2018.
 */

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // show toast
        Log.i("App", "called receiver method");
        int requestCode = intent.getIntExtra("id", 0);
        String subject = intent.getStringExtra("name");
        try{
            Utils.generateNotification(context, requestCode, subject);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}