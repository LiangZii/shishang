package com.liang.service.impl;

import com.liang.entity.Dish;
import com.liang.jsoup.DishJsoup;
import com.liang.jsoup.MenuJsoup;
import com.liang.jsoup.RandomJsoup;
import com.liang.jsoup.RecommendJsoup;
import com.liang.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    public List<Dish> select(String KeyWord){
        MenuJsoup menuJsoup=new MenuJsoup(KeyWord);
        List<Dish> a=menuJsoup.getData();
        return a;
    }

    public  Dish selectById(long id){
        DishJsoup dishJsoup=new DishJsoup(id);
        Dish a=dishJsoup.getData();
        return a;
    }
    public List<Dish> recommend(int page){
        RecommendJsoup recommendJsoup=new RecommendJsoup(page);
        List<Dish> a=recommendJsoup.getData();
        return a;
    }
    public Dish random(){
        RandomJsoup randomJsoup=new RandomJsoup();
        Dish a=randomJsoup.getData();
        return a;
    }
}
