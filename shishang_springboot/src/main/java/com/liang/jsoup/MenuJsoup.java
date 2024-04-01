package com.liang.jsoup;

import com.liang.entity.Dish;
import com.liang.entity.Food;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MenuJsoup extends Thread{
    private String KeyWord;
    private List<Dish> data;
    public static String remove(String str) {
        return null == str ? null : str.replaceAll("[^\\u0000-\\uFFFF]", " ");
    }

    public MenuJsoup(String KeyWord){
        this.KeyWord=KeyWord;
        this.run();
    }
    public void run() {
        Connection conn = Jsoup.connect("https://www.xiachufang.com/search/?keyword="+KeyWord);
//         修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements element = doc.getElementsByClass("normal-recipe-list");
        Elements elements = element.get(0).getElementsByTag("li");
        String href = null;
        List<Dish> dishes=new ArrayList<>();
        int i=0;
        for (Element element1 : elements) {
            Dish dish=new Dish();
            Element element2 = element1.child(0);
            //菜名
            String name=element2.child(1).child(0).text();
            dish.setName(remove(name));
            //图片
            dish.setImg(element2.getElementsByTag("a").get(0).child(0).child(0).attr("data-src"));
            //评分
            dish.setMessage(element2.getElementsByClass("info pure-u").get(0).getElementsByClass("stats").text());
            //链接
            String result=element2.getElementsByTag("a").attr("href");
            String []src=result.split("/");
            dish.setSrc(src[2]);
            Elements elements1=element2.child(1).child(1).getElementsByTag("a");
            //食材
            List<Food> foods=new ArrayList<>();
            for (Element element4: elements1){
                Food food=new Food();
                food.setName(element4.text());
                foods.add(food);
            }
            dish.setFoods(foods);
            dishes.add(dish);
            i++;
        }
        this.data=dishes;
    }
    public List<Dish> getData() {
        return this.data;
    }
}
