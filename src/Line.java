import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;

/**
 * A line shape.
 */
public class Line extends MyShape {

  /**
   * Constructs a line object.
   */
  public Line() {
    points = Arrays.asList(null, null);
  }

  @Override
  public void draw(GraphicsContext gc) {
    if (points.get(0) == null || points.get(1) == null)
      return;

    Point2D start = points.get(0).add(transform);
    Point2D end = points.get(1).add(transform);
    gc.setStroke(color);
    gc.setLineWidth(2);
    gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
  }

  @Override
  public void handle(MouseEvent e) {
    Point2D currentPoint = new Point2D(e.getX(), e.getY());
    if (e.getEventType() == MouseEvent.MOUSE_PRESSED)
      points.set(0, currentPoint);
    else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED)
      points.set(1, currentPoint);
    else if (e.getEventType() == MouseEvent.MOUSE_RELEASED)
      didFinishDrawingCallback.run();
  }

  @Override
  public void handle(KeyEvent e) {

  }
}
