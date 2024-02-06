package objectWithArray;

import serialization.XMLSerializer;

import java.io.IOException;

public class MainObjectWithArray {

    private final static String FILE_NAME = "objects_with_array_of_objects.xml";

    public static void main(String[] args) {
        Object[] arr = new Object[] {
                new User("001", new Product("Wine", 11.4), new Product("Pizza", 5.9)),
                new User("002"),
                new User("001", new Product("Apple", 1), new Product("Eggs", 1.75), new Product("Ham", 0.75)),
        };

        try {
            XMLSerializer.serialize(arr, FILE_NAME);
        } catch (IOException e) {
            System.err.println("I/O error occurred serializing the array of persons");
            e.printStackTrace();
        }
    }
}
