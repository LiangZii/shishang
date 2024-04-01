package com.liang.dietitian.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liang.dietitian.Fragment.LoginFragment;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private MaterialEditText register_username;
    private MaterialEditText register_userid;
    private MaterialEditText register_password;
    private MaterialEditText register_password_confirm;

    private Button register_button;

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

//      toolBar导航返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (register_username.getText().toString().equals("") || register_userid.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
                } else if (!register_password.getText().toString().equals(register_password_confirm.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    doRequest();
                }
            }
        });

    }

    private void initView() {
        toolbar = findViewById(R.id.tool_bar_register);

        register_username = findViewById(R.id.register_username);
        register_userid = findViewById(R.id.register_userid);
        register_password = findViewById(R.id.register_password);
        register_password_confirm = findViewById(R.id.register_password_confirm);
        register_button = findViewById(R.id.register_button);

    }

    private void doRequest() {
//        当前日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = format.format(date);

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = "{" +
                "\"userId\":" + "\"" + register_userid.getText().toString() + "\"," +
                "\"name\":" + "\"" + register_username.getText().toString() + "\"," +
                "\"password\":" + "\"" + register_password.getText().toString() + "\"," +
                "\"register\":" + "\"" + today + "\"" +
                "}";//json数据.

        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/users/register")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                获取返回结果
                result = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Intent intent = new Intent(RegisterActivity.this, LoginFragment.class);
                            intent.putExtra("userId", register_userid.getText().toString());
                            setResult(100, intent);

                            JSONObject jsonObject = new JSONObject(result);
                            Integer code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
                            if (code == 200) {
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


    }
}