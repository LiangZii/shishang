package com.liang.dietitian.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.liang.dietitian.R;
import com.liang.dietitian.activity.DetailActivity;
import com.liang.dietitian.entity.FoodPojo;

import java.util.ArrayList;
import java.util.List;

public class FoodHomeRecommendAdapter extends BaseAdapter {
    private final Context context;
    private List<FoodPojo> foodPojos = new ArrayList<>();
    private RequestOptions options = null;
    private int flag = 1;

    public FoodHomeRecommendAdapter(Context context) {
        this.context = context;
    }

    public void addFood(FoodPojo foodPojo) {
        foodPojos.add(foodPojo);
    }

    public void clearFood() {
        foodPojos.clear();
    }

    @Override
    public int getCount() {
        return foodPojos.size();
    }

    @Override
    public FoodPojo getItem(int i) {
        if (i > foodPojos.size()) {
            return null;
        } else {
            return foodPojos.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.food_home_item, null);
            holder = new ViewHolder();
            holder.food_home_recommend_text = convertView.findViewById(R.id.food_home_text);
            holder.food_home_recommend_image = convertView.findViewById(R.id.food_home_img);
            convertView.setTag(holder);
        }

        FoodPojo foodPojo = getItem(i);
        if (foodPojo != null) {
//            文本
            holder.food_home_recommend_text.setText(foodPojo.getText());
//            图片
            Glide.with(context).asBitmap().load(foodPojo.getImgUrl()).placeholder(R.drawable.loading).into(holder.food_home_recommend_image);
        }

        holder.food_home_recommend_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putString("toDetailSrc",foodPojo.getToDetailSrc());
                bundle.putSerializable("toDetailSrc", foodPojo);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    static class ViewHolder {
        TextView food_home_recommend_text;
        ImageView food_home_recommend_image;
    }


}
