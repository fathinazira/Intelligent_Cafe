package com.fathin.psm1.sugarlipscafe;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user-pc on 5/19/2018.
 */

public class Product implements Serializable {

    @SerializedName("product_id")
    private int product_id;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("product_category")
    private String product_category;

    @SerializedName("product_calories")
    private double product_calories;

    @SerializedName("product_price")
    private double product_price;

    @SerializedName("image_url")
    private String image_url;

    public Product(int product_id, String product_name, String product_category, double product_calories, double product_price, String image_url) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_calories = product_calories;
        this.product_price = product_price;
        this.image_url = image_url;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public double getProduct_calories() {
        return product_calories;
    }

    public void setProduct_calories(double product_calories) {
        this.product_calories = product_calories;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}