/**
 * Le service le plus simple au monde! Envie de voir si je peux le démarrer en ligne de commande
 * basé sur HelloActivity, et sur la classe de service Alarm dans dev../samples
 * 
 * Lancement en shell avec la cmd am (activity manager), avec un intent explicite:
 * 
 * am start-service -a android.intent.action.DIAL com.example.android.servicevvnx/.ServiceVvnx
 * 
 * Pb si je fais que ça: le système refuse de démarrer avec les msg:
 * 		en shell:  Error: app is in background uid null
 * 		en logcat: ActivityManager: Background start not allowed: service Intent { act=android.intent.action.DIAL cmp=com.example.android.servicevvnx/.ServiceVvnx } to com.example.android.servicevvnx/.ServiceVvnx from pid=13083 uid=0 pkg=com.android.shell
 * * En greppant à partir du msg d'erreur je trouve où ça bloque:
 * 		frameworks/base/services/core/java/com/android/server/am/ActiveServices.java		--> dans la fonction ActiveServices.startServiceLocked -> li 373 qui appelle:
 * 		frameworks/base/services/core/java/com/android/server/am/ActivityManagerService.java dans getAppStartModeLocked() li 8677
 * J'ai franchement pas tout compris à ce qui se passe dans getAppStartModeLocked(), mais en lisant il y avait des mention de whitelisting de deviceidle
 * (genre + " onwhitelist=" + isOnDeviceIdleWhitelistLocked(uid));) --> du coup comme je sais faire ce whitelisting j'ai essayé et ça marche (miracle)
 * 
 * dumpsys deviceidle whitelist +com.example.android.servicevvnx
 * 
 * 
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

