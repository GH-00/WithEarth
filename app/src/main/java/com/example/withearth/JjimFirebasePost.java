package com.example.withearth;

import java.util.HashMap;
import java.util.Map;

public class JjimFirebasePost {
    private String image;
    private String name;
    private String price;
    private String timeStamp;

    public JjimFirebasePost(){

    }

    public JjimFirebasePost(String image, String name, String price, String timeStamp) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.timeStamp = timeStamp;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("image", image);
        result.put("name", name);
        result.put("price", price);
        result.put("time", timeStamp);
        return  result;
    }


}
