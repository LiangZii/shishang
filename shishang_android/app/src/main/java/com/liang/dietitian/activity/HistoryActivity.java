package com.liang.dietitian.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.liang.dietitian.R;
import com.liang.dietitian.Utils.HistoryBrowserDataBase;
import com.liang.dietitian.adapter.HistoryAdapter;
import com.liang.dietitian.entity.FoodPojo;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;


public class HistoryActivity extends AppCompatActivity {
    private SwipeRecyclerView swipeRecyclerView;
    private HistoryAdapter adapter;
    private Toolbar toolbar;

    private HistoryBrowserDataBase historyBrowserDataBase;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyBrowserDataBase = new HistoryBrowserDataBase(HistoryActivity.this);
        sqLiteDatabase = historyBrowserDataBase.getWritableDatabase();

        initView();

        refreshBrowserHistory();

//        退出页面
        if (toolbar != null) {
            //只有点击左侧图标时有效
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            //        toolBar的menu点击事件
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_history_clear:
                            AlertDialog dialog = new AlertDialog.Builder(HistoryActivity.this)
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
                                            historyBrowserDataBase.clearData(sqLiteDatabase);
                                            adapter.clearFood();
                                            adapter.notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    }).create();
                            dialog.show();
                            break;
                    }

                    return false;
                }
            });
        }


    }

    private void initView() {


        toolbar = findViewById(R.id.tool_bar_history);
//        显示菜单 右上角
        toolbar.inflateMenu(R.menu.menu_history);

        swipeRecyclerView = findViewById(R.id.swipe_recycler_view);

        adapter = new HistoryAdapter();


//        设置监听器与绑定菜单
        swipeRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(HistoryActivity.this);
                deleteItem.setText("删除")
                        .setBackground(R.color.red_200)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setWidth(getResources().getDisplayMetrics().widthPixels / 5)  //获取屏幕宽度，并除以5
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                rightMenu.addMenuItem(deleteItem);
            }
        });
//  菜单单击的监听器
        swipeRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                menuBridge.closeMenu();
                historyBrowserDataBase.deleteData(sqLiteDatabase, adapter.getItem(adapterPosition).getToDetailSrc());
                adapter.deleteFood(adapterPosition);
                adapter.notifyDataSetChanged();
            }
        });

        swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                //                跳转到详情页
                Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("toDetailSrc", adapter.getItem(adapterPosition));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        swipeRecyclerView.setAdapter(adapter);


    }


    private void refreshBrowserHistory() {
        adapter.clearFood();
        List<FoodPojo> foodPojos = historyBrowserDataBase.selectData(sqLiteDatabase);
        for (FoodPojo food : foodPojos) {
            adapter.addFood(food);
        }
        adapter.notifyDataSetChanged();
    }
}