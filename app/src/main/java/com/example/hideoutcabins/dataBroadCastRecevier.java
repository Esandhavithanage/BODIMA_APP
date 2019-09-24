package com.example.hideoutcabins;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class dataBroadCastRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, Request.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
