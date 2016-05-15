package orz.keng.scoremanager.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import orz.keng.scoremanager.R;
import orz.keng.scoremanager.dao.StuDataDao;
import orz.keng.scoremanager.entity.StuActivity;
import orz.keng.scoremanager.entity.StuBaseInfo;
import orz.keng.scoremanager.entity.StuDetailData;

public class DetailActivity extends AppCompatActivity {

    private StuDataDao stuDataDao;
    private StuDetailData stuDetailData;
    private EditText et_detail_name;
    private EditText et_detail_code;
    private EditText et_detail_point;
    private EditText et_detail_class;
    private EditText et_detail_activity;
    private EditText et_detail_statis;
    private ListView lv_detail_activity;
    private Long stuCode;
    private List<StuActivity> stuActivities;
    private ActAdapter actAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initActivity();
        initData();
    }

    private void initData() {
        refreshStuInfo();
        stuActivities = stuDataDao.getStuActivity(stuCode);
        actAdapter = new ActAdapter();
        lv_detail_activity.setAdapter(actAdapter);
    }

    private void initActivity() {
        stuCode = getIntent().getLongExtra("stu_code",0);
        stuDataDao = new StuDataDao(this);


        et_detail_name = (EditText) findViewById(R.id.et_detail_name);
        et_detail_code = (EditText) findViewById(R.id.et_detail_code);
        et_detail_class = (EditText) findViewById(R.id.et_detail_class);
        et_detail_point = (EditText) findViewById(R.id.et_detail_point);
        et_detail_activity = (EditText) findViewById(R.id.et_detail_activity);
        et_detail_statis = (EditText) findViewById(R.id.et_detail_statis);
        lv_detail_activity = (ListView) findViewById(R.id.lv_detail_activity);
    }

    private void refreshStuInfo(){
        stuDetailData = stuDataDao.getStuDetailData(stuCode);
        et_detail_name.setText(stuDetailData.getStuName());
        et_detail_code.setText(stuDetailData.getStuCode().toString());
        et_detail_class.setText(stuDetailData.getClassName());
        et_detail_point.setText(stuDetailData.getGradePoint().toString());
        et_detail_activity.setText(stuDetailData.getActivityPoint().toString());
        et_detail_statis.setText(stuDetailData.getStatisPoint().toString());
    }

    public void deleteActivity(View view){
        final ActivityHolder holder = (ActivityHolder)view.getTag();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean status = stuDataDao.deleteActivity(holder.id);
                if(status){
                    Toast.makeText(DetailActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    refreshStuInfo();
                }else {
                    Toast.makeText(DetailActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
                stuActivities = stuDataDao.getStuActivity(stuCode);
                actAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    public void addActivity(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialog = View.inflate(this,R.layout.dialog_addactivity,null);
        final EditText et_activity_name = (EditText) dialog.findViewById(R.id.et_activity_name);
        final EditText et_activity_point = (EditText) dialog.findViewById(R.id.et_activity_point);
        builder.setView(dialog);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StuActivity activity = new StuActivity();
                activity.setStuCode(stuCode);
                activity.setActivityName(et_activity_name.getText().toString());
                activity.setActivityPoint(Double.valueOf(et_activity_point.getText().toString()));
                boolean status = stuDataDao.addActivity(activity);
                if(status){
                    Toast.makeText(DetailActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    refreshStuInfo();
                }else {
                    Toast.makeText(DetailActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                }
                stuActivities = stuDataDao.getStuActivity(stuCode);
                actAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    public void deleteStudent(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stuDataDao.deleteStudent(stuCode);
                Toast.makeText(DetailActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    public void saveStudent(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要保存？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StuBaseInfo info = new StuBaseInfo();
                info.setStuName(et_detail_name.getText().toString().trim());
                info.setClassName(et_detail_class.getText().toString().trim());
                info.setGradePoint(Double.valueOf(et_detail_point.getText().toString().trim()));
                info.setStuCode(stuCode);
                stuDataDao.modifyInfo(info);
                Toast.makeText(DetailActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    public void goBack(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要返回？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    private class ActAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return stuActivities.size();
        }

        @Override
        public Object getItem(int position) {
            return stuActivities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ActivityHolder holder;
            if(convertView == null){
                convertView = View.inflate(DetailActivity.this,R.layout.item_detail_activity,null);
                holder = new ActivityHolder();
                holder.tv_detail_activity = (TextView) convertView.findViewById(R.id.tv_detail_activity);
                holder.tv_detail_activity_btn = (TextView) convertView.findViewById(R.id.tv_detail_activity_btn);
                holder.tv_detail_activity_btn.setTag(holder);
                convertView.setTag(holder);

            }else{
                holder = (ActivityHolder) convertView.getTag();
            }
            holder.text = stuActivities.get(position).getActivityName()+"："+stuActivities.get(position).getActivityPoint()+"分";
            holder.tv_detail_activity.setText(holder.text);
            holder.id = stuActivities.get(position).getId();
            return convertView;
        }
    }

    static class ActivityHolder{
        TextView tv_detail_activity;
        TextView tv_detail_activity_btn;
        String text;
        Long id;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(0);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要返回？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }
}
