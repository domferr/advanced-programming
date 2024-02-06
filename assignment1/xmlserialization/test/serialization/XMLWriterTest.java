package serialization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

public class XMLWriterTest {

    @Test
    public void testOpenAndCloseTag() throws IOException {
        StringWriter stringWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(new BufferedWriter(stringWriter));

        xmlWriter.openTag("root");
        xmlWriter.closeTag();
        xmlWriter.close();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root />", stringWriter.toString().trim());
    }

    @Test
    public void testOpenAndCloseTagWithAttributes() throws IOException {
        StringWriter stringWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(new BufferedWriter(stringWriter));

        xmlWriter.openTag("person", "id", "123", "name", "Dante Alighieri");
        xmlWriter.closeTag();
        xmlWriter.close();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<person id=\"123\" name=\"Dante Alighieri\" />", stringWriter.toString().trim());
    }

    @Test
    public void testWriteValue() throws IOException {
        StringWriter stringWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(new BufferedWriter(stringWriter));

        xmlWriter.openTag("name");
        xmlWriter.writeValue("Dante Alighieri");
        xmlWriter.closeTag();
        xmlWriter.close();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<name>Dante Alighieri</name>", stringWriter.toString().trim());
    }

    @Test
    public void testNestedTags() throws IOException {
        StringWriter stringWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(new BufferedWriter(stringWriter));

        xmlWriter.openTag("book");
        xmlWriter.openTag("title");
        xmlWriter.writeValue("La Divina Commedia");
        xmlWriter.closeTag();
        xmlWriter.openTag("author");
        xmlWriter.writeValue("Dante Alighieri");
        xmlWriter.closeTag();
        xmlWriter.closeTag();
        xmlWriter.close();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<book>\n\t<title>La Divina Commedia</title>\n\t<author>Dante Alighieri</author>\n</book>", stringWriter.toString().trim());
    }

    @Test()
    public void testUnevenAttributes() throws IOException {
        StringWriter stringWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(new BufferedWriter(stringWriter));

        assertThrows(IllegalArgumentException.class, () -> {
            xmlWriter.openTag("person", "id", "123", "name"); // Uneven attributes, should throw IllegalArgumentException
            xmlWriter.close();
        });
    }

    @Test
    public void testEmptyTag() throws IOException {
        StringWriter stringWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(new BufferedWriter(stringWriter));

        xmlWriter.openTag("empty");
        xmlWriter.closeTag();
        xmlWriter.close();

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<empty />", stringWriter.toString().trim());
    }
}
