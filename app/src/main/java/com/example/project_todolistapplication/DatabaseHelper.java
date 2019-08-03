package com.example.project_todolistapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    // database variables
    private static final String DATABASE_NAME = "todolist.db";
    private static final String TABLE_NAME = "todolist_table";
    private static final String TASK_ID = "task_id";
    private static final String TASK_NAME = "task_name";
    private static final String TASK_STATUS = "task_status";
    private static final String TASK_DATE = "task_due";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASK_NAME + " TEXT, " +
                TASK_DATE + " TEXT, " +
                TASK_STATUS + " TEXT);";

        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String task_name, String task_due) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues tableValues = new ContentValues();
        tableValues.put(TASK_NAME, task_name);
        tableValues.put(TASK_DATE, task_due);
        tableValues.put(TASK_STATUS, "false");

        long result = db.insert(TABLE_NAME, null, tableValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public void updateStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues taskValues = new ContentValues();
        taskValues.put(TASK_STATUS, status);
        taskValues.put(TASK_ID, id);

        db.update(TABLE_NAME, taskValues, "task_id = ?", new String[] {id});
    }

    public Cursor getTasksToday() {
        SQLiteDatabase db = this.getWritableDatabase();
        String dateToday = getCurrentDate();
        String taskQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                TASK_DATE + " = date(\"" + dateToday + "\") and " + TASK_STATUS + " = \"false\"";

        deleteChecked();

        return db.rawQuery(taskQuery, null);
    }

    public Cursor getTaskUpcoming() {
        SQLiteDatabase db = this.getWritableDatabase();
        String dateToday = getCurrentDate();
        String taskQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                TASK_DATE + " > date(\"" + dateToday + "\") and " + TASK_STATUS + " = \"false\"";

        deleteChecked();

        return db.rawQuery(taskQuery, null);
    }

    public Cursor getTaskOverdue() {
        SQLiteDatabase db = this.getWritableDatabase();
        String dateToday = getCurrentDate();
        String taskQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                TASK_DATE + " < date(\"" + dateToday + "\") and " + TASK_STATUS + " = \"false\"";

        deleteChecked();

        return db.rawQuery(taskQuery, null);
    }

    public void deleteChecked() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "task_status = ?", new String[] {"true"});
    }

    public void deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "task_id = ?", new String[] {id});
    }

    public String getCurrentDate() {
        // get current day
        Calendar calender = Calendar.getInstance();
        int month = calender.get(Calendar.MONTH) + 1;
        int year  = calender.get(Calendar.YEAR);
        int day =  calender.get(Calendar.DAY_OF_MONTH);

        // format date
        String date = year + "-" + month + "-" + day;
        String[] temp_date = date.split("-");
        if (temp_date[1].length() == 1) {
            temp_date[1] = "0" + temp_date[1];
        }
        if (temp_date[2].length() == 1) {
            temp_date[2] = "0" + temp_date[2];
        }

        String formattedDate = temp_date[0] + "-" + temp_date[1] + "-" +temp_date[2];

        return formattedDate;
    }
}
