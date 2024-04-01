package com.liang.dietitian.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HistorySearchDataBase extends SQLiteOpenHelper {

    public HistorySearchDataBase(Context context){
        super(context,"history_search_db",null,1);
    }

//    数据库第一次创建时调用该方法
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists history_search(id integer primary key autoincrement,history varchar(50))";
        sqLiteDatabase.execSQL(sql);
    }

//    数据库版本号更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    添加数据
    public void addData(SQLiteDatabase sqLiteDatabase,String history){
        ContentValues values = new ContentValues();
        values.put("history",history);
        sqLiteDatabase.insert("history_search",null,values);
    }

//    删除数据
    public void deleteData(SQLiteDatabase sqLiteDatabase,String history){
        sqLiteDatabase.delete("history_search","history=?",new String[]{history});
    }

//    清空数据
    public void clearData(SQLiteDatabase sqLiteDatabase){
        String sql = "delete from history_search";
        sqLiteDatabase.execSQL(sql);
    }

//    查询数据
    public List<String> selectData(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.query("history_search",null,null,null,null,null,"id DESC");
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String history = cursor.getString(1);
            list.add(history);
        }
        cursor.close();

        return list;
    }

}
