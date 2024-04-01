package com.liang.dietitian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liang.dietitian.R;
import com.liang.dietitian.entity.FoodPojo;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends BaseAdapter {

    private final Context context;
    private List<FoodPojo> foodPojos = new ArrayList<>();

    public FoodListAdapter(Context context) {
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
            convertView = View.inflate(context, R.layout.food_list_item,null);
            holder = new ViewHolder();
            holder.food_list_text = convertView.findViewById(R.id.food_list_text);
            holder.food_list_image = convertView.findViewById(R.id.food_list_img);
            holder.food_list_ingredient = convertView.findViewById(R.id.food_list_ingredient);
            holder.food_list_score = convertView.findViewById(R.id.food_list_score);
            convertView.setTag(holder);
        }

        final FoodPojo foodPojo = getItem(i);
        if(foodPojo != null){
            holder.food_list_text.setText(foodPojo.getText());
//            加载图片
            Glide.with(context).asBitmap().load(foodPojo.getImgUrl()).into(holder.food_list_image);

            holder.food_list_ingredient.setText(convertView.getResources().getString(R.string.ingredient) + "：" + foodPojo.getIngredient());

//            加载评分
            holder.food_list_score.setText(foodPojo.getScore());
        }

        return convertView;
    }

    static class ViewHolder{
        TextView food_list_text;
        TextView food_list_ingredient;
        TextView food_list_score;
        ImageView food_list_image;
    }
}
