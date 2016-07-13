package com.elder.item;

/**
 * Created by jan on 10/07/2016.
 */
public class Product {

    private Double price;
    private Integer quantity;

    public Product(final Double price, final Integer quantity) {

        this.price = price;
        this.quantity = quantity;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

}
