package com.rdc.ruan.zzia.Main.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.Utils.HttpUtil;
import com.rdc.ruan.zzia.Main.Utils.MyJsoup;
import com.rdc.ruan.zzia.Main.R;

/**
 * 主界面
 */
public class MainActivity extends ActionBarActivity {
    TextView textView;
    Button btn_score,btn_restart;
    Button btn_cet,btn_cTable;
    private Button btn_detail;
    String temp;
    String name;
    String userid;
    String foot,url,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //InitStatusBar.InitStatusBar(this, getWindow(), true);
        textView = (TextView)findViewById(R.id.textview);
        btn_score = (Button)findViewById(R.id.btn_score);
        btn_restart = (Button)findViewById(R.id.restart);
        btn_cet=(Button)findViewById(R.id.btn_cet);
        btn_cTable=(Button)findViewById(R.id.classtable);
        btn_detail = (Button) findViewById(R.id.btn_detail);
        temp="";
        Bundle bundle =getIntent().getExtras();
        content = bundle.getString("content");
        if (content.equals("")){
            return;
        }
        userid=bundle.getString("id");
        url=bundle.getString("url");
        name= MyJsoup.getName(content);
        setTitle(name);
        MyJsoup.setMainUrl(HttpUtil.GetCookieUrl(url) + "xs_main.aspx?xh=" + userid);
        name=name.replace("同学","");
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foot = "xsgrxx.aspx?xh="+userid+"&xm="+
                        name+"&gnmkdm=N121618";
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("url",HttpUtil.GetCookieUrl(url)+foot);
                startActivity(intent);
            }
        });
        btn_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foot = "xscjcx_dq.aspx?xh="+userid+"&xm="+
                        name+"&gnmkdm=N121618";
                System.out.println(HttpUtil.GetCookieUrl(url)+foot);
                Intent intent = new Intent(MainActivity.this,ScoreActivity.class);
                intent.putExtra("url", HttpUtil.GetCookieUrl(url)+foot);
                intent.putExtra("header",url);
                intent.putExtra("id",userid);
                //System.out.println(url);
                startActivity(intent);
            }
        });
        btn_cet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foot = "xsdjkscx.aspx?xh="+userid+"&xm="+
                        name+"&gnmkdm=N121618";
                Intent intent = new Intent(MainActivity.this,CetActivity.class);
                intent.putExtra("url",HttpUtil.GetCookieUrl(url)+foot);
                startActivity(intent);
            }
        });
        btn_cTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,
                        ClassTableActivity.class);
                intent.putExtra("url",
                        HttpUtil.GetCookieUrl(url)+
                                "xsxkqk.aspx?xh="+
                                userid+"&xm="+
                                name+"&gnmkdm=N121616");
                startActivity(intent);
            }
        });
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("userid", "");
                editor.putString("password", "");
                editor.commit();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.T1){
            startActivity(new Intent(MainActivity.this,TestActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
