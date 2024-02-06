package serialization;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The XMLWriter class provides functionality to write XML documents.
 * It allows creating XML tags, specifying attributes, and writing values to those tags.
 * The class implements the Closeable interface to ensure proper resource management
 * by closing the underlying BufferedWriter when necessary.
 */
public class XMLWriter implements Closeable {

    /**
     * XML_PROLOG contains the XML declaration specifying the version and encoding.
     */
    private final String XML_PROLOG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    /**
     * The stack is used to keep track of nested XML tags.
     * It helps in maintaining the structure of the XML document.
     */
    private final Deque<Tag> stack;

    /**
     * The writer is used to write XML content to a stream, typically a file, but any type of writer is supported.
     */
    private final BufferedWriter writer;

    /**
     * Constructs an XMLWriter with the specified BufferedWriter.
     * Writes the XML declaration to the writer.
     *
     * @param writer The BufferedWriter to write XML content.
     * @throws IOException If an I/O error occurs while writing the XML declaration.
     */
    public XMLWriter(BufferedWriter writer) throws IOException {
        this.stack = new ArrayDeque<>();
        this.writer = writer;
        this.writer.write(XML_PROLOG);
    }

    /**
     * Generates indentation string based on the level of nesting.
     *
     * @param level The level of nesting.
     * @return The indentation string.
     */
    private static String getIndentation(int level) {
        return "\t".repeat(Math.max(0, level));
    }

    /**
     * Opens a new XML tag with the specified name and optional attributes.
     *
     * @param tagName The name of the XML tag.
     * @param attrs   Optional attributes in the form of name-value pairs.
     * @throws IOException If an I/O error occurs while writing the tag.
     */
    public void openTag(String tagName, String... attrs) throws IOException {
        // If the stack is not empty and the parent tag has no children, append '>' to the previous opening tag to close it
        if (!stack.isEmpty() && !stack.peekFirst().hasChildren()) writer.write(">");
        // If the stack is not empty, start a new line for proper formatting
        if (!stack.isEmpty()) writer.write("\n");

        // Generate indentation based on the current level of nesting, then write the opening tag
        String tabs = getIndentation(stack.size());
        writer.write(tabs); writer.write("<"); writer.write(tagName); writeTagAttributes(writer, attrs);

        // All the writes were successful, the stack can be updated safely
        // If the stack is not empty, update the parent tag status to remember it has now a child
        if (!stack.isEmpty()) {
            Tag parentTag = stack.peekFirst();
            parentTag.updateHasChildren();
        }
        // Add the new tag to the stack
        stack.addFirst(new Tag(tagName));
    }

    /**
     * Closes the most recently opened XML tag.
     *
     * @throws IOException If an I/O error occurs while closing the tag.
     */
    public void closeTag() throws IOException {
        // If the stack is empty, there are no open XML tags to close
        if (stack.isEmpty()) return;

        // Get the current tag being closed
        Tag currentTag = stack.peekFirst();
        // If the parent tag is empty (no value and no children), close it with a self-closing syntax
        if (!currentTag.hasValue() && !currentTag.hasChildren()) {
            // empty tag, do quick close of tag
            writer.write(" />");
            stack.pollFirst();
            return;
        }

        // If the parent tag has children, go to a new line and add indentation before writing the closing tag
        if (currentTag.hasChildren()) {
            String tabs = getIndentation(stack.size()-1);
            writer.write("\n");
            writer.write(tabs);
        }

        writer.write("</"); writer.write(currentTag.getName()); writer.write(">");

        // All the writes were successful. Remove the closed tag from the stack
        stack.pollFirst();
    }

    /**
     * Writes the value for the current XML tag.
     *
     * @param valueString The value to be written.
     * @throws IOException If an I/O error occurs while writing the value.
     */
    public void writeValue(String valueString) throws IOException {
        // If the stack is empty, there are no open XML tags to write a value to
        if (stack.isEmpty()) return;

        // Get the parent tag of the current value being written
        Tag parentTag = stack.peekFirst();
        // If the parent tag has children or already has a value, XML syntax doesn't allow to have a value
        if (parentTag.hasChildren() || parentTag.hasChildren()) return;

        // Close the parent tag and write the value
        writer.write(">");
        writer.write(valueString);

        // All the writes were successful. Update the parent tag to indicate that it now has a value
        parentTag.updateHasValue();
    }

    /**
     * Writes the XML attributes to the writer.
     *
     * @param writer The writer to write the attributes.
     * @param attrs  The attributes to be written in name-value pairs.
     * @throws IOException If an I/O error occurs while writing the attributes.
     */
    private static void writeTagAttributes(BufferedWriter writer, String[] attrs) throws IOException {
        // Ensure that the number of attributes is even, as they come in pairs (name, value)
        if (attrs.length % 2 != 0) throw new IllegalArgumentException("Uneven number of attributes");

        // Iterate through the attributes array in pairs (name, value)
        for (int i = 0; i < attrs.length; i += 2) {
            String attrName = attrs[i];
            String attrValue = attrs[i+1];

            // Write the attribute name, value pair to the writer, following XML attribute syntax
            writer.write(" "); writer.write(attrName); writer.write("=\""); writer.write(attrValue); writer.write("\"");
        }
    }

    /**
     * Closes all the open XML tags and the underlying writer.
     *
     * @throws IOException If an I/O error occurs while closing the writer.
     */
    @Override
    public void close() throws IOException {
        while (!stack.isEmpty()) this.closeTag();
        writer.close();
    }
}
