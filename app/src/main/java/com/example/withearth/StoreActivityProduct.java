package com.example.withearth;

public class StoreActivityProduct {

    String category, name, image, price;

    public StoreActivityProduct(){}

    public StoreActivityProduct(String category, String name, String image, String price){
        this.category = category;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
