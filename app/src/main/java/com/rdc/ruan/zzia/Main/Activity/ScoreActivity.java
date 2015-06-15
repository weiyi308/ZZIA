package com.rdc.ruan.zzia.Main.Activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.ruan.zzia.Main.AsyncTask.ScorePostTask;
import com.rdc.ruan.zzia.Main.AsyncTask.ScoreTask;
import com.rdc.ruan.zzia.Main.HttpUtils.ClassInfo;
import com.rdc.ruan.zzia.Main.HttpUtils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.InitStatusBar;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/***
 * 成绩查询界面
 */
public class ScoreActivity extends ActionBarActivity {
    TextView textView;
    String url,header,userid;
    String content;
    Spinner sp1,sp2;
    String selectedYear,selectedTerm;
    String[] year,term;
    ArrayAdapter<String> adapter1,adapter2;
    Button btn_cx;
    List<BasicNameValuePair> parms;
    String viewstate;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        InitStatusBar.InitStatusBar(this, getWindow(), true);
        sp1=(Spinner)findViewById(R.id.sp1);
        sp2=(Spinner)findViewById(R.id.sp2);
        btn_cx=(Button)findViewById(R.id.btn_cx);
        textView = (TextView) findViewById(R.id.textview);
        if (getSupportActionBar() != null){
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        header=intent.getStringExtra("header");
        userid=intent.getStringExtra("id");


        //查询网络任务启动
        ScoreTask scoreTask =new ScoreTask(url,userid);
        scoreTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {
                content = (String)result;
                //textView.setText(content);
                if (content.isEmpty())
                    return null;
                updateSpinner();
                return null;
            }
        });
        scoreTask.execute();
        //查询按钮事件
        btn_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                ScorePostTask scorePostTask=new ScorePostTask(url,selectedYear,selectedTerm,userid);
                scorePostTask.setCallbackListener(new CallbackListener() {
                    @Override
                    public Object Return(Object result) {
                        List<ClassInfo> list = MyJsoup.getClassInfos((String) result);
                        ClassInfo title = list.remove(0);
                        for (int i=0;i<list.size();i++){
                            textView.append(list.get(i).toString());
                        }
                        return null;
                    }
                });
                scorePostTask.execute();
            }
        });

    }
    //更新下拉选框的数据
    public void updateSpinner(){
        year= MyJsoup.getSpinnerYear(content);
        term=MyJsoup.getSpinnerTerm(content);


        adapter1=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                year);
        adapter2=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                term);
        sp1.setAdapter(adapter1);
        sp2.setAdapter(adapter2);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedYear=year[i];
                Toast.makeText(getApplicationContext(),selectedYear,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTerm=term[i];
                Toast.makeText(getApplicationContext(),selectedTerm,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_score,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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