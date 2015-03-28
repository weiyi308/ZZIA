package com.rdc.ruan.zzia.Main.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.AsyncTask.ClassTableTask;
import com.rdc.ruan.zzia.Main.HttpUtils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.InitStatusBar;

/**
 * 课程表查询
 */
public class ClassTableActivity extends Activity {
    String url;
    String selectedYear,SelectedTerm;
    Spinner sp1,sp2;
    Button btn_cx;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);
        InitStatusBar.InitStatusBar(this);
        url=getIntent().getStringExtra("url");
        sp1=(Spinner)findViewById(R.id.sp1);
        sp2=(Spinner)findViewById(R.id.sp2);
        btn_cx = (Button)findViewById(R.id.btn_cx);
        textView = (TextView)findViewById(R.id.textview);
        btn_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassTableTask classTableTask = new ClassTableTask(url,
                        "2014-2015",
                        "2");
                classTableTask.setCallbackListener(new CallbackListener() {
                    @Override
                    public Object Return(Object result) {
                        textView.setText(MyJsoup.getClassTable((String)result));
                        return null;
                    }
                });
                classTableTask.execute();
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
        if (id == R.id.action_cx) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
