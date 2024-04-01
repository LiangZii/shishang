package com.liang.dietitian.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.liang.dietitian.MainActivity;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.Utils.HistoryBrowserDataBase;
import com.liang.dietitian.Utils.JsonUtil;
import com.liang.dietitian.Utils.NetUtil;
import com.liang.dietitian.adapter.FoodDetailAdapter;
import com.liang.dietitian.entity.FoodPojo;
import com.liang.dietitian.entity.Note;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView food_detail_img;
    private TextView food_detail_ingredient_name;
    private ListView food_detail_ingredient;
    private ListView food_detail_method;
    private FoodDetailAdapter adapter;
    private List<String> foodIngredient;
    private Toolbar toolbar;

    private LikeButton star_button;

    private TextView publish_text;

    private FoodPojo foodPojo;
    private String toDetailSrc;
    private String result;

    private HistoryBrowserDataBase historyBrowserDataBase;
    private SQLiteDatabase sqLiteDatabaseForBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_detail);
        //        浏览历史
        historyBrowserDataBase = new HistoryBrowserDataBase(DetailActivity.this);
        sqLiteDatabaseForBrowser = historyBrowserDataBase.getWritableDatabase();

        initView();

//          不设置紫色
//        getWindow().setStatusBarColor(getResources().getColor(R.color.purple));

        /*因使用okhttp而过时
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
//                    网络请求成功处理
                    String result = (String) msg.obj;
//                    显示“用料”组件
                    if(result != ""){
                        food_detail_ingredient_name.setVisibility(View.VISIBLE);
                    }
                    //        设置图片
                    Glide.with(DetailActivity.this).load(JsonUtil.JsonParseFoodDetailImgSrc(result)).into(food_detail_img);
//        设置名字
                    toolbar.setTitle(JsonUtil.JsonParseFoodDetailName(result));
//        获取原料
                    foodIngredient = JsonUtil.JsonParseFoodDetailIngredient(result);
                    ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<>(DetailActivity.this, android.R.layout.simple_list_item_1, foodIngredient);
                    food_detail_ingredient.setAdapter(ingredientAdapter);
//        步骤
                    JsonUtil.JsonParseFoodDetailMethod(result, adapter);
                }
            }
        };

         */

        //        退出页面
        if (toolbar != null) {
            /*
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

             */

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }




//      加载详情
        Intent intent = this.getIntent();
//        toDetailSrc = intent.getStringExtra("toDetailSrc");
        foodPojo = (FoodPojo)intent.getSerializableExtra("toDetailSrc");
//         记录浏览历史
        historyBrowserDataBase.addData(sqLiteDatabaseForBrowser,foodPojo);
        toDetailSrc = foodPojo.getToDetailSrc();


        if (this.toDetailSrc != null) {
            doRequest(this.toDetailSrc);
        } else {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }

//        加载收藏按钮是否被点亮
        doRequestForSelect();

        //        图片放大
        food_detail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePreview.getInstance()
                        .setContext(DetailActivity.this)
                        .setImage(JsonUtil.JsonParseFoodDetailImgSrc(result))
                        .start();
            }
        });


//        收藏按钮监听事件
        star_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                Boolean loginFlag = sharedPreferences.getBoolean("flag", false);
                if (loginFlag == false) {
                    star_button.setLiked(false);
                    Toast.makeText(DetailActivity.this, "您还没有登录哦", Toast.LENGTH_SHORT).show();
                }else if(foodPojo != null){
                    doRequestForCollect();
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(foodPojo != null){
                    doRequestForDelete();
                }
            }
        });

//        发笔记标签监听事件
        publish_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                Boolean loginFlag = sharedPreferences.getBoolean("flag", false);
                if (loginFlag == false) {
                    Toast.makeText(DetailActivity.this, "您还没有登录哦", Toast.LENGTH_SHORT).show();
                }else{
                    //                跳转到发笔记页面
                    Note note = new Note();
                    note.setTitle(foodPojo.getText());

                    note.setText(foodPojo.getText());
                    note.setScore(foodPojo.getScore());
                    note.setIngredient(foodPojo.getIngredient());
                    note.setImgUrl(foodPojo.getImgUrl());
                    note.setToDetailSrc(foodPojo.getToDetailSrc());

                    Intent intent = new Intent(DetailActivity.this, PublishActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("note",note);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }

    private void initView() {
        food_detail_img = findViewById(R.id.food_detail_img);
        food_detail_ingredient_name = findViewById(R.id.food_detail_ingredient_name);
        food_detail_ingredient = findViewById(R.id.food_detail_ingredient);
        food_detail_method = findViewById(R.id.food_detail_method);

        star_button = findViewById(R.id.star_button);

        adapter = new FoodDetailAdapter(this);
        food_detail_method.setAdapter(adapter);

        toolbar = findViewById(R.id.tool_bar);

        publish_text = findViewById(R.id.publish_text);


    }

    public void doRequest(String toDetailSrc) {
        /*因使用okhttp而过时
        new Thread() {
            @Override
            public void run() {
                String result = "";
                result = NetUtil.doGet("http://101.43.243.221:8081/recipe/" + toDetailSrc);
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }.start();

         */

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(Config.UrlHeader + "/recipe/" + toDetailSrc)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            //            请求成功时
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
//                  子线程更新UI
                    result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //                    显示“用料”组件
                            if (result != "") {
                                food_detail_ingredient_name.setVisibility(View.VISIBLE);
                            }
                            //        设置图片
                            Glide.with(DetailActivity.this).load(JsonUtil.JsonParseFoodDetailImgSrc(result)).into(food_detail_img);
//        设置名字
                            toolbar.setTitle(JsonUtil.JsonParseFoodDetailName(result));
//        获取原料
                            foodIngredient = JsonUtil.JsonParseFoodDetailIngredient(result);
                            ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<>(DetailActivity.this, android.R.layout.simple_list_item_1, foodIngredient);
                            food_detail_ingredient.setAdapter(ingredientAdapter);
//        步骤
                            JsonUtil.JsonParseFoodDetailMethod(result, adapter);
                        }
                    });
                }

            }
        });

    }

    public void doRequestForCollect() {
        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(foodPojo));
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/stars")
                .addHeader("token",token)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            //            请求成功时
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
//                  子线程更新UI
                    result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                Integer code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                                if(code != 200){
                                    star_button.setLiked(false);
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

    public void doRequestForDelete() {
        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(foodPojo));
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/stars")
                .addHeader("token",token)
                .delete(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            //            请求成功时
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
//                  子线程更新UI
                    result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                Integer code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                                if(code != 200){
                                    star_button.setLiked(true);
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
        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/stars")
                .addHeader("token",token)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            //            请求成功时
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
//                  子线程更新UI
                    result = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                Integer code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                String data = jsonObject.getString("data");
                                Log.d("star", data);
                                List<FoodPojo> foods = JSON.parseArray(data,FoodPojo.class);

                                if(code == 200){
                                    for(FoodPojo f:foods){
                                        if(f.getToDetailSrc().equals(toDetailSrc)){
                                            star_button.setLiked(true);
                                            break;
                                        }
                                    }
                                }else{
                                    Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        super.onDestroy();

//        getWindow().setStatusBarColor(getResources().getColor(R.color.white));

    }
}