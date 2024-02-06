package multipleObjects;

import serialization.XMLSerializer;

import java.io.IOException;
import java.util.HashMap;

public class MainMultipleObjects {
    private final static String FILE_NAME = "multiple_objects.xml";

    public static void main(String[] args) {
        Object[] arr = new Object[] {
                new Student(),
                new Student("Jane", "Doe", 42),
                new HashMap<>(), // non xmlable
        };

        try {
            XMLSerializer.serialize(arr, FILE_NAME);
        } catch (IOException e) {
            System.err.println("I/O error occurred serializing the array");
            e.printStackTrace();
        }
    }
}
