package V0_3;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform extends Rectangle {

    public Platform(double x, double y, double w, double h){
        super(w,h);
    }
    public Platform (double x, double y, double w, double h, Color c) {
        super(w,h,c);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}