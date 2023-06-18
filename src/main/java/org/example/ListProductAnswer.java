package org.example;

import com.google.gson.Gson;

import java.util.List;

public class ListProductAnswer extends Answer
{
    private List<Product> products;

    public ListProductAnswer(Boolean result, String message, List<Product> products) {
        super(result, message);
        this.products = products;
    }

    String asJSON()
    {
        Gson g = new Gson();
        String toJson = g.toJson(this);
        return toJson;
    }
}

