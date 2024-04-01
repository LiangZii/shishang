package com.liang.dietitian.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.liang.dietitian.Fragment.LoginFragment;
import com.liang.dietitian.MainActivity;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonalInformation extends AppCompatActivity implements View.OnClickListener
{
    private TextView tvname;
    private TextView tvpassword;
    private TextView tvQuit;
    private ImageView imageView;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        imageView=(ImageView)findViewById(R.id.imageView_head);
        tvname=(TextView) findViewById(R.id.tvname);
        tvpassword=(TextView) findViewById(R.id.tvpassword);
        tvQuit=(TextView) findViewById(R.id.textViewQuit);

        SharedPreferences share=getSharedPreferences("users",MODE_PRIVATE);

        String head=share.getString("head","");

        userid=share.getString("userid","");

//        Toast.makeText(this, head, Toast.LENGTH_LONG).show();


        Glide.with(this).load(Config.UrlHeader + "/headPhoto/" + head)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .placeholder(R.drawable.ic_default_image).into(imageView);




        tvname.setOnClickListener(this);
        tvpassword.setOnClickListener(this);
        tvQuit.setOnClickListener(this);
        imageView.setOnClickListener(this);

    }

    public void onClick(View v1){
        switch(v1.getId()){
            case R.id.tvname:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);
                // 获取编辑对话框中的控件
                EditText et_content = view.findViewById(R.id.et_content);
                Button btn_cancel = view.findViewById(R.id.btn_cancel);
                Button btn_save = view.findViewById(R.id.btn_save);
                TextView tvTop=view.findViewById(R.id.tvTop);
                tvTop.setText("修改用户名");
                TextView tvBottom=view.findViewById(R.id.tvBottom);
                tvBottom.setText("为自己起一个帅气的名字吧！");
                // 清除对话框原本的内容，并将自定义视图加载进对话框
                Dialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setContentView(view);
                // 使EditText可以唤起软键盘
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                // 添加自定义按钮的点击事件
                btn_cancel.setOnClickListener(v -> {
                    // 点击取消按钮执行的内容
                    dialog.dismiss();
                });
                btn_save.setOnClickListener(v -> {
                    // 点击确定按钮执行的内容
                    String name=et_content.getText().toString();

                    String url=Config.UrlHeader + "/users/updateNameById";

                    OkHttpClient client=new OkHttpClient();
                    //构建表单参数
                    FormBody.Builder requestBuild=new FormBody.Builder();
                    //添加请求体
                    RequestBody requestBody=requestBuild
                            .add("userId",userid)
                            .add("name",name)
                            .build();

                    Request request=new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    System.out.println(request.toString());
                    //异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //Toast.makeText(com.liang.dietitian.activity.PersonalInformation.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //editor.putString("password", login_password.getText().toString());
                            editor.putString("name", name);
                            editor.commit();
//                            Toast.makeText(PersonalInformation.this, "修改成功", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    });
                });
                break;
            case R.id.tvpassword:
                builder = new AlertDialog.Builder(this);
                view = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);
                // 获取编辑对话框中的控件
                et_content = view.findViewById(R.id.et_content);
                btn_cancel = view.findViewById(R.id.btn_cancel);
                btn_save = view.findViewById(R.id.btn_save);
                tvTop=view.findViewById(R.id.tvTop);
                tvTop.setText("修改密码");
                tvBottom=view.findViewById(R.id.tvBottom);
                tvBottom.setText("为自己设置一个安全的密码吧！");
                // 清除对话框原本的内容，并将自定义视图加载进对话框
                dialog = builder.create();
                dialog.show();
                dialog.getWindow().setContentView(view);
                // 使EditText可以唤起软键盘
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                // 添加自定义按钮的点击事件
                btn_cancel.setOnClickListener(v -> {
                    // 点击取消按钮执行的内容
                    dialog.dismiss();
                });
                btn_save.setOnClickListener(v -> {
                    // 点击确定按钮执行的内容
                    String password=et_content.getText().toString();

                    String url=Config.UrlHeader + "/users/updatePwdById";

                    OkHttpClient client=new OkHttpClient();
                    //构建表单参数
                    FormBody.Builder requestBuild=new FormBody.Builder();
                    //添加请求体
                    RequestBody requestBody=requestBuild
                            .add("userId",userid)
                            .add("password",password)
                            .build();

                    Request request=new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    System.out.println(request.toString());
                    //异步请求
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
//                            Toast.makeText(PersonalInformation.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("password", password);
                            //editor.putString("name", name);
                            editor.commit();
//                            Toast.makeText(PersonalInformation.this, "修改成功", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                    });
                });
                break;

            case R.id.textViewQuit:

                dialog = new AlertDialog.Builder(PersonalInformation.this)
                        .setTitle("提示")//设置对话框的标题
                        .setMessage("确认退出登录吗？")//设置对话框的内容
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
                                //清除保存的数据
                                SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();

                                Toast.makeText(PersonalInformation.this, "退出成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                //修改LoginFragment的界面UI
                                Intent intent = new Intent(PersonalInformation.this, LoginFragment.class);
                                setResult(100, intent);
                                finish();

                            }
                        }).create();
                dialog.show();
                break;

        }
    }

}