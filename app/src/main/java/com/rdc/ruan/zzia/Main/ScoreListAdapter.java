package com.rdc.ruan.zzia.Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.HttpUtils.ClassInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/6/17.
 */
public class ScoreListAdapter extends BaseAdapter {
    private List<ClassInfo> list;
    private LayoutInflater inflater;
    private Context context;
    public ScoreListAdapter(Context context,List<ClassInfo> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.scorelist,null);
            viewHolder = new ViewHolder();
            viewHolder.year = (TextView) convertView.findViewById(R.id.text_year);
            viewHolder.className = (TextView) convertView.findViewById(R.id.text_className);
            viewHolder.type = (TextView) convertView.findViewById(R.id.text_type);
            viewHolder.credit = (TextView) convertView.findViewById(R.id.text_credit);
            viewHolder.score = (TextView) convertView.findViewById(R.id.text_score);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ClassInfo classInfo = list.get(position);
        viewHolder.year.setText(classInfo.getYear()+"-"+classInfo.getTerm());
        viewHolder.className.setText(classInfo.getClassName());
        viewHolder.type.setText(classInfo.getClassType());
        viewHolder.credit.setText(classInfo.getClassCredit());
        String makeupScore = "(" + classInfo.getMakeupScore()+")";
        if (classInfo.getMakeupScore().isEmpty())
            makeupScore = "";
        viewHolder.score.setText(classInfo.getTotalScore()+makeupScore);
        return convertView;
    }
    public final class ViewHolder{
        private TextView year;
        private TextView className;
        private TextView type;
        private TextView credit;
        private TextView score;
    }
}
