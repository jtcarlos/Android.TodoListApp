package com.example.project_todolistapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<String> task_ids;
    private ArrayList<String> task_names;
    private ArrayList<String> task_dues;
    private ArrayList<String> tasks_status;

    private Context currentContext;
    private DatabaseHelper db;

    public ListAdapter(Context context, String[] given_names, String[] given_dues, String[] given_ids, String[] given_status){
        task_ids = new ArrayList<>(Arrays.asList(given_ids));
        task_names = new ArrayList<>(Arrays.asList(given_names));
        task_dues = new ArrayList<>(Arrays.asList(given_dues));
        tasks_status = new ArrayList<>(Arrays.asList(given_status));
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        currentContext = context;
        db = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return task_names.size();
    }

    public String getItemStatus(int i) {
        return tasks_status.get(i);
    }

    public String getStringId(int i) {
        return task_ids.get(i);
    }

    @Override
    public Object getItem(int i) {
        return task_names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private void deleteItemFromList(int i) {
        task_ids.remove(i);
        task_names.remove(i);
        task_dues.remove(i);
        tasks_status.remove(i);
    }

    private void deleteDBItem(String id) {
        db.deleteItem(id);
    }

    public void updateStatus(int i, String status) {
        tasks_status.set(i, status);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View listView = mInflater.inflate(R.layout.list_item_layout, null);

        final TextView taskNameTV = (TextView)listView.findViewById(R.id.taskNameTV);
        TextView taskDueTV = (TextView)listView.findViewById(R.id.taskDueTV);

        String task_name = task_names.get(i);
        String task_due = task_dues.get(i);

        // set delete button
        RelativeLayout deleteIco = listView.findViewById(R.id.deleteIco);
        deleteIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDBItem(getStringId(i));
                deleteItemFromList(i);
                notifyDataSetChanged();
                Toast.makeText(currentContext, "Task successfully deleted.", Toast.LENGTH_LONG).show();
            }
        });

        taskNameTV.setText(task_name);
        taskDueTV.setText(task_due);

        return listView;
    }
}
