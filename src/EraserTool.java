import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * A handler to erase shapes by clicking them.
 */
public class EraserTool implements EventResponsible {

  /**
   * The canvas that holds the shapes.
   */
  private CanvasPane canvas;

  /**
   * Constructs a new eraser handler.
   *
   * @param canvas canvas to erase shapes from
   */
  public EraserTool(CanvasPane canvas) {
    this.canvas = canvas;
  }

  @Override
  public void handle(MouseEvent e) {
    if (e.getEventType() != MouseEvent.MOUSE_PRESSED)
      return;

    Point2D point = new Point2D(e.getX(), e.getY());
    for (int i = canvas.getShapes().size() - 1; i >= 0; i--) {
      MyShape shape = canvas.getShapes().get(i);
      if (shape.contains(point)) {
        canvas.getShapes().remove(i);
        canvas.update();
        return;
      }
    }
  }

  @Override
  public void handle(KeyEvent e) {
    // No keyboard actions needed for the eraser tool.
  }
}
