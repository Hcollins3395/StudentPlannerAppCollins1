package com.example.studentplanner;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


/***************************************************************************************
                             *** Create an Event ***
 * This class was not what I was planning on using so the name is a messed up,
 * however, this class is very important to the app.
 *
 * This class allows the user to select a class and an assignment type and packages
 * that information with the date from the home fragment and adds all of it to the
 * database.
 *
 * This is also where the app calls the notification method to send a notification
 * to the phone whenever the timer is up.  To delay the notification, a Handler
 * object was used.
 ****************************************************************************************/
public class sub2Activity extends AppCompatActivity  {

    // This is the actual class that comes off of the Home screen when they press the button
    // The XML file that displays this should be activity_sub2
    // content_sub2 is where we can edit the XML and the activity_sub2 is just what the view is.


    private NotificationCompat.Builder notification;
    DatabaseHelper db;
    private Handler handler = new Handler();

    // Strings in this class:
    public String selectedAssignmentType;
    public String selectedClass;
    public static final String PRIMARY_CHANNEL_ID = "Primary Notifications Channel";
    String selectedDate;
    String selectedDatePlusTime;
    String currentSemester;

    // ID for notifications:
    public static final int uniqueID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    // Used to help calculate values
    Date date;
    Date currentDate;
    Long sDateTIM;
    Long subCDAndSD;

    // values from database
    Long currentDateInMilliseconds;
    Long homeworkValueInMilliseconds;
    Long quizValueInMilliseconds;
    Long testValueInMilliseconds;
    Long examValueInMilliseconds;
    Long paperValueInMilliseconds;
    Long projectValueInMilliseconds;

    // Final calculated values:
    Long finalHomeworkInMilliseconds;
    Long finalQuizInMilliseconds;
    Long finalTestInMilliseconds;
    Long finalExamInMilliseconds;
    Long finalPaperInMilliseconds;
    Long finalProjectValueInMilliseconds;

    Button btn_create_event;
    TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = DatabaseHelper.getInstance(this);

        tv_date = findViewById(R.id.calendar_date);


        // Getting value from the home fragment
        Intent incomingIntent = getIntent();
        selectedDate = incomingIntent.getStringExtra("date");
        tv_date.setText(selectedDate);
        selectedDatePlusTime = selectedDate + " 07:00:00";

        // sDateTIM = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = sdf.parse(selectedDatePlusTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sDateTIM = date.getTime();

        currentDate = new Date();
        currentDateInMilliseconds = currentDate.getTime();
        subCDAndSD = sDateTIM - currentDateInMilliseconds;

        // Calculating assignment type values
        homeworkValueInMilliseconds = new Long(db.getHomeworkValue() * 86400000);
        quizValueInMilliseconds     = new Long(db.getQuizValue() * 86400000);
        testValueInMilliseconds     = new Long(db.getTestValue() * 86400000);
        examValueInMilliseconds     = new Long(db.getExamValue() * 86400000);
        paperValueInMilliseconds     = new Long(db.getPaperValue() * 86400000);
        projectValueInMilliseconds  = new Long(db.getProjectValue() * 86400000);

        // Calculating final values:
        final Long finalHomeworkInMilliseconds     = subCDAndSD - homeworkValueInMilliseconds;
        final Long finalQuizInMilliseconds         = subCDAndSD - quizValueInMilliseconds;
        final Long finalTestInMilliseconds         = subCDAndSD - testValueInMilliseconds;
        final Long finalExamInMilliseconds         = subCDAndSD - examValueInMilliseconds;
        final Long finalPaperInMilliseconds        = subCDAndSD - paperValueInMilliseconds;
        final Long finalProjectValueInMilliseconds = subCDAndSD - projectValueInMilliseconds;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getIntent().getExtras();



        //  Loading classes into class spinner:
        final Spinner currentClasses = findViewById(R.id.spinner_classes);
        ArrayList<String> listClasses = db.getCurrentSemesterClasses();
        ArrayAdapter<String> semesterAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listClasses);
        currentClasses.setAdapter(semesterAdapter1);

        currentClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = currentClasses.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //  Writing the assignment spinner:
        final Spinner spinner_assignment = findViewById(R.id.spinner_assignment_type);
        String[] assignmentSpinner = new String[]{"Homework Assignment", "Quiz", "Paper", "Test", "Exam", "Project"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, assignmentSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_assignment.setAdapter(adapter);


        spinner_assignment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAssignmentType = spinner_assignment.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // When this button is clicked, we want to add all values to database and create a notification:
        btn_create_event = findViewById(R.id.btn_create_event);
        btn_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notification = new NotificationCompat.Builder(sub2Activity.this, PRIMARY_CHANNEL_ID);
                notification.setSmallIcon(R.drawable.ic_notification);
                notification.setWhen(System.currentTimeMillis());
                notification.setContentTitle("You have an upcoming event");
                notification.setContentText("You have an event for " + selectedAssignmentType + " for " + selectedClass);

                boolean isInserted = db.insertEvent(selectedDate, currentSemester, selectedClass, selectedAssignmentType);
                if (isInserted == true)
                    Toast.makeText(sub2Activity.this, "EVENT ADDED", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(sub2Activity.this, "EVENT NOT ADDED", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(sub2Activity.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(sub2Activity.this,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                notification.setContentIntent(pendingIntent);
                if(selectedAssignmentType == "Homework Assignment")
                    handler.postDelayed(runnable, finalHomeworkInMilliseconds);
                else if(selectedAssignmentType == "Quiz")
                    handler.postDelayed(runnable, finalQuizInMilliseconds);
                else if(selectedAssignmentType == "Test")
                    handler.postDelayed(runnable, finalTestInMilliseconds);
                else if(selectedAssignmentType == "Exam")
                    handler.postDelayed(runnable, finalExamInMilliseconds);
                else if(selectedAssignmentType == "Project")
                    handler.postDelayed(runnable, finalPaperInMilliseconds);
                else if(selectedAssignmentType == "Paper")
                    handler.postDelayed(runnable, finalProjectValueInMilliseconds);

            }
            private Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    NotificationManager nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(uniqueID, notification.build());
                }
            };
        });


    }
}
