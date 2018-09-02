/**
 * Le service le plus simple au monde! Envie de voir si je peux le démarrer en ligne de commande
 * basé sur HelloActivity, et sur la classe de service Alarm dans dev../samples
 */

package com.example.android.servicevvnx;

import android.app.Service;
import android.content.Intent;
import android.util.Log;
import android.os.IBinder;


public class ServiceVvnx extends Service {
	
	private static final String TAG = "ServiceVvnx";
 
    @Override
    public void onCreate() {
        stopSelf();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "ServiceVvnx:  OnStartCommand");
		stopSelf();
		return START_NOT_STICKY;
	}

    @Override
    public void onDestroy() {		
		Log.d(TAG, "ServiceVvnx: OnDestroy");		
	 }
	 
	  @Override
	public IBinder onBind(Intent intent) {
      // We don't provide binding, so return null
      return null;
	}
}

