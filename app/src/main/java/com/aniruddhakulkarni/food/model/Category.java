package com.aniruddhakulkarni.food.model;

/**
 * Created by aniruddhakulkarni on 11/05/18.
 */

public class Category {

    private String Image;
    private String Name;


    public Category() {
    }


    public Category(String image, String name) {
        Image = image;
        this.Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
