package com.liang.jsoup;

import com.liang.entity.Dish;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class RandomJsoup {
    public static String remove(String str) {
        return null == str ? null : str.replaceAll("[^\\u0000-\\uFFFF]", " ");
    }
    private Dish data;
    private int page;
    public RandomJsoup(){
        this.page= (int)( Math.random()*10);
        this.run();
    }
    public void run() {
        Connection conn = Jsoup.connect("https://www.xiachufang.com/explore/?page="+this.page);
//         修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element element=doc.getElementsByClass("pure-u-3-4 search-result-list").get(0).child(1).child(0);
        Element element1=element.child((int)(Math.random()*10));
        Dish dish=new Dish();

        //菜名
        dish.setName(remove(element1.child(0).child(1).child(0).child(0).text()));
        this.data=dish;

        //图片
        dish.setImg(element1.child(0).child(0).child(0).child(0).attr("data-src"));
        //链接
        String result=element1.child(0).child(1).child(0).child(0).attr("href");
        String []Src=result.split("/");
        dish.setSrc(Src[2]);
    }
    public Dish getData() {
        return this.data;
    }
}

