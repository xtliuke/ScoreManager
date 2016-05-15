package orz.keng.scoremanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StuDataHelper extends SQLiteOpenHelper{
    public StuDataHelper(Context context) {
        super(context, "stu_data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE \"stu_activity\" (\n" +
                "\"_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\"stu_code\"  INTEGER NOT NULL,\n" +
                "\"activity_name\"  TEXT,\n" +
                "\"activity_point\"  REAL NOT NULL\n" +
                ")");
        db.execSQL("CREATE TABLE \"stu_baseinfo\" (\n" +
                "\"_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\"stu_code\"  INTEGER NOT NULL,\n" +
                "\"stu_name\"  TEXT NOT NULL,\n" +
                "\"class_name\"  TEXT NOT NULL,\n" +
                "\"grade_point\"  REAL\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
