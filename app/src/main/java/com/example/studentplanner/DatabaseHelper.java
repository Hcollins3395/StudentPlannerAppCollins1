package com.example.studentplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseInstance = null;

    public static DatabaseHelper getInstance(Context context) {

        if (databaseInstance == null) {

            databaseInstance = new DatabaseHelper((FragmentActivity) context);

        }

        return databaseInstance;

    }

    public static final String DATABASE_NAME = "StudentPlannerApp.db";

    // The Semester Table:
    public static final String SEMESTER_TABLE = "sem_table";
    public static final String SEMESTER_ID = "sem_id";
    public static final String SEMESTER_NAME = "sem_name";
    public static final String CLASS_ONE = "class_1";
    public static final String CLASS_TWO = "class_2";
    public static final String CLASS_THREE = "class_3";
    public static final String CLASS_FOUR = "class_4";
    public static final String CLASS_FIVE = "class_5";

    // The Event Table:
    public static final String EVENTS_TABLE = "event_table";
    public static final String EVENT_ID = "event_id";
    public static final String SEMESTER_OF_EVENT = "sem_of_event";
    public static final String CLASS_OF_EVENT = "class_of_event";
    public static final String ASSIGNMENT_TYPE = "assignment_type";
    public static final String DATE_OF_EVENT = "date_of_event";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SEMESTER_TABLE +
                " (sem_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sem_name TEXT NOT NULL, " +
                "class_1 TEXT, class_2 TEXT, class_3 TEXT, class_4 TEXT, class_5 TEXT)");

        db.execSQL("CREATE TABLE " + EVENTS_TABLE + " (" + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_OF_EVENT + " DATE, " + SEMESTER_OF_EVENT +
                " TEXT, " + CLASS_OF_EVENT + " TEXT, " + ASSIGNMENT_TYPE + " TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SEMESTER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EVENTS_TABLE);
    }

    // Called in AddSemesterFragment
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

    // Called in sub2Activity
    public boolean insertEvent(String date_of_event, String sem_of_event, String class_of_event, String assignment_type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
            contentValues.put(DATE_OF_EVENT, date_of_event);
            contentValues.put(SEMESTER_OF_EVENT, sem_of_event);
            contentValues.put(CLASS_OF_EVENT, class_of_event);
            contentValues.put(ASSIGNMENT_TYPE, assignment_type);
            long result = db.insert(EVENTS_TABLE, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
    }

    // Called in PrevSemesterFragment
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

    // Used in PrevSemesterFragment
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
            String selectQuery = "SELECT * FROM " + SEMESTER_TABLE + " ORDER BY " + SEMESTER_ID + " DESC LIMIT 1;";
            Cursor cursor = db.rawQuery(selectQuery,null);
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

    public List<String> getCurrentSemesterName()
    {
        List<String> name = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try
        {
            String selectQuery = "SELECT * FROM " + SEMESTER_TABLE + " ORDER BY " + SEMESTER_ID + " DESC LIMIT 1;";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() > 0)
            {
                while(cursor.moveToNext())
                {
                    String currentSemesterName = cursor.getString(cursor.getColumnIndex(SEMESTER_NAME));
                    name.add(currentSemesterName);
                }
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
        return name;
    }


    public ArrayList<String> getEventDates()
    {
        ArrayList<String> dateList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try
        {
            String selectQuery = "SELECT date_of_event FROM " + EVENTS_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    for (int i = 0; i < db.getMaximumSize(); i++) {
                        dateList.add(cursor.getString(cursor.getColumnIndex(DATE_OF_EVENT)));
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();

            }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
        return dateList;
    }

    public ArrayList<String> getEventClasses()
    {
        ArrayList<String> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try
        {
        String selectQuery = "SELECT class_of_event FROM " + EVENTS_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                for (int i = 0; i < db.getMaximumSize(); i++) {
                    classList.add(cursor.getString(cursor.getColumnIndex(CLASS_OF_EVENT)));
                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();

        }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
        return classList;
    }

    public ArrayList<String> getEventAssignmentTypes()
    {
        ArrayList<String> assignmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try
        {
            String selectQuery = "SELECT assignment_type FROM " + EVENTS_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    for (int i = 0; i < db.getMaximumSize(); i++) {
                        assignmentList.add(cursor.getString(cursor.getColumnIndex(ASSIGNMENT_TYPE)));
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();

            }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
        return assignmentList;
    }

}