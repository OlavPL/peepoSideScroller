package V0_1;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Coins extends Node {
    Rectangle r;

    Coins(int x, int y){
        Rectangle r = new Rectangle(50 ,50, Color.TRANSPARENT);
        r.setTranslateX(x);
        r.setTranslateY(y);
    }
}
