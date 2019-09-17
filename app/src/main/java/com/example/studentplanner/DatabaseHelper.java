package com.example.studentplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseInstance = null;

    public static DatabaseHelper getInstance(Context context) {

        if (databaseInstance == null) {

            databaseInstance = new DatabaseHelper((FragmentActivity) context);

        }

        return databaseInstance;

    }

    public static final String DATABASE_NAME = "StudentPlanner.db";

    // The Semester Table:
    public static final String SEMESTER_TABLE = "sem_table";
    public static final String SEMESTER_ID = "sem_id";               // Primary key (autoincrement)
    public static final String SEMESTER_NAME = "sem_name";           // NOT NULL
    public static final String CLASS_ONE = "class_1";
    public static final String CLASS_TWO = "class_2";
    public static final String CLASS_THREE = "class_3";
    public static final String CLASS_FOUR = "class_4";
    public static final String CLASS_FIVE = "class_5";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SEMESTER_TABLE +
                " (sem_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sem_name TEXT NOT NULL, " +
                "class_1 TEXT, class_2 TEXT, class_3 TEXT, class_4 TEXT, class_5 TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SEMESTER_TABLE);
        onCreate(db);
    }

    public boolean insertSemesterData(String sem_name, String class_1, String class_2, String class_3, String class_4, String class_5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SEMESTER_NAME, sem_name);
        contentValues.put(CLASS_ONE, class_1);
        contentValues.put(CLASS_TWO, class_2);
        contentValues.put(CLASS_THREE, class_3);
        contentValues.put(CLASS_FOUR, class_4);
        contentValues.put(CLASS_FIVE, class_5);
        long result = db.insert(SEMESTER_TABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<String> getSemesterClasses(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + SEMESTER_TABLE + " WHERE " + SEMESTER_ID + " = " + (position + 1);
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String class_1 = cursor.getString(cursor.getColumnIndex(CLASS_ONE));
                    String class_2 = cursor.getString(cursor.getColumnIndex(CLASS_TWO));
                    String class_3 = cursor.getString(cursor.getColumnIndex(CLASS_THREE));
                    String class_4 = cursor.getString(cursor.getColumnIndex(CLASS_FOUR));
                    String class_5 = cursor.getString(cursor.getColumnIndex(CLASS_FIVE));
                    list.add(class_1);
                    list.add(class_2);
                    list.add(class_3);
                    list.add(class_4);
                    list.add(class_5);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getSemesterNames() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + SEMESTER_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String semName = cursor.getString(cursor.getColumnIndex(SEMESTER_NAME));
                    list.add(semName);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }


    // This method should be called in sub2Activity
    public ArrayList<String> getCurrentSemesterClasses() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT sem_id FROM " + SEMESTER_TABLE;
            Cursor cursor = db.rawQuery(selectQuery,null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()); {
                    String class_1 = cursor.getString(cursor.getColumnIndex(CLASS_ONE));
                    String class_2 = cursor.getString(cursor.getColumnIndex(CLASS_TWO));
                    String class_3 = cursor.getString(cursor.getColumnIndex(CLASS_THREE));
                    String class_4 = cursor.getString(cursor.getColumnIndex(CLASS_FOUR));
                    String class_5 = cursor.getString(cursor.getColumnIndex(CLASS_FIVE));
                    list.add(class_1);
                    list.add(class_2);
                    list.add(class_3);
                    list.add(class_4);
                    list.add(class_5);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

}