package com.rdc.ruan.zzia.Main.Activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.AsyncTask.ClassTableGetTask;
import com.rdc.ruan.zzia.Main.Utils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;

import java.util.List;
import java.util.Map;

/**
 * 四六级查询
 */
public class CetActivity extends ActionBarActivity {
    String txt_cetid,txt_name;
    TextView textView;
    private ActionBar actionBar;
    private List<Map<String,String>> list;
    private String[] from;
    private int[] to;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet);
        textView=(TextView)findViewById(R.id.textview);
        listView = (ListView) findViewById(R.id.cet_listview);
        String url = getIntent().getStringExtra("url");
        if (getSupportActionBar() != null){
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        from = new String[]{"year","term","name","time","score"};
        to = new int[]{R.id.tv_cet_year,R.id.tv_cet_term,R.id.tv_cet_name,R.id.tv_cet_time,R.id.tv_cet_score};
        ClassTableGetTask classTableGetTask = new ClassTableGetTask(url);
        classTableGetTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {
                String str = (String)result;
                if (str.isEmpty()){
                    return null;
                }
                list = MyJsoup.getCet(str);
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                        list,R.layout.cetitem,from,to);
                listView.setAdapter(adapter);
                return null;
            }
        });
        classTableGetTask.execute();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
