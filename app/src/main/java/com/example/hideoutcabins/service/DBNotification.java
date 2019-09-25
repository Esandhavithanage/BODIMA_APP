package com.example.hideoutcabins.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.hideoutcabins.Request;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBNotification extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    FirebaseDatabase database;
    public static DatabaseReference RequestRef, SpyStatus;
    String devicemodel;

    @Override
    public void onCreate() {
        super.onCreate();
        //android.os.Debug.waitForDebugger();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        RequestRef = FirebaseDatabase.getInstance().getReference().child("reqest");
        ListenerForRequestDone();
        System.out.println("Service started");
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("RESTART_SERVICE");
        sendBroadcast(intent);
    }

    public void ListenerForRequestDone() {
        RequestRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.hasChild("status") && dataSnapshot.child("status").getValue().equals("PendingP")){
                    System.out.println("child added");
                    System.out.println(dataSnapshot);


                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), Request.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("RID",dataSnapshot.getKey());
                    i.putExtra("RCname",String.valueOf(dataSnapshot.child("cName").getValue()));
                    startActivity(i);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // StartImageCapture(1);
                System.out.println("child changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("child removed");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                System.out.println("child moved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("child error");
            }
        });
    }
}
