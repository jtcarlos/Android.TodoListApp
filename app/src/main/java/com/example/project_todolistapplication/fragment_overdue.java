package com.example.project_todolistapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Calendar;

public class fragment_overdue extends Fragment {

    private static final String SHARED_PREF = "sharedPrefs";
    private static final String BACKGROUND = "background";

    private String backgroundChoice;

    private DatabaseHelper db;
    private String[] task_ids = new String[0];
    private String[] task_names = new String[0];
    private String[] task_dates = new String[0];
    private String[] task_status = new String[0];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.overdue_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        db = new DatabaseHelper(getContext());

        // display task list if list is not empty
        RelativeLayout emptyMessage = view.findViewById(R.id.emptyMessage);
        ListView task_lists = view.findViewById(R.id.task_lists);
        if (db.getTaskOverdue().getCount() > 0) {
            emptyMessage.setVisibility(View.GONE);
            getData(task_lists);
        }
        else if (db.getTaskOverdue().getCount() == 0) {
            task_lists.setVisibility(View.GONE);
        }

        // set current date
        Calendar c = Calendar.getInstance();
        String current_date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView headerTextView = (TextView)getView().findViewById(R.id.headerDateTextView);
        headerTextView.setText(current_date);

        // set header background
        LinearLayout fragment_header = (LinearLayout)view.findViewById(R.id.fragment_header);
        loadHeaderBackground(fragment_header);
    }

    private void loadHeaderBackground(View header) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        backgroundChoice = sharedPreferences.getString(BACKGROUND, "default");

        switch (backgroundChoice) {
            case "default":
                header.setBackgroundResource(R.drawable.default_bg);
                break;
            case "ember":
                header.setBackgroundResource(R.drawable.ember_spark);
                break;
            case "persian":
                header.setBackgroundResource(R.drawable.persian_lounge);
                break;
            case "phase":
                header.setBackgroundResource(R.drawable.phase);
                break;
            case "spectrum":
                header.setBackgroundResource(R.drawable.spectrum);
                break;
            case "stealth":
                header.setBackgroundResource(R.drawable.stealth);
                break;
        }
    }

    private void getData(final ListView task_lists) {
        // load database query results
        Cursor tasks = db.getTaskOverdue();

        // populate string arrays
        if (tasks.getCount() > 0) {
            int counter = 0;
            task_ids = new String[db.getTaskOverdue().getCount()];
            task_names = new String[db.getTaskOverdue().getCount()];
            task_dates = new String[db.getTaskOverdue().getCount()];
            task_status = new String[db.getTaskOverdue().getCount()];

            while (tasks.moveToNext()) {
                task_ids[counter] = tasks.getString(0);
                task_names[counter] = tasks.getString(1);
                task_dates[counter] = "Due on " + tasks.getString(2);
                task_status[counter++] = tasks.getString(3);
            }
        }

        // populate task list
        final ListAdapter listAdapter = new ListAdapter(getContext(), task_names, task_dates, task_ids, task_status);
        task_lists.setAdapter(listAdapter);

        task_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RelativeLayout deleteIco = view.findViewById(R.id.deleteIco);
                ImageView statusIco = view.findViewById(R.id.statusIco);

                // get task information
                final String id = listAdapter.getStringId(i);

                if (listAdapter.getItemStatus(i).equals("false")) {
                    statusIco.setImageResource(R.drawable.ic_checked);
                    db.updateStatus(id, "true");
                    listAdapter.updateStatus(i, "true");

                    Toast.makeText(getContext(), "Completed task will be automatically delete afterwards.", Toast.LENGTH_LONG).show();
                }
                else if (listAdapter.getItemStatus(i).equals("true")) {
                    statusIco.setImageResource(R.drawable.ic_unchecked);
                    db.updateStatus(id, "false");
                    listAdapter.updateStatus(i, "false");
                }
            }
        });
    }
}
