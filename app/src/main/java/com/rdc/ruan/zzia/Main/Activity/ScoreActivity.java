package com.rdc.ruan.zzia.Main.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.rdc.ruan.zzia.Main.HttpUtils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.InitStatusBar;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/***
 * 成绩查询界面
 */
public class ScoreActivity extends Activity {
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
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        //textView=(TextView)findViewById(R.id.textview);
        InitStatusBar.InitStatusBar(this,getWindow(),true);
        sp1=(Spinner)findViewById(R.id.sp1);
        sp2=(Spinner)findViewById(R.id.sp2);
        btn_cx=(Button)findViewById(R.id.btn_cx);
        webView = (WebView) findViewById(R.id.webview);


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
                updateSpinner();
                return null;
            }
        });
        scoreTask.execute();
        //查询按钮事件
        btn_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScorePostTask scorePostTask=new ScorePostTask(url,selectedYear,selectedTerm,userid);
                scorePostTask.setCallbackListener(new CallbackListener() {
                    @Override
                    public Object Return(Object result) {
                        //textView.setText(MyJsoup.getTable((String )result));
                        webView.loadDataWithBaseURL(null,
                                "<html>"+"<body>"+"<table border=\"0\" bordercolor=\"Black\">"+
                                MyJsoup.getTable((String )result)+
                                        "</table>"+"</body>" + "</html>",
                                "text/html",
                                "utf-8",
                                null);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setWebChromeClient(new WebChromeClient());
                        DisplayMetrics dm = getResources().getDisplayMetrics();
                        int scale = dm.densityDpi;
                        if (scale == 240) { //
                            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
                        } else if (scale == 160) {
                            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
                        } else {
                            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
                        }
                        //webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                        webView.setHorizontalScrollBarEnabled(false);
                        webView.getSettings().setSupportZoom(true);
                        webView.getSettings().setBuiltInZoomControls(true);
                        //扩大比例的缩放
                        webView.getSettings().setUseWideViewPort(true);
//自适应屏幕
                        /*webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                        webView.getSettings().setLoadWithOverviewMode(true);*/
                        //webView.setInitialScale(70);
                        //webView.setHorizontalScrollbarOverlay(true);
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

        //Toast.makeText(this,year[8],Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,term[2],Toast.LENGTH_SHORT).show();
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