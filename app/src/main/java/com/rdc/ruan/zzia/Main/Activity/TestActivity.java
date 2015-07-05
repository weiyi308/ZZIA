package com.rdc.ruan.zzia.Main.Activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.AsyncTask.TestTask;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.InitStatusBar;

public class TestActivity extends ActionBarActivity {
    private ActionBar actionBar;
    private TextView textView;
    private TextView test_content;
    private ProgressBar progressBar;
    private int i;
    private boolean flag;
    private long first_time,second_time,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = (TextView) findViewById(R.id.test_text);
        test_content = (TextView) findViewById(R.id.test_content);
        progressBar = (ProgressBar) findViewById(R.id.test_progress);
        //InitStatusBar.InitStatusBar(this,getWindow(),true);

        if (getSupportActionBar() != null){
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        flag = false;
        i = 1;
        task();
    }

    public void task(){
        textView.setText("第"+i+"次访问中:");
        String url = "http://202.196.166.180/bysj/Account";
        TestTask testTask = new TestTask(url,progressBar);
        testTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {
                String str = (String) result;
                second_time = System.currentTimeMillis();
                time = second_time - first_time;
                if (!str.isEmpty()) {
                    textView.setText("第" + i + "次访问成功"+"耗时"+time/1000*1.0+"秒");
                    test_content.append(str);
                    flag = true;
                } else {
                    textView.setText("第" + i + "次访问失败");
                    test_content.append("第" + i + "次访问失败" + "耗时" +time/1000*1.0+"秒"+"\n");
                    flag = false;
                    i++;
                    task();
                }
                return null;
            }
        });
        testTask.execute();
        first_time = System.currentTimeMillis();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            } else {
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, upIntent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
