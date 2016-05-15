package orz.keng.scoremanager.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import orz.keng.scoremanager.R;
import orz.keng.scoremanager.dao.StuDataDao;
import orz.keng.scoremanager.entity.StuActivity;
import orz.keng.scoremanager.entity.StuBaseInfo;
import orz.keng.scoremanager.entity.StuPreData;
import orz.keng.scoremanager.utils.DbValueUtils;

public class MainActivity extends AppCompatActivity {

    private ListView lv_stu;
    private StuDataDao stuDataDao;
    private List<StuPreData> preDataList;
    private final int[] userIcons = {R.mipmap.user_icon_1, R.mipmap.user_icon_2, R.mipmap.user_icon_3, R.mipmap.user_icon_4, R.mipmap.user_icon_5, R.mipmap.user_icon_6, R.mipmap.user_icon_7, R.mipmap.user_icon_8, R.mipmap.user_icon_9, R.mipmap.user_icon_10, R.mipmap.user_icon_11, R.mipmap.user_icon_12};
    private StuAdapter stuAdapter;
    private View searchDialog;
    private String searchText;
    private String searchOrder;
    private int searchOrderChoosed = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
        initListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all:
                doMenuAll();
                break;
            case R.id.menu_search:
                doMenuSearch();
                break;
            case R.id.menu_order:
                doMenuOrder();
                break;
            case R.id.menu_add:
                doMenuAdd();
                break;
            case R.id.menu_about:
                doMenuAbout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doMenuAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("关于");
        builder.setMessage("提供奖学金管理的基础功能\n版本：v1.0");
        builder.setPositiveButton("确定",null);
        builder.show();
    }

    private void doMenuAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialog = View.inflate(this,R.layout.dialog_addstudent,null);
        final EditText et_add_stucode = (EditText) dialog.findViewById(R.id.et_add_stucode);
        final EditText et_add_name = (EditText) dialog.findViewById(R.id.et_add_name);
        final EditText et_add_class = (EditText) dialog.findViewById(R.id.et_add_class);
        final EditText et_add_gradepoint = (EditText) dialog.findViewById(R.id.et_add_gradepoint);
        builder.setView(dialog);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StuBaseInfo info = new StuBaseInfo();
                info.setStuName(et_add_name.getText().toString().trim());
                info.setClassName(et_add_class.getText().toString().trim());
                info.setGradePoint(Double.valueOf(et_add_gradepoint.getText().toString().trim()));
                info.setStuCode(Long.valueOf(et_add_stucode.getText().toString().trim()));
                boolean status = stuDataDao.addBaseInfo(info);
                if(status){
                    Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    preDataList = stuDataDao.getStuPreData(searchOrder, searchText);
                    stuAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    private void doMenuOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(new String[]{"按绩点排序", "按活动排序", "按总分排序"}, searchOrderChoosed, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        searchOrder = DbValueUtils.GRADE;
                        preDataList = stuDataDao.getStuPreData(searchOrder, searchText);
                        stuAdapter.notifyDataSetChanged();
                        searchOrderChoosed=0;
                        break;
                    case 1:
                        searchOrder = DbValueUtils.ACTIVITY;
                        preDataList = stuDataDao.getStuPreData(searchOrder, searchText);
                        stuAdapter.notifyDataSetChanged();
                        searchOrderChoosed=1;
                        break;
                    case 2:
                        searchOrder = DbValueUtils.STATIS;
                        preDataList = stuDataDao.getStuPreData(searchOrder, searchText);
                        stuAdapter.notifyDataSetChanged();
                        searchOrderChoosed=2;
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        }).show();
    }

    private void doMenuSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        searchDialog = View.inflate(this, R.layout.dialog_search, null);

        builder.setView(searchDialog);
        builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et_search_name = (EditText) searchDialog.findViewById(R.id.et_search_name);
                searchText = et_search_name.getText().toString().trim();
                preDataList = stuDataDao.getStuPreData(searchOrder, searchText);
                stuAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

    private void doMenuAll() {
        searchText = "";
        searchOrder = DbValueUtils.STATIS;
        preDataList = stuDataDao.getStuPreData(searchOrder, searchText);
        stuAdapter.notifyDataSetChanged();
    }

    private void initListView() {
        searchText = "";
        searchOrder = DbValueUtils.STATIS;
        preDataList = stuDataDao.getStuPreData(searchOrder, searchText);
        stuAdapter = new StuAdapter();
        lv_stu.setAdapter(stuAdapter);
        lv_stu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,DetailActivity.class);
                intent.putExtra("stu_code",preDataList.get(position).getStuCode());
                startActivityForResult(intent,0);
            }
        });
    }

    private void initActivity() {
        lv_stu = (ListView) findViewById(R.id.lv_stu);
        stuDataDao = new StuDataDao(this);
    }

    private class StuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return preDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return preDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemHolder itemHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_predata, null);
                itemHolder = new ListItemHolder();
                itemHolder.iv_usericon = (ImageView) convertView.findViewById(R.id.iv_usericon);
                itemHolder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
                itemHolder.tv_userdesc = (TextView) convertView.findViewById(R.id.tv_userdesc);
                convertView.setTag(itemHolder);
            }else{
                itemHolder = (ListItemHolder) convertView.getTag();
            }
            itemHolder.data = preDataList.get(position);
            itemHolder.iv_usericon.setImageResource(userIcons[(int) (itemHolder.data.getStuCode() % 12)]);
            itemHolder.tv_username.setText(itemHolder.data.getStuName());
            String text = "绩点:" + itemHolder.data.getGradePoint() + "  活动:" + itemHolder.data.getActivityPoint() + "  总分:" + itemHolder.data.getStatisPoint();
            itemHolder.tv_userdesc.setText(text);
            return convertView;
        }
    }

    static class ListItemHolder{
        ImageView iv_usericon;
        TextView tv_username;
        TextView tv_userdesc;
        StuPreData data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        preDataList = stuDataDao.getStuPreData(searchOrder, searchText);
        stuAdapter.notifyDataSetChanged();
    }

}
