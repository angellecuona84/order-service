package com.everymundo.model;

import java.io.Serializable;
/**
 * {@link Item class}
 *
 * @author Angel Lecuona
 */
public class Item implements Serializable{

    private String name;
    private Float price;

    public Item() {
    }

    public Item(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
