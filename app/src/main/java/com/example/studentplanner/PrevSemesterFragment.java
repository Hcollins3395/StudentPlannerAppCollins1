package com.example.studentplanner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/****************************************************************************************
                                 ***  Previous Semesters ***
 * This class displays the classes of whatever semester the user chooses from a
 * spinner.
 *
 * There is a spinner that calls the database class to get all of the semester
 * names and, depending on what is chosen, the courses are then displayed in a
 * TextView.
 ****************************************************************************************/

public class PrevSemesterFragment extends Fragment {
    DatabaseHelper myDB;
    TextView tvClass1;
    TextView tvClass2;
    TextView tvClass3;
    TextView tvClass4;
    TextView tvClass5;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prev_semesters, container, false);
        // Initialize Database:
        myDB = DatabaseHelper.getInstance(getActivity());
        final DatabaseHelper myDB = new DatabaseHelper(getActivity());

        tvClass1 = v.findViewById(R.id.tvClass1);
        tvClass2 = v.findViewById(R.id.tvClass2);
        tvClass3 = v.findViewById(R.id.tvClass3);
        tvClass4 = v.findViewById(R.id.tvClass4);
        tvClass5 = v.findViewById(R.id.tvClass5);

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
                tvClass1.setText(myDB.getSemesterClasses(position).get(0));
                tvClass2.setText(myDB.getSemesterClasses(position).get(1));
                tvClass3.setText(myDB.getSemesterClasses(position).get(2));
                tvClass4.setText(myDB.getSemesterClasses(position).get(3));
                tvClass5.setText(myDB.getSemesterClasses(position).get(4));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return v;
    }


}
