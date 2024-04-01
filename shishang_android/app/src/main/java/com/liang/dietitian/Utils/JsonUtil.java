package com.liang.dietitian.Utils;

import com.liang.dietitian.activity.HistoryActivity;
import com.liang.dietitian.adapter.FoodDetailAdapter;
import com.liang.dietitian.adapter.FoodHomeRecommendAdapter;
import com.liang.dietitian.adapter.FoodListAdapter;
import com.liang.dietitian.adapter.HistoryAdapter;
import com.liang.dietitian.entity.FoodPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static void JsonParseFoodList(String result, FoodListAdapter adapter) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
//                遍历数组中需要的内容
                String ingredient = "";
                JSONObject object = jsonArray.getJSONObject(i);
                JSONArray array = object.getJSONArray("foods");

//                遍历用料生成字符串
                for (int j = 0; j < array.length(); j++) {
                    ingredient = ingredient + array.getJSONObject(j).getString("name") + "、";
                }
//                删除最有一个、符号
                if (ingredient != "") {
                    ingredient = ingredient.substring(0, ingredient.length() - 1);
                }


                FoodPojo foodPojo = new FoodPojo(object.getString("name"), object.getString("img"), ingredient, object.getString("message"));
                foodPojo.setToDetailSrc(object.getString("src"));
                adapter.addFood(foodPojo);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void JsonParseFoodHomeRecommend(String result, FoodHomeRecommendAdapter adapter) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
//                遍历数组中需要的内容

                JSONObject object = jsonArray.getJSONObject(i);
                FoodPojo foodPojo = new FoodPojo(object.getString("name"), object.getString("img"));
                foodPojo.setToDetailSrc(object.getString("src"));
                foodPojo.setScore(object.getString("message"));
                adapter.addFood(foodPojo);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String JsonParseFoodDetailName(String result) {
        String data = "";
        try {
            JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
            data = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String JsonParseFoodDetailImgSrc(String result) {
        String data = "";
        try {
            JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
            data = jsonObject.getString("src");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String JsonParseFoodImg(String result) {
        String data = "";
        try {
            JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
            data = jsonObject.getString("img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<String> JsonParseFoodDetailIngredient(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("foods");
            for (int i = 0; i < jsonArray.length(); i++) {
//                遍历数组中需要的内容
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(object.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void JsonParseFoodDetailMethod(String result, FoodDetailAdapter adapter) {
        try {
            JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("method");
            for (int i = 0; i < jsonArray.length(); i++) {
//                遍历数组中需要的内容
                JSONObject object = jsonArray.getJSONObject(i);
                FoodPojo foodPojo = new FoodPojo(object.getString("message"), object.getString("src"));
                adapter.addFood(foodPojo);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<String> JsonParseIngredient(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObjectxx = new JSONObject(result).getJSONObject("result");
            JSONObject jsonObject = jsonObjectxx.getJSONObject("ingredient");

            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
//                遍历数组中需要的内容
                JSONObject object = jsonArray.getJSONObject(i);
                String x1 = object.getString("score");

                double xx1 = Double.parseDouble(x1);
                NumberFormat numberFormat = NumberFormat.getPercentInstance();
                numberFormat.setMinimumFractionDigits(2);
                x1 = numberFormat.format(xx1);


                String x2 = object.getString("name");
                if (!x1.equals("")) {
                    list.add(x1 + "    " + x2);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> JsonParseMenu(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObjectxx = new JSONObject(result).getJSONObject("result");
            JSONObject jsonObject = jsonObjectxx.getJSONObject("dishs");
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
//                遍历数组中需要的内容
                JSONObject object = jsonArray.getJSONObject(i);
                String x1 = object.getString("probability");

                double xx1 = Double.parseDouble(x1);
                NumberFormat numberFormat = NumberFormat.getPercentInstance();
                numberFormat.setMinimumFractionDigits(2);
                x1 = numberFormat.format(xx1);

                String x2 = object.getString("has_calorie");
                String x3 = object.getString("name");
                if (x2.equals("true")) {
                    String x4 = object.getString("calorie");
                    if (!x1.equals("")) {
                        list.add(x1 + "    " + x3 + "    " + x4 + "calorie");
                    }
                } else {
                    if (!x1.equals("")) {
                        list.add(x1 + "    " + x3);
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> JsonParseChinese(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObjectxx = new JSONObject(result);

            String x1 = jsonObjectxx.getString("top1-acc");
            x1 = x1 + "    "+jsonObjectxx.getString("top1-chname");
            double a=100 + Math.random() * 100;
            x1=x1+"    "+String.format("%.2f", a).toString(); ;
            x1=x1+"卡路里";
            list.add(x1);

            String x2 = jsonObjectxx.getString("top2-acc");
            x2 = x2 +"    "+jsonObjectxx.getString("top2-chname");
            a=100 + Math.random() * 100;
            x2=x2+"    "+String.format("%.2f", a).toString(); ;
            x2=x2+"卡路里";
            list.add(x2);

            String x3 = jsonObjectxx.getString("top3-acc");
            x3 = x3 + "    "+jsonObjectxx.getString("top3-chname");
            a=100 + Math.random() * 100;
            x3=x3+"    "+String.format("%.2f", a).toString(); ;
            x3=x3+"卡路里";
            list.add(x3);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String JsonParseWesternName(String result) {
        String x1=new String();
        try {
            JSONObject jsonObjectxx = new JSONObject(result);
            x1=jsonObjectxx.getString("top1-name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return x1;
    }

    public static List<String> JsonParseWestern1(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObjectxx = new JSONObject(result);
            JSONArray jsonArray = jsonObjectxx.getJSONArray("top1-ingredients");
            for (int i = 0; i < jsonArray.length(); i++) {
//                遍历数组中需要的内容
                String x1 = jsonArray.getString(i);
                list.add(x1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> JsonParseWestern2(String result) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObjectxx = new JSONObject(result);
            JSONArray jsonArray = jsonObjectxx.getJSONArray("top1-instructions");
            for (int i = 0; i < jsonArray.length(); i++) {
//                遍历数组中需要的内容
                String x1 = jsonArray.getString(i);
                list.add(x1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
