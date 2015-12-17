package com.rdc.ruan.zzia.Main.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rdc.ruan.zzia.Main.AsyncTask.UrlTask;
import com.rdc.ruan.zzia.Main.DateBase.DBManager;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.Interface.RequestListener;
import com.rdc.ruan.zzia.Main.MyApp;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.Constant;
import com.rdc.ruan.zzia.Main.Utils.Http.HttpUtil;
import com.squareup.picasso.Picasso;


/**
 * 登录界面
 */
public class LoginActivity extends ActivityBase {
    private AutoCompleteTextView mUserView;
    private EditText mPasswordView,et_code;
    private ProgressDialog progressDialog;
    private String url;
    private String userid,password;
    private DBManager dbManager;
    private ImageView iv_code;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }


    @Override
    public void initView() {
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        dbManager = new DBManager(this);
        mUserView = (AutoCompleteTextView) this.findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        et_code = (EditText) findViewById(R.id.et_code);
        iv_code = (ImageView) findViewById(R.id.iv_code);
    }

    @Override
    public void initData() {
        //自动填充账号密码
        if (dbManager.getCount("user") != 0) {
            String[] user = dbManager.selectId();
            Log.i("user", user.length + "");
            mUserView.setAdapter(new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.autocomplete_textview,
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
        geturl();
        //登录按钮
        final Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
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

    private void geturl(){
        //根据ip地址获取Url
        UrlTask urlTask = new UrlTask("http://202.196.166.138");
        urlTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {

                url = (String)result;
                Log.i("url",url);
                if (url.length()<50){
                    Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    MyApp.setZziaUrlWithcookie(url);
                    Picasso.with(getApplicationContext())
                            .load(HttpUtil.GetCookieUrl(url)+"CheckCode.aspx")
                            .into(iv_code);
                    SharedPreferences preferences=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                    /*String userid=preferences.getString("userid", "");
                    String password=preferences.getString("password", "");*/
                    String str_userid="";
                    String str_password="";
                    if (!str_userid.isEmpty()&&!str_password.isEmpty()){
                        mUserView.setText(str_userid);
                        mPasswordView.setText(str_password);
                        progressDialog = ProgressDialog.show(LoginActivity.this,"自动登录...","请稍候",true,false);
                    }

                }
                return null;
            }
        });
        urlTask.execute();
    }

    @Override
    protected RequestListener requestListener() {
        return new RequestListener() {
            @Override
            public void requestSuccess(Object tag, String result) {
                if (tag.equals(Constant.TAG_LOGIN)){
                    Log.e("result=",result);
                    Verifi(result);
                }
            }

            @Override
            public void requestError(Object tag, VolleyError e) {
                progressDialog.dismiss();
            }
        };
    }



    //本地账号密码验证
    public void attemptLogin() {
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
            MyApp.setUser(userid);
            MyApp.setPassword(password);
            httpManager.login(userid,password,et_code.getText().toString());
            //mAuthTask = new UserLoginTask(userid, password);
            progressDialog = ProgressDialog.show(LoginActivity.this,"正在登录...","请稍候",true,false);
            //mAuthTask.execute((Void) null);
        }
    }
    //网络验证账号密码
    private void Verifi(String result){
        Log.i("result",result);
        if (result.equals("")){
            Toast.makeText(LoginActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else if (result.contains("用户不存在")){
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
        }else if (result.contains("密码错误")) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
        }else if (result.contains("ERROR - 出错啦")){
            progressDialog.dismiss();
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
            //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            Intent intent = new Intent(LoginActivity.this,MainFragmentActivity.class);
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


}


