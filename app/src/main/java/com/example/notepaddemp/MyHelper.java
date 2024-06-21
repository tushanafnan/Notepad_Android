package com.example.notepaddemp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.notepaddemp.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class MyHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public MyHelper(Context context) {
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERSION);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL(DBUtils.sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Handle database upgrade
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBUtils.DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String userContent, String userTime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT, userContent);
        contentValues.put(DBUtils.NOTEPAD_TIME, userTime);
        return sqLiteDatabase.insert(DBUtils.DATABASE_TABLE, null, contentValues) > 0;
    }

    public boolean deleteData(String id) {
        String whereClause = DBUtils.NOTEPAD_ID + "=?";
        String[] whereArgs = new String[]{id};
        return sqLiteDatabase.delete(DBUtils.DATABASE_TABLE, whereClause, whereArgs) > 0;
    }

    public boolean updateData(String id, String content, String time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT, content);
        contentValues.put(DBUtils.NOTEPAD_TIME, time);
        String whereClause = DBUtils.NOTEPAD_ID + "=?";
        String[] whereArgs = new String[]{id};
        return sqLiteDatabase.update(DBUtils.DATABASE_TABLE, contentValues, whereClause, whereArgs) > 0;
    }

    public List<Note> queryData() {
        List<Note> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DBUtils.DATABASE_TABLE, null, null, null, null, null, DBUtils.NOTEPAD_ID + " desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Note noteInfo = new Note();
                @SuppressLint("Range")
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBUtils.NOTEPAD_ID)));
                @SuppressLint("Range")
                String content = cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_CONTENT));
                @SuppressLint("Range")
                String time = cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_TIME));
                noteInfo.setId(id);
                noteInfo.setNotepadContent(content);
                noteInfo.setNotepadTime(time);
                list.add(noteInfo);
            }
            cursor.close();
        }
        return list;
    }
}