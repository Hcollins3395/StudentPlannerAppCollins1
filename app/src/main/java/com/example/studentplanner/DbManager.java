package com.example.studentplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;

public class DbManager {

  /*  private static DatabaseHelper databaseInstance = null;
    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DbManager(Context c) {
        this.context = c;
    }


    public DbManager open() throws SQLException {
        this.dbHelper = new DatabaseHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {

        this.dbHelper.close();
    }

    public static DatabaseHelper getInstance(Context context) {

        if (databaseInstance == null) {

            databaseInstance = new DatabaseHelper((FragmentActivity) context);

        }

        return databaseInstance;

    }
    public void insertSemesterData(String name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SEMESTER_NAME, name);
        this.database.insert(DatabaseHelper.SEMESTER_TABLE, null, contentValue);
    }


    public boolean insertClassData(String class_1, String class_2, String class_3, String class_4, String class_5) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CLASS_ONE, class_1);
        contentValues.put(DatabaseHelper.CLASS_TWO, class_2);
        contentValues.put(DatabaseHelper.CLASS_THREE, class_3);
        contentValues.put(DatabaseHelper.CLASS_FOUR, class_4);
        contentValues.put(DatabaseHelper.CLASS_FIVE, class_5);
        this.database.insert(DatabaseHelper.CLASS_TABLE, null, contentValues);
        return false;
    }


    public Cursor fetchClasses() {
        Cursor cursor = this.database.query(DatabaseHelper.CLASS_TABLE, new String[]{DatabaseHelper.CLASS_ONE, DatabaseHelper.CLASS_TWO,
                DatabaseHelper.CLASS_THREE, DatabaseHelper.CLASS_FOUR, DatabaseHelper.CLASS_FIVE}, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;

    }
    public Cursor fetchSemesterName() {
        Cursor cursor = this.database.query(DatabaseHelper.SEMESTER_TABLE,
                new String[]{DatabaseHelper.SEMESTER_NAME}, null, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
*/
}

