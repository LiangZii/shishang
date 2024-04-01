package com.liang.dietitian.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.Utils.JsonUtil;
import com.liang.dietitian.Utils.NetUtil;
import com.liang.dietitian.adapter.FoodHomeRecommendAdapter;
import com.liang.dietitian.entity.FoodPojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChooseActivity extends AppCompatActivity {
    static int flag = 1;
    Timer timer = null;
    Handler handler = null;
    private FoodHomeRecommendAdapter adapter;
    private FoodPojo foodPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Button btn = findViewById(R.id.button);
        TextView t = findViewById(R.id.textView);
        TextView tips = findViewById(R.id.tipsText);
        ImageView z = findViewById(R.id.ImageView);
        Toolbar toolbar = findViewById(R.id.tool_bar_choose);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    /*
                    Bitmap bitmap = (Bitmap) msg.obj;
                    z.setImageBitmap(bitmap);
                    String result = (String) msg.obj;
                    JsonUtil.JsonParseFoodHomeRecommend(result,adapter);

                     */
                    doRequest();
                } else if (msg.what == 0) {
                    String result = (String) msg.obj;
                    Glide.with(ChooseActivity.this).asBitmap().load(JsonUtil.JsonParseFoodImg(result)).placeholder(R.drawable.loading).into(z);
                    t.setText(JsonUtil.JsonParseFoodDetailName(result));
                    try {
                        foodPojo = new FoodPojo();
                        JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
                        foodPojo.setText(jsonObject.getString("name"));
                        foodPojo.setToDetailSrc(jsonObject.getString("src"));
                        foodPojo.setImgUrl(jsonObject.getString("img"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = findViewById(R.id.button);
                if (flag == 1) {
                    v.setBackgroundColor(Color.parseColor("#FF646464"));
                    b.setText("点击停下");
                    tips.setVisibility(View.INVISIBLE);
                    flag = 0;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message message = Message.obtain();
                            message.what = 1;
                            handler.sendMessage(message);

                        }
                    }, 0, 200);
                } else {
                    v.setBackgroundColor(getResources().getColor(R.color.red));
                    b.setText("随机推荐");
                    tips.setVisibility(View.VISIBLE);
                    flag = 1;
                    timer.cancel();
                }
            }
        });

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t.getText() != "") {
                    //                跳转到详情页
                    Intent intent = new Intent(ChooseActivity.this, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("toDetailSrc",foodPojo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

    }

    public void doRequest() {

        new Thread() {
            @Override
            public void run() {
                String result = "";
                result = NetUtil.doGet(Config.UrlHeader + "/random");
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }.start();
    }


}