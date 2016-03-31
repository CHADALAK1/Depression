package com.example.chadreddick.depressionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chadreddick on 3/08/16.
 * SQL DATABASE OBJECT TO HOLD USER'S IMPORTANT DATA OVERTIME
 */
public class DBHelper extends SQLiteOpenHelper
{
    //Create Database Name, Version, and Table
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Client_Info";
    private static final String DATABASE_TABLE = "Info";

    //Create Database values
    private static final String KEY_TASK_ID = "_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    private int taskCount;

    public DBHelper(Context context)
    {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        //TODO: Create another table to hold User's data but still have a Username and Password
        //Create the Database Table
        String sqlStatement = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY, "
                + KEY_USERNAME + " TEXT, "
                + KEY_PASSWORD + " TEXT" + ")";
        database.execSQL(sqlStatement);
        taskCount = 0;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Destroy table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public void addClientData(ClientData CD)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        taskCount++;

        //put values in the table
        values.put(KEY_TASK_ID, taskCount);
        values.put(KEY_USERNAME, CD.getUsername());
        values.put(KEY_PASSWORD, CD.getPassword());

        //Insert values in the table
        db.insert(DATABASE_TABLE, null, values);

        //Close Database
        db.close();
    }

    public void editClientDataItem(ClientData CD)
    {
        //Get the Database and Content values
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Edit values
        values.put(KEY_USERNAME, CD.getUsername());
        values.put(KEY_PASSWORD, CD.getPassword());

        //Update the database
        db.update(DATABASE_TABLE, values, KEY_TASK_ID + " = ?", new String[]{String.valueOf(CD.getID())});
        db.close();
    }

    public ClientData getClientData(int ID)
    {
        //Get Database and Table to retrieve values
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_TASK_ID,KEY_USERNAME, KEY_PASSWORD},
                KEY_TASK_ID + "=?",
                new String[]{String.valueOf(ID)},
                null, null, null, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        //Assign values in temporary retrieval from Cursor object
        ClientData CD = new ClientData(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2));
        //Close Database
        db.close();

        //return assigned values from Cursor into the Temp ClientData object
        return CD;
    }

    public void deleteClientDataTable()
    {
        //Delete Database table
        SQLiteDatabase database = this.getReadableDatabase();

        database.delete(DATABASE_NAME, null, null);
        database.close();
    }

}
