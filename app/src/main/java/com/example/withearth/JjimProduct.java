package com.example.withearth;

public class JjimProduct {

    private String productImage;
    private String price;
    private String name;
    private boolean selectable = true;

    public JjimProduct() {

    }

    public JjimProduct(String product_image, String price, String name) {
        this.productImage = product_image;
        this.price = price;
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public String toString() {
        return "상품: " + name + '\n' +
                "가격: " + price + '\n';
    }
}
