package serialization;

/**
 * The Tag class represents an XML tag.
 * It holds information about the name of the tag, whether it contains a value, and whether it contains children tags.
 * Tags are used within XMLWriter for tracking the state of XML elements.
 */
public class Tag {

    /**
     * A constant representing the tag name for elements that are not XML-serializable.
     */
    public static final String TAG_NAME_NOT_XML_LABLE = "notXMLable";

    /**
     * A constant representing the tag name for array elements.
     */
    public static final String TAG_NAME_ARRAY = "Array";

    private final String name; // The name of the XML tag
    private boolean containsValue; // Flag indicating whether the tag contains a value
    private boolean containsChildren; // Flag indicating whether the tag contains children tags

    /**
     * Constructs a new Tag with the specified name.
     *
     * @param name The name of the XML tag.
     */
    public Tag(String name) {
        this.name = name;
        this.containsValue = false;
        this.containsChildren = false;
    }

    /**
     * Checks if the tag contains a value.
     *
     * @return true if the tag contains a value, false otherwise.
     */
    public boolean hasValue() {
        return containsValue;
    }

    /**
     * Sets the flag indicating that the tag contains a value.
     */
    public void updateHasValue() {
        this.containsValue = true;
    }

    /**
     * Retrieves the name of the XML tag.
     *
     * @return The name of the XML tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves if the tag contains children tags.
     *
     * @return true if the tag contains children tags, false otherwise.
     */
    public boolean hasChildren() {
        return containsChildren;
    }

    /**
     * Sets the flag indicating that the tag contains children tags.
     */
    public void updateHasChildren() {
        this.containsChildren = true;
    }
}
