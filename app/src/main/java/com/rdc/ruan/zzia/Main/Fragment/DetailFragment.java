package com.rdc.ruan.zzia.Main.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rdc.ruan.zzia.Main.Interface.RequestListener;
import com.rdc.ruan.zzia.Main.R;
import com.rdc.ruan.zzia.Main.Utils.Constant;
import com.rdc.ruan.zzia.Main.Utils.Http.HttpManager;
import com.rdc.ruan.zzia.Main.Utils.Http.HttpUtil;
import com.rdc.ruan.zzia.Main.Utils.Http.MyJsoup;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String  mParam2;
    private HttpManager httpManager;
    private TextView tv_name,tv_sex,tv_num,tv_zy,tv_stdnum,tv_zzmm,tv_highSchool,tv_zkzh,tv_sfz,tv_phone;
    private ProgressBar progressBar;
    private ImageView image;
    private Map<String,String > map;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isCreate = false;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.activity_detail,
                container,false);
        //textView.setText(R.string.hello_blank_fragment);
        initView(swipeRefreshLayout);
        isCreate = true;
        initData();
        return swipeRefreshLayout;
    }
    public void initView(View v){
        image = (ImageView) v.findViewById(R.id.imageView1);
        tv_name=(TextView) v.findViewById(R.id.tv_name);
        tv_sex=(TextView) v.findViewById(R.id.tv_sex);
        tv_num=(TextView) v.findViewById(R.id.tv_num);
        tv_zy=(TextView) v.findViewById(R.id.tv_major);
        tv_stdnum=(TextView) v.findViewById(R.id.tv_exnum);
        tv_zzmm=(TextView) v.findViewById(R.id.tv_plst);
        tv_highSchool=(TextView) v.findViewById(R.id.tv_grsc);
        tv_zkzh=(TextView) v.findViewById(R.id.tv_ern);
        tv_sfz=(TextView) v.findViewById(R.id.tv_IDnum);
        tv_phone = (TextView) v.findViewById(R.id.tv_phnum);
        //progressBar = (ProgressBar) v.findViewById(R.id.progressbar_detail);
    }

    public void initData(){
        httpManager = new HttpManager(getActivity(),requestListener());
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                httpManager.getInfo(mParam1,mParam2);
            }
        });
    }

    public RequestListener requestListener(){
        return new RequestListener() {
            @Override
            public void requestSuccess(Object tag, String result) {
                if (tag.equals(Constant.TAG_DETAIL)){
                    Log.e("detail", result);
                    if (result.equals("")){
                        return ;
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    //progressBar.setVisibility(View.GONE);
                    map = MyJsoup.getDetail(result);
                    String url = HttpUtil.GetCookieUrl(MyJsoup.getMainUrl())+map.get("image");
                    if (!TextUtils.isEmpty(url)) {
                        Picasso.with(getActivity()).load(url).into(image);
                    }
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
                }
            }

            @Override
            public void requestError(Object tag, VolleyError e) {
                swipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        httpManager.getInfo(mParam1,mParam2);
        Log.e("getinfo","here");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
