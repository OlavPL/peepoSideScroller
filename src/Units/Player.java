package Units;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter

public class Player extends Sprite {
    private final int PLAYER_SIZE = 45;
    Image moveRight;
    Image moveLeft;
    final private float BASE_X_SPEED = 7;
    final private float BASE_Y_SPEED = 30;
    public float xSpeed = 8;
    public float ySpeed = 30;
    boolean isFalling = true;
    boolean canJump = true;
    private Point2D tempVelocity = new Point2D(0,0);
    private Point2D Velocity = new Point2D(0,0);

    private AnimationTimer pwrSTimer;
    private AnimationTimer pwrJTimer;

    public double sCounter;
    public double jCounter;

    public Player(){
        super();
        velX = 0;
        velY = 0;
        moveRight = new Image(
            Objects.requireNonNull(getClass().getClassLoader().
            getResourceAsStream( "Images/peepoSpeedR.GIF")));
        moveLeft = new Image(
            Objects.requireNonNull(getClass().getClassLoader().
            getResourceAsStream( "Images/peepoSpeedL.GIF")));
        setIV(moveRight);
        setWidth(PLAYER_SIZE);
        setHeight(PLAYER_SIZE);

        pwrJTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
            jCounter--;
            if (jCounter<=0){
                setYSpeed(BASE_Y_SPEED);
                pwrJTimer.stop();
            }
            }
        };
        pwrSTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
            sCounter--;
            if (sCounter<=0){
                setXSpeed(BASE_X_SPEED);
                pwrSTimer.stop();
            }
            }
        };
    }


    public void playerJump(){
        if (canJump) {
            setVelocity(Velocity.add(0,-ySpeed));
            canJump = false;
        }
    }

    public boolean isFalling(){return isFalling;}
    public void setIsFalling(boolean bool){isFalling = bool;}
    public void moveLeft(){setIV(moveLeft);}
    public void moveRight(){setIV(moveRight);}

    public void jumpPowerUp(int duration, float speed){
        jCounter = duration;
        setYSpeed(speed);
        pwrJTimer.start();
    }
    public void speedPowerUp(int duration, float speed){
        sCounter = duration;
        setXSpeed(speed);
        pwrSTimer.start();
    }
}