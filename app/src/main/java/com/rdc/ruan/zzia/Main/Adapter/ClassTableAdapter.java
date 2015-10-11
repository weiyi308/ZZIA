package com.rdc.ruan.zzia.Main.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Ruan on 2015/10/1.
 */
public class ClassTableAdapter extends RecyclerView.Adapter<ClassTableAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Map<String,String>> list;
    public ClassTableAdapter(Context context,List<Map<String,String>> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.itemlayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_number.setText(list.get(position).get("classid"));
        holder.tv_name.setText(list.get(position).get("name"));
        holder.tv_character.setText(list.get(position).get("type"));
        holder.tv_teacher.setText(list.get(position).get("teacher"));
        holder.tv_score.setText(list.get(position).get("credit"));
        holder.tv_time.setText(list.get(position).get("time"));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_number;
        public TextView tv_name;
        public TextView tv_character;
        public TextView tv_teacher;
        public TextView tv_score;
        public TextView tv_time;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_number = (TextView) itemView.findViewById(R.id.class_number);
            tv_name = (TextView) itemView.findViewById(R.id.class_name);
            tv_character = (TextView) itemView.findViewById(R.id.class_character);
            tv_teacher = (TextView) itemView.findViewById(R.id.class_teacher);
            tv_score = (TextView) itemView.findViewById(R.id.class_score);
            tv_time = (TextView) itemView.findViewById(R.id.class_time);
        }
    }
}
