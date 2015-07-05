package com.rdc.ruan.zzia.Main.Activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.AsyncTask.ClassTableGetTask;
import com.rdc.ruan.zzia.Main.AsyncTask.ClassTablePostTask;
import com.rdc.ruan.zzia.Main.Utils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 课程表查询
 */
public class ClassTableActivity extends ActionBarActivity {
    String url;
    String selectedYear,selectedTerm;
    Spinner sp1,sp2;
    Button btn_cx;
    TextView textView;
    private ActionBar actionBar;
    private String[] year,term;
    private ListView listView;
    private List<Map<String,String>> list;
    private String[] from;
    private int[] to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);
        //InitStatusBar.InitStatusBar(this,getWindow(),true);
        url=getIntent().getStringExtra("url");
        sp1=(Spinner)findViewById(R.id.sp1);
        sp2=(Spinner)findViewById(R.id.sp2);
        btn_cx = (Button)findViewById(R.id.btn_cx);
        textView = (TextView)findViewById(R.id.textview);
        listView = (ListView) findViewById(R.id.listview_table);
        list = new ArrayList<>();
        from = new String[] {"classid","name", "type", "teacher", "credit","time"};
        to = new int[] {R.id.class_number, R.id.class_name, R.id.class_character,
                R.id.class_teacher,R.id.class_score,R.id.class_time};
        if (getSupportActionBar() != null){
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ClassTableGetTask classTableGetTask = new ClassTableGetTask(url);
        classTableGetTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {
                String str = (String)result;
                textView.setText(str);
                list = MyJsoup.getClassTable(str);
                year = MyJsoup.getSpinnerYear(str);
                term = MyJsoup.getSpinnerTerm(str);
                sp1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.spannerlayout,
                        year));
                sp2.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.spannerlayout,
                        term));
                int index_year=0,index_term=0;
                for (int i = 0;i<year.length;i++){
                    if (year[i].equals(MyJsoup.getYearSelected(str))){
                        index_year = i;
                    }
                }
                for (int i = 0;i<term.length;i++){
                    if (year[i].equals(MyJsoup.getTermSelected(str))){
                        index_term = i;
                    }
                }
                sp1.setSelection(index_year);
                sp2.setSelection(index_term);
                selectedTerm = term[index_term];
                selectedYear = year[index_year];
                sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedYear = year[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedTerm = term[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                        list,R.layout.itemlayout,from,to);
                listView.setAdapter(adapter);
                return null;
            }
        });
        classTableGetTask.execute();
        btn_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassTablePostTask classTablePostTask = new ClassTablePostTask(url,
                        selectedYear,
                        selectedTerm);
                classTablePostTask.setCallbackListener(new CallbackListener() {
                    @Override
                    public Object Return(Object result) {
                        //textView.setText(MyJsoup.getClassTable((String)result));
                        list.clear();
                        list = MyJsoup.getClassTable((String)result);
                        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                                list,R.layout.itemlayout,from,to);
                        listView.setAdapter(adapter);
                        return null;
                    }
                });
                classTablePostTask.execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class_table, menu);
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
