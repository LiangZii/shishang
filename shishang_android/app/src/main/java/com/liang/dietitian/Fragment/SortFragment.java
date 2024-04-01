package com.liang.dietitian.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.liang.dietitian.R;
import com.liang.dietitian.activity.SearchActivity;
import com.liang.dietitian.entity.FilterType;
import com.liang.dietitian.entity.FilterUrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SortFragment extends Fragment {
    private View view;

    private DoubleListView doubleListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sort, container, false);

        if (view != null) {
            initView();
        }

        doubleListView = view.findViewById(R.id.double_list);
        doubleListView.leftAdapter(new SimpleTextAdapter<FilterType>(null, getActivity()) {
                    @Override
                    public String provideText(FilterType filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(getActivity(), 44), UIUtil.dp(getActivity(), 15), 0, UIUtil.dp(getActivity(), 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<String>(null, getActivity()) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(getActivity(), 30), UIUtil.dp(getActivity(), 15), 0, UIUtil.dp(getActivity(), 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, String>() {
                    @Override
                    public List<String> provideRightList(FilterType item, int position) {
                        List<String> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            FilterUrl.instance().doubleListLeft = item.desc;
                            FilterUrl.instance().doubleListRight = "";

                            FilterUrl.instance().position = 0;
                            FilterUrl.instance().positionTitle = item.desc;

                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, String>() {
                    @Override
                    public void onRightItemClick(FilterType item, String string) {
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("ingredient",string);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });




        List<FilterType> list = new ArrayList<>();

//第一项
        FilterType filterType = new FilterType();
        filterType.desc = "肉类";
        String child = "猪肉 排骨 猪蹄 五花肉 猪小排 牛肉 牛腩 牛排 牛蹄筋 肥牛 羊肉 羊排 羊肚 羊蝎子 鸡肉 鸡翅 鸡腿 鸡爪 腊肉 火腿 香肠 培根";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

//第二项
        filterType = new FilterType();
        filterType.desc = "蛋/奶";
        child = "鸡蛋 鸭蛋 松花蛋 鹌鹑蛋 奶酪 黄油 奶油";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

//第三项
        filterType = new FilterType();
        filterType.desc = "鱼类";
        child = "草鱼 鲤鱼 鲫鱼 鲢鱼 罗非鱼 带鱼 三文鱼 金枪鱼 鱼丸";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "水产";
        child = "蛤蜊 牡蛎 鲍鱼 干贝 扇贝 海螺 海带 紫菜 发菜 章鱼 田螺";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "菜类";
        child = "白菜 油菜 芹菜 菠菜 小白菜 韭菜 生菜 茼蒿 香菜 芦笋 菜花 西兰花 苦菊 土豆 红薯芋头 胡萝卜 白萝卜 山药 藕 豆角 茄子 青椒 西红柿 荷兰豆 黄瓜 冬瓜 南瓜 丝瓜 彩椒 蘑菇 草菇 香菇 金针菇 银耳 木耳 洋葱 小葱 大葱";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "豆类";
        child = "绿豆芽 黄豆芽 黄豆 毛豆 青豆 绿豆 豆腐 腐竹 豆腐皮 豆豉";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "果品类";
        child = "苹果 香蕉 菠萝 草莓 梨 杏 猕猴桃 柚子 柿子 荔枝 葡萄 樱桃 木瓜 火龙果 无花果 桂圆 西瓜 桃 蔓越莓 香瓜 金桔 蓝莓 牛油果 桔子 花生 腰果 松子 核桃 芝麻 莲子 榛子 夏威夷果 海底椰 红枣 南瓜子 开心果 板栗 葵花籽仁 阿胶枣 桂圆肉 山核桃 干无花果 葡萄干 蜜枣 柿饼";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "五谷杂粮";
        child = "荞麦面 面包 米饭 粉丝 米粉 全麦粉 小麦面粉 燕麦片 淀粉 大米粥 葛根粉 糯米粉 乔麦面 麦芽 大米 糯米 黑米 小米 小麦 玉米 西米 薏米 燕麦 红豆 紫米 大麦";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "饮品";
        child = "牛奶 酸奶 豆浆 蜂蜜 绿茶 可可粉 藕粉 江米酒 蜂王浆 茶叶 麦乳精 黄酒 花茶 普洱茶 柠檬水 玫瑰花茶 红茶 花粉 茉莉花茶 菊花茶 金银花茶";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "主食";
        child = "寿司 饼 炒饭 馒头 饺子 烧麦 炒面 包子 土豆粉 三明治 拌面 汤圆 米粉 钝 面条 焖饭 盖浇饭 意大利面 凉面 饭团 煎饼 发糕 汉堡 花卷 煲仔饭 春卷 卷饼 米线 春饼 锅贴 玉米饼 盒子";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "口味";
        child = "清淡 咖喱 麻辣 咸 辣 香辣 椒麻 甜 酸辣 孜然 酸 酸甜 糖醋 咸鲜 苦 泡椒 巧克力 椒盐 剁椒 香酥 黑椒 酱香 鱼香 原味 五香 柠檬味 薄荷味 番茄味 茄汁 奶香 蒜香 苹果味 芝士味";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "节日";
        child = "春节 年夜饭 中秋节 元旦 元宵节 教师节 七夕节 端午节 清明节 圣诞节 小年 万圣节 重阳节 中元节 母亲节 腊八节 感恩节 二月二 下元节 情人节 父亲节 儿童节 复活节 妇女节";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);

        filterType = new FilterType();
        filterType.desc = "甜品";
        child = "布丁 糖水 糕点 糖果 冰品 奶昔";
        filterType.child = Arrays.asList(child.split(" "));
        list.add(filterType);



//初始化选中.
        doubleListView.setLeftList(list, 0);
        doubleListView.setRightList(list.get(0).child, -1);

        return view;
    }

    private void initView() {
        doubleListView = view.findViewById(R.id.double_list);

    }
}