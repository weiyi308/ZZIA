package com.rdc.ruan.zzia.Main.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdc.ruan.zzia.Main.AsyncTask.CetTask;
import com.rdc.ruan.zzia.Main.R;

public class CetActivity extends ActionBarActivity {
    EditText cetid,name;
    String txt_cetid,txt_name;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet);
        cetid =(EditText)findViewById(R.id.cetid);
        name=(EditText)findViewById(R.id.name);
        textView=(TextView)findViewById(R.id.textview);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cet, menu);
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
            txt_cetid=cetid.getText().toString();
            txt_name=name.getText().toString();
            CetTask cetTask=new CetTask(txt_cetid,txt_name,textView);
            cetTask.execute();
            Toast.makeText(this,"here",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
