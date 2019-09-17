package com.example.studentplanner;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.studentplanner.DatabaseHelper.CLASS_ONE;

public class PrevSemesterFragment extends Fragment {
    DatabaseHelper myDB;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prev_semesters, container, false);
        // Initialize Database:
        myDB = DatabaseHelper.getInstance(getActivity());
        final DatabaseHelper myDB = new DatabaseHelper(getActivity());

        // TextView to display classes:
        final TextView tv_display_classes = v.findViewById(R.id.tv_display_classes);

        // Initializing the Spinner
        final Spinner spinners_semesters = v.findViewById(R.id.spinners_semesters);


        // To populate the spinner:
        ArrayList<String> listSemesters = myDB.getSemesterNames();
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listSemesters);
        spinners_semesters.setAdapter(semesterAdapter);


        // Set spinner to button method:
        spinners_semesters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_display_classes.setText(myDB.getSemesterClasses(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }


}
