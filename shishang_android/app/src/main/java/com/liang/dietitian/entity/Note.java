package com.liang.dietitian.entity;

import java.io.Serializable;

public class Note implements Serializable {
    private Integer noteId;
    private String userId;
    private String title;
    private String content;
    private String publishDate;

    private String text;
    private String imgUrl;
    private String ingredient;
    private String score;
    private String toDetailSrc;

    public Note() {
    }


    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
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

    public void setToDetailSrc(String toDetailSrc) {
        this.toDetailSrc = toDetailSrc;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", text='" + text + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", score='" + score + '\'' +
                ", toDetailSrc='" + toDetailSrc + '\'' +
                '}';
    }
}
