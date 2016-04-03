package com.rdc.ruan.zzia.Main.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.ClassInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/6/17.
 */
public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ViewHolder> {
    private List<ClassInfo> list;
    private LayoutInflater inflater;
    private Context context;
    public ScoreListAdapter(Context context,List<ClassInfo> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =  inflater.inflate(R.layout.scorelist,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ClassInfo classInfo = list.get(i);
        //viewHolder.year.setText(classInfo.getYear()+"-"+classInfo.getTerm());
        viewHolder.className.setText(classInfo.getClassName());
        viewHolder.type.setText(classInfo.getClassType());
        viewHolder.credit.setText(classInfo.getClassCredit());
        String score = "";
        if (!TextUtils.isEmpty(classInfo.getChongxiuScore())){
            if (Float.parseFloat(classInfo.getChongxiuScore())<60)
                score = "0";
            else
                score = classInfo.getScore() +
                        "("+classInfo.getBukaoScore()+")"+
                        "("+classInfo.getChongxiuScore()+")";
        }else if (!TextUtils.isEmpty(classInfo.getBukaoScore())){
            if (Float.parseFloat(classInfo.getBukaoScore())<60){
                score = "0";
            }else
                score = classInfo.getScore() +
                        "("+classInfo.getBukaoScore()+")";
        }else {
            score = classInfo.getScore();
        }
        /*String bukaoScore = "(" + classInfo.getChongxiuScore()+")";
        if (classInfo.getChongxiuScore().isEmpty())
            bukaoScore = "";*/
        viewHolder.score.setText(score);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*@Override
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
        String makeupScore = "(" + classInfo.getChongxiuScore()+")";
        if (classInfo.getChongxiuScore().isEmpty())
            makeupScore = "";
        viewHolder.score.setText(classInfo.getTotalScore()+makeupScore);
        return convertView;
    }*/
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView year;
        public TextView className;
        public TextView type;
        public TextView credit;
        public TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            //this.year = (TextView) itemView.findViewById(R.id.text_year);
            this.className = (TextView) itemView.findViewById(R.id.text_className);
            this.type = (TextView) itemView.findViewById(R.id.text_type);
            this.credit = (TextView) itemView.findViewById(R.id.text_credit);
            this.score = (TextView) itemView.findViewById(R.id.text_score);
        }
    }
}
