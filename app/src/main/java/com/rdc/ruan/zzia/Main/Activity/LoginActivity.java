package com.rdc.ruan.zzia.Main.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.ruan.zzia.Main.AsyncTask.ImageTask;
import com.rdc.ruan.zzia.Main.AsyncTask.UrlTask;
import com.rdc.ruan.zzia.Main.DateBase.DBManager;
import com.rdc.ruan.zzia.Main.Utils.HttpUtil;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 登录界面
 */
public class LoginActivity extends ActionBarActivity {
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mUserView;
    private EditText mPasswordView;
    private ImageTask imageTask;
    private ProgressDialog progressDialog;
    private String url;
    private String userid,password;
    private DBManager dbManager;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //根据ip地址获取Url
        UrlTask urlTask = new UrlTask("http://202.196.166.138");
        urlTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {
                url = (String)result;
                if (url.length()<50){
                    Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences preferences=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                    /*String userid=preferences.getString("userid", "");
                    String password=preferences.getString("password", "");*/
                    String str_userid="";
                    String str_password="";
                    if (!str_userid.isEmpty()&&!str_password.isEmpty()){
                        mUserView.setText(str_userid);
                        mPasswordView.setText(str_password);
                        mAuthTask = new UserLoginTask(str_userid, str_password);
                        progressDialog = ProgressDialog.show(LoginActivity.this,"自动登录...","请稍候",true,false);
                        mAuthTask.execute((Void) null);
                    }

                }
                return null;
            }
        });
        urlTask.execute();
        //登录按钮
        final Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                attemptLogin();
            }
        });
        mSignInButton.setBackgroundColor(Color.argb(200, 24, 116, 191));
        mSignInButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mSignInButton.setBackgroundColor(Color.argb(255, 24, 116, 191));
                        break;
                    case MotionEvent.ACTION_UP:
                        mSignInButton.setBackgroundColor(Color.argb(200, 24, 116, 191));
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }

    public void initView(){//初始化控件
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        dbManager = new DBManager(this);
        mUserView = (AutoCompleteTextView) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        imageTask =null;
        //自动填充账号密码
        if (dbManager.getCount("user") != 0) {
            String[] user = dbManager.selectId();
            Log.i("user", user.length + "");
            mUserView.setAdapter(new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    user));
            mUserView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view;
                    String password = dbManager.selectPassword(textView.getText().toString());
                    mPasswordView.setText(password);
                    InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });
        }
    }

    //本地账号密码验证
    public void attemptLogin() {
        if (mAuthTask != null) {
            System.out.println("here");
            mAuthTask=null;
        }
        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);
        mUserView.requestFocus();

        // Store values at the time of the login attempt.
        userid = mUserView.getText().toString();
        password = mPasswordView.getText().toString();
        if (!isUserValid(userid)){
            mUserView.setError(getString(R.string.error_invalid_user));
            mUserView.requestFocus();
        }else if (isEmptyPassword(password)){
            mPasswordView.setError(getString(R.string.error_empty_password));
            mPasswordView.requestFocus();
        }else if (!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_short_password));
            mPasswordView.requestFocus();
        }
        else {
            mAuthTask = new UserLoginTask(userid, password);
            progressDialog = ProgressDialog.show(LoginActivity.this,"正在登录...","请稍候",true,false);
            mAuthTask.execute((Void) null);
        }
    }
    //网络验证账号密码
    private void Verifi(String result){
        Log.i("result",result);
        if (result.equals("")){
            Toast.makeText(LoginActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else if (result.indexOf("用户不存在")!=-1){
            progressDialog.dismiss();
            if (imageTask!=null)
                imageTask=null;
            Toast.makeText(LoginActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
        }else if (result.indexOf("密码错误")!=-1) {
            progressDialog.dismiss();
            if (imageTask!=null)
                imageTask=null;
            Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
        }else if (result.indexOf("ERROR - 出错啦")!=-1){
            progressDialog.dismiss();
            if (imageTask!=null)
                imageTask=null;
            //eImageTask();
            Toast.makeText(LoginActivity.this,"请重新登陆，如无法解决，请稍后再试",Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences preferences=getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            if (preferences.getString("userid","").isEmpty()){
                /*editor.putString("userid", userid);
                editor.putString("password", password);*/
                editor.putString(userid,password.hashCode()+"");
                editor.commit();
            }
            if (!dbManager.contains(userid)) {
                dbManager.add(userid, password);
            }
            Bundle bundle = new Bundle();
            bundle.putString("url",url);//url地址附带cookie
            bundle.putString("content",result);//首页html代码
            bundle.putString("id", mUserView.getText().toString());//学号
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtras(bundle);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(intent);
            LoginActivity.this.finish();
            progressDialog.dismiss();
        }
    }

    private boolean isUserValid(String user) {
        //TODO: Replace this with your own logic

        return user.length()==9;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }
    private boolean isEmptyPassword(String password){
        return password.isEmpty();
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private String mUser;
        private String mPassword;

        UserLoginTask(String user, String password) {
            mUser = user;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            DefaultHttpClient client = new DefaultHttpClient();
            // 此处先获取页面，读取到value值为post做准备

            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("__VIEWSTATE", "dDwyODE2NTM0OTg7Oz4egbL55hZdXDEySb4xyhjc5fd+ig=="));
            pairs.add(new BasicNameValuePair("txtUserName", mUser));
            pairs.add(new BasicNameValuePair("TextBox2", mPassword));
            pairs.add(new BasicNameValuePair("Button1", ""));
            pairs.add(new BasicNameValuePair("lbLanguage", ""));

            String info = "";
            try {
                info = HttpUtil.postUrl(url,
                        pairs, client, url);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return info;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Verifi(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}


