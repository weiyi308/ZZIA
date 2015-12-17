package com.rdc.ruan.zzia.Main.Fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rdc.ruan.zzia.Main.Adapter.ScoreListAdapter;
import com.rdc.ruan.zzia.Main.Interface.RequestListener;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.ClassInfo;
import com.rdc.ruan.zzia.Main.Utils.Constant;
import com.rdc.ruan.zzia.Main.Utils.Http.HttpManager;
import com.rdc.ruan.zzia.Main.Utils.Http.MyJsoup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HttpManager httpManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView listView;
    private ProgressBar progressBar;
    private List<ClassInfo> list;
    private TextView textView;
    private String url,header,userid;
    private String content;
    private Spinner sp1,sp2;
    private String selectedYear,selectedTerm;
    private String[] year,term;
    private ArrayAdapter<String> adapter1,adapter2;
    private Button btn_cx,btn_status;
    private boolean isCreate = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate = true;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_score,container,false);
        initView(view);
        //initData();
        //listView = (RecyclerView) view.findViewById(R.id.scoreList);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreate){
            return;
        }
        if (isVisibleToUser){
            initData();
            isCreate = false;
        }
    }

    public void initView(View v){
        sp1=(Spinner)v.findViewById(R.id.sp1);
        sp2=(Spinner)v.findViewById(R.id.sp2);
        btn_cx=(Button)v.findViewById(R.id.btn_cx);
        btn_status = (Button) v.findViewById(R.id.btn_status);
        //textView = (TextView) v.findViewById(R.id.textview);
        listView = (RecyclerView) v.findViewById(R.id.scoreList);
        progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
        //progressBar.setVisibility(View.GONE);
        //查询按钮事件
        btn_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //textView.setText("");
                //progressBar.setVisibility(View.VISIBLE);

                httpManager.postScore(selectedYear,selectedTerm,progressBar);
            }
        });
        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusScore();
            }
        });
    }

    private void statusScore(){
        if (list == null)
            return ;
        float sum_credit = 0;//学分总和
        float sum_gpa = 0;//绩点总和
        float score = 0;//分数
        float sum_score = 0;//分数总和
        String str_score = "";
        List<Integer> list_count = new ArrayList<Integer>();
        List<Float> list_credit = new ArrayList<Float>();
        List<String> list_type = new ArrayList<String>();
        int total_failed = 0;//补考数
        int restudy = 0;//重修数
        //去重
        List<ClassInfo> mlist = new ArrayList<ClassInfo>(list);
        for (int i=0;i<mlist.size();i++){
            String classID = mlist.get(i).getClassName();
            for (int j=mlist.size()-1;j>i;j--){
                if (classID.equals(mlist.get(j).getClassName())){
                    mlist.remove(j);
                    restudy++;
                }
            }
        }
        for (int i=0;i<mlist.size();i++){
            float credit = Float.parseFloat(mlist.get(i).getClassCredit());//学分
            float gpa = 0;//绩点
            //自动分类
            if (!list_type.contains(mlist.get(i).getClassType())) {
                list_type.add(mlist.get(i).getClassType());
                list_count.add(1);
                list_credit.add(Float.parseFloat(mlist.get(i).getClassCredit()));
            }else {
                int current_index = list_type.indexOf(mlist.get(i).getClassType());
                list_count.set(current_index,list_count.get(current_index)+1);
                list_credit.set(current_index,list_credit.get(current_index)+
                        Float.parseFloat(mlist.get(i).getClassCredit()));
            }
            sum_credit = sum_credit +credit;
            if (!mlist.get(i).getMakeupScore().isEmpty()) {
                str_score = mlist.get(i).getMakeupScore();
                if (Float.parseFloat(str_score)>=60) {
                    total_failed++;
                }
            }
            else {
                str_score = mlist.get(i).getScore();
            }
            switch (str_score){
                case "优秀":
                    score = 95f;
                    gpa = 4.5f;
                    break;
                case "良好":
                    score = 85f;
                    gpa = 3.5f;
                    break;
                case "中等":
                    score = 75f;
                    gpa = 2.5f;
                    break;
                case "及格":
                    score = 65f;
                    gpa = 1.5f;
                    break;
                case "不及格":
                    score = 0;
                    gpa = 0;
                    break;
                default:
                    score = Float.parseFloat(str_score);
                    if (score<60){
                        total_failed++;
                    }
                    //score = (float)((int)score/10-5+(int)score%10*0.1);
                    gpa = (float)((int)score/10-5+(int)score%10*0.1);
                    Log.i("score", score + "");
            }
            //gpa = score*credit;
            sum_score = sum_score + score;
            sum_gpa = sum_gpa+gpa*credit;//平均绩点=学分*单科绩点求和然后除以学分总和
        }
        //平均绩点
        float avg_gpa = sum_gpa/sum_credit;
        //平均分数
        float avg_score = sum_score/mlist.size();
                /*BigDecimal b   =   new   BigDecimal(avg_gpa);
                avg_gpa   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();*/
        String str_type = "";
        for (int i=0;i<list_type.size();i++){
            str_type = str_type + list_type.get(i)+":共"+list_count.get(i)+"门 学分:"+list_credit.get(i)+"\n";
        }
        new AlertDialog.Builder(getActivity())
                .setTitle("统计")
                .setMessage(str_type+"\n"
                        +"共"+mlist.size()+"门课"+"\n"
                        +"总学分:"+sum_credit+"\n"
                        +"平均分数:"+avg_score+"\n"
                        +"平均绩点:"+avg_gpa+"\n"
                        +"挂科数:"+total_failed+"\n"
                        +"重修数:"+restudy+"\n")
                .setPositiveButton("确定", null)
                .show();
    }

    public void initData(){
        httpManager = new HttpManager(getActivity(),requestListener());
        //httpManager.getScore(MyJsoup.getMainUrl(),progressBar);
        httpManager.postScore("","",progressBar);
    }

    public RequestListener requestListener(){
        return new RequestListener() {
            @Override
            public void requestSuccess(Object tag, String result) {
                if (tag.equals(Constant.TAG_SCORE)){
                    content = result;
                    if (content.isEmpty()) {
                        return;
                    }
                    updateSpinner();
                }
                if (tag.equals(Constant.TAG_POST_SCORE)){
                    Log.i("score=",result);
                    list = MyJsoup.getClassInfos(result);
                    ClassInfo title = list.remove(0);
                        /*for (int i=0;i<list.size();i++){
                            textView.append(list.get(i).toString());
                        }*/
                    //Toast.makeText(getActivity(),result.length()+"",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(),list.size()+"",Toast.LENGTH_SHORT).show();
                    listView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    listView.setAdapter(new ScoreListAdapter(getActivity(),list));
                    //progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void requestError(Object tag, VolleyError e) {

            }
        };
    }

    //更新下拉选框的数据
    public void updateSpinner(){
        year= MyJsoup.getSpinnerYear(content);
        term=MyJsoup.getSpinnerTerm(content);


        adapter1=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                year);
        adapter2=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                term);
        sp1.setAdapter(adapter1);
        sp2.setAdapter(adapter2);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedYear=year[i];
                Toast.makeText(getActivity(), selectedYear, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTerm=term[i];
                Toast.makeText(getActivity(),selectedTerm,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
