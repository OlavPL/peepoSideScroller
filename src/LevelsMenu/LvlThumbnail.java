package LevelsMenu;

import MainMenu.Main;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LvlThumbnail extends BorderPane {
    private static int levelNum = 0;
    private static final Border lvlBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));

    public LvlThumbnail(String fileName) {
        super();
        String fName = fileName.substring(0,fileName.length()-4);
        setUserData(fName);
        setCenter(new Label(fName));
        setBorder(lvlBorder);
        setOnMouseClicked(e->{
            System.out.println("Level "+getUserData()+" Selected");
            Main.setLevelScene("Levels/"+getUserData()+".txt");
        });
    }

    public static void exitScene(){
        levelNum = 0;
    }
}
