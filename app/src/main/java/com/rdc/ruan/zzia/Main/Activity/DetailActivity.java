package com.rdc.ruan.zzia.Main.Activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.AsyncTask.ImageTask;
import com.rdc.ruan.zzia.Main.AsyncTask.TestTask;
import com.rdc.ruan.zzia.Main.HttpUtils.HttpUtil;
import com.rdc.ruan.zzia.Main.HttpUtils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;
import com.rdc.ruan.zzia.Main.R;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends ActionBarActivity {
	private ActionBar actionBar;
	private ImageView image;
	private TextView tv_name,tv_sex,tv_num,tv_zy,tv_stdnum,tv_zzmm,tv_highSchool,tv_zkzh,tv_sfz,tv_phone;
	private ProgressBar progressBar;
	private Map<String,String > map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		if (getSupportActionBar()!=null){
			actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		map = new HashMap<>();
		image = (ImageView) findViewById(R.id.imageView1);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_sex=(TextView) findViewById(R.id.tv_sex);
		tv_num=(TextView) findViewById(R.id.tv_num);
		tv_zy=(TextView) findViewById(R.id.tv_major);
		tv_stdnum=(TextView) findViewById(R.id.tv_exnum);
		tv_zzmm=(TextView) findViewById(R.id.tv_plst);
		tv_highSchool=(TextView) findViewById(R.id.tv_grsc);
		tv_zkzh=(TextView) findViewById(R.id.tv_ern);
		tv_sfz=(TextView) findViewById(R.id.tv_IDnum);
		tv_phone = (TextView) findViewById(R.id.tv_phnum);
		progressBar = (ProgressBar) findViewById(R.id.progressbar_detail);
		Intent intent = getIntent();
		String url = getIntent().getStringExtra("url");
		TestTask testTask = new TestTask(url,progressBar);
		testTask.setCallbackListener(new CallbackListener() {
			@Override
			public Object Return(Object result) {
				String str = (String)result;
				if (str.equals("")){
					return null;
				}
				map = MyJsoup.getDetail(str);
				String url = HttpUtil.GetCookieUrl(MyJsoup.getMainUrl())+map.get("image");
				String name =map.get("name");
				String sex =map.get("sex");
				String id =map.get("id");
				String zy =map.get("zy");
				String stdnum =map.get("stdnum");
				String zzmm = map.get("zzmm");
				String highSchool = map.get("highSchool");
				String zkzh = map.get("zkzh");
				String sfz = map.get("sfz");
				String phone = map.get("phone");
				tv_name.setText(name);
				tv_num.setText(id);
				tv_sex.setText(sex);
				tv_zy.setText(zy);
				tv_stdnum.setText(stdnum);
				tv_zzmm.setText(zzmm);
				tv_highSchool.setText(highSchool);
				tv_zkzh.setText(zkzh);
				tv_sfz.setText(sfz);
				tv_phone.setText(phone);
				ImageTask imageTask = new ImageTask(url,image);
				imageTask.execute();
				return null;
			}
		});
		testTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
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
