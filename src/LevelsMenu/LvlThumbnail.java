package LevelsMenu;

import MainMenu.Main;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LvlThumbnail extends BorderPane {
    private static final Border lvlBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));

    public LvlThumbnail(String fileName) {
        super();
        System.out.println(fileName);
        setUserData(fileName);
        String name = fileName.substring(0,fileName.length()-4);
        setCenter(new Label(name));
        setBorder(lvlBorder);

        setOnMouseClicked(e->{
            System.out.println(name+" Selected");
            Main.setLevelScene(Main.getAppdata()+"/"+fileName);
        });
    }
}
