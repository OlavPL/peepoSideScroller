package V0_3;

import javafx.scene.image.Image;

public class PowerUp extends Sprite {
    int ID;

    PowerUp(Image i, int id){
        setIV(i);
        width = this.getBoundsInLocal().getWidth();
        height = this.getBoundsInLocal().getHeight();
        this.ID = id;
    }
    PowerUp(double x, double y, Image i, int id){
        this.setX(x);
        this.setY(y);
        setIV(i);
        this.ID = id;
    }
}
