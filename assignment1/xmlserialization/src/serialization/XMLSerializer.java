package serialization;

import annotations.XMLable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The XMLSerializer class provides static methods to serialize objects into XML format.
 * It utilizes reflection and annotations to introspect objects and serialize them.
 */
public class XMLSerializer {
    private static final Map<Class<?>, Introspection> cache = new HashMap<>();

    /**
     * Serializes an array of objects into XML format and writes it to a file.
     *
     * @param arr      The array of objects to serialize.
     * @param fileName The name of the file to write the XML data to.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void serialize(Object[] arr, String fileName) throws IOException {
        fileName = fileName.endsWith(".xml") ? fileName:fileName+".xml";
        serialize(arr, new FileWriter(fileName));
    }

    /**
     * Serializes an array of objects into XML format and writes it to a writer.
     *
     * @param arr    The array of objects to serialize.
     * @param writer The writer to which the XML data is written.
     * @throws IOException If an I/O error occurs while writing to the writer.
     */
    public static void serialize(Object[] arr, Writer writer) throws IOException {
        try (XMLWriter xmlWriter = new XMLWriter(new BufferedWriter(writer))) {
            serializeArray(arr, xmlWriter);
        } finally {
            XMLSerializer.cache.clear();
        }
    }

    /**
     * Serializes an array of objects into XML format and writes it to the XMLWriter.
     * This method recursively serializes each object in the array and wraps them with an <Array> tag.
     *
     * @param arr    The array of objects to serialize.
     * @param writer The XMLWriter instance to write the serialized XML data.
     * @throws IOException If an I/O error occurs while writing the XML data.
     */
    private static void serializeArray(Object[] arr, XMLWriter writer) throws IOException {
        // Open the <Array> tag
        writer.openTag(Tag.TAG_NAME_ARRAY);

        for(Object obj: arr) {
            // Recursively serialize the object
            serializeObject(obj, writer, true);
        }

        // Close the <Array> tag
        writer.closeTag();
    }

    /**
     * Serializes an object into XML format and writes it to the XMLWriter.
     * This method introspects the object's fields, serializes them recursively,
     * and wraps them with an XML tag representing the object's class name.
     *
     * @param obj        The object to serialize.
     * @param writer     The XMLWriter instance to write the serialized XML data.
     * @param wrapWithTag A flag indicating whether to wrap the object with an XML tag.
     * @throws IOException If an I/O error occurs while writing the XML data.
     */
    private static void serializeObject(Object obj, XMLWriter writer, boolean wrapWithTag) throws IOException {
        Class<?> objectClass = obj.getClass();

        // Check if the object's class is XMLable
        if (objectClass.getAnnotation(XMLable.class) == null) {
            // If not XMLable, serialize as a non-XMLable object
            serializeNotXMLable(objectClass.getSimpleName(), writer);
            return;
        }

        // Cache the introspection data for the object's class
        if (!cache.containsKey(objectClass)) {
            cache.put(objectClass, Introspection.of(objectClass));
        }

        String classSimpleName = objectClass.getSimpleName();
        // Wrap the object with an XML tag if required
        if (wrapWithTag) writer.openTag(classSimpleName);

        // Retrieve the introspection data for the object's class
        Introspection intro = cache.get(objectClass);

        // Iterate through the serializable fields of the object
        for (FieldInfo fieldInfo: intro.getSerializableFields()) {
            Object fieldObject = fieldInfo.ofObject(obj);

            // Open an XML tag for the field with its name and type attributes
            writer.openTag(fieldInfo.getFieldName(), "type", fieldInfo.getFieldType());

            if (fieldObject == null) {
                writer.writeValue("null"); // Serialize null values as "null"
            } else {
                Class<?> fieldObjectClass = fieldObject.getClass();
                if (fieldObjectClass.isArray()) {
                    // If the field is an array, recursively serialize it
                    Object[] arr = (Object[]) fieldObject;
                    serializeArray(arr, writer);
                } else if (fieldObjectClass.isAnnotationPresent(XMLable.class)) {
                    // If the field is XMLable, recursively serialize it
                    serializeObject(fieldObject, writer, false);
                } else {
                    // Serialize non-array, non-XMLable fields as string representations
                    writer.writeValue(fieldObject.toString());
                }
            }

            // Close the XML tag for the field
            writer.closeTag();
        }

        // Close the XML tag for the object if required
        if (wrapWithTag) writer.closeTag();
    }

    private static void serializeNotXMLable(String classSimpleName, XMLWriter writer) throws IOException {
        /*
         * writes
         * <ClassName>
         *  <notXMLable />
         * </Classname>
         */
        writer.openTag(classSimpleName);
        writer.openTag(Tag.TAG_NAME_NOT_XML_LABLE);
        writer.closeTag();
        writer.closeTag();
    }
}