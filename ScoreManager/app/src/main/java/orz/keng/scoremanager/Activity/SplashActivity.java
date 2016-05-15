package orz.keng.scoremanager.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import orz.keng.scoremanager.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView iv_splash_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initAPP();
    }

    private void initAPP() {
        /**
         * 新建半透明动画，让背景图片加载半透明动画，实现渐变效果。
         */
        iv_splash_bg = (ImageView) findViewById(R.id.iv_splash_bg);
        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(1000);
        iv_splash_bg.startAnimation(animation);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
