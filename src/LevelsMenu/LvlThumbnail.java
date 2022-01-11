package LevelsMenu;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LvlThumbnail extends BorderPane {
    private static int levelNum = 0;
    private static final Border lvlBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));

    public LvlThumbnail() {
        super();
        setUserData(++levelNum);
        setCenter(new Label("Lv. "+levelNum));
        setBorder(lvlBorder);
        setOnMouseClicked(e->{
            System.out.println("Level "+getUserData()+" Selected");
        });
    }

    public static void exitScene(){
        levelNum = 0;
    }
}
