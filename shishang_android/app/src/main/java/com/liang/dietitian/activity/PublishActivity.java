package com.liang.dietitian.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.entity.FoodPojo;
import com.liang.dietitian.entity.Note;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.timonknispel.ktloadingbutton.KTLoadingButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishActivity extends AppCompatActivity {

    private Toolbar tool_bar_publish;
    private KTLoadingButton publish_button;

    private EditText note_publish_headline;
    private EditText note_publish_content;

    private TextView publish_to_detail;
    private CardView publish_card;

    private String result;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        initView();

        //      加载详情
        Intent intent = this.getIntent();
        note = (Note)intent.getSerializableExtra("note");

        if(note != null){
            note_publish_headline.setText(note.getTitle());
            note_publish_content.setText(note.getContent());

            if(note.getToDetailSrc() != null && note.getNoteId() != null){
                publish_card.setVisibility(View.VISIBLE);
            }
        }



        tool_bar_publish.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        publish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(note_publish_headline.getText().toString().equals("") || note_publish_content.getText().toString().equals("")){
                    Toast.makeText(PublishActivity.this, "请完善信息哦~", Toast.LENGTH_SHORT).show();
                    publish_button.doResult(false,null);
                }else {
                    publish_button.startLoading();
                    doRequestForPublish();
                }

            }
        });

        publish_to_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                跳转到详情页
                FoodPojo food = new FoodPojo();
                food.setText(note.getText());
                food.setIngredient(note.getIngredient());
                food.setToDetailSrc(note.getToDetailSrc());
                food.setScore(note.getScore());
                food.setImgUrl(note.getImgUrl());

                Intent intent = new Intent(PublishActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("toDetailSrc",food);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }

    private void initView() {
        tool_bar_publish = findViewById(R.id.tool_bar_publish);
        publish_button = findViewById(R.id.publish_button);
        note_publish_headline = findViewById(R.id.note_publish_headline);
        note_publish_content = findViewById(R.id.note_publish_content);

        publish_to_detail = findViewById(R.id.publish_to_detail);
        publish_card = findViewById(R.id.publish_card);

    }

    private void doRequestForPublish(){
        //        当前日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String today = format.format(date);

        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，

        if(note == null){
            note = new Note();
        }
        note.setTitle(note_publish_headline.getText().toString());
        note.setContent(note_publish_content.getText().toString());
        note.setPublishDate(today);
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(note));
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/notes")
                .addHeader("token",token)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PublishActivity.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                        publish_button.doResult(false,null);
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
                                if(code == 200){
                                    publish_button.doResult(true,null);
                                }else{
                                    Toast.makeText(PublishActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    publish_button.doResult(false,null);
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
}