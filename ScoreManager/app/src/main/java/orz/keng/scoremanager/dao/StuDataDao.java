package orz.keng.scoremanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import orz.keng.scoremanager.entity.StuActivity;
import orz.keng.scoremanager.entity.StuBaseInfo;
import orz.keng.scoremanager.entity.StuDetailData;
import orz.keng.scoremanager.entity.StuPreData;
import orz.keng.scoremanager.utils.NumberUtils;

public class StuDataDao {

    private final SQLiteDatabase db;

    public StuDataDao(Context context) {
        StuDataHelper helper = new StuDataHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<StuPreData> getStuPreData(String what, String name) {
        List<StuPreData> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select \n" +
                "\tsb.stu_code,\n" +
                "\tsb.stu_name,\n" +
                "\tsb.grade_point,\n" +
                "\tifnull(sum(sa.activity_point),0) activity_sum,\n" +
                "\tsb.grade_point+ifnull(sum(sa.activity_point),0) as statis_point\n" +
                "from stu_baseinfo sb\n" +
                "left join stu_activity sa on sb.stu_code = sa.stu_code where sb.stu_name like '%" + name + "%'\n" +
                "group by sb.stu_code order by " + what + " desc\n", null);
        while (cursor.moveToNext()) {
            StuPreData data = new StuPreData();
            Long stuCode = cursor.getLong(cursor.getColumnIndex("stu_code"));
            String stuName = cursor.getString(cursor.getColumnIndex("stu_name"));
            Double gradePoint = cursor.getDouble(cursor.getColumnIndex("grade_point"));
            Double activityPoint = cursor.getDouble(cursor.getColumnIndex("activity_sum"));
            Double statisPoint = cursor.getDouble(cursor.getColumnIndex("statis_point"));
            data.setStuCode(stuCode);
            data.setStuName(stuName);
            data.setGradePoint(NumberUtils.formatDouble(gradePoint));
            data.setActivityPoint(NumberUtils.formatDouble(activityPoint));
            data.setStatisPoint(NumberUtils.formatDouble(statisPoint));
            list.add(data);
        }
        cursor.close();
        return list;
    }

    public StuDetailData getStuDetailData(Long stuCode) {
        StuDetailData stuDetailData = new StuDetailData();
        Cursor cursor = db.rawQuery("select \n" +
                "sb.stu_code,\n" +
                "sb.stu_name,\n" +
                "sb.class_name,\n" +
                "sb.grade_point,\n" +
                "ifnull(sum(sa.activity_point),0) activity_sum,\n" +
                "sb.grade_point+ifnull(sum(sa.activity_point),0) as statis_point\n" +
                "from stu_baseinfo sb\n" +
                "left join stu_activity sa on sb.stu_code = sa.stu_code\n" +
                "where sb.stu_code = " + stuCode, null);
        while (cursor.moveToNext()) {
            String stuName = cursor.getString(cursor.getColumnIndex("stu_name"));
            Double gradePoint = cursor.getDouble(cursor.getColumnIndex("grade_point"));
            Double activityPoint = cursor.getDouble(cursor.getColumnIndex("activity_sum"));
            Double statisPoint = cursor.getDouble(cursor.getColumnIndex("statis_point"));
            String className = cursor.getString(cursor.getColumnIndex("class_name"));
            stuDetailData.setStuCode(stuCode);
            stuDetailData.setStuName(stuName);
            stuDetailData.setGradePoint(NumberUtils.formatDouble(gradePoint));
            stuDetailData.setActivityPoint(NumberUtils.formatDouble(activityPoint));
            stuDetailData.setStatisPoint(NumberUtils.formatDouble(statisPoint));
            stuDetailData.setClassName(className);
        }
        cursor.close();
        return stuDetailData;
    }

    public List<StuActivity> getStuActivity(Long stuCode) {
        List<StuActivity> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select \n" +
                "sa._id,\n" +
                "sa.stu_code,\n" +
                "sa.activity_name,\n" +
                "sa.activity_point\n" +
                "from stu_activity sa\n" +
                "where sa.stu_code = " + stuCode, null);
        while (cursor.moveToNext()) {
            StuActivity data = new StuActivity();
            Long id = cursor.getLong(cursor.getColumnIndex("_id"));
            String activityName = cursor.getString(cursor.getColumnIndex("activity_name"));
            Double activityPoint = cursor.getDouble(cursor.getColumnIndex("activity_point"));
            data.setActivityName(activityName);
            data.setStuCode(stuCode);
            data.setActivityPoint(NumberUtils.formatDouble(activityPoint));
            data.setId(id);
            list.add(data);
        }
        cursor.close();
        return list;
    }

    public boolean deleteActivity(Long id) {
        int i = db.delete("stu_activity", "_id=?", new String[]{String.valueOf(id)});
        if (i > 0) {
            return true;
        }
        return false;
    }

    public void deleteStudent(Long code){
        db.delete("stu_activity", "stu_code=?", new String[]{String.valueOf(code)});
        db.delete("stu_baseinfo", "stu_code=?", new String[]{String.valueOf(code)});
    }

    public boolean addBaseInfo(StuBaseInfo baseInfo) {
        ContentValues values = new ContentValues();
        values.put("stu_code", baseInfo.getStuCode());
        values.put("stu_name", baseInfo.getStuName());
        values.put("class_name", baseInfo.getClassName());
        values.put("grade_point", baseInfo.getGradePoint());
        long i = db.insert("stu_baseinfo", null, values);
        if (i > 0) {
            return true;
        }
        return false;
    }

    public boolean addActivity(StuActivity stuActivity) {
        ContentValues values = new ContentValues();
        values.put("stu_code", stuActivity.getStuCode());
        values.put("activity_name", stuActivity.getActivityName());
        values.put("activity_point", stuActivity.getActivityPoint());
        long i = db.insert("stu_activity", null, values);
        if (i > 0) {
            return true;
        }
        return false;
    }

    public void modifyInfo(StuBaseInfo info){
        ContentValues values = new ContentValues();
        values.put("stu_name",info.getStuName());
        values.put("class_name",info.getClassName());
        values.put("grade_point",info.getGradePoint());
        db.update("stu_baseinfo",values,"stu_code=?",new String[]{String.valueOf(info.getStuCode())});
    }

}
