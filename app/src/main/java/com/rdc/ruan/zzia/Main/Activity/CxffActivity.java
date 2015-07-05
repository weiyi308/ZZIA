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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.AsyncTask.CxffTask;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 创新学分
 */
public class CxffActivity extends ActionBarActivity {
    private ActionBar actionBar;
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Button btn_login;
    private EditText edit_user,edit_password,edit_code;
    private List<BasicNameValuePair> list;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cxff);
        if (getSupportActionBar()!=null){
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //InitStatusBar.InitStatusBar(this,getWindow(),true);
        textView = (TextView) findViewById(R.id.tv_cxff);
        imageView = (ImageView) findViewById(R.id.image_code);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_code);
        btn_login = (Button) findViewById(R.id.btn_cxff_login);
        edit_user = (EditText) findViewById(R.id.edit_user);
        edit_code = (EditText) findViewById(R.id.edit_code);
        edit_password = (EditText) findViewById(R.id.edit_password);
        list = new ArrayList<>() ;
        url = "http://202.196.166.180/cxxf/UserLogin";
        /*ImageTask imageTask = new ImageTask("http://202.196.166.180/cxxf/UserLogin/ValidateCode",
                imageView,progressBar);
        imageTask.execute();*/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBar.getVisibility() == View.GONE) {
                    /*ImageTask imageTask = new ImageTask("http://202.196.166.180/cxxf/UserLogin/ValidateCode",
                            imageView, progressBar);
                    imageTask.execute();*/
                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
                CxffTask cxffTask = new CxffTask(url,list);
                cxffTask.setCallbackListener(new CallbackListener() {
                    @Override
                    public Object Return(Object result) {
                        String str = (String)result;
                        textView.setText(str);
                        /*ImageTask imageTask = new ImageTask("http://202.196.166.180/cxxf/UserLogin/ValidateCode",
                                imageView, progressBar);
                        imageTask.execute();*/
                        return null;
                    }
                });
                cxffTask.execute();
            }
        });
    }
    public void checkLogin(){
        String str_user = edit_user.getText().toString();
        String str_password = edit_password.getText().toString();
        String str_code = edit_code.getText().toString();
        String view_state = "/wEPDwULLTEwMDkyMjk5MTBkZN5gCtcTIItnuML06vfBOf09mLRA57YmhODr7xdp8OSD";
        if (str_user.length() != 9){
            edit_user.setError("9位数字");
        }else if (str_password.length() <8){
            edit_password.setError("密码过短");
        }else if (str_code.length() != 4){
            edit_code.setError("验证码错误");
        }
        list.add(new BasicNameValuePair("PersonName",str_user));
        list.add(new BasicNameValuePair("Password",str_password));
        list.add(new BasicNameValuePair("ValidateCode",str_code));
        list.add(new BasicNameValuePair("__VIEWSTATE",view_state));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cxff, menu);
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
