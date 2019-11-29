package com.example.studentplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

/**********************************************************************
                     ***  Database Class ***
 *  This class connects the app to an SQLite Database.
 *
 *  There are multiple methods here that are called all throughout
 *  the app to add methods into the database.  There are two
 *  tables in the database, one to store the name of the
 *  semesters and courses as well as a table to save events
 *  that the user creates that includes the date, course,
 *  and assignment type.
 ***********************************************************************/
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
        // The next two calls create the two tables with the values specified above:
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

    // Called in AddSemesterFragment - inserts the courses and semester name into database
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

    // Called in sub2Activity - inserts info into the events table
    public boolean insertEvent(String date_of_event, String sem_of_event, String class_of_event, String assignment_type) {
        SQLiteDatabase db = this.getWritableDatabase();         // To write into the database.
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

    // Called in PrevSemesterFragment - based on what is chosen in the spinner, this displays that semester's courses:
    public ArrayList<String> getSemesterClasses(int position) {
        SQLiteDatabase db = this.getReadableDatabase();     // To read the database.
        ArrayList<String> list = new ArrayList<>();         // This will be used to print out all of the classes
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

    // Used in PrevSemesterFragment - This gets a list of all of the semesters that have been created:
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


    // This method is called in sub2Activity - This just shows all of the classes in the latest semester inside a spinner:
    public ArrayList<String> getCurrentSemesterClasses() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + SEMESTER_TABLE + " ORDER BY " + SEMESTER_ID + " DESC LIMIT 1;";
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

    // This method is called in the sub2Activity class to be used in the creation of an event:
    public List<String> getCurrentSemesterName() {
        List<String> name = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + SEMESTER_TABLE + " ORDER BY " + SEMESTER_ID + " DESC LIMIT 1;";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String currentSemesterName = cursor.getString(cursor.getColumnIndex(SEMESTER_NAME));
                    name.add(currentSemesterName);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return name;
    }


    //  This method is called in the ViewAssignments class that displays all the dates from the database:
    public ArrayList<String> getEventDates() {
        ArrayList<String> dateList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT date_of_event FROM " + EVENTS_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    for (int i = 0; i < db.getMaximumSize(); i++) {
                        dateList.add(cursor.getString(cursor.getColumnIndex(DATE_OF_EVENT)));
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();

            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return dateList;
    }

    //  This gets all of the courses that are inside the events table:
    public ArrayList<String> getEventClasses() {
        ArrayList<String> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT class_of_event FROM " + EVENTS_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    for (int i = 0; i < db.getMaximumSize(); i++) {
                        classList.add(cursor.getString(cursor.getColumnIndex(CLASS_OF_EVENT)));
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();

            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return classList;
    }

    //  This method is also called in the sub2Activity and is used to get all of the assignment types from the database:
    public ArrayList<String> getEventAssignmentTypes() {
        ArrayList<String> assignmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT assignment_type FROM " + EVENTS_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    for (int i = 0; i < db.getMaximumSize(); i++) {
                        assignmentList.add(cursor.getString(cursor.getColumnIndex(ASSIGNMENT_TYPE)));
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();

            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return assignmentList;
    }

    //  This method is called in the PopUp class to display classes on a certain date:
    public ArrayList<String> getClassesOnDate(String date) {
        ArrayList<String> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + DATE_OF_EVENT + " LIKE '%" + date + "%'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    for (int i = 0; i < db.getMaximumSize(); i++) {
                        classList.add(cursor.getString(cursor.getColumnIndex(CLASS_OF_EVENT)));
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();

            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return classList;


    }

    //  This method is called in the PopUp class to display assignment types on a certain date:
    public ArrayList<String> getAssignmentsOnDate(String date) {
        ArrayList<String> assignmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + DATE_OF_EVENT + " LIKE '%" + date + "%'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    for (int i = 0; i < db.getMaximumSize(); i++) {
                        assignmentList.add(cursor.getString(cursor.getColumnIndex(ASSIGNMENT_TYPE)));
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();

            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return assignmentList;

    }

    public ArrayList<Events> collectEventsPerMonth(String month, String year)
    {
        ArrayList<Events> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + DATE_OF_EVENT + " LIKE '%" + year + "." + month + "%'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    for (int i = 0; i < db.getMaximumSize(); i++) {
                        String course = cursor.getString(cursor.getColumnIndex(CLASS_OF_EVENT));
                        String assignmentType = cursor.getString(cursor.getColumnIndex(ASSIGNMENT_TYPE));
                        String date = cursor.getString(cursor.getColumnIndex(DATE_OF_EVENT));
                        Events event = new Events(course, assignmentType, date);
                        events.add(event);
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();

            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return events;
    }

}