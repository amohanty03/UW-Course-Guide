package com.cs407.uwcourseguide;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// Actually, don't need this for user info, only for courses/professors

/*
We decided for most our data to be stored in local database, like this DBHelper class
and Shared Preferences. However, since the reviews data need to be synced in all users account,
we will be using some database server, like AWS services or DigitalOcean
 */
public class DBHelper {
    static SQLiteDatabase sqLiteDatabase;

    public DBHelper(SQLiteDatabase sqLiteDatabase) {this.sqLiteDatabase = sqLiteDatabase;}

    public static void createTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users" + "(id INTEGER PRIMARY KEY, username TEXT, password TEXT, email TEXT, fullName TEXT)");
    }

    public void saveUsers(String username, String password, String email, String fullName) {
        createTable();
        sqLiteDatabase.execSQL("INSERT INTO users (username, password, email, fullName) VALUES (?, ?, ?, ?)",
                new String[]{username, password, email, fullName});
    }

    public String getUsersName(String username) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users", null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int a = cursor.getColumnIndex("username");
                    String username1 = cursor.getString(a);
                    int b = cursor.getColumnIndex("password");
                    String password = cursor.getString(b);
                    int c = cursor.getColumnIndex("email");
                    String email = cursor.getString(c);
                    int d = cursor.getColumnIndex("fullName");
                    String fullName = cursor.getString(d);

                    Log.d("U", "HERE!!!!!!!!!!!!!!!!................");
                    Log.d("U", "Username: " + username + ", Password: " + password + ", Email: " + email + ", Full Name: " + fullName);
                }
            } finally {
                cursor.close();
            }
        }


        String[] columns = {"id", "username", "password", "email", "fullName"};
        String selection = "username=?";
        String[] selectionArgs = {username};

        Cursor userInfo = sqLiteDatabase.query("users", columns, selection, selectionArgs, null, null, null);

        String fullName = "";
        if (userInfo.moveToFirst()) {
            int fullNameIndex = userInfo.getColumnIndex("email");
            fullName = userInfo.getString(fullNameIndex);
        } else {
            fullName = "notFound";
        }
        return fullName;
    }
}










