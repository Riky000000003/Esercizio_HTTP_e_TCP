package org.example;

public class Product
{
    private String code;
    private String name;
    private double cost;

    public Product(String code, String name, double cost) {
        this.code = code;
        this.name = name;
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

