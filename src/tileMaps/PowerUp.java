package tileMaps;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
