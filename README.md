# Réception d'intents systèmes sans UI

## Service - ServiceVvnx

Indispensable pour lancer la première fois l'appli (démarrage intent BOOT_COMPLETED -> l'appli doit avoir été lancée au moins
une fois)


## BroadcastReceiver - MyReceiver

2 receivers: BOOT_COMPLETED et BATTERY_CHANGED
nb: le BOOT_COMPLETED peut se contenter d'être registeré dans le manifest
mais pour BATTERY_CHANGED, depuis OREO et la chasse aux sorcières du background: il faut registerer le receiver dans le code,
ce que je fais dans le service. (registerReceiver)


## Les commandes:

adb uninstall com.example.android.servicevvnx

adb install out/target/product/generic_arm64/system/app/ServiceVvnx/ServiceVvnx.apk
ou
adb install out/target/product/mido/system/app/ServiceVvnx/ServiceVvnx.apk

dumpsys deviceidle whitelist +com.example.android.servicevvnx

am start-service com.example.android.servicevvnx/.ServiceVvnx

logcat -s ReceiverVvnx

am force-stop com.example.android.servicevvnx
