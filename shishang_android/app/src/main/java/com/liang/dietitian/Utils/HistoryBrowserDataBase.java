package com.liang.dietitian.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liang.dietitian.entity.FoodPojo;

import java.util.ArrayList;
import java.util.List;

public class HistoryBrowserDataBase extends SQLiteOpenHelper {

    public HistoryBrowserDataBase(Context context){
        super(context,"history_browser_db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS history_browser(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "text VARCHAR(50)," +
                "img_url VARCHAR(100)," +
                "ingredient VARCHAR(100)," +
                "score VARCHAR(50)," +
                "to_detail_src VARCHAR(50)" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //    添加数据
    public void addData(SQLiteDatabase sqLiteDatabase, FoodPojo foodPojo){
        List<FoodPojo> foodPojos = selectData(sqLiteDatabase);
        for (FoodPojo food: foodPojos) {
            if(food.getToDetailSrc().equals(foodPojo.getToDetailSrc())){
                deleteData(sqLiteDatabase,foodPojo.getToDetailSrc());
                break;
            }
        }

        ContentValues values = new ContentValues();
        values.put("text",foodPojo.getText());
        values.put("img_url",foodPojo.getImgUrl());
        values.put("ingredient",foodPojo.getIngredient());
        values.put("score",foodPojo.getScore());
        values.put("to_detail_src",foodPojo.getToDetailSrc());
        sqLiteDatabase.insert("history_browser",null,values);
    }

    //    删除数据
    public void deleteData(SQLiteDatabase sqLiteDatabase,String toDetailSrc){
        sqLiteDatabase.delete("history_browser","to_detail_src=?",new String[]{toDetailSrc});
    }

    //    清空数据
    public void clearData(SQLiteDatabase sqLiteDatabase){
        String sql = "delete from history_browser";
        sqLiteDatabase.execSQL(sql);
    }

    //    查询数据
    public List<FoodPojo> selectData(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.query("history_browser",null,null,null,null,null,"id DESC");
        List<FoodPojo> list = new ArrayList<>();
        while (cursor.moveToNext()){
            FoodPojo pojo = new FoodPojo();
            pojo.setText(cursor.getString(1));
            pojo.setImgUrl(cursor.getString(2));
            pojo.setIngredient(cursor.getString(3));
            pojo.setScore(cursor.getString(4));
            pojo.setToDetailSrc(cursor.getString(5));
            list.add(pojo);
        }
        cursor.close();

        return list;
    }
}
