/*
package com.cs407.uwcourseguide;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade database if necessary
    }

    // Method to search courses in the database
    public List<Course> searchCourses(String query) {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COURSES, new String[] {"column1", "column2"}, "column1 LIKE ?",
                new String[] {"%" + query + "%"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Create Course objects from cursor and add them to the list
            } while (cursor.moveToNext());
        }

        cursor.close();
        return courses;
    }

    // Method to search professors in the database
    public List<Course> searchProfessors(String query) {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFFESORS, new String[] {"column1", "column2"}, "column1 LIKE ?",
                new String[] {"%" + query + "%"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Create Course objects from cursor and add them to the list
            } while (cursor.moveToNext());
        }

        cursor.close();
        return courses;
    }
}

*/
