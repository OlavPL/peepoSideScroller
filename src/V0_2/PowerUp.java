package V0_2;

import javafx.scene.image.Image;

public class PowerUp extends Sprite {
    int ID;

    PowerUp(double x, double y, Image i, int id){
        this.setX(x);
        this.setY(y);
        velX = 0;
        velY = 0;
        setIV(i);
        width = this.getBoundsInLocal().getWidth();
        height = this.getBoundsInLocal().getHeight();
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.ID = id;
    }

}
