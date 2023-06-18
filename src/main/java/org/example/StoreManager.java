package org.example;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StoreManager
{
    private List<Product> products;

    private String codeHash="24dh2";

    public StoreManager() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product p)
    {
        this.products.add(p);
    }

    public void esempioProdotti()
    {
        this.products.add(new Product("01","Prodotto1",2.50));
        this.products.add(new Product("02","Prodotto2",3.50));
        this.products.add(new Product("03","Prodotto3",4.50));
    }

    public List viewProduct()
    {
        return this.products;
    }

    public String getCodeHash() {
        return codeHash;
    }

    public String getListAsJSON()
    {
        Gson gson = new Gson();
        String jsonS = gson.toJson(products);
        return jsonS;
    }
}

