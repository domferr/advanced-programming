package recursiveSerialization;

import annotations.XMLable;
import annotations.XMLfield;

@XMLable
public class Person {
    @XMLfield(type = "String")
    public final String name;

    @XMLfield(type = "String")
    public final String surname;

    @XMLfield(type = "Address")
    private final Address address;

    public Person(String name, String surname, Address address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }
}
