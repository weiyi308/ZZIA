package com.rdc.ruan.zzia.Main.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rdc.ruan.zzia.Main.Adapter.ViewPagerAdapter;
import com.rdc.ruan.zzia.Main.Fragment.CetFragment;
import com.rdc.ruan.zzia.Main.Fragment.ClassTableFragment;
import com.rdc.ruan.zzia.Main.Fragment.DetailFragment;
import com.rdc.ruan.zzia.Main.Fragment.ScoreFragment;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.Http.MyJsoup;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainFragmentActivity extends ActivityBase implements DetailFragment.OnFragmentInteractionListener{
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private String content,userid,url,name;
    public static MainFragmentActivity mainFragmentActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
    }

    @Override
    public void initView() {
        mainFragmentActivity = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        setSupportActionBar(toolbar);
    }

    @Override
    public void initData() {
        Bundle bundle =getIntent().getExtras();
        content = bundle.getString("content");
        if (content==null||content.isEmpty()){
            return;
        }
        userid=bundle.getString("id");
        url=bundle.getString("url");
        name= MyJsoup.getName(content);
        name=name.replace("同学","");
        MyJsoup.setUrl(url);
        try {
            MyJsoup.setName(URLEncoder.encode(name,"gb2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MyJsoup.setUserid(userid);
        getSupportActionBar().setTitle(name+"同学");
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailFragment.newInstance(MyJsoup.getDetailUrl(),
                MyJsoup.getMainUrl()),"个人信息");
        adapter.addFragment(ScoreFragment.newInstance("",
                ""),"成绩查询");
        adapter.addFragment(CetFragment.newInstance("",""),"四六级查询");
        adapter.addFragment(ClassTableFragment.newInstance("",""),
                "课表查询");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
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
            SharedPreferences preferences=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("userid", "");
            editor.putString("password", "");
            editor.commit();
            startActivity(new Intent(MainFragmentActivity.this,LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
