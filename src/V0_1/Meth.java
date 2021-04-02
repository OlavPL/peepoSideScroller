package V0_1;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Meth {

    public static void setFillStage(Stage stage){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }

    public static double getScreenW() {
        double width = 0;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        width = primaryScreenBounds.getWidth();

        return width;
    }

    //  Gets the height of primary screen
    public static double getScreenH() {
        double height = 0;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        height = primaryScreenBounds.getHeight();

        return height;
    }

}
