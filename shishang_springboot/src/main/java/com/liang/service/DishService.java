package com.liang.service;

import com.liang.entity.Dish;

import java.util.List;

public interface DishService {
    List<Dish> select(String url);
    List<Dish> recommend(int page);
    Dish selectById(long id);
    Dish random();
}
