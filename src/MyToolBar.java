import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * The toolbar that holds drawing tools and color picker.
 */
public class MyToolBar extends ToolBar {

    /**
     * Default selected color.
     */
    public static final Color DEFAULT_COLOR = Color.BLUE;

    /**
     * The canvas to draw on.
     */
    private CanvasPane canvas;

    /**
     * The stage for window controls.
     */
    private Stage stage;

    /**
     * Color picker for shape colors.
     */
    private ColorPicker colorPicker;

    /**
     * Color picker for canvas background.
     */
    private ColorPicker canvasColorPicker;

    /**
     * Group of radio buttons.
     */
    private ToggleGroup group;

    /**
     * The current selected tool.
     */
    private Tool selectedTool;

    /**
     * File chooser for saving drawings.
     */
    private FileChooser fileChooser;

    /**
     * Constructs a toolbar.
     * 
     * @param canvas the canvas to draw
     * @param stage  the application stage for window controls
     */
    public MyToolBar(CanvasPane canvas, Stage stage) {
        this.canvas = canvas;
        this.stage = stage;
        group = new ToggleGroup();
        colorPicker = new ColorPicker(DEFAULT_COLOR);
        canvasColorPicker = new ColorPicker(Color.WHITE);
        fileChooser = new FileChooser();
        fileChooser.setTitle("Save Drawing");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));
        setupItems();
        setupHandlers();
    }

    /**
     * Sets up ui controls.
     */
    private void setupItems() {
        final RadioButton circleButton = new RadioButton("Circle");
        final RadioButton ovalButton = new RadioButton("Oval");
        final RadioButton rectangleButton = new RadioButton("Rectangle");
        final RadioButton squareButton = new RadioButton("Square");
        final RadioButton lineButton = new RadioButton("Line");
        final RadioButton arrowButton = new RadioButton("Arrow");
        final RadioButton polygonButton = new RadioButton("Polygon");
        final RadioButton eraserButton = new RadioButton("Eraser");
        final RadioButton selectButton = new RadioButton("Select");

        circleButton.setToggleGroup(group);
        ovalButton.setToggleGroup(group);
        rectangleButton.setToggleGroup(group);
        squareButton.setToggleGroup(group);
        lineButton.setToggleGroup(group);
        arrowButton.setToggleGroup(group);
        polygonButton.setToggleGroup(group);
        eraserButton.setToggleGroup(group);
        selectButton.setToggleGroup(group);

        circleButton.setUserData(Tool.CIRCLE);
        ovalButton.setUserData(Tool.OVAL);
        rectangleButton.setUserData(Tool.RECTANGLE);
        squareButton.setUserData(Tool.SQUARE);
        lineButton.setUserData(Tool.LINE);
        arrowButton.setUserData(Tool.ARROW);
        polygonButton.setUserData(Tool.POLYGON);
        eraserButton.setUserData(Tool.ERASER);
        selectButton.setUserData(Tool.SELECT);
        canvasColorPicker.setValue(Color.WHITE);

        // default selection
        selectedTool = Tool.CIRCLE;
        circleButton.setSelected(true);
        canvas.setEventHandler(createEventHandler());

        final Button minimizeButton = new Button("_");
        final Button maximizeButton = new Button("☐");
        final Button closeButton = new Button("X");
        final Button resetButton = new Button("Reset");
        final Button saveButton = new Button("Save");

        minimizeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setIconified(true);
            }
        });

        maximizeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setMaximized(!stage.isMaximized());
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                canvas.reset();
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    if (!file.getName().toLowerCase().endsWith(".png")) {
                        file = new File(file.getAbsolutePath() + ".png");
                    }
                    try {
                        canvas.saveAsPng(file);
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Save Error");
                        alert.setHeaderText("Unable to save drawing");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinWidth(Region.USE_PREF_SIZE);
        getItems().addAll(circleButton, ovalButton, rectangleButton,
                squareButton, lineButton, arrowButton, polygonButton,
                eraserButton, spacer, selectButton, colorPicker,
                // canvasColorLabel, canvasColorPicker, resetButton,
                saveButton, minimizeButton, maximizeButton,
                closeButton);
    }

    /**
     * Sets up handlers.
     */
    private void setupHandlers() {
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                selectedTool = (Tool) group.getSelectedToggle().getUserData();
                canvas.setEventHandler(createEventHandler());
            }
        });

        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                canvas.setEventHandler(createEventHandler());
            }
        });

        canvasColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                canvas.setBackgroundColor(canvasColorPicker.getValue());
            }
        });
    }

    private javafx.scene.Node createArrowGraphic() {
        javafx.scene.shape.Polygon arrow = new javafx.scene.shape.Polygon();
        arrow.getPoints().addAll(0.0, 16.0, 18.0, 8.0, 0.0, 0.0);
        arrow.setFill(javafx.scene.paint.Color.TRANSPARENT);
        arrow.setStroke(javafx.scene.paint.Color.BLACK);
        arrow.setStrokeWidth(2);
        return arrow;
    }

    private javafx.scene.Node createPolygonGraphic() {
        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon();
        polygon.getPoints().addAll(0.0, 16.0, 8.0, 0.0, 24.0, 10.0, 18.0, 24.0, 4.0, 24.0);
        polygon.setFill(javafx.scene.paint.Color.TRANSPARENT);
        polygon.setStroke(javafx.scene.paint.Color.BLACK);
        polygon.setStrokeWidth(2);
        return polygon;
    }

    private javafx.scene.Node createEraserGraphic() {
        javafx.scene.shape.Rectangle eraser = new javafx.scene.shape.Rectangle(18, 12);
        eraser.setFill(javafx.scene.paint.Color.TRANSPARENT);
        eraser.setStroke(javafx.scene.paint.Color.BLACK);
        eraser.setStrokeWidth(2);
        return eraser;
    }

    private javafx.scene.Node createSelectGraphic() {
        javafx.scene.shape.Rectangle select = new javafx.scene.shape.Rectangle(20, 16);
        select.setFill(javafx.scene.paint.Color.TRANSPARENT);
        select.setStroke(javafx.scene.paint.Color.BLACK);
        select.getStrokeDashArray().addAll(4.0, 4.0);
        select.setStrokeWidth(2);
        return select;
    }

    /**
     * Creates event handler depending on the selected tool and color.
     * 
     * @return a new event handler
     */
    private EventResponsible createEventHandler() {
        Color selectedColor = colorPicker.getValue();
        switch (selectedTool) {
            case OVAL:
                return new DrawingTool(canvas, Oval::new, selectedColor);
            case CIRCLE:
                return new DrawingTool(canvas, Circle::new, selectedColor);
            case SQUARE:
                return new DrawingTool(canvas, Square::new, selectedColor);
            case RECTANGLE:
                return new DrawingTool(canvas, Rectangle::new, selectedColor);
            case LINE:
                return new DrawingTool(canvas, Line::new, selectedColor);
            case ARROW:
                return new DrawingTool(canvas, Arrow::new, selectedColor);
            case POLYGON:
                return new DrawingTool(canvas, Polygon::new, selectedColor);
            case ERASER:
                return new EraserTool(canvas);
            case SELECT:
                return new SelectionTool(canvas);
            default:
                return null;
        }
    }
}
