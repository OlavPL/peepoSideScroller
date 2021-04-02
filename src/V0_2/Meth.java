package V0_2;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import tileMaps.Map;
import tileMaps.TileMap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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

    public static Image newImage(String s){
        try {
            return (new Image(new FileInputStream(s)));
        }catch (FileNotFoundException exc){
            exc.printStackTrace();
            System.out.println("404 Image File");
            return null;
        }
    }

    public boolean circleCircle(Circle a, Circle b){
        PointXY ap = new PointXY(a.getCenterX(), a.getCenterY());
        PointXY ab = new PointXY(b.getCenterX(), b.getCenterY());
        return Math.sqrt((ap.x-ab.x)*(ap.x-ab.x)+(ap.y-ab.y)*(ap.y-ab.y))<=a.getRadius()+b.getRadius();
    }

}
