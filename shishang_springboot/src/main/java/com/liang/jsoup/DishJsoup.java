package com.liang.jsoup;

import com.liang.entity.Dish;
import com.liang.entity.Food;
import com.liang.entity.Step;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DishJsoup extends Thread{
    public static String remove(String str) {
        return null == str ? null : str.replaceAll("[^\\u0000-\\uFFFF]", " ");
    }

    private long id;
    private Dish data;
    public DishJsoup(long id){
        this.id=id;
        this.run();
    }
    public void run(){
        Connection conn = Jsoup.connect("https://www.xiachufang.com/recipe/"+id+"/");
//         修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dish dish=new Dish();
        Elements element=doc.getElementsByClass("pure-u-2-3 main-panel");
        //菜名
        dish.setName(remove(element.get(0).getElementsByClass("page-title").get(0).text()));
        //图片
        dish.setSrc(element.get(0)
                .getElementsByClass("block recipe-show").get(0)
                .getElementsByClass("cover image expandable block-negative-margin").get(0)
                .child(0).attr("src"));
        //食材
        List<Food> foods=new ArrayList<>();
        Elements elements=element.get(0)
                .getElementsByClass("block recipe-show").get(0)
                .getElementsByClass("ings").get(0)
                .getElementsByTag("table").get(0)
                .getElementsByTag("tbody").get(0)
                .getElementsByTag("tr");

        for (Element element1:elements){
            Food food=new Food();
            food.setName(
                    remove(element1.child(1).text() //2个
                            +element1.child(0).text()) //鸡蛋
            );
            //拼接成食材名字
            foods.add(food);
        }
        dish.setFoods(foods);
        //信息

        //做法
        List<Step> steps=new ArrayList<>();
        Elements elements1=element.get(0)
                .getElementsByClass("block recipe-show").get(0)
                .getElementsByClass("steps").get(0)
                .getElementsByTag("ol").get(0)
                .getElementsByTag("li");
        for (Element element1:elements1){
            Step step=new Step();
            step.setMessage(remove(element1.getElementsByTag("p").get(0).text()));
            step.setSrc(element1.getElementsByTag("img").get(0).attr("src"));
            steps.add(step);
        }
        dish.setMethod(steps);
        this.data=dish;
    }
    public Dish getData(){
        return this.data;
    }
}
