/*
 * Basé sur samples/ShortcutSample
 * 
 * 
 * 
 * 
 */
package com.example.android.servicevvnx;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "ReceiverVvnx";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive dans MyReceiver");
    }

}
