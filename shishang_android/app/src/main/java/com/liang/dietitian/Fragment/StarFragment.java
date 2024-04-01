package com.liang.dietitian.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.activity.DetailActivity;
import com.liang.dietitian.activity.HistoryActivity;
import com.liang.dietitian.adapter.HistoryAdapter;
import com.liang.dietitian.entity.FoodPojo;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StarFragment extends Fragment {
    private View view;

    private SwipeRecyclerView swipeRecyclerView;
    private HistoryAdapter adapter;

    private String result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_star, container, false);

        if (view != null) {
            initView();
        }


        return view;
    }

    private void initView() {

        swipeRecyclerView = view.findViewById(R.id.star_swipe_recycler_view);
        adapter = new HistoryAdapter();


//        设置监听器与绑定菜单
        swipeRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setText("删除")
                        .setBackground(R.color.red_200)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setWidth(getResources().getDisplayMetrics().widthPixels / 5)  //获取屏幕宽度，并除以5
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                rightMenu.addMenuItem(deleteItem);
            }
        });

//  菜单单击的监听器
        swipeRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                menuBridge.closeMenu();
//                historyBrowserDataBase.deleteData(sqLiteDatabase, adapter.getItem(adapterPosition).getToDetailSrc());
                doRequestForDelete(adapterPosition);
            }
        });

        swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                //                跳转到详情页
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("toDetailSrc", adapter.getItem(adapterPosition));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        swipeRecyclerView.setAdapter(adapter);
    }

    public void doRequestForDelete(Integer position) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(adapter.getItem(position)));
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/stars")
                .addHeader("token", token)
                .delete(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getActivity(), "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            //            请求成功时
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
//                  子线程更新UI
                    result = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                Integer code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                if (code == 200) {
                                    adapter.deleteFood(position);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
                                    Boolean loginFlag = sharedPreferences.getBoolean("flag", false);
                                    if (loginFlag == true) {
                                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });

    }

    public void doRequestForSelect() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/stars")
                .addHeader("token", token)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getActivity(), "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            //            请求成功时
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
//                  子线程更新UI
                    result = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                Integer code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                String data = jsonObject.getString("data");
                                Log.d("star", data);
                                List<FoodPojo> foods = JSON.parseArray(data, FoodPojo.class);

                                if (code == 200) {
                                    adapter.clearFood();
                                    for (FoodPojo f : foods) {
                                        adapter.addFood(f);
                                    }
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        Boolean loginFlag = sharedPreferences.getBoolean("flag", false);
        if (loginFlag == true) {
            adapter.clearFood();
            doRequestForSelect();
            adapter.notifyDataSetChanged();
        }

    }
}