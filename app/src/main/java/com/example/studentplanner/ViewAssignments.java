package com.example.studentplanner;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/*************************************************************************************************
                                ***  View Assignments  ***
 * This class shows all of the events that were created in the sub2Activity class.
 *
 * There are three TextViews used to accomplish this: one for the date, one for the
 * class, and one for the type of the assignment.
 *************************************************************************************************/
public class ViewAssignments extends AppCompatActivity {
DatabaseHelper db;
TableLayout tableLayout;
Button backButton;
String datesForTV = " ";
String classesForTV = " ";
String assignmentsForTV = " ";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assignments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(this);

        // Finding my table's IDs:
        tableLayout = findViewById(R.id.tableLayout);

        final TextView dates = findViewById(R.id.datesInformation);
        dates.setText(db.getEventDates().toString());

        final TextView classes = findViewById(R.id.classInformation);
        classes.setText(db.getEventClasses().toString());

        final TextView assignmentTypes = findViewById(R.id.assignmentInformation);
        assignmentTypes.setText(db.getEventAssignmentTypes().toString());


        // Creating the strings to go inside of the TextViews:
        // Getting dates from database and putting them into a String:
        for(int i = 0; i < db.getEventDates().size(); i++)
        {
            datesForTV = datesForTV.concat(db.getEventDates().get(i) + "\n");
        }

        // Getting classes from the database and putting them into a String:
        for(int i = 0; i < db.getEventClasses().size(); i++)
        {
            classesForTV = classesForTV.concat(db.getEventClasses().get(i) + "\n");
        }

        for(int i = 0; i < db.getEventAssignmentTypes().size(); i++)
        {
            assignmentsForTV = assignmentsForTV.concat(db.getEventAssignmentTypes().get(i) + "\n");
        }

        // Assigning Strings to columns:
        dates.setText(datesForTV);
        classes.setText(classesForTV);
        assignmentTypes.setText(assignmentsForTV);


        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAssignments.this, MainActivity.class);
                startActivity(intent);
            }
        });

     }

}
