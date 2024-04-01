package com.liang.dietitian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jaeger.library.StatusBarUtil;
import com.liang.dietitian.Fragment.HomeFragment;
import com.liang.dietitian.Fragment.LoginFragment;
import com.liang.dietitian.Utils.Config;
import com.liang.dietitian.activity.ChooseActivity;
import com.liang.dietitian.activity.HistoryActivity;
import com.liang.dietitian.activity.PublishActivity;
import com.next.easynavigation.view.EasyNavigationBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private EasyNavigationBar navigationBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    //第一次点击事件发生的时间
    private long mExitTime;

    private String[] tabText = {"首页", "我"};
    //未选中icon
    private int[] normalIcon = {R.drawable.ic_home, R.drawable.ic_person};
    //选中时icon
    private int[] selectIcon = {R.drawable.ic_home_red, R.drawable.ic_person_red};
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = findViewById(R.id.bottomNav);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);


//        设置沉浸式状态栏
        StatusBarUtil.setTransparentForDrawerLayout(MainActivity.this,drawerLayout);


        /*
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment()).commit();
        }

         */

        fragments.add(new HomeFragment());
        //fragments.add(new CookFragment());
        fragments.add(new LoginFragment());

        //配置参数
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .centerImageRes(R.drawable.ic_add)
                .fragmentManager(getSupportFragmentManager())
                .mode(EasyNavigationBar.NavigationMode.MODE_ADD)
                .centerIconSize(48)
                .iconSize(24)
                .centerLayoutBottomMargin(5)
                .build();

        //        底部导航栏点击监听事件
        navigationBar.setOnCenterTabClickListener(new EasyNavigationBar.OnCenterTabSelectListener() {
            @Override
            public boolean onCenterTabSelectEvent(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        navigationBar.getCenterImage().animate().rotationBy(180).setDuration(400);

                        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                        Boolean loginFlag = sharedPreferences.getBoolean("flag", false);
                        if (loginFlag == false) {
                            //旋转动画
                            Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        } else {
                            //                    跳转到发布笔记页面
                            Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                            startActivity(intent);
                        }


                    }
                });
                return false;
            }
        });

        /*
        //        底部导航栏点击监听事件
        navigationBar.setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {
            @Override
            public boolean onTabClickEvent(View view, int position) {
                if (position == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            navigationBar.getAddImage().animate().rotationBy(180).setDuration(400);

                            SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                            Boolean loginFlag = sharedPreferences.getBoolean("flag", false);
                            if (loginFlag == false) {
                                //旋转动画
                                Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            } else {
                                //                    跳转到发布笔记页面
                                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                                startActivity(intent);
                            }


                        }
                    });

                }
                return false;
            }
        });

         */

        //        侧滑菜单点击监听事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
//                    浏览历史
                    case R.id.menu_drawer_left_history:
                        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                        startActivity(intent);
                        break;
//                        退出登录
                    case R.id.menu_drawer_left_exit:
                        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);

//                       退出程序
                        finish();

                        break;
//                        随机推荐
                    case R.id.menu_drawer_left_choose:
                        intent = new Intent(MainActivity.this, ChooseActivity.class);
                        startActivity(intent);
                        break;


                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });

        /*  因使用EasyNavigationBar而过时
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_cook:
                        fragment = new CookFragment();
                        break;
                    case R.id.nav_choose:
                        fragment = new ChooseFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,fragment).commit();

                return true;
            }
        });
         */


    }

    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    //    两次返回键退出APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                //System.currentTimeMillis()系统当前时间
                mExitTime = System.currentTimeMillis();
            } else {
                /*
                SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("flag", false);
                editor.commit();
                 */

                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        /*
//        清除登录状态标志
        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("flag", false);
        editor.commit();

         */

        super.onDestroy();
    }




}