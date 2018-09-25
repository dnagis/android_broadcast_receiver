/**
 * Le service le plus simple au monde! register un broadcastReceiver
 *  
 * Squelette (arborescence et Android.mk) tiré de development/samples/
 * make ServiceVvnx (LOCAL_PACKAGE_NAME dans le Android.mk
 * 
 * adb uninstall com.example.android.receivervvnx
 * 
 * 
 * adb install out/target/product/mido/system/app/ReceiverVvnx/ReceiverVvnx.apk
 * ou
 * adb install out/target/product/generic_arm64/system/app/ReceiverVvnx/ReceiverVvnx.apk
 * 
 * Lancement du service en shell (nom du service: celui déclaré dans le manifest -component name-) 
 * 
 * #indispensable, survit au reboot (tant que tu réinstalles pas l'appli)
 * dumpsys deviceidle whitelist +com.example.android.receivervvnx
 * 
 * am start-service com.example.android.receivervvnx/.ServiceVvnx  
 *  
 * 
 * logcat -s ReceiverVvnx
 * 
 * 
 * 

 * 
 * Sans dumpsys deviceidle whitelist: 
 * 		en shell:  Error: app is in background uid null
 * 		en logcat: ActivityManager: Background start not allowed: service Intent { act=android.intent.action.DIAL cmp=com.example.android.servicevvnx/.ServiceVvnx } to com.example.android.servicevvnx/.ServiceVvnx from pid=13083 uid=0 pkg=com.android.shell
 * * En greppant à partir du msg d'erreur je trouve où ça bloque:
 * 		frameworks/base/services/core/java/com/android/server/am/ActiveServices.java		--> dans la fonction ActiveServices.startServiceLocked -> li 373 qui appelle:
 * 		frameworks/base/services/core/java/com/android/server/am/ActivityManagerService.java dans getAppStartModeLocked() li 8677
 * J'ai franchement pas tout compris à ce qui se passe dans getAppStartModeLocked(), mais en lisant il y avait des mention de whitelisting de deviceidle
 * (genre + " onwhitelist=" + isOnDeviceIdleWhitelistLocked(uid));) --> du coup comme je sais faire ce whitelisting j'ai essayé et ça marche (miracle), même sur un tel pas rooté
 * 
 * 
 * Lancement avec un intent explicite, syntaxe:
 * am start-service -a android.intent.action.DIAL com.example.android.receivervvnx/.ReceiverVvnx
 *
 * 
 * 
 */

package com.example.android.receivervvnx;

import android.app.Service;
import android.content.Intent;
import android.util.Log;
import android.os.IBinder;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;


public class ServiceVvnx extends Service {
	
	private static final String TAG = "ServiceVvnx";
 
    @Override
    public void onCreate() {
		Log.d(TAG, "onCreate");		
		/*
		 * "Context Registered Receivers", voir l'Android Dev Guide (manifest register suffit pas)
		 * obligatoire pour ACTION_BATTERY_CHANGED -> frameworks/base/core/java/android/content/Intent.java
		 * "You ****cannot**** receive this through components declared in manifests, only by explicitly registering for it with Context#registerReceiver(BroadcastReceiver, IntentFilter) Context.registerReceiver()}"
		 */
		BroadcastReceiver br = new ReceiverVvnx();
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		this.registerReceiver(br, filter);
		
		// /data/data/com.example.android.receivervvnx/databases
		BaseDeDonnees maBDD = new BaseDeDonnees(this); //pas suffisant pour passer dans le onCreate() de BaseDeDonnees
		SQLiteDatabase bdd=maBDD.getWritableDatabase(); //avec ça on passe dans le onCreate()
				
        //stopSelf(); //j'avais mis ça juste parce que le dev guide disait qu'il fallait faire le ménage soi-même
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "OnStartCommand");
		//stopSelf(); //j'avais mis ça juste parce que le dev guide disait qu'il fallait faire le ménage soi-même
		return START_NOT_STICKY;
	}

    @Override
    public void onDestroy() {		
		Log.d(TAG, "OnDestroy");
		stopSelf();		
	 }
	 
	  @Override
	public IBinder onBind(Intent intent) {
      // We don't provide binding, so return null
      return null;
	}
}

