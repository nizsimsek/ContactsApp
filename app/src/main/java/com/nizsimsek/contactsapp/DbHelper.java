package com.nizsimsek.contactsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context Context, String Name, SQLiteDatabase.CursorFactory Factory, int Version) {
        super(Context, Name, Factory, Version);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Contacts(Id Integer PRIMARY KEY AUTOINCREMENT, Name TEXT, Surname TEXT, Number TEXT, Email TEXT, Website TEXT, Address TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
