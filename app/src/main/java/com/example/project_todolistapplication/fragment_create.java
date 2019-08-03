package com.example.project_todolistapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class fragment_create extends Fragment{

    String mDate;
    EditText taskNameTextView;
    CalendarView calendarView;
    Button createBtn;
    int mMonth, mDay, mYear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // initialization
        final DatabaseHelper db = new DatabaseHelper(getContext());

        calendarView = (CalendarView)view.findViewById(R.id.calendarView);
        taskNameTextView = (EditText) view.findViewById(R.id.taskNameTextView);
        createBtn = (Button)view.findViewById(R.id.createBtn);

        // set initial date
        Calendar calendar = Calendar.getInstance();
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mYear = calendar.get(Calendar.YEAR);
        mDate = mYear + "-" + mMonth + "-" +mDay;

        // listeners
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                mMonth = month + 1;
                mYear = year;
                mDay = day;

                mDate = mYear + "-" + mMonth + "-" +mDay;
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task_name = taskNameTextView.getText().toString();

                // format date to standard
                String[] temp_date = mDate.split("-");
                if (temp_date[1].length() == 1) {
                    temp_date[1] = "0" + temp_date[1];
                }
                if (temp_date[2].length() == 1) {
                    temp_date[2] = "0" + temp_date[2];
                }

                String formattedDate = temp_date[0] + "-" + temp_date[1] + "-" +temp_date[2];

                // insert date
                if (task_name.length() > 0) {
                    Boolean isInserted = db.insertData(task_name, formattedDate);

                    if (isInserted) {
                        Toast.makeText(getContext(), "Task Created!", Toast.LENGTH_SHORT).show();
                        taskNameTextView.setText("");
                    }
                    else {
                        Toast.makeText(getContext(), "Error! Task was not inserted.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Please name your task.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
