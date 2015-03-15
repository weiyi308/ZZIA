package com.rdc.ruan.zzia.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.AsyncTask.ScoreTask;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;

public class ScoreActivity extends ActionBarActivity {
    TextView textView;
    String url,header,userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        textView=(TextView)findViewById(R.id.textview);
        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        header=intent.getStringExtra("header");
        userid=intent.getStringExtra("id");
        ScoreTask scoreTask =new ScoreTask(url,userid);
        scoreTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {
                textView.setText((String)result);
                return null;
            }
        });
        scoreTask.execute();

    }




}