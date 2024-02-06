package recursiveSerialization;

import serialization.XMLSerializer;

import java.io.IOException;

public class MainRecursiveSerialization {

    private final static String FILE_NAME = "recursive_serialization.xml";

    public static void main(String[] args) {
        Object[] arr = new Object[] {
                new Person("Giulio", "Cesare", new Address("Piazza del Colosseo", "Roma", 1, "00184")),
                new Person("Alan", "Turing", new Address("High Street", "Wilmslow", 78, "SK9 1AX")),
                new Person("Rino", "Gaetano", new Address("Viale Cristoforo Colombo", "Crotone", 117, "88900")),
        };

        try {
            XMLSerializer.serialize(arr, FILE_NAME);
        } catch (IOException e) {
            System.err.println("I/O error occurred serializing the array of persons");
            e.printStackTrace();
        }
    }
}
