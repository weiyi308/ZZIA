package com.rdc.ruan.zzia.Main.Activity;

import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.ruan.zzia.Main.AsyncTask.ScorePostTask;
import com.rdc.ruan.zzia.Main.AsyncTask.ScoreTask;
import com.rdc.ruan.zzia.Main.Utils.ClassInfo;
import com.rdc.ruan.zzia.Main.Utils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Adapter.ScoreListAdapter;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/***
 * 成绩查询界面
 */
public class ScoreActivity extends ActionBarActivity {
    private ListView listView;
    private ProgressBar progressBar;
    private List<ClassInfo> list;
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
        //InitStatusBar.InitStatusBar(this, getWindow(), true);
        sp1=(Spinner)findViewById(R.id.sp1);
        sp2=(Spinner)findViewById(R.id.sp2);
        btn_cx=(Button)findViewById(R.id.btn_cx);
        textView = (TextView) findViewById(R.id.textview);
        listView = (ListView) findViewById(R.id.scoreList);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
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
                //textView.setText("");
                progressBar.setVisibility(View.VISIBLE);
                ScorePostTask scorePostTask=new ScorePostTask(url,selectedYear,selectedTerm,userid);
                scorePostTask.setCallbackListener(new CallbackListener() {
                    @Override
                    public Object Return(Object result) {
                        list = MyJsoup.getClassInfos((String) result);
                        ClassInfo title = list.remove(0);
                        /*for (int i=0;i<list.size();i++){
                            textView.append(list.get(i).toString());
                        }*/
                        listView.setAdapter(new ScoreListAdapter(getApplicationContext(),list));
                        progressBar.setVisibility(View.GONE);
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
            case R.id.action_count:
                if (list == null)
                    return true;
                float sum_credit = 0;
                float sum_gpa = 0;
                float score = 0;
                String str_score = "";
                List<Integer> list_count = new ArrayList<>();
                List<Float> list_credit = new ArrayList<>();
                List<String> list_type = new ArrayList<>();
                int total_failed = 0;
                int restudy = 0;
                //去重
                List<ClassInfo> mlist = new ArrayList<>(list);
                for (int i=0;i<mlist.size();i++){
                    String classID = mlist.get(i).getClassName();
                    for (int j=mlist.size()-1;j>i;j--){
                        if (classID.equals(mlist.get(j).getClassName())){
                            mlist.remove(j);
                            restudy++;
                        }
                    }
                }


                for (int i=0;i<mlist.size();i++){
                    float credit = Float.parseFloat(mlist.get(i).getClassCredit());
                    float gpa = 0;
                    //自动分类
                    if (!list_type.contains(mlist.get(i).getClassType())) {
                        list_type.add(mlist.get(i).getClassType());
                        list_count.add(1);
                        list_credit.add(Float.parseFloat(mlist.get(i).getClassCredit()));
                    }else {
                        int current_index = list_type.indexOf(mlist.get(i).getClassType());
                        list_count.set(current_index,list_count.get(current_index)+1);
                        list_credit.set(current_index,list_credit.get(current_index)+
                            Float.parseFloat(mlist.get(i).getClassCredit()));
                    }
                    sum_credit = sum_credit +credit;
                    if (!mlist.get(i).getMakeupScore().isEmpty()) {
                        str_score = mlist.get(i).getMakeupScore();
                        if (Float.parseFloat(str_score)>=60) {
                            total_failed++;
                        }
                    }
                    else {
                        str_score = mlist.get(i).getTotalScore();
                    }
                    switch (str_score){
                        case "优秀":
                            score = 4.5f;
                            break;
                        case "良好":
                            score = 3.5f;
                            break;
                        case "中等":
                            score = 2.5f;
                            break;
                        case "及格":
                            score = 1.5f;
                            break;
                        case "不及格":
                            score = 0;
                            break;
                        default:
                            score = Float.parseFloat(str_score);
                            if (score<60){
                                total_failed++;
                            }
                            score = (float)((int)score/10-5+(int)score%10*0.1);
                            Log.i("score",score+"");
                    }
                    gpa = score*credit;
                    sum_gpa = sum_gpa+gpa;
                }
                float avg_gpa = sum_gpa/sum_credit;
                /*BigDecimal b   =   new   BigDecimal(avg_gpa);
                avg_gpa   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();*/
                String str_type = "";
                for (int i=0;i<list_type.size();i++){
                    str_type = str_type + list_type.get(i)+":共"+list_count.get(i)+"门 学分:"+list_credit.get(i)+"\n";
                }
                new AlertDialog.Builder(this)
                        .setTitle("统计")
                        .setMessage(str_type+"\n"
                                +"共"+mlist.size()+"门课"+"\n"
                                +"总学分:"+sum_credit+"\n"
                                +"平均绩点:"+avg_gpa+"\n"
                                +"挂科数:"+total_failed+"\n"
                                +"重修数:"+restudy+"\n")
                        .setPositiveButton("确定", null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}