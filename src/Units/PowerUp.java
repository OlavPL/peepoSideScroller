package Units;

import LevelPlayer.GamePane;
import LevelPlayer.GameUpdate;
import javafx.scene.image.Image;

public class PowerUp extends Sprite {
    int size;
    int duration;
    PowerUpType type;
    int speed;

    public PowerUp(Image i){
        setIV(i);
        setHeight(60);
        setWidth(60);
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
        size = 60;
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
    public void givePower(Player player){
        switch (type){
            case Speed -> {
                GamePane.Speeds().remove(this);
                player.speedPowerUp(duration, speed);
            }

            case Jump -> {
                GamePane.Jumps().remove(this);
                player.jumpPowerUp(duration, speed);
            }
            case Coin -> {
                GamePane.Coins().remove(this);
                GameUpdate.addPoint(this);
            }
        }
    }
}
