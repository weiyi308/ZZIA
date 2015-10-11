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
public class CetAdapter extends RecyclerView.Adapter<CetAdapter.ViewHolder>{
    private List<Map<String,String>> list;
    private LayoutInflater inflater;
    public CetAdapter(Context context,List<Map<String,String>> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cetitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_cet_year.setText(list.get(position).get("year"));
        holder.tv_cet_term.setText(list.get(position).get("term"));
        holder.tv_cet_name.setText(list.get(position).get("name"));
        holder.tv_cet_time.setText(list.get(position).get("time"));
        holder.tv_cet_score.setText(list.get(position).get("score"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_cet_year;
        public TextView tv_cet_term;
        public TextView tv_cet_name;
        public TextView tv_cet_time;
        public TextView tv_cet_score;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_cet_year = (TextView) itemView.findViewById(R.id.tv_cet_year);
            tv_cet_term = (TextView) itemView.findViewById(R.id.tv_cet_term);
            tv_cet_name = (TextView) itemView.findViewById(R.id.tv_cet_name);
            tv_cet_time = (TextView) itemView.findViewById(R.id.tv_cet_time);
            tv_cet_score = (TextView) itemView.findViewById(R.id.tv_cet_score);
        }
    }
}
