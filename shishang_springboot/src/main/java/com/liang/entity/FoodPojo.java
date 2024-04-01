package com.liang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodPojo implements Serializable {
    String text;
    String imgUrl;
    String ingredient;
    String score;
    String toDetailSrc;
}
