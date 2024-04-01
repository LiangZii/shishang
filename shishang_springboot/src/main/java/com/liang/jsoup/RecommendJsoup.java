package com.liang.jsoup;

import com.liang.entity.Dish;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecommendJsoup {
    public static String remove(String str) {
        return null == str ? null : str.replaceAll("[^\\u0000-\\uFFFF]", " ");
    }
    private int page;
    private List<Dish> data;
    public RecommendJsoup(int page){
        this.page=page;
        this.run();
    }
    public void run() {
        Connection conn = Jsoup.connect("https://www.xiachufang.com/explore/?page="+page);
//         修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element element=doc.getElementsByClass("pure-u-3-4 search-result-list").get(0).child(1).child(0);
        Elements elements=element.getElementsByTag("li");
        String name=null;
        String src=null;
        String img=null;
        String message=null;
        List<Dish> recommend=new ArrayList<>();
        for (Element e:elements) {
            Dish dish=new Dish();
            //name
            name=e.child(0).child(1).child(0).child(0).text();
            dish.setName(remove(name));

            //src
            String result=e.child(0).child(1).child(0).child(0).attr("href");
            String []Src=result.split("/");
            src=Src[2];
            dish.setSrc(src);

            //img
            img=e.child(0).child(0).child(0).child(0).attr("data-src");
            dish.setImg(img);

            //message
            message=e.child(0).child(1).child(2).text();
            dish.setMessage(remove(message));
            recommend.add(dish);
        }
        data=recommend;
    }
    public List<Dish> getData() {
        return this.data;
    }
}
