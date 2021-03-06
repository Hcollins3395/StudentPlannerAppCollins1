package com.example.studentplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/*****************************************************************************
                            *** Pop Up ***
 * This class is used to display any events that are happening on
 * whatever day is chosen on the calendar.
 ******************************************************************************/
public class PopUp extends AppCompatActivity {
    DatabaseHelper db;
    String selectedDate = " ";
    TextView classes;
    TextView tvTitle;
    TextView assignmentTypes;
    String classesOnDate = " ";
    String assignmentsOnDate = " ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.popupwindow);

        Intent incomingIntent = getIntent();
        selectedDate = incomingIntent.getStringExtra("date");


        // Intent incomingIntent = getIntent();
        // selectedDate = incomingIntent.getStringExtra("homeScreenDate");
        db = DatabaseHelper.getInstance(this);
        tvTitle = findViewById(R.id.tvPopupTitle);
        classes = findViewById(R.id.classNames);
        assignmentTypes = findViewById(R.id.assignmentNames);
        Button goToCreateEvent = findViewById(R.id.goToCreateEvent);


        // This is to set the size of the popup window:

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width  = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.85), (int)(height*.50));

        tvTitle.setText(selectedDate);


        for(int i = 0; i < db.getClassesOnDate(selectedDate).size(); i++)
        {
            classesOnDate = classesOnDate.concat(db.getClassesOnDate(selectedDate).get(i) + "\n");
        }

        for(int i = 0; i < db.getAssignmentsOnDate(selectedDate).size(); i++)
        {
            assignmentsOnDate = assignmentsOnDate.concat(db.getAssignmentsOnDate(selectedDate).get(i) + "\n");
        }

        if(db.getClassesOnDate(selectedDate).isEmpty() == true)
        {
            classes.setText("No assignments.");
        }
        else
        {
            classes.setText(classesOnDate);
        }


        if(db.getAssignmentsOnDate(selectedDate).isEmpty() == true)
        {
            assignmentTypes.setText(" ");
        }
        else
        {
            assignmentTypes.setText(assignmentsOnDate);
        }

        goToCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in  = new Intent(PopUp.this, sub2Activity.class);
                in.putExtra("date", selectedDate);
                startActivity(in);
            }
        });


    }


}
