package com.example.studentplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/************************************************************************************
    *** Adding a Semester ***

    This class is used to allow the user to create a semester that can hold up to
    five courses - all of which is saved into a database and used throughout the
    app to create events, display events, view previous semesters etc.
 *************************************************************************************/
public class AddSemesterFragment extends Fragment {
    DatabaseHelper myDB;
    String class_1;
    String class_2;
    String class_3;
    String class_4;
    String class_5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_semester, container, false);

        // initiating the EditText fields to save the values from the user input:
        myDB = DatabaseHelper.getInstance(getActivity());
        final DatabaseHelper myDB = new DatabaseHelper(getActivity());
        final EditText et_semester_name = v.findViewById(R.id.et_semester_name);
        final EditText et_class_one     = v.findViewById(R.id.et_class_one);
        final EditText et_second_class  = v.findViewById(R.id.et_second_class);
        final EditText et_third_class   = v.findViewById(R.id.et_third_class);
        final EditText et_fourth_class  = v.findViewById(R.id.et_fourth_class);
        final EditText et_fifth_class   = v.findViewById(R.id.et_fifth_class);
        Button btn_create_semester      = v.findViewById(R.id.btn_create_semester);


            // What happens when the "create semester" button is clicked:
            btn_create_semester.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // if (TextUtils.isEmpty(editText.getText().toString())){

                    if (et_semester_name.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please enter a Semester Name.", Toast.LENGTH_SHORT).show();
                    } else if (et_class_one.getText().toString().isEmpty() || et_second_class.getText().toString().isEmpty()
                            || et_third_class.getText().toString().isEmpty() || et_fourth_class.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please make sure you have at least four classes.", Toast.LENGTH_SHORT).show();
                    } else {
                        // This first part converts inputs to a string to be added to the database:
                        String name = et_semester_name.getText().toString();
                        class_1 = et_class_one.getText().toString();
                        class_2 = et_second_class.getText().toString();
                        class_3 = et_third_class.getText().toString();
                        class_4 = et_fourth_class.getText().toString();
                        class_5 = et_fifth_class.getText().toString();

                        // this checks to see if the information was added successfully or not:
                        boolean isInserted = myDB.insertSemesterData(name, class_1, class_2, class_3, class_4, class_5); // actually adding it to db.
                        if (isInserted == true)
                            Toast.makeText(AddSemesterFragment.this.getActivity(), "SEMESTER ADDED", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddSemesterFragment.this.getActivity(), "SEMESTER NOT ADDED", Toast.LENGTH_LONG).show();
                    }
                }
            });


        return v;
    }


}
