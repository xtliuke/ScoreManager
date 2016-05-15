package orz.keng.scoremanager;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.List;

import orz.keng.scoremanager.dao.StuDataDao;
import orz.keng.scoremanager.entity.StuActivity;
import orz.keng.scoremanager.entity.StuBaseInfo;
import orz.keng.scoremanager.entity.StuPreData;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAddBaseInfo(){
        StuDataDao dao = new StuDataDao(getContext());
        for(int i=0;i<100;i++){
            StuBaseInfo baseInfo = new StuBaseInfo();
            baseInfo.setStuName("学生"+i);
            baseInfo.setStuCode(100000L+i);
            baseInfo.setGradePoint(Math.random()*50+50);
            baseInfo.setClassName("计算机一班");
            dao.addBaseInfo(baseInfo);
        }
    }

    public void testAddActivity(){
        StuDataDao dao = new StuDataDao(getContext());
        for(int i=0;i<10;i++){
            StuActivity activity = new StuActivity();
            activity.setStuCode(100000L+i);
            activity.setActivityName("活动"+i);
            activity.setActivityPoint(Math.random()*10);
            dao.addActivity(activity);
        }
        for(int i=0;i<30;i++){
            StuActivity activity = new StuActivity();
            activity.setStuCode(100000L+i);
            activity.setActivityName("活动"+(i+10));
            activity.setActivityPoint(Math.random()*10);
            dao.addActivity(activity);
        }
    }

}