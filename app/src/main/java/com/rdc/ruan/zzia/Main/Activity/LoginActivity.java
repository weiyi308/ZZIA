package com.rdc.ruan.zzia.Main.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rdc.ruan.zzia.Main.AsyncTask.ImageTask;
import com.rdc.ruan.zzia.Main.AsyncTask.UrlTask;
import com.rdc.ruan.zzia.Main.HttpUtils.HttpUtil;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity{

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private EditText code_txt;
    private ImageView imageView;
    private ImageTask imageTask;
    private View view;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    private String url;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.user);
        code_txt = (EditText)findViewById(R.id.code_txt);
        mPasswordView = (EditText) findViewById(R.id.password);
        imageView = (ImageView)findViewById(R.id.image);
        view = findViewById(R.id.login_form);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        imageTask =null;

        UrlTask urlTask = new UrlTask("http://202.196.166.138");
        urlTask.setCallbackListener(new CallbackListener() {
            @Override
            public Object Return(Object result) {
                url = (String)result;
                if (url.length()<50){
                    Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    eImageTask();
                }
                return null;
            }
        });
        urlTask.execute();
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                eImageTask();
            }
        });
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
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mSignInButton.setBackgroundColor(Color.argb(255,24,116,191));
                        break;
                    case MotionEvent.ACTION_UP:
                        mSignInButton.setBackgroundColor(Color.argb(200,24,116,191));
                        break;
                }
                return false;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        mSignInButton.setBackgroundColor(Color.argb(200,24,116,191));
                        break;
                }
                return false;
            }
        });

    }



    public void eImageTask(){
        imageTask =new ImageTask(url,imageView,progressBar);
        progressBar.setVisibility(View.VISIBLE);
        imageTask.execute();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
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
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (!isUserValid(user)){
            mUserView.setError(getString(R.string.error_invalid_user));
            mUserView.requestFocus();
        }else if (isEmptyPassword(password)){
            mPasswordView.setError(getString(R.string.error_empty_password));
            mPasswordView.requestFocus();
        }else if (!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_short_password));
            mPasswordView.requestFocus();
        }else if (isEmptyCode(code_txt.getText().toString())){
            code_txt.setError("验证码为空");
            code_txt.requestFocus();
        }
        else {
            mAuthTask = new UserLoginTask(user, password);
            progressDialog = ProgressDialog.show(LoginActivity.this,"正在登录...","请稍候",true,false);
            mAuthTask.execute((Void) null);

        }
    }
    private void Verifi(String result){
        if (result==""){
            Toast.makeText(LoginActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else if (result.indexOf("用户不存在")!=-1){
            progressDialog.dismiss();
            code_txt.setText("");
            if (imageTask!=null)
                imageTask=null;
            eImageTask();
            Toast.makeText(LoginActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
        }else if (result.indexOf("密码错误")!=-1) {
            progressDialog.dismiss();
            code_txt.setText("");
            if (imageTask!=null)
                imageTask=null;
            eImageTask();
            Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
        }else if (result.indexOf("验证码不正确")!=-1){
            progressDialog.dismiss();
            code_txt.setText("");
            if (imageTask!=null)
                imageTask=null;
            eImageTask();
            Toast.makeText(LoginActivity.this,"验证码不正确",Toast.LENGTH_SHORT).show();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            bundle.putString("content",result);
            bundle.putString("id",mUserView.getText().toString());
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtras(bundle);
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
    private boolean isEmptyCode(String code){
        return code.isEmpty();
    }
    /**
     * Shows the progress UI and hides the login form.


     /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
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
            String __VIEWSTATE = "";


            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("__VIEWSTATE", "dDwyODE2NTM0OTg7Oz4egbL55hZdXDEySb4xyhjc5fd+ig=="));
            System.out.println("__VIEWSTATE" + __VIEWSTATE);
            pairs.add(new BasicNameValuePair("txtUserName", mUser));
            pairs.add(new BasicNameValuePair("TextBox2", mPassword));
            pairs.add(new BasicNameValuePair("RadioButtonList1", "%D1%A7%C9%FA"));
            pairs.add(new BasicNameValuePair("Button1", ""));
            pairs.add(new BasicNameValuePair("lbLanguage", ""));
            pairs.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "92719903"));
            pairs.add(new BasicNameValuePair("txtSecretCode",code_txt.getText().toString()));
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
            //System.out.println(info);
            //textView.setText(info);
            return info;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //System.out.println(result);
            Verifi(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}


