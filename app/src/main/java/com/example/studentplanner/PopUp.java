package com.example.studentplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*****************************************************************************
                            *** Pop Up ***
 * This class is used to display any events that are happening on
 * whatever day is chosen on the calendar.
 ******************************************************************************/
public class PopUp extends AppCompatActivity {
    DatabaseHelper db;
    CustomCalendarView capture;
    String selectedDate;
    TextView classes;
    TextView tvTitle;
    TextView assignmentTypes;
    String classesOnDate = " ";
    String assignmentsOnDate = " ";
    String capturedDate = capture.getTheSelectedDate();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

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
        getWindow().setLayout((int)(width*.85), (int)(height*.25));

        tvTitle.setText(capturedDate);


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
                in.putExtra("date", capturedDate);

                startActivity(in);
            }
        });


    }


}
