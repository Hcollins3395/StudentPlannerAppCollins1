package com.example.studentplanner;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.PendingIntent.getBroadcast;

public class sub2Activity extends AppCompatActivity  {

    DatabaseHelper db;
    public String selectedClass;
    public String selectedAssignmentType;

    // This is the actual class that comes off of the Home screen when they press the button
    // The XML file that displays this should be activity_sub2
    // content_sub2 is where we can edit the XML and the activity_sub2 is just what the view is.



    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = DatabaseHelper.getInstance(this);
        final TextView tv_token = findViewById(R.id.tv_token);

        // Initializing Buttons:
        Button btn_create_event = findViewById(R.id.btn_create_event);
        Button btnCancel = findViewById(R.id.btn_cancel);

        // Checking the version because it wont work with certain versions:
 /*       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
*/

        // Setting action for btn_create_event:
        btn_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();
            }
        });

        //Getting date from HomeFragment and displaying in a TextView:
        Intent dateIntent = getIntent();
        String date = dateIntent.getStringExtra("date");
        TextView calendarDate = findViewById(R.id.calendar_date);
        calendarDate.setText(date);


        // Code to actually get to this activity from the button on home screen:
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

        // Cancel button Intent:
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sub2Activity.this, HomeFragment.class);
                startActivity(intent);
            }
        });


        //  Loading classes into class spinner:
        Spinner currentClasses = findViewById(R.id.spinner_classes);
        ArrayList<String> listClasses = db.getCurrentSemesterClasses();
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listClasses);
        currentClasses.setAdapter(semesterAdapter);


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


    }


    // Creating a notification:
    private void displayNotification()
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_event_note)
                    .setContentTitle("Test")
                    .setContentText("You have a " + selectedAssignmentType + " for ")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat nManagerCompat = NotificationManagerCompat.from(this);
        nManagerCompat.notify(1, mBuilder.build());
    }



}
