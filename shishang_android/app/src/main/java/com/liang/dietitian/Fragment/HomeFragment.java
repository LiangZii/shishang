package com.liang.dietitian.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.liang.dietitian.MainActivity;
import com.liang.dietitian.R;
import com.liang.dietitian.activity.SearchActivity;
import com.liang.dietitian.activity.albums;
import com.liang.dietitian.activity.camera;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickClick;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private View view;
    private SearchView searchView;

    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton openMenu;

    private ArrayList<Fragment> fragments;
    private Button btn_camera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        if (view != null) {
            initView();
        }


        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //当得到焦点的时候执行
                if (hasFocus) {
                    searchView.clearFocus();
//                    跳转到搜索页面
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });

        return view;
    }

    private void setDialog() {
        Intent intent1 = new Intent(getActivity(), albums.class);//创建跳转到Albums显示的窗口的Intent2
        Intent intent2 = new Intent(getActivity(), camera.class);

        PickSetup setup = new PickSetup();
        PickImageDialog.build(setup)
                .setOnClick(new IPickClick() {
                    @Override
                    public void onGalleryClick() {
                        startActivity(intent1);//打开album的界面
                    }

                    @Override
                    public void onCameraClick() {
                        startActivity(intent2);
                    }
                }).show(getActivity());

    }

    public void initView() {
        searchView = view.findViewById(R.id.search_view);
//        清除焦点
        searchView.clearFocus();

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.vp);

        openMenu = view.findViewById(R.id.open_menu);

        btn_camera = view.findViewById(R.id.button_camera);


//        String[] nameList = new String[]{"推荐", "分类", "商城"};
        String[] nameList = new String[]{"推荐", "分类"};

        fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new SortFragment());
//        fragments.add(new MallFragment());

        tabLayout.setViewPager(viewPager, nameList, getActivity(), fragments);

    }


}