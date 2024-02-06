package serialization;

import java.lang.reflect.Field;

/**
 * The FieldInfo class represents metadata about a field, including the field itself,
 * the field name, and the field type.
 * It provides methods to retrieve the field value from an object and get information about the field.
 */
public class FieldInfo {
    private final Field field;
    private final String fieldName;
    private final String fieldType;

    /**
     * Constructs a FieldInfo object with the specified field, field name, and field type.
     * The accessibility of the field is set to true to allow access to private fields.
     *
     * @param field      The field object.
     * @param fieldName  The name of the field.
     * @param fieldType  The type of the field.
     */
    public FieldInfo(Field field, String fieldName, String fieldType) {
        this.field = field;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.field.setAccessible(true);
    }

    /**
     * Retrieves the value of the field from the specified object.
     *
     * @param obj The object from which to retrieve the field value.
     * @return The value of the field in the object, or null if access is denied.
     */
    public Object ofObject(Object obj) {
        try {
            return this.field.get(obj);
        } catch (IllegalAccessException e) {
            System.err.print("Serialization of field '"+this.field.getName()+"' of class '"+obj.getClass()+"' failed: ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }
}
