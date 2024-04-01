package com.liang.dietitian.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.GridViewWithHeaderAndFooter;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.Utils.JsonUtil;
import com.liang.dietitian.adapter.FoodHomeRecommendAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecommendFragment extends Fragment {
    private View view;
    private GridViewWithHeaderAndFooter recommendListView;
    private FoodHomeRecommendAdapter adapter;

    private PtrClassicFrameLayout ptrFrame;

    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recommend, container, false);

        if(view != null){
            initView();
        }

        /* 因使用Okhttp而过时
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
//                    网络请求成功处理
                    String result = (String) msg.obj;
                    JsonUtil.JsonParseFoodHomeRecommend(result, adapter);
                }
            }
        };

         */

        /* 过时
        recommendListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                    doRequest();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

         */



//        下拉刷新
        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                adapter.clearFood();
                doRequest();
                adapter.notifyDataSetChanged();
                ptrFrame.refreshComplete();
            }
        });

//        加载更多
        ptrFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                page++;
                doRequest();
                ptrFrame.loadMoreComplete(true);
            }
        });



        return view;
    }

    private void initView() {
        recommendListView = view.findViewById(R.id.food_home_recommend_list);

        adapter = new FoodHomeRecommendAdapter(getActivity());
        recommendListView.setAdapter(adapter);

        ptrFrame = view.findViewById(R.id.ptr_fresh);
//        设置开启加载更多  在这个位置开启会报错  放在了doRequest()函数里
//        ptrFrame.setLoadMoreEnable(true);

        doRequest();
    }

    public void doRequest() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(Config.UrlHeader + "/recommend?page=" + page)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                        if (Config.UrlHeader_1.equals(Config.UrlHeader)) {
                            Config.UrlHeader = Config.UrlHeader_2;
                            doRequest();
                        } else if (Config.UrlHeader_2.equals(Config.UrlHeader)) {
                            doRequest();
                            Config.UrlHeader = Config.UrlHeader_3;
                        }
                    }
                });
            }

            //            请求成功时
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
//                  子线程更新UI
                    String result = response.body().string();
                    Log.d("urlHeader", "onResponse: " + result);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.contains("error") && Config.UrlHeader_1.equals(Config.UrlHeader)) {
                                Config.UrlHeader = Config.UrlHeader_2;
                                doRequest();
                            } else if (result.contains("error") && Config.UrlHeader_2.equals(Config.UrlHeader)) {
                                doRequest();
                                Config.UrlHeader = Config.UrlHeader_3;
                            }
                            ptrFrame.setLoadMoreEnable(true);
                            JsonUtil.JsonParseFoodHomeRecommend(result, adapter);
                        }
                    });
                }

            }
        });

        /*
        因使用okhttp而过时
        new Thread() {
            @Override
            public void run() {
                String result = "";
                result = NetUtil.doGet("http://101.43.243.221:8081/recommend?page=" + page);
//                页码+1
                page++;
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }.start();

         */

    }
}