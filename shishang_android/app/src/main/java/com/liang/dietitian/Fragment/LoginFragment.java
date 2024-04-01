package com.liang.dietitian.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.SlidingTabLayout;
import com.liang.dietitian.MainActivity;
import com.liang.dietitian.R;
import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.activity.IphoneActivity;
import com.liang.dietitian.activity.PersonalInformation;
import com.liang.dietitian.activity.RegisterActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginFragment extends Fragment {

    private View view;

    private TextView register_tip;

    private ImageButton loginWechat;
    private ImageButton loginQQ;
    private ImageButton loginPhone;

    private MaterialEditText login_userid;
    private MaterialEditText login_password;

    private LinearLayout login_status_yes;
    private LinearLayout login_status_no;
    private Toolbar tool_bar_login;
    private SuperTextView login_user_info;
    private Button login_button;

    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;

    private String result;

    private StarFragment starFragment;
    private NoteFragment noteFragment;

    private static final int REGISTER_OK = 100;
    private static final int IMAGE_PICKER = 200;
    private static final int REGISTER_BY_PHONE_OK = 300;
    private static final int INFORMATION_EXIT = 400;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        if (view != null) {
            initView();
        }

        autoLogin();


//        跳转到注册页面
        register_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, REGISTER_OK);
            }
        });

        loginWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "暂未开通", Toast.LENGTH_SHORT).show();
            }
        });
        loginQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "暂未开通", Toast.LENGTH_SHORT).show();
            }
        });

        /*loginPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "暂未开通", Toast.LENGTH_SHORT).show();
            }
        });*/

        //跳转到手机验证界面
        loginPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IphoneActivity.class);
                startActivityForResult(intent, REGISTER_BY_PHONE_OK);

            }
        });


//        登录按钮
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_userid.getText().toString().equals("") || login_password.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "请完善信息", Toast.LENGTH_SHORT).show();
                } else {
                    doRequestForLogin();
                }
            }
        });
//        打开抽屉菜单
        tool_bar_login.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        login_user_info.setLeftImageViewClickListener(new SuperTextView.OnLeftImageViewClickListener() {
            @Override
            public void onClickListener(ImageView imageView) {
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);


            }
        });
//        跳转到个人信息页面
        login_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalInformation.class);
                startActivityForResult(intent,INFORMATION_EXIT);
            }
        });


        return view;
    }


    private void initView() {
        register_tip = view.findViewById(R.id.register_tip);

        loginWechat = view.findViewById(R.id.login_wechat);
        loginQQ = view.findViewById(R.id.login_qq);
        loginPhone = view.findViewById(R.id.login_phone);

        login_userid = view.findViewById(R.id.login_userid);
        login_password = view.findViewById(R.id.login_password);


        login_status_yes = view.findViewById(R.id.login_status_yes);
        login_status_no = view.findViewById(R.id.login_status_no);
        tool_bar_login = view.findViewById(R.id.tool_bar_login);
        login_user_info = view.findViewById(R.id.login_user_info);

        login_button = view.findViewById(R.id.login_button);

        tabLayout = view.findViewById(R.id.login_tabs);
        viewPager = view.findViewById(R.id.login_vp);

        String[] nameList = new String[]{"收藏", "笔记"};

        fragments = new ArrayList<>();
        starFragment = new StarFragment();
        noteFragment = new NoteFragment();
        fragments.add(starFragment);
        fragments.add(noteFragment);

        tabLayout.setViewPager(viewPager, nameList, getActivity(), fragments);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_OK && resultCode == 100) {
            if (data != null) {
                login_userid.setText(data.getStringExtra("userId"));
                //设置可焦
                login_password.setFocusable(true);
                //触摸效果获取焦点
                login_password.setFocusableInTouchMode(true);
                //获取焦点
                login_password.requestFocus();
            }

        } else if (requestCode == IMAGE_PICKER && resultCode == ImagePicker.RESULT_CODE_ITEMS) {

            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

                ImageItem imageItem = images.get(0);
                doRequestForUpdateHead(String.valueOf(imageItem.uri).substring(5));

            } else {
                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == REGISTER_BY_PHONE_OK && resultCode == 100) {
            if (data.getBooleanExtra("flag", false) == true) {
                login_status_yes.setVisibility(View.VISIBLE);
                login_status_no.setVisibility(View.GONE);
//               让收藏和笔记页面进行刷新显示
//                ((StarFragment) starFragment).doRequestForSelect();
//                ((NoteFragment) noteFragment).doRequestForSelect();
                doRequestForStarAndNoteNum();
            }
        }else if(requestCode == INFORMATION_EXIT && resultCode == 100){
//            切换界面布局
            exitUser();
        }
    }

    private void doRequestForLogin() {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = "{" +
                "\"userId\":" + "\"" + login_userid.getText().toString() + "\"," +
                "\"password\":" + "\"" + login_password.getText().toString() + "\"" +
                "}";//json数据.

        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/users/login")
                .post(body)
                .build();
        System.out.println(request.url().url().toString());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                获取返回结果
                result = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("login", result);
                            JSONObject jsonObject = new JSONObject(result);
                            Integer code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");

                            if (code == 200) {
                                login_status_yes.setVisibility(View.VISIBLE);
                                login_status_no.setVisibility(View.GONE);

                                JSONObject object = jsonObject.getJSONObject("data");
                                String name = object.getString("name");
                                String head = object.getString("head");
                                String token = object.getString("token");

                                login_user_info.setLeftTopString(name);
                                Glide.with(getActivity()).load(Config.UrlHeader + "/headPhoto/" + head)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.drawable.ic_default_image).into(login_user_info.getLeftIconIV());


//保存数据
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userid", login_userid.getText().toString());
                                editor.putString("password", login_password.getText().toString());
                                editor.putString("name", name);
                                editor.putString("head", head);
                                editor.putString("token", token);
                                editor.putString("method", "normal");
                                editor.putLong("now",System.currentTimeMillis());
                                editor.putLong("expire",System.currentTimeMillis() + 2592000);
                                editor.putBoolean("flag", true);
                                editor.commit();

//                                让收藏和笔记页面进行刷新显示
//                                ((StarFragment) starFragment).doRequestForSelect();
//                                ((NoteFragment) noteFragment).doRequestForSelect();
                                doRequestForStarAndNoteNum();

                            } else {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                                login_status_no.setVisibility(View.VISIBLE);
                                login_status_yes.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

    private void doRequestForUpdateHead(String path) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (token.equals("")) {
            Toast.makeText(getActivity(), "异常，请退出重新登录", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        File file = new File(path);//file对象.
        File file = new File(path);//file对象.


        bodyBuilder.addFormDataPart("photo", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/users/updateHead")
                .addHeader("token", token)
                .post(bodyBuilder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("headPhoto", "onFailure: " + e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getActivity(), "服务器状态异常，请稍后重试", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                获取返回结果
                result = response.body().string();
                Log.d("headPhoto", "onResponse result: " + result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            Integer code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            String head = jsonObject.getString("data");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            if (code == 200) {
                                Glide.with(getActivity()).load(Config.UrlHeader + "/headPhoto/" + head)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.drawable.ic_default_image).into(login_user_info.getLeftIconIV());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void doRequestForStarAndNoteNum() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        Request request = new Request.Builder()
                .url(Config.UrlHeader + "/users/number")
                .addHeader("token", token)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                获取返回结果
                result = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            Integer code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            String data = jsonObject.getString("data");
                            if (code == 200) {
                                login_user_info.setLeftBottomString(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void autoLogin() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        Long now = sharedPreferences.getLong("now",0);
        Long expire = sharedPreferences.getLong("expire",0);
        String userId = sharedPreferences.getString("userid", "");
        String name = sharedPreferences.getString("name", "");
        String head = sharedPreferences.getString("head", "");

        if(now < expire){
//            登录未过期
            login_status_yes.setVisibility(View.VISIBLE);
            login_status_no.setVisibility(View.GONE);
            login_user_info.setLeftTopString(name);
            Glide.with(getActivity()).load(Config.UrlHeader + "/headPhoto/" + head)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .placeholder(R.drawable.ic_default_image).into(login_user_info.getLeftIconIV());


            //                                让收藏和笔记页面进行刷新显示
//            ((StarFragment) starFragment).doRequestForSelect();
//            ((NoteFragment) noteFragment).doRequestForSelect();
            doRequestForStarAndNoteNum();
        }else {
            if(!userId.equals("")){
                Toast.makeText(getActivity(),"登录过期，请重新登录",Toast.LENGTH_SHORT).show();
            }
//            登录过期
            login_status_yes.setVisibility(View.GONE);
            login_status_no.setVisibility(View.VISIBLE);
        }

        /*
        String userId = sharedPreferences.getString("userid", "");
        String password = sharedPreferences.getString("password", "");

        if (!userId.equals("") && !password.equals("")) {
            login_userid.setText(userId);
            login_password.setText(password);
            doRequestForLogin();
        }

         */
    }

    //    退出登录更换界面UI
    public void exitUser() {
        login_userid.setText("");
        login_password.setText("");
        login_status_no.setVisibility(View.VISIBLE);
        login_status_yes.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String head = sharedPreferences.getString("head", "");

        login_user_info.setLeftTopString(name);
        Glide.with(getActivity()).load(Config.UrlHeader + "/headPhoto/" + head)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .placeholder(R.drawable.ic_default_image).into(login_user_info.getLeftIconIV());
        doRequestForStarAndNoteNum();
    }


}