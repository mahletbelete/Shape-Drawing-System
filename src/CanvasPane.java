import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The canvas pane to draw on.
 */
public class CanvasPane extends Pane {

    /**
     * The canvas.
     */
    private Canvas canvas;

    /**
     * Holds a list of shapes to render on the canvas.
     */
    private List<MyShape> shapes;

    /**
     * A handler corresponding to the current selected tool.
     */
    private EventResponsible eventHandler;

    /**
     * Constructs the canvas pane.
     */
    public CanvasPane() {
        canvas = new Canvas();
        shapes = new ArrayList<>();
        getChildren().add(canvas);

        // Bind canvas size to pane size
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());

        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                eventHandler.handle(e);
            }
        };

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, handler);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }

    /**
     * Getter for shapes.
     * 
     * @return list of shapes
     */
    public List<MyShape> getShapes() {
        return shapes;
    }

    /**
     * Getter for event handler.
     * 
     * @return the current event handler
     */
    public EventResponsible getEventHandler() {
        return eventHandler;
    }

    /**
     * Setter for event handler.
     * 
     * @param eventHandler new event handler
     */
    public void setEventHandler(EventResponsible eventHandler) {
        this.eventHandler = eventHandler;
    }

    private Color backgroundColor = Color.WHITE;

    /**
     * Clears the canvas.
     * 
     * @param color the background color of the canvas after clearing
     */
    public void clear(Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Clears the canvas with the current background color.
     */
    public void clear() {
        clear(backgroundColor);
    }

    /**
     * Sets the background color used when clearing the canvas.
     *
     * @param color the new background color
     */
    public void setBackgroundColor(Color color) {
        if (color == null)
            return;
        this.backgroundColor = color;
        update();
    }

    /**
     * Draw all the shapes onto the canvas.
     */
    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (MyShape myShape : shapes)
            myShape.draw(gc);
    }

    /**
     * Update the canvas by clearing and redrawing the shapes.
     */
    public void update() {
        clear();
        render();
    }

    /**
     * Deselects any selected shape.
     */
    private void deselectShapes() {
        for (MyShape shape : shapes)
            shape.setSelected(false);
        update();
    }

    /**
     * Saves the current contents of the canvas to a PNG file.
     *
     * @param file the file to save to
     * @throws IOException when writing the file fails
     */
    public void saveAsPng(File file) throws IOException {
        WritableImage snapshot = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, snapshot);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
        ImageIO.write(bufferedImage, "png", file);
    }

    /**
     * Clears all shapes from the canvas.
     */
    public void reset() {
        shapes.clear();
        update();
    }
}
