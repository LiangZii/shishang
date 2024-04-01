package com.liang.dietitian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.liang.dietitian.R;
import com.liang.dietitian.entity.FoodPojo;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

public class FoodDetailAdapter extends BaseAdapter {
    private final Context context;
    private List<FoodPojo> foodPojos = new ArrayList<>();
    private RequestOptions options = null;

    public FoodDetailAdapter(Context context) {
        this.context = context;
    }

    public void addFood(FoodPojo foodPojo){
        foodPojos.add(foodPojo);
    }

    public void clearFood(){
        foodPojos.clear();
    }

    @Override
    public int getCount() {
        return foodPojos.size();
    }

    @Override
    public FoodPojo getItem(int i) {
        if (i > foodPojos.size()){
            return null;
        }else{
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
        if(convertView != null){
            holder = (ViewHolder) convertView.getTag();
        }else {
            convertView = View.inflate(context, R.layout.food_detail_item,null);
            holder = new ViewHolder();
            holder.food_detail_step = convertView.findViewById(R.id.food_detail_step);
            holder.food_detail_image = convertView.findViewById(R.id.food_detail_img);
            holder.food_detail_message = convertView.findViewById(R.id.food_detail_message);
            convertView.setTag(holder);
        }

        final FoodPojo foodPojo = getItem(i);
        if(foodPojo != null){
//            i从0开始，步骤从1开始
            int step = i + 1;
            holder.food_detail_step.setText(convertView.getResources().getString(R.string.step) + " " + step);

//            圆角在设置centerCrop后失效，
//            options = new RequestOptions().centerCrop().bitmapTransform(new RoundedCorners(DipPxUtil.dipToPx(context,10)));

//            加载图片
            Glide.with(context).asBitmap().load(foodPojo.getImgUrl()).into(holder.food_detail_image);
            holder.food_detail_message.setText(foodPojo.getText());
        }

        //        图片点击放大效果↓ ↓ ↓
//        拿到图片地址的集合
        List<String> foodImgs = new ArrayList<>();
        for (FoodPojo x:foodPojos) {
            foodImgs.add(x.getImgUrl());
        }
//        设置放大效果
        holder.food_detail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePreview.getInstance()
                        .setContext(context)
                        .setIndex(i)
                        .setImageList(foodImgs)
                        .start();
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView food_detail_message;
        TextView food_detail_step;
        ImageView food_detail_image;
    }
}
