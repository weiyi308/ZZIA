package com.rdc.ruan.zzia.Main.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rdc.ruan.zzia.Main.Adapter.CetAdapter;
import com.rdc.ruan.zzia.Main.Interface.RequestListener;
import com.rdc.ruan.zzia.Main.MyApp;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.Constant;
import com.rdc.ruan.zzia.Main.Utils.Http.HttpManager;
import com.rdc.ruan.zzia.Main.Utils.Http.MyJsoup;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CetFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean isCreate = false;

    private TextView textView;

    private HttpManager httpManager;
    private List<Map<String,String>> list;
    private String[] from;
    private int[] to;
    private RecyclerView listView;
    private SwipeRefreshLayout swipeRefreshLayout;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CetFragment newInstance(String param1, String param2) {
        CetFragment fragment = new CetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CetFragment() {
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
        swipeRefreshLayout =
                (SwipeRefreshLayout) inflater.inflate(R.layout.activity_cet,container,false);
        initView(swipeRefreshLayout);
        return swipeRefreshLayout;
    }
    public void initView(View v){
        //textView=(TextView)v.findViewById(R.id.textview);
        listView = (RecyclerView) v.findViewById(R.id.cet_listview);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        httpManager = new HttpManager(getActivity(),requestListener());
    }
    public void initData(){
        from = new String[]{"year","term","name","time","score"};
        to = new int[]{R.id.tv_cet_year,R.id.tv_cet_term,R.id.tv_cet_name,R.id.tv_cet_time,R.id.tv_cet_score};
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                httpManager.getCet();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public RequestListener requestListener(){
        return new RequestListener() {
            @Override
            public void requestSuccess(Object tag, String result) {
                if (tag.equals(Constant.TAG_CET)){
                    swipeRefreshLayout.setRefreshing(false);
                    list = MyJsoup.getCet(result);
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                            list,R.layout.cetitem,from,to);
                    listView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    listView.setAdapter(new CetAdapter(getActivity(),list));
                }
                if (tag.equals(Constant.TAG_LOGIN)){

                }
            }

            @Override
            public void requestError(Object tag, VolleyError e) {
                String msg =e.getMessage();
                if (msg.equals("reconnect")){
                    httpManager.login(MyApp.getUser(),MyApp.getPassword());
                }
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

    @Override
    public void onRefresh() {
        httpManager.getCet();
    }
}
