package com.liang.dietitian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.liang.dietitian.R;
import com.liang.dietitian.entity.FoodPojo;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<FoodPojo> foodPojos = new ArrayList<>();
    private Context context;

    //静态内部类， 每个条目对应的布局
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView food_list_text;
        TextView food_list_ingredient;
        TextView food_list_score;
        ImageView food_list_image;

        public ViewHolder (View view)
        {
            super(view);
            food_list_text = view.findViewById(R.id.food_list_text);
            food_list_image = view.findViewById(R.id.food_list_img);
            food_list_ingredient = view.findViewById(R.id.food_list_ingredient);
            food_list_score = view.findViewById(R.id.food_list_score);
        }

    }


    public void addFood(FoodPojo foodPojo){
        foodPojos.add(foodPojo);
    }

    public void clearFood(){
        foodPojos.clear();
    }

    public FoodPojo getItem(int i) {
        if (i > foodPojos.size()){
            return null;
        }else{
            return foodPojos.get(i);
        }
    }

    public void deleteFood(int position){
        foodPojos.remove(position);
    }

    //用于创建ViewHolder实例,并把加载的布局传入到ViewHolder的构造函数去
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item,parent,false);
        context = parent.getContext();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //是用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行。position得到当前项的实例
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodPojo foodPojo = foodPojos.get(position);
        if(foodPojo != null){
            holder.food_list_text.setText(foodPojo.getText());
//            加载图片
            Glide.with(context).asBitmap().load(foodPojo.getImgUrl()).into(holder.food_list_image);

//            用料
            if("".equals(foodPojo.getIngredient()) || foodPojo.getIngredient()==null){
                holder.food_list_ingredient.setText("用料：请查看详情");
            }else{
                holder.food_list_ingredient.setText("用料：" + foodPojo.getIngredient());
            }

//            加载评分
            holder.food_list_score.setText(foodPojo.getScore());
        }
    }

    //返回RecyclerView的子项数目
    @Override
    public int getItemCount() {
        return foodPojos == null ? 0 : foodPojos.size();
    }



}
