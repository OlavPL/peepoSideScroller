
package MainMenu;

import LevelsMenu.LevelsPane;
import Units.PointXY;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;
import static LevelsMenu.LevelsPane.fileSystem;

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
        return Math.sqrt((ap.getX()-ab.getX())*(ap.getX()-ab.getX())+(ap.getY()-ab.getY())*(ap.getY()-ab.getY()))<=a.getRadius()+b.getRadius();
    }

    public static Path getLevelPath(URI uri) {
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            try {
                if (!fileSystem.isOpen()) {
                    fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                }
            } catch (NullPointerException NPexc) {
                try {
                    fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                }catch (IOException IOexc){
                    IOexc.printStackTrace();
                }
            }catch (IOException IOexc){
            IOexc.printStackTrace();
            }
            myPath = fileSystem.getPath("Levels");
        } else {
            myPath = Paths.get(uri);
        }
        return myPath;
    }
}
