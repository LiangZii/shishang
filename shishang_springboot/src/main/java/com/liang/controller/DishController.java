package com.liang.controller;

import com.liang.entity.Dish;
import com.liang.lang.Result;
import com.liang.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DishController {
    @Autowired
    DishService dishService;

    @GetMapping("/search")
    public Result select(@RequestParam String KeyWord){
        List<Dish> dishes=dishService.select(KeyWord);
        if (dishes!=null) return Result.success(dishes);
        else return Result.fail(null);
    }
    @GetMapping("/recipe/{id}")
    public Result detail(@PathVariable long id){
        Dish dish=dishService.selectById(id);
        if(dish!=null) return Result.success(dish);
        else return Result.fail(null);
    }
    @GetMapping("/recommend")
    public Result recommend(@RequestParam int page){
        List<Dish> dishes=dishService.recommend(page);
        if (dishes!=null) return Result.success(dishes);
        else return Result.fail(null);
    }

    @GetMapping("/random")
    public Result random(){
        Dish dish=dishService.random();
        if (dish!=null) return Result.success(dish);
        else return Result.fail(null);
    }
}
