package serialization;

import annotations.XMLfield;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntrospectionTest {
    @XMLfield(name = "age", type = "int")
    private int age;

    @XMLfield(name = "name", type = "String")
    private String name;

    @XMLfield(name = "isStudent", type = "boolean")
    private boolean isStudent;

    @Test
    public void testOfMethod() {
        Introspection introspection = Introspection.of(this.getClass());
        List<FieldInfo> fieldInfoList = introspection.getSerializableFields();

        assertEquals(3, fieldInfoList.size());

        // Verify the fields and their annotations
        assertEquals("age", fieldInfoList.get(0).getFieldName());
        assertEquals("int", fieldInfoList.get(0).getFieldType());

        assertEquals("name", fieldInfoList.get(1).getFieldName());
        assertEquals("String", fieldInfoList.get(1).getFieldType());

        assertEquals("isStudent", fieldInfoList.get(2).getFieldName());
        assertEquals("boolean", fieldInfoList.get(2).getFieldType());
    }

    @Test
    public void testGetSerializableFieldsMethod() {
        Introspection introspection = Introspection.of(this.getClass());
        List<FieldInfo> fieldInfoList = introspection.getSerializableFields();

        assertNotNull(fieldInfoList);
        assertEquals(3, fieldInfoList.size());

        // Ensure that the returned list is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> {
            fieldInfoList.add(fieldInfoList.get(0));
        });
    }
}