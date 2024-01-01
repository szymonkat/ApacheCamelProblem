package com.example.demo;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

@FixedLengthRecord
public class Mobile {

    @DataField(pos = 1, length = 4, align = "L", trim = true)
    private String id;

    @DataField(pos = 2, length = 10)
    private String brand;

    @DataField(pos = 3, length = 10)
    private String model;

    @DataField(pos = 4, length = 5)
    private int numberOfPieces;

    @DataField(pos = 5, length = 10)
    private double price;

    @DataField(pos = 6, length = 4)
    private String currency;

    // Getters and Setters

    @Override
    public String toString() {
        return "Mobile [id=" + id + ", brand=" + brand + ", model="
                + model + ", numberOfPieces=" + numberOfPieces
                + ", price=" + price + ", currency=" + currency + "]";
    }
}