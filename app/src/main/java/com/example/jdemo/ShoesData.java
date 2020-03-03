package com.example.jdemo;

public class ShoesData {


    /**
     * id : 1
     * name : AJ 1
     * description : Air Jordan 1是耐克乔丹系列的第一款正代篮球鞋，诞生于1985年，因为这是NBA第一双黑红配色的鞋子，所以也让大家第一眼记住了它。由这双鞋子又衍生出了另一款经典复古鞋款——Nike Dunk
     * price : 1299.0
     * brand : Air Jordan
     * imageUrl : https://raw.githubusercontent.com/mCyp/Photo/master/1560651081240.jpeg
     */

    private int id;
    private String name;
    private String description;
    private double price;
    private String brand;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
}
