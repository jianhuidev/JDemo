package com.example.jdemo.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoe")
public class Shoe {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "shoe_name")
    private String name;

    @ColumnInfo(name = "shoe_description")
    private String description;

    @ColumnInfo(name = "shoe_price")
    private Float price;

    @ColumnInfo(name = "shoe_brand")
    private String brand;

    @ColumnInfo(name = "shoe_imgUrl")
    private String imageUrl;

    public Shoe(String name, String description, Float price, String brand, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPriceStr() {
        return String.valueOf(price);
    }
}
