package com.om.dairy.ramdairy.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Awesome Pojo Generator
 */
@IgnoreExtraProperties
public class ItemMaster  {

    private int unitAm  = 0;
    private int unitPm  = 0;

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    float subTotal;

    @SerializedName("price")
    private float price;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

    public ItemMaster(float price, String name, int id) {
        this.price = price;
        this.name = name;
        this.id = id;
    }

    public ItemMaster() {
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUnitPm() {
        return unitPm;
    }

    public void setUnitPm(int unitPm) {
        this.unitPm = unitPm;
    }

    public int getUnitAm() {
        return unitAm;
    }

    public void setUnitAm(int unitAm) {
        this.unitAm = unitAm;
    }
}