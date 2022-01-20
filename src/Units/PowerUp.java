package Units;

import LevelPlayer.GamePane;
import LevelPlayer.GameUpdate;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PowerUp extends Sprite {
    int size;
    int duration;
    PowerUpType type;
    float speed;

    public PowerUp(Image i){
        setIV(i);
        setHeight(50);
        setWidth(50);
        this.speed = 0;
    }
    public PowerUp(Image i, int speed, int duration){
        this(i);
        this.speed = speed;
        this.duration = duration;
    }
    public PowerUp(double x, double y, Image i){
        this(i);
        this.speed = 0;
        this.setX(x);
        this.setY(y);
        size = 50;
    }

    public PowerUp(double x, double y, Image i, int speed, int duration){
        this(x,y, i);
        this.speed = speed;
        this.duration = duration;
    }
    public PowerUp(double x, double y, Image i, PowerUpType type){
        this(x, y, i);
        speed = 0;
        this.type = type;
    }

    public PowerUp(double x, double y, Image i, int speed, int duration, PowerUpType type){
        this(x, y, i, speed, duration);
        this.type = type;
    }
    public int getSize(){return size;}
}
