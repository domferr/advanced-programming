package recursiveSerialization;

import annotations.XMLable;
import annotations.XMLfield;

@XMLable
public class Address {

    @XMLfield(type = "String", name = "streetname")
    public String street;

    @XMLfield(type = "String", name = "postalcode")
    private String zipCode;

    @XMLfield(type = "String")
    private String city;

    @XMLfield(type = "int")
    private int number;

    public Address(String street, String city, int number, String zipCode) {
        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
        this.number = number;
    }
}
