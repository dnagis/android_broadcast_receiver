package com.example.android.receivervvnx;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * récup bdd sqlite
 * adb pull /data/data/com.example.android.receivervvnx/databases/log_intents.db
 * 
 */ 



public class BaseDeDonnees extends SQLiteOpenHelper {
	
	private static final String TAG = "SQLVvnx";

    private static final String DATABASE_NAME = "log_intents.db";
    private static final int DATABASE_VERSION = 1;
    //private static final String CREATE_BDD = "CREATE TABLE loc (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIME INTEGER NOT NULL, CELLID INTEGER NOT NULL, MCC INTEGER NOT NULL, MNC INTEGER NOT NULL, LAC INTEGER NOT NULL, RADIO TEXT NOT NULL)";
    private static final String CREATE_BDD = "CREATE TABLE batterie (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIME INTEGER NOT NULL, BATT INTEGER NOT NULL)";

    public BaseDeDonnees(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate sql");	
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
