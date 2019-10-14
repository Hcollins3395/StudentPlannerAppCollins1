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

public class AddSemesterFragment extends Fragment {
    DatabaseHelper myDB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_semester, container, false);

        myDB = DatabaseHelper.getInstance(getActivity());
        final DatabaseHelper myDB = new DatabaseHelper(getActivity());
        final EditText et_semester_name = v.findViewById(R.id.et_semester_name);
        final EditText et_class_one     = v.findViewById(R.id.et_class_one);
        final EditText et_second_class  = v.findViewById(R.id.et_second_class);
        final EditText et_third_class   = v.findViewById(R.id.et_third_class);
        final EditText et_fourth_class  = v.findViewById(R.id.et_fourth_class);
        final EditText et_fifth_class   = v.findViewById(R.id.et_fifth_class);
        Button btn_create_semester      = v.findViewById(R.id.btn_create_semester);

        btn_create_semester.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                String name = et_semester_name.getText().toString();
                String class_1 = et_class_one.getText().toString();
                String class_2 = et_second_class.getText().toString();
                String class_3 = et_third_class.getText().toString();
                String class_4 = et_fourth_class.getText().toString();
                String class_5 = et_fifth_class.getText().toString();
                boolean isInserted = myDB.insertSemesterData(name, class_1, class_2, class_3, class_4, class_5);
                if (isInserted == true)
                    Toast.makeText(AddSemesterFragment.this.getActivity(), "SEMESTER ADDED", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddSemesterFragment.this.getActivity(), "SEMESTER NOT ADDED", Toast.LENGTH_LONG).show();
            }
        });


        return v;
    }


}
