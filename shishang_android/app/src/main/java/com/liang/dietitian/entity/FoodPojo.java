package com.liang.dietitian.entity;

import java.io.Serializable;

public class FoodPojo implements Serializable {
    String text;
    String imgUrl;
    String ingredient;
    String score;
    String toDetailSrc;


    public FoodPojo(){

    }

    public FoodPojo(String text, String imgUrl){
        this.text = text;
        this.imgUrl = imgUrl;
    }

    public FoodPojo(String text, String imgUrl, String ingredient){
        this.text = text;
        this.imgUrl = imgUrl;
        this.ingredient = ingredient;
    }

    public FoodPojo(String text, String imgUrl, String ingredient,String score){
        this.text = text;
        this.imgUrl = imgUrl;
        this.ingredient = ingredient;
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getToDetailSrc() {
        return toDetailSrc;
    }

    public void setToDetailSrc(String src) {
        this.toDetailSrc = src;
    }


}
