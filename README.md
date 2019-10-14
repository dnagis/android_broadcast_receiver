# Réception d'intents systèmes sans UI

## Service - ServiceVvnx

Indispensable pour lancer la première fois l'appli (pour un démarrage par intent BOOT_COMPLETED -> l'appli doit avoir été lancée au moins
une fois).
Le lancement du service 
	-> dumpsys deviceidle whitelist +com.example.android.receivervvnx (voir le .java pour détails du pourquoi)
	-> am start-service com.example.android.receivervvnx/.ServiceVvnx
	
Arrêter le service
	-> am force-stop com.example.android.receivervvnx

## BroadcastReceiver - ReceiverVvnx

3 receivers: BOOT_COMPLETED BATTERY_CHANGED BOND_STATE_CHANGED (bluetooth)

receivers can be Manifest-declared and/or Context-registered (calling this.registerReceiver(br, filter);)
see https://developer.android.com/guide/components/broadcasts

BOOT_COMPLETED 			can be manifest-declared only
BATTERY_CHANGED 		depuis OREO et la chasse aux sorcières du background: Context-registering mandatory
BOND_STATE_CHANGED		context-registering only works


## Les commandes:

make ReceiverVvnx

adb uninstall com.example.android.receivervvnx

adb install out/target/product/mido/system/app/ReceiverVvnx/ReceiverVvnx.apk
#ou
adb install out/target/product/generic_arm64/system/app/ReceiverVvnx/ReceiverVvnx.apk

dumpsys deviceidle whitelist +com.example.android.receivervvnx

am start-service com.example.android.receivervvnx/.ServiceVvnx

logcat -s ReceiverVvnx

am force-stop com.example.android.receivervvnx
