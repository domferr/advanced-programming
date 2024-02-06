package serialization;

import annotations.XMLfield;

import java.lang.reflect.Field;
import java.util.*;

/**
 * The Introspection class provides functionality to introspect a given class
 * and retrieve metadata about fields annotated with @XMLfield.
 * It allows obtaining information about serializable fields within a class.
 */
public class Introspection {
    private final List<FieldInfo> unmodifiableFieldsInfo; // List of serializable fields

    /**
     * Constructs an Introspection object with a list of serializable fields.
     * The list is made unmodifiable to prevent external modification.
     *
     * @param unmodifiableFieldsInfo The list of serializable fields.
     */
    private Introspection(List<FieldInfo> unmodifiableFieldsInfo) {
        this.unmodifiableFieldsInfo = Collections.unmodifiableList(unmodifiableFieldsInfo);
    }

    /**
     * Retrieves metadata about serializable fields annotated with @XMLfield in a given class.
     *
     * @param theClass The class to introspect.
     * @return An Introspection object containing metadata about serializable fields.
     */
    public static Introspection of(Class<?> theClass) {
        List<FieldInfo> data = new ArrayList<>();

        // Iterate through the declared fields of the class
        for (Field field: theClass.getDeclaredFields()) {
            // Retrieve the XMLfield annotation of the field
            XMLfield fieldAnnotation = field.getAnnotation(XMLfield.class);
            if (fieldAnnotation == null) continue; // skip fields without the annotation @XMLfield

            // Determine the field name and type based on the annotation
            String fieldName = fieldAnnotation.name().isEmpty() ? field.getName() : fieldAnnotation.name();
            String fieldType = fieldAnnotation.type();

            // Add the field information to the data list
            data.add(new FieldInfo(field, fieldName, fieldType));
        }
        return new Introspection(data);
    }

    public List<FieldInfo> getSerializableFields() {
        return this.unmodifiableFieldsInfo;
    }
}
