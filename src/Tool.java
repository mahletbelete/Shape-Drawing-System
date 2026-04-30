/**
 * Supported tools.
 */
public enum Tool {
    CIRCLE("Circle"),
    OVAL("Oval"),
    RECTANGLE("Rectangle"),
    SQUARE("Square"),
    LINE("Line"),
    ARROW("Arrow"),
    POLYGON("Polygon"),
    ERASER("Eraser"),
    SELECT("Select");

    /**
     * The label of a tool.
     */
    public final String label;

    Tool(String label) {
        this.label = label;
    }
}
