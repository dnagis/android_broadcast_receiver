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

2 receivers: BOOT_COMPLETED et BATTERY_CHANGED
nb: le BOOT_COMPLETED peut se contenter d'être registeré dans le manifest
mais pour BATTERY_CHANGED, depuis OREO et la chasse aux sorcières du background: il faut registerer le receiver dans le code,
ce que je fais dans le service. (registerReceiver)


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
