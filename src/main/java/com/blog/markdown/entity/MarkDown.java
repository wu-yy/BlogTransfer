package com.blog.markdown.entity;

import lombok.Data;



@Data
public class MarkDown {

    private String website;

    private String blogUrl;

    private String imagePath;

    private String imageUrl;

    private String imageName;

    private String title;

    private String article_lable;

    private String fenlei_lable;

    public String getTitle() {
     return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleLable() {
        return article_lable;
    }
       
    public void setArticleLable(String article_lable) {
        this.article_lable = article_lable;
    }

    public String getFenleiLable() {
        return fenlei_lable;
    }
       
    public void setFenleiLable(String fenlei_lable) {
           this.fenlei_lable = fenlei_lable;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
