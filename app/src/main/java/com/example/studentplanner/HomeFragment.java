package com.example.studentplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/****************************************************************************
                            *** Home Fragment ***
 *  This is the Home Fragment aka the home screen.  This is the first
 *  screen that the user sees when they open the app and can be used to
 *  the view assignments page as well as the creating an event page.
 *
 *  This is also where the calendar is where they will be able to select a
 *  date and see what is going on on that date.
 *****************************************************************************/
public class HomeFragment extends Fragment {
DatabaseHelper db;
Button btnDisplayEvents;
String dateSelected = " ";
CalendarView calendarView;
TextView tv_welcome;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        calendarView = view.findViewById(R.id.calendar_view);
        tv_welcome = view.findViewById(R.id.tv_welcome);


        db = DatabaseHelper.getInstance(getActivity());
        if(db.isDBEmpty() == true) {
            Intent intent = new Intent(getActivity(), StartUpWindow.class);
            startActivity(intent);
        }

        // When a value on the calendar is clicked, this method is called:
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                tv_welcome.setText((month + 1) + "/" + dayOfMonth + "/" + year);  // changes the welcome text to the date
                dateSelected = year + "/" + (month + 1) + "/" + dayOfMonth;       // this is to be passed to the sub2Activity class
                Intent intent = new Intent(getActivity(), PopUp.class);
                intent.putExtra("date", dateSelected);
                startActivity(intent);
            }
        });

        



        // This displays the ViewAssignments class:
        btnDisplayEvents = view.findViewById(R.id.btn_display_events);
        btnDisplayEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getActivity(), ViewAssignments.class);
                startActivity(in1);
            }
        });


        return view;
    }

}
