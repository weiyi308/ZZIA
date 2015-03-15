package com.rdc.ruan.zzia.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.ruan.zzia.Main.AsyncTask.ScorePostTask;
import com.rdc.ruan.zzia.Main.AsyncTask.ScoreTask;
import com.rdc.ruan.zzia.Main.HttpUtils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        textView=(TextView)findViewById(R.id.textview);
        sp1=(Spinner)findViewById(R.id.sp1);
        sp2=(Spinner)findViewById(R.id.sp2);
        btn_cx=(Button)findViewById(R.id.btn_cx);


        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        header=intent.getStringExtra("header");
        userid=intent.getStringExtra("id");


        ScoreTask scoreTask =new ScoreTask(url,userid);
        scoreTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {
                content = (String)result;
                textView.setText(content);
                updateSpinner();
                return null;
            }
        });
        scoreTask.execute();
        btn_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScorePostTask scorePostTask=new ScorePostTask(url,selectedYear,selectedTerm,userid);
                scorePostTask.setCallbackListener(new CallbackListener() {
                    @Override
                    public Object Return(Object result) {
                        textView.setText((String)result);
                        return null;
                    }
                });
                scorePostTask.execute();
            }
        });

    }
    public void updateSpinner(){
        year= MyJsoup.getSpinnerYear(content);
        term=MyJsoup.getSpinnerTerm(content);

        Toast.makeText(this,year[8],Toast.LENGTH_SHORT).show();
        Toast.makeText(this,term[2],Toast.LENGTH_SHORT).show();
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




}