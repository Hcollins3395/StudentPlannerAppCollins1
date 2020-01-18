package com.example.studentplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import yuku.ambilwarna.AmbilWarnaDialog;

/***********************************************************************************
                        ***  Notification Settings ***
 * The Notification Settings class is so the user can personalize the app
 * to fit their needs.
 *
 * The Notification Settings class currently allows you to choose how many
 * days in advance to be notified about an event and will also allow you
 * to change the colors of assignment types.
 *
 * There are default values if the user does not have too much preference to
 * the colors or the number of days in advance.
 ************************************************************************************/
public class NotificationSettingsFragment extends Fragment {

    DatabaseHelper db;
    //  These are the spinners used on the settings screen:
    Spinner sHomework;
    Spinner sQuiz;
    Spinner sTest;
    Spinner sExam;
    Spinner sPaper;
    Spinner sProject;

    // These are the values that will be used in the sub2Activity class:
    public static Long setHomeworkTime;
    public static Long setQuizTime;
    public static Long setTestTime;
    public static Long setExamTime;
    public static Long setPaperTime;
    public static Long setProjectTime;

    //  Use these values if the user has not selected a set number of days ahead;
    public Long oneDayMil = longValue(8.64 * Math.pow(10, 7));         // default homework
    public Long fiveDaysMil = longValue(4.32 * Math.pow(10, 8));       // default quiz
    public Long nineDaysMil = longValue(7.776 * Math.pow(10, 8));      // default test
    public Long twelveDaysMil = longValue(1.037 * Math.pow(10, 9));    // default exam, paper, project


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_notification_settings, container, false);

        // Initializing database:
        db = DatabaseHelper.getInstance(getActivity());

        db.isTableCreated();

        // Initializing our spinners:
        sHomework = v.findViewById(R.id.spinnerHomework);
        sQuiz     = v.findViewById(R.id.spinnerQuiz);
        sTest     = v.findViewById(R.id.spinnerTest);
        sExam     = v.findViewById(R.id.spinnerExam);
        sPaper    = v.findViewById(R.id.spinnerPaper);
        sProject  = v.findViewById(R.id.spinnerProject);


        // displaying possible values for the number of days before the assignment is due:
        Integer[] sHomeworkDays = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> homeworkAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sHomeworkDays);
        sHomework.setAdapter(homeworkAdapter);

        Integer[] sQuizDays = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> quizAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sQuizDays);
        sQuiz.setAdapter(quizAdapter);

        Integer[] sTestDays = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> testAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sTestDays);
        sTest.setAdapter(testAdapter);

        Integer[] sExamDays = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> examAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sExamDays);
        sExam.setAdapter(examAdapter);

        Integer[] sPaperDays = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> paperAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sPaperDays);
        sPaper.setAdapter(paperAdapter);

        Integer[] sProjectDays = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> projectAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sProjectDays);
        sProject.setAdapter(projectAdapter);


        //  Now we need to save their settings using an onItemSelectedListener:
        sHomework.setSelection(findIndex(sHomeworkDays, db.getHomeworkValue()));  // Doing this sets the spinners to my "default" value.
        sHomework.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedHomeworkTime = (sHomework.getSelectedItemPosition() + 1); //  This saves the value the user chose.
                setHomeworkTime = getDayToMillisecond(selectedHomeworkTime);          //  This converts that time to milliseconds
                db.updateHomeworkValue(selectedHomeworkTime);                         //  This is where the database is called to update the value.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setHomeworkTime = oneDayMil;
            }
        });

        sQuiz.setSelection(findIndex(sQuizDays,db.getQuizValue()));
        sQuiz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedQuizTime = (sQuiz.getSelectedItemPosition() + 1);
                setQuizTime = getDayToMillisecond(selectedQuizTime);
                db.updateQuizValue(selectedQuizTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setQuizTime = fiveDaysMil;
            }
        });

        sTest.setSelection(findIndex(sTestDays,db.getTestValue()));
        sTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedTestTime = (sTest.getSelectedItemPosition() + 1);
                setTestTime = getDayToMillisecond(selectedTestTime);
                db.updateTestValue(selectedTestTime);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setTestTime = nineDaysMil;
            }
        });

        sExam.setSelection(findIndex(sExamDays, db.getExamValue()));
        sExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedExamTime = (sExam.getSelectedItemPosition() + 1);
                setExamTime = getDayToMillisecond(selectedExamTime);
                db.updateExamValue(selectedExamTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setExamTime = twelveDaysMil;
            }
        });

        sPaper.setSelection(findIndex(sPaperDays,db.getPaperValue()));
        sPaper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedPaperTime = (sPaper.getSelectedItemPosition() + 1);
                setPaperTime = getDayToMillisecond(selectedPaperTime);
                db.updatePaperValue(selectedPaperTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setPaperTime = twelveDaysMil;
            }
        });

        sProject.setSelection(findIndex(sProjectDays,db.getProjectValue()));
        sProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedProjectTime = (sProject.getSelectedItemPosition() + 1);
                setProjectTime = getDayToMillisecond(selectedProjectTime);
                db.updateProjectValue(selectedProjectTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setProjectTime = twelveDaysMil;
            }
        });


        return v;


    }

    // Depending on what number is chosen, it converts it from that number of days to milliseconds:
    public long getDayToMillisecond(int numberOfDays)
    {
        Long numOfMilliseconds = longValue(1.0);
        switch(numberOfDays)
        {
            case 1:
                numOfMilliseconds = longValue(8.64 * Math.pow(10, 7));
                break;

            case 2:
                numOfMilliseconds = longValue(1.72 * Math.pow(10, 8));
                break;

            case 3:
                numOfMilliseconds = longValue(2.59 * Math.pow(10, 8));
                break;

            case 4:
                numOfMilliseconds = longValue(3.45 * Math.pow(10, 8));
                break;

            case 5:
                numOfMilliseconds = longValue(4.32 * Math.pow(10, 8));
                break;

            case 6:
                numOfMilliseconds = longValue(5.18 * Math.pow(10, 8));
                break;

            case 7:
                numOfMilliseconds = longValue(6.05 * Math.pow(10, 8));
                break;

            case 8:
                numOfMilliseconds = longValue(6.91 * Math.pow(10, 8));
                break;

            case 9:
                numOfMilliseconds = longValue(7.78 * Math.pow(10, 8));
                break;

            case 10:
                numOfMilliseconds = longValue(8.64 * Math.pow(10, 8));
                break;

            case 11:
                numOfMilliseconds = longValue(9.5 * Math.pow(10, 8));
                break;

            case 12:
                numOfMilliseconds = longValue(1.04 * Math.pow(10, 9));
                break;

            case 13:
                numOfMilliseconds = longValue(1.12 * Math.pow(10, 9));
                break;

            case 14:
                numOfMilliseconds = longValue(1.21 * Math.pow(10, 9));
                break;

            case 15:
                numOfMilliseconds = longValue(1.3 * Math.pow(10, 9));
                break;

            case 16:
                numOfMilliseconds = longValue(1.38 * Math.pow(10, 9));
                break;

            case 17:
                numOfMilliseconds = longValue(1.47 * Math.pow(10, 9));
                break;

            case 18:
                numOfMilliseconds = longValue(1.56 * Math.pow(10, 9));
                break;

            case 19:
                numOfMilliseconds = longValue(1.64 * Math.pow(10, 9));
                break;

            case 20:
                numOfMilliseconds = longValue(1.73 * Math.pow(10, 9));
                break;
        }
        return numOfMilliseconds;

    }

    // Converts a double to a long:
    public long longValue( double value) {
        return (long)value;
    }

    // Linear-search function to find the index of an element
    public static int findIndex(Integer arr[], int t)
    {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }
}
