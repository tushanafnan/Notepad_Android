package com.example.notepaddemp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBUtils {
    public static final String DATABASE_NAME = "Notepad.db"; // database name
    public static final String DATABASE_TABLE = "note"; // table name
    public static final int DATABASE_VERSION = 1; // database version
    public static final String NOTEPAD_ID = "id";
    public static final String NOTEPAD_CONTENT = "content";
    public static final String NOTEPAD_TIME = "time";

    // SQL
    public static final String sql = "create table " + DATABASE_TABLE +
            "(id integer primary key autoincrement, content text, time text)";

    // Method to get the current date and time in the specified format
    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}