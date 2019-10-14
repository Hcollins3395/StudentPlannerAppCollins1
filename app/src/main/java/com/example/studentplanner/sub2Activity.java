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
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class sub2Activity extends AppCompatActivity  {

    // This is the actual class that comes off of the Home screen when they press the button
    // The XML file that displays this should be activity_sub2
    // content_sub2 is where we can edit the XML and the activity_sub2 is just what the view is.


    private NotificationCompat.Builder notification;
    DatabaseHelper db;
    public String selectedAssignmentType;
    public String selectedClass;
    Button btn_create_event;
    TextView current_classes;
    public static final String PRIMARY_CHANNEL_ID = "Primary Notifications Channel";
    public static final int uniqueID = 12345;
    private Handler handler = new Handler();
    String selectedDate;
    String selectedDatePlusTime;
    TextView tv_date;
    Calendar cDate;
    Long cDateTIM;
    Long oneDayMil = longValue(8.64 * Math.pow(10, 7));         // homework
    Long fiveDaysMil = longValue(4.32 * Math.pow(10, 8));       // quiz
    Long nineDaysMil = longValue(7.776 * Math.pow(10, 8));      // test
    Long twelveDaysMil = longValue(1.037 * Math.pow(10, 9));    // exam, paper, project
    String currentSemester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = DatabaseHelper.getInstance(this);

        tv_date = findViewById(R.id.calendar_date);

        Intent incomingIntent = getIntent();
        selectedDate = incomingIntent.getStringExtra("date");
        tv_date.setText(selectedDate);
        selectedDatePlusTime = selectedDate + " at 07:00:00";

        cDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.ENGLISH);
        try {
            cDate.setTime(sdf.parse(selectedDatePlusTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cDateTIM = cDate.getTimeInMillis();

        current_classes = findViewById(R.id.currentSemesterName);
        currentSemester = db.getCurrentSemesterName().toString();
        current_classes.setText(currentSemester);


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


        final Spinner currentClasses = findViewById(R.id.spinner_classes);
        //  Loading classes into class spinner:
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

        btn_create_event = findViewById(R.id.btn_create_event);
        btn_create_event.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                    handler.postDelayed(runnable, (cDateTIM - oneDayMil));
                else if(selectedAssignmentType == "Quiz")
                    handler.postDelayed(runnable, (cDateTIM - fiveDaysMil));
                else if(selectedAssignmentType == "Test")
                    handler.postDelayed(runnable, (cDateTIM - nineDaysMil));
                else
                    handler.postDelayed(runnable, (cDateTIM - twelveDaysMil));
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
    public long longValue( double value) {
        return (long)value;
    }
}
