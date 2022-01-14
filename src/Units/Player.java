package Units;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Player extends Sprite {
    Image moveRight;
    Image moveLeft;
    final private int BASE_X_SPEED = 8;
    final private int BASE_Y_SPEED = 30;
    public int xSpeed = 8;
    public int ySpeed = 30;
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
        try {
            velX = 0;
            velY = 0;
            moveRight = new Image(new FileInputStream("images/peepoSpeedR.gif"));
            moveLeft = new Image(new FileInputStream("images/peepoSpeedL.gif"));
            setIV(moveRight);
            setWidth(60);
            setHeight(60);

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

        }catch (FileNotFoundException FNFexc){
            FNFexc.printStackTrace();
        }
    }


    public void playerJump(){
        if (canJump) {
            setVelocity(Velocity.add(0,-ySpeed));
            canJump = false;
        }
    }

    public boolean getCanJump(){return canJump;}
    public void setCanJump(boolean bool){canJump = bool;}
    public boolean isFalling(){return isFalling;}
    public void setIsFalling(boolean bool){isFalling = bool;}
    public void moveLeft(){setIV(moveLeft);}
    public void moveRight(){setIV(moveRight);}
    public int getXSpeed(){return xSpeed;}
    public void setXSpeed(int speed){xSpeed = speed;}
    public int getYSpeed(){return ySpeed;}
    public void setYSpeed(int speed){ySpeed = speed;}

    public void jumpPowerUp(int duration, int speed){
        jCounter = duration;
        setYSpeed(speed);
        pwrJTimer.start();
    }
    public void speedPowerUp(int duration, int speed){
        sCounter = duration;
        setXSpeed(speed);
        pwrSTimer.start();
    }

    public Point2D getVelocity() {return Velocity;}
    public void setVelocity(Point2D velocity) {Velocity = velocity;}
    public Point2D getTempVelocity() {return tempVelocity;}
    public void setTempVelocity(Point2D tempVelocity) {
        this.tempVelocity = tempVelocity;
    }


}
