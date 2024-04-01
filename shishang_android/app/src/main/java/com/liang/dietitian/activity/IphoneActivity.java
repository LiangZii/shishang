package com.liang.dietitian.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.flyco.tablayout.SlidingTabLayout;
import com.liang.dietitian.Fragment.LoginFragment;
import com.liang.dietitian.Fragment.NoteFragment;
import com.liang.dietitian.Fragment.StarFragment;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IphoneActivity extends AppCompatActivity {

    private View view;
    private String result;

    private Toolbar toolbar;
    //所需申请的权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
    };

    EventHandler eventHandler;
    EditText editTextPhone,editTextNumber;
    Button get_code_id,login_id;
    Timer timer;
    int count = 60;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg) {
            int tag = msg.what;
            switch (tag){
                case 1:
                    int arg = msg.arg1;
                    if(arg==1){
                        get_code_id.setText("重新获取");//计时结束停止计时把值恢复
                        count = 60;
                        timer.cancel();
                        get_code_id.setEnabled(true);
                    }else{
                        get_code_id.setText(count+"");
                    }
                    break;
                case 2:
                    Toast.makeText(IphoneActivity.this,"获取短信验证码成功",Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Log.i("Codr","获取短信验证码失败");
                    Toast.makeText(IphoneActivity.this,msg.getData().getString("code"),Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this,"获取短信验证码失败",Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(IphoneActivity.this,msg.getData().getString("code"),Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }

        };
    };


//这是个什么
    private void initView() {
        toolbar = findViewById(R.id.tool_bar_phone);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNumber = findViewById(R.id.editTextNumber);
        get_code_id = findViewById(R.id.get_code_id);
        login_id = findViewById(R.id.login_id);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iphone);
        applypermission();
        MobSDK.submitPolicyGrantResult(true,null);
        init();

        initView();


//      toolBar导航返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



    //定义判断权限申请的函数，在onCreat中调用就行
    public void applypermission(){
        if(Build.VERSION.SDK_INT>=23){
            boolean needapply=false;
            for(int i=0;i<PERMISSIONS_STORAGE.length;i++){
                int chechpermission= ContextCompat.checkSelfPermission(getApplicationContext(),
                        PERMISSIONS_STORAGE[i]);
                if(chechpermission!= PackageManager.PERMISSION_GRANTED){
                    needapply=true;
                }
            }
            if(needapply){
                ActivityCompat.requestPermissions(IphoneActivity.this,PERMISSIONS_STORAGE,1);
            }
        }
    }

    //重载用户是否同意权限的回调函数，进行相应处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int i=0;i<grantResults.length;i++){      //检查权限是否获取
            if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                //同意后的操作
                //Toast.makeText(MainActivity.this, permissions[i]+"已授权",Toast.LENGTH_SHORT).show();//提示
            }
            else {
                //不同意后的操作
                //Toast.makeText(MainActivity.this,permissions[i]+"拒绝授权",Toast.LENGTH_SHORT).show();//提示

            }
        }
    }

    private void init(){
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNumber = findViewById(R.id.editTextNumber);
        get_code_id = findViewById(R.id.get_code_id);
        get_code_id.setOnClickListener(this::onClick);
        login_id = findViewById(R.id.login_id);
        login_id.setOnClickListener(this::onClick);

        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {// TODO 此处为子线程！不可直接处理UI线程！处理后续操作需传到主线程中操作！
                Log.i("返回:",event+" | "+result+" | "+data.toString());
                if (result == SMSSDK.RESULT_COMPLETE) {//成功回调
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交短信、语音验证码成功
                        /* 因提示信息重复而取消
                        Bundle bundle = new Bundle();
                        bundle.putString("code","登录成功！");
                        Message message = new Message();
                        message.what = 4;
                        message.setData(bundle);
                        handler.sendMessage(message);
                         */
                        doRequestForIphone();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {//获取语音验证码成功
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {//失败回调
                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){//提交返回
                        Bundle bundle = new Bundle();
                        bundle.putString("code","输入的验证码不正确！");
                        Message message = new Message();
                        message.what = 4;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("code",data.toString());
                        Message message = new Message();
                        message.what = 3;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }

                } else {//其他失败回调
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    //进行手机号验证,数据库查询name存在则直接跳转刷新，
    // 数据库查询不存在保存手机号为name跳转刷新，并将手机号和验证码保存为初始用户名和密码
    private void doRequestForIphone() {

        //        当前日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = format.format(date);

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = "{" +
                "\"userId\":" + "\"" + editTextPhone.getText().toString() + "\"," +
                "\"name\":" + "\"" + editTextPhone.getText().toString() + "\"," +
                "\"register\":" + "\"" + today + "\"" +
                "}";//json数据.

        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/users/loginByPhone")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(IphoneActivity.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
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
                            Intent intent = new Intent(IphoneActivity.this, LoginFragment.class);
                            intent.putExtra("userId", editTextPhone.getText().toString());
                            setResult(100, intent);

                            JSONObject jsonObject = new JSONObject(result);
                            Integer code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(IphoneActivity.this, msg, Toast.LENGTH_LONG).show();
                            if (code == 200) {
                                intent.putExtra("flag",true);


                                JSONObject object = jsonObject.getJSONObject("data");
                                String name = object.getString("name");
                                String head = object.getString("head");
                                String token = object.getString("token");

                                //保存数据
                                SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userid", editTextPhone.getText().toString());
                                editor.putString("name", name);
                                editor.putString("head", head);
                                editor.putString("token", token);
                                editor.putString("method", "phone");
                                editor.putLong("now",System.currentTimeMillis());
                                editor.putLong("expire",System.currentTimeMillis() + 2592000);
                                editor.putBoolean("flag", true);
                                editor.commit();


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




    private void onClick(View view) {
        String phone = "";
        String code = "";
        int id = view.getId();
        switch (id){
            case R.id.get_code_id:
                phone = editTextPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)){//倒计时
                    CountdownStart();
                    getVerificationCode("86",phone);
                }else{
                    Toast.makeText(this,"请输入手机号码",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.login_id:
                phone = editTextPhone.getText().toString().trim();
                code = editTextNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(this,"请输入手机号码",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(code)){
                    Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();
                }else{//登录逻辑
                    /**
                     * cn.smssdk.SMSSDK.class
                     * 提交验证码
                     * @param country   国家区号
                     * @param phone     手机号
                     * @param code      验证码
                     */
                    SMSSDK.submitVerificationCode("86",phone,code);//提交验证码

                }
                break;
        }
    }

    /**
     * cn.smssdk.SMSSDK.class
     * 请求文本验证码
     * @param country   国家区号
     * @param phone     手机号
     */
    public static void getVerificationCode(String country, String phone){
        //获取短信验证码
        SMSSDK.getVerificationCode(country,phone);
    }

    /**cn.smssdk.SMSSDK.class
     * 请求文本验证码(带模板编号)
     * @param tempCode  模板编号
     * @param country   国家区号
     * @param phone     手机号
     */
    public static void getVerificationCode(String tempCode,String country, String phone){
        //获取短信验证码
        SMSSDK.getVerificationCode(tempCode,country,phone);
    }

    //倒计时函数
    private void CountdownStart(){
        get_code_id.setEnabled(false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                message.arg1 = count;
                if(count!=0){
                    count--;
                }else {
                    return;
                }
                handler.sendMessage(message);
            }
        }, 1000,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// 使用完EventHandler需注销，否则可能出现内存泄漏
        SMSSDK.unregisterEventHandler(eventHandler);
    }

}

