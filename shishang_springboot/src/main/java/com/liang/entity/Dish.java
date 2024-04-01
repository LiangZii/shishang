package com.liang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish implements Serializable {
    long id;
    String name;
    List<Food> foods;
    int energies;
    String message;
    List<Step> method;
    String img;
    String src;
}
