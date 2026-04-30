import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;

/**
 * An arrow shape.
 */
public class Arrow extends MyShape {

  /**
   * Constructs an arrow object.
   */
  public Arrow() {
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

    double angle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
    double arrowLength = 12;
    double arrowAngle = Math.toRadians(25);

    Point2D arrowPoint1 = new Point2D(
        end.getX() - arrowLength * Math.cos(angle - arrowAngle),
        end.getY() - arrowLength * Math.sin(angle - arrowAngle));
    Point2D arrowPoint2 = new Point2D(
        end.getX() - arrowLength * Math.cos(angle + arrowAngle),
        end.getY() - arrowLength * Math.sin(angle + arrowAngle));

    gc.strokeLine(end.getX(), end.getY(), arrowPoint1.getX(), arrowPoint1.getY());
    gc.strokeLine(end.getX(), end.getY(), arrowPoint2.getX(), arrowPoint2.getY());
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
