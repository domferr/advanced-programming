package objectWithArray;

import annotations.XMLable;
import annotations.XMLfield;

@XMLable
public class Product {
    @XMLfield(type = "String", name = "productname")
    private String name;
    @XMLfield(type = "double")
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
