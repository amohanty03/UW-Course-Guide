package com.cs407.uwcourseguide;

import android.content.Context;
import android.content.SharedPreferences;

public class Util {
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.cs407.uwcourseguide", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    public static void setToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.cs407.uwcourseguide", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("token", "logged in").apply();
    }
}
