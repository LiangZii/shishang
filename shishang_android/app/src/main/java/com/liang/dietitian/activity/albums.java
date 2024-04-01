package com.liang.dietitian.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.liang.dietitian.MainActivity;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.Utils.JsonUtil;
import com.liang.dietitian.activity.SearchActivity;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class albums extends Activity {
    private  ImageView albumsPicture;
    public static final int CHOOSE_PHOTO = 2;
    private List<String> ListStr1 = new ArrayList<>();
    private List<String> ListStrbaidu1 = new ArrayList<>();
    private List<String> ListStrbaidu2 = new ArrayList<>();
    private List<String> ListStrChinese = new ArrayList<>();
    private List<String> ListStrWestern1 = new ArrayList<>();
    private List<String> ListStrWestern2 = new ArrayList<>();
    private ListView lvsource,lv1,lv2,lvChinese,lvWestern1,lvWestern2;
    private TextView tv1,tv2,tvChinese,tvWestern,tvWestern1,tvWestern2;
    private Toolbar toolbar;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        albumsPicture = super.findViewById(R.id.picture);

        lvsource=(ListView)findViewById(R.id.lvsource);
        lv1=(ListView)findViewById(R.id.lv1);
        lv2=(ListView)findViewById(R.id.lv2);
        tv1=(TextView) findViewById(R.id.tv1);
        tv2=(TextView) findViewById(R.id.tv2);
        lvChinese=(ListView) findViewById(R.id.lvChinese);
        tvChinese=(TextView) findViewById(R.id.tvChinese);

        lvWestern1=(ListView) findViewById(R.id.lvWestern1);
        lvWestern2=(ListView) findViewById(R.id.lvWestern2);
        tvWestern=(TextView) findViewById(R.id.tvWestern);
        tvWestern1=(TextView) findViewById(R.id.tvWestern1);
        tvWestern2=(TextView) findViewById(R.id.tvWestern2);

        toolbar = findViewById(R.id.tool_bar_recognition);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
            finish();
        } else {
            openAlbum();
        }


    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //返回成功，请求码（对应启动时的requestCode）
        if(resultCode == RESULT_OK && CHOOSE_PHOTO == 2)
        {
            File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

            //方式二
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                //根据Uri获取流文件
                InputStream is = cr.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize =3;
                Bitmap bitmap = BitmapFactory.decodeStream(is,null,options);

                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

                    try {
                        outputImage = File.createTempFile(
                                "head",  /* prefix */
                                ".jpg",         /* suffix */
                                storageDir      /* directory */
                        );
                        FileOutputStream fos = new FileOutputStream(outputImage);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                albumsPicture.setImageBitmap(bitmap);
            }
            catch(Exception e) {
                Log.i("lyf", e.toString());
            }

            final String[] strings = {"百度", "自制模型——中餐","自制模型——西餐"};
            File finalOutputImage = outputImage;
            new AlertDialog.Builder(this)
                    .setTitle("请选择识别引擎")
                    .setSingleChoiceItems(new String[]{"百度", "自制模型——中餐","自制模型——西餐"}, 1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            LoadingDialog ld = new LoadingDialog(albums.this);
                            ld.setLoadingText("加载中")
                                    .setSuccessText("加载成功")//显示加载成功时的文字
                                    .setInterceptBack(true)
                                    .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)
                                    //.setRepeatCount()
                                    .show();

                            if (which == 0)
                            {
                                lvsource.setVisibility(View.GONE);
                                lv1.setVisibility(View.VISIBLE);
                                lv2.setVisibility(View.VISIBLE);
                                tv1.setVisibility(View.VISIBLE);
                                tv2.setVisibility(View.VISIBLE);

                                ///////////////////////////////////////////////////////////////图片请求
                                OkHttpClient client = new OkHttpClient();
                                if (!finalOutputImage.exists()) {
                                    Toast.makeText(getApplicationContext(), "文件不存在", Toast.LENGTH_SHORT).show();
                                } else {

                                    RequestBody muiltipartBody = new MultipartBody.Builder()
                                            //一定要设置这句
                                            .setType(MultipartBody.FORM)
                                            .addFormDataPart("photo", "output_image.jpg", RequestBody.create(MediaType.parse("image/*"), finalOutputImage))
                                            .build();
                                    final Request request = new Request.Builder()
                                            .url(Config.UrlHeader + "/baiduAI")
                                            .post(muiltipartBody)
                                            .build();


                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ld.loadFailed();
                                                    Toast.makeText(albums.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
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
                                                        ld.loadSuccess();
                                                        //                    显示“用料”组件
                                                        if (result != "") {
                                                            //mListStr.add(result);
//                                                            Toast.makeText(albums.this, result, Toast.LENGTH_SHORT).show();

                                                            ListStrbaidu1 = JsonUtil.JsonParseIngredient(result);
                                                            ListStrbaidu2 = JsonUtil.JsonParseMenu(result);


                                                            lv1.setAdapter(new ArrayAdapter<String>(albums.this, android.R.layout.simple_list_item_1, ListStrbaidu1));
                                                            lv2.setAdapter(new ArrayAdapter<String>(albums.this, android.R.layout.simple_list_item_1, ListStrbaidu2));

                                                            //为ListView控件的每个Item设置监听事件
                                                            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                                            {
                                                                @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                                                {
                                                                    String text= (String) lv1.getItemAtPosition(i);
                                                                    String[] xx=text.split("    ");
                                                                    String xx1=xx[1];
//                                                                    Toast.makeText(albums.this, text, Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(albums.this, SearchActivity.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putString("ingredient",xx1);
                                                                    intent.putExtras(bundle);
                                                                    startActivity(intent);

                                                                }
                                                            });
                                                            //为ListView控件的每个Item设置监听事件
                                                            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                                            {
                                                                @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                                                {
                                                                    String text= (String) lv2.getItemAtPosition(i);
                                                                    String[] xx=text.split("    ");
                                                                    String xx1=xx[1];
//                                                                    Toast.makeText(albums.this, text, Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(albums.this, SearchActivity.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putString("ingredient",xx1);
                                                                    intent.putExtras(bundle);
                                                                    startActivity(intent);

                                                                }
                                                            });

                                                        }

                                                    }
                                                });
                                            }
                                        }
                                    });
                                }


                            }

                            if(which == 1)
                            {
                                lvsource.setVisibility(View.GONE);
                                lv1.setVisibility(View.GONE);
                                lv2.setVisibility(View.GONE);
                                tv1.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                                tvChinese.setVisibility(View.VISIBLE);
                                lvChinese.setVisibility(View.VISIBLE);

                                ///////////////////////////////////////////////////////////////图片请求
                                OkHttpClient client = new OkHttpClient();
                                if (!finalOutputImage.exists()) {
                                    Toast.makeText(getApplicationContext(), "文件不存在", Toast.LENGTH_SHORT).show();
                                } else {

                                    RequestBody muiltipartBody = new MultipartBody.Builder()
                                            //一定要设置这句
                                            .setType(MultipartBody.FORM)
                                            .addFormDataPart("image", "output_image.jpg", RequestBody.create(MediaType.parse("image/*"), finalOutputImage))
                                            .build();
                                    final Request request = new Request.Builder()
                                            .url("http://8.130.102.219:5000/predict_chfood")
                                            .post(muiltipartBody)
                                            .build();


                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ld.loadFailed();
                                                    Toast.makeText(albums.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
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
                                                        ld.loadSuccess();
                                                        //                    显示“用料”组件
                                                        if (result != "") {
                                                            //tvChinese.setText(result);
//                                                            Toast.makeText(albums.this, result, Toast.LENGTH_SHORT).show();

                                                            ListStrChinese = JsonUtil.JsonParseChinese(result);


                                                            lvChinese.setAdapter(new ArrayAdapter<String>(albums.this, android.R.layout.simple_list_item_1, ListStrChinese));


                                                            //为ListView控件的每个Item设置监听事件
                                                            lvChinese.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                                            {
                                                                @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                                                {
                                                                    String text= (String) lvChinese.getItemAtPosition(i);
                                                                    String[] xx=text.split("    ");
                                                                    String xx1=xx[1];
//                                                                    Toast.makeText(albums.this, text, Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(albums.this, SearchActivity.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putString("ingredient",xx1);
                                                                    intent.putExtras(bundle);
                                                                    startActivity(intent);

                                                                }
                                                            });
                                                        }

                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }

                            if(which==2)
                            {
                                lvsource.setVisibility(View.GONE);
                                lv1.setVisibility(View.GONE);
                                lv2.setVisibility(View.GONE);
                                tv1.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                                tvChinese.setVisibility(View.GONE);
                                lvChinese.setVisibility(View.GONE);

                                tvWestern.setVisibility(View.VISIBLE);
                                tvWestern1.setVisibility(View.VISIBLE);
                                tvWestern2.setVisibility(View.VISIBLE);
                                lvWestern1.setVisibility(View.VISIBLE);
                                lvWestern2.setVisibility(View.VISIBLE);


                                ///////////////////////////////////////////////////////////////图片请求
                                OkHttpClient client = new OkHttpClient()
                                        .newBuilder()
                                        .connectTimeout(20, TimeUnit.SECONDS)//设置连接超时时间
                                        .readTimeout(20, TimeUnit.SECONDS)
                                        .build();//设置读取超时时间;

                                if (!finalOutputImage.exists()) {
                                    Toast.makeText(getApplicationContext(), "文件不存在", Toast.LENGTH_SHORT).show();
                                } else {

                                    RequestBody muiltipartBody = new MultipartBody.Builder()
                                            //一定要设置这句
                                            .setType(MultipartBody.FORM)
                                            .addFormDataPart("image", "output_image.jpg", RequestBody.create(MediaType.parse("image/*"), finalOutputImage))
                                            .build();
                                    final Request request = new Request.Builder()
                                            .url("http://8.130.102.219:5000/predict_enfood")
                                            .post(muiltipartBody)
                                            .build();


                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ld.loadFailed();
                                                    Toast.makeText(albums.this, "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
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
                                                        ld.loadSuccess();
                                                        //                    显示“用料”组件
                                                        if (result != "") {
                                                            //ListStrChinese.add(result);
//                                                            Toast.makeText(albums.this, result, Toast.LENGTH_SHORT).show();

                                                            String name=JsonUtil.JsonParseWesternName(result);
                                                            tvWestern.setText(name);
                                                            ListStrWestern1 = JsonUtil.JsonParseWestern1(result);
                                                            ListStrWestern2 = JsonUtil.JsonParseWestern2(result);


                                                            lvWestern1.setAdapter(new ArrayAdapter<String>(albums.this, android.R.layout.simple_list_item_1, ListStrWestern1));
                                                            lvWestern2.setAdapter(new ArrayAdapter<String>(albums.this, android.R.layout.simple_list_item_1, ListStrWestern2));

                                                            lvWestern1.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                                            {
                                                                @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                                                {
                                                                    String text= (String) lvWestern1.getItemAtPosition(i);

//                                                                    Toast.makeText(albums.this, text, Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(albums.this, SearchActivity.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putString("ingredient",text);
                                                                    intent.putExtras(bundle);
                                                                    startActivity(intent);

                                                                }
                                                            });


                                                        }

                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }

                            dialog.dismiss();//移除警告框

                        }
                    })
                    .show();



        }else{
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}