package com.example.studentplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
Button btnDisplayEvents;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        final CalendarView calendarView = view.findViewById(R.id.calendar);
        final TextView tv_welcome = view.findViewById(R.id.tv_welcome);

        Button btn_add_event = view.findViewById(R.id.btn_add_event);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                tv_welcome.setText(year + "." + month + "." + dayOfMonth);

            }
        });

        btn_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in  = new Intent(getActivity(), sub2Activity.class);
                in.putExtra("date", tv_welcome.getText());


                startActivity(in);
            }
        });

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
