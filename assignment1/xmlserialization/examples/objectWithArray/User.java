package objectWithArray;

import annotations.XMLable;
import annotations.XMLfield;

@XMLable
public class User {
    @XMLfield(type = "String")
    private String id;
    @XMLfield(type = "Product")
    private Product[] products;

    public User(String id, Product... products) {
        this.id = id;
        this.products = products;
    }
}
