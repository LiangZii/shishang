package com.liang.dietitian.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.Utils.HistoryBrowserDataBase;
import com.liang.dietitian.Utils.HistorySearchDataBase;
import com.liang.dietitian.Utils.JsonUtil;
import com.liang.dietitian.R;
import com.liang.dietitian.adapter.FoodListAdapter;
import com.liang.dietitian.entity.FoodPojo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView foodList;
    private FoodListAdapter adapter;

    private TagContainerLayout tagContainerLayout;

    private LinearLayout searchHistoryLinearLayout;
    private ScrollView searchList;
    private TextView searchHistoryClear;

    private HistorySearchDataBase historySearchDataBase;
    private SQLiteDatabase sqLiteDatabase;
    private HistoryBrowserDataBase historyBrowserDataBase;
    private SQLiteDatabase sqLiteDatabaseForBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        搜索历史
        historySearchDataBase = new HistorySearchDataBase(SearchActivity.this);
        sqLiteDatabase = historySearchDataBase.getWritableDatabase();
//        浏览历史
        historyBrowserDataBase = new HistoryBrowserDataBase(SearchActivity.this);
        sqLiteDatabaseForBrowser = historyBrowserDataBase.getWritableDatabase();

        initView();

        /*
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
//                    网络请求成功，处理数据
                    String result = (String) msg.obj;
                    JsonUtil.JsonParseFoodList(result,adapter);
                }
            }
        };

         */

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            当搜索按钮被点击时触发此事件
            @Override
            public boolean onQueryTextSubmit(String query) {
//                清除焦点，收回软键盘
                searchView.clearFocus();
                adapter.clearFood();
                doRequest();

                return false;
            }

            //            当搜索内容发生改变时触发此事件
            @Override
            public boolean onQueryTextChange(String newText) {
//                显示搜索历史界面
                if (newText.equals("")) {
                    searchList.setVisibility(View.GONE);
                    searchHistoryLinearLayout.setVisibility(View.VISIBLE);

//                    刷新列表
                    refreshSearchHistory();
                }
                return false;
            }
        });

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                记录浏览历史
//                historyBrowserDataBase.addData(sqLiteDatabaseForBrowser,adapter.getItem(i));

//                跳转到详情页
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("toDetailSrc",adapter.getItem(i));
//                bundle.putString("toDetailSrc", adapter.getItem(i).getToDetailSrc());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        searchHistoryClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("提示")//设置对话框的标题
                        .setMessage("确认清空历史记录吗？")//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                historySearchDataBase.clearData(sqLiteDatabase);
                                refreshSearchHistory();
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        refreshSearchHistory();

//标签点击事件监听
        tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
//                Toast.makeText(SearchActivity.this, "点击 " + text, Toast.LENGTH_SHORT).show();
                searchView.setQuery(text, true);
                searchView.clearFocus();

                doRequest();
            }

            @Override
            public void onTagLongClick(int position, String text) {
//                Toast.makeText(SearchActivity.this, "长按  " + text, Toast.LENGTH_SHORT).show();

                AlertDialog dialog = new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("提示")//设置对话框的标题
                        .setMessage("确认删除该条历史记录吗？")//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                historySearchDataBase.deleteData(sqLiteDatabase,text);
                                refreshSearchHistory();
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });

    }

    //    初始化组件
    public void initView() {
        searchView = findViewById(R.id.search_view);
        searchView.setFocusable(true);
        searchView.setFocusableInTouchMode(true);
        searchView.requestFocus();

        foodList = findViewById(R.id.food_list);

        tagContainerLayout = findViewById(R.id.search_history_tag_container);

        searchHistoryLinearLayout = findViewById(R.id.search_history_linearLayout);
        searchList = findViewById(R.id.search_list);

        searchHistoryClear = findViewById(R.id.search_history_clear);

        adapter = new FoodListAdapter(this);
        foodList.setAdapter(adapter);

//        判断是否是“出餐”fragment跳转
        Intent intent = this.getIntent();
        String receive_str = intent.getStringExtra("ingredient");
        if (receive_str != null) {
            searchView.setQuery(receive_str, true);
            searchView.clearFocus();

            doRequest();
        }

    }

    public void doRequest() {
//        隐藏搜索历史
        searchList.setVisibility(View.VISIBLE);
        searchHistoryLinearLayout.setVisibility(View.GONE);

//        记录搜索历史
        List<String> history = new ArrayList<>();
        history = historySearchDataBase.selectData(sqLiteDatabase);
        if(history.contains(searchView.getQuery().toString())){
//            先删除后添加，改变该条历史得存放数据
            historySearchDataBase.deleteData(sqLiteDatabase,searchView.getQuery().toString());
            historySearchDataBase.addData(sqLiteDatabase,searchView.getQuery().toString());
        }else{
            historySearchDataBase.addData(sqLiteDatabase,searchView.getQuery().toString());
        }

        String url = Config.UrlHeader + "/search?KeyWord=";
        String commitText = "";
        try {
            commitText = URLEncoder.encode(searchView.getQuery().toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url + commitText)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SearchActivity.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            //            请求成功时
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
//                  子线程更新UI
                    String result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //                    显示“用料”组件
                            if (result != "") {
                                JsonUtil.JsonParseFoodList(result, adapter);
                            }

                        }
                    });
                }
            }
        });


/*
        new Thread() {
            @Override
            public void run() {
                String url = "http://101.43.243.221:8081/search?KeyWord=";
                String commitText = "";
                try {
                    commitText = URLEncoder.encode(searchView.getQuery().toString(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String result = NetUtil.doGet(url + commitText);
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }.start();

 */


    }

    private void refreshSearchHistory() {

//添加数据
        List<String> history = new ArrayList<>();
//        String child = "白菜油菜芹菜菠菜小白菜韭菜 生菜 茼蒿香菜芦笋菜花西兰花 苦菊 土豆 红薯芋头胡萝卜 白萝卜 山药藕豆角茄子 青椒 西红柿 荷兰豆 黄瓜 冬瓜 南瓜 丝瓜 彩椒 蘑菇 草菇 香菇 金针菇 银耳 木耳 洋葱 小葱 大葱";
//        history.addAll(Arrays.asList(child.split(" ")));
        history = historySearchDataBase.selectData(sqLiteDatabase);

//添加数据关键代码
        tagContainerLayout.setTags(history);
    }


}