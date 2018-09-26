/**
 * Receiver qui récupère des Intents systèmes
 * 
 * Le service registerReceiver pour battery_changed. Comme ce receiver reçoit aussi le boot, il appelle le service qui, lui, peut registerReceiver battery_changed
 * 
 * 
 * 
 * NB: Pour l'intent BOOT_COMPLETED -> register via manifest only = OK, alors que des intents comme BATTERY_CHANGED: pas possible
 * (registration de receiver pour des systems broadcasts limitée depuis android 7.0 (API level 25))
 *
 * au boot, je voudrais bien faire registerReceiver pour battery_changed directement ici (simplicité) mais je peux pas registerReceiver à partir d'un broadcastReceiver 
 * du coup j'appelle le service, et dans le onCreate() de ce service il y a registerReceiver.
 */ 

package com.example.android.receivervvnx;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.BatteryManager;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentResolver;
import android.content.ContentValues;

public class ReceiverVvnx extends BroadcastReceiver {
    private static final String TAG = "ReceiverVvnx";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive dans mon Receiver, action=" + intent.getAction());
        
             
        if (intent.getAction().equals(android.content.Intent.ACTION_BOOT_COMPLETED)) {
			Log.d(TAG, "intent boot received");	
			
			Intent i = new Intent(context, ServiceVvnx.class);
			context.startService(i);
			
			/** Pas possible car au runtime: 
			 * Unable to start receiver com.example.android....: android.content.ReceiverCallNotAllowedException: BroadcastReceiver components are not allowed to register to receive intents
			*BroadcastReceiver br = new MyReceiver();
			IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			context.registerReceiver(br, filter);*/
		}
        
        // frameworks/base/core/java/android/os/BatteryManager.java        
        if (intent.getAction().equals(android.content.Intent.ACTION_BATTERY_CHANGED)) {
			final int mBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 100);
			Log.d(TAG, "intent battery_changed - level: " + mBatteryLevel);
			
			/*
			 * SQL
			 * adb pull /data/data/com.example.android.receivervvnx/databases/log_intents.db
			 *  
			 */ 
			
			BaseDeDonnees maBDD = new BaseDeDonnees(context); //pas suffisant pour passer dans le onCreate() de BaseDeDonnees
			SQLiteDatabase bdd=maBDD.getWritableDatabase(); //avec ça on passe dans le onCreate()
			ContentValues values = new ContentValues();
			long timestamp = System.currentTimeMillis();
			values.put("TIME", timestamp);
            values.put("BATT", mBatteryLevel);
            bdd.insert("batterie", null, values);
		}
    }

}
