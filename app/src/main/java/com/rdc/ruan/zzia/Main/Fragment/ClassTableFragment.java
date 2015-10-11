package com.rdc.ruan.zzia.Main.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.rdc.ruan.zzia.Main.Adapter.ClassTableAdapter;
import com.rdc.ruan.zzia.Main.Interface.RequestListener;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.Constant;
import com.rdc.ruan.zzia.Main.Utils.Http.HttpManager;
import com.rdc.ruan.zzia.Main.Utils.Http.MyJsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassTableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean isCreate = false;

    private String selectedYear,selectedTerm;
    private Spinner sp1,sp2;
    private Button btn_cx;
    private String[] year,term;
    private RecyclerView listView;
    private ProgressBar progressBar;
    private List<Map<String,String>> list;
    private String[] from;
    private int[] to;

    private HttpManager httpManager;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassTableFragment newInstance(String param1, String param2) {
        ClassTableFragment fragment = new ClassTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ClassTableFragment() {
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
        View view = inflater.inflate(R.layout.activity_class_table,container,false);
        initView(view);
        return view;
    }
    public void initView(View view){
        sp1=(Spinner)view.findViewById(R.id.sp1);
        sp2=(Spinner)view.findViewById(R.id.sp2);
        btn_cx = (Button)view.findViewById(R.id.btn_cx);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        listView = (RecyclerView) view.findViewById(R.id.listview_table);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<Map<String, String>>();
        httpManager = new HttpManager(getActivity(),requestListener());
    }
    public void initData(){
        httpManager.getClasstable(progressBar);
        from = new String[] {"classid","name", "type", "teacher", "credit","time"};
        to = new int[] {R.id.class_number, R.id.class_name, R.id.class_character,
                R.id.class_teacher,R.id.class_score,R.id.class_time};
        btn_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpManager.postClasstable(selectedYear,selectedTerm,progressBar);
            }
        });
    }

    public RequestListener requestListener(){
        return new RequestListener() {
            @Override
            public void requestSuccess(Object tag, String result) {
                if (tag.equals(Constant.TAG_GET_CLASSTABLE)){
                    list = MyJsoup.getClassTable(result);
                    year = MyJsoup.getSpinnerYear(result);
                    term = MyJsoup.getSpinnerTerm(result);
                    sp1.setAdapter(new ArrayAdapter<String>(getActivity(),
                            R.layout.spannerlayout,
                            year));
                    sp2.setAdapter(new ArrayAdapter<String>(getActivity(),
                            R.layout.spannerlayout,
                            term));
                    int index_year=0,index_term=0;
                    for (int i = 0;i<year.length;i++){
                        if (year[i].equals(MyJsoup.getYearSelected(result))){
                            index_year = i;
                        }
                    }
                    for (int i = 0;i<term.length;i++){
                        if (year[i].equals(MyJsoup.getTermSelected(result))){
                            index_term = i;
                        }
                    }
                    sp1.setSelection(index_year);
                    sp2.setSelection(index_term);
                    selectedTerm = term[index_term];
                    selectedYear = year[index_year];
                    sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedYear = year[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedTerm = term[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                            list,R.layout.itemlayout,from,to);
                    listView.setAdapter(new ClassTableAdapter(getActivity(),
                            list));
                }
                if (tag.equals(Constant.TAG_POST_CLASSTABLE)){
                    list.clear();
                    list = MyJsoup.getClassTable((String)result);
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                            list,R.layout.itemlayout,from,to);
                    listView.setAdapter(new ClassTableAdapter(getActivity(),
                            list));
                }
            }

            @Override
            public void requestError(Object tag, VolleyError e) {

            }
        };
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
}
