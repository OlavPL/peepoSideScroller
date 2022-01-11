package V0_3;

import javafx.scene.image.Image;

public class Player extends Sprite{
    Image moveRight;
    Image moveLeft;
    private final int playerWidth = 60;
    private final int playerHeight = 60;
    boolean falling = false;
    boolean canJump = true;
    final private int baseXSpeed = 8;
    public int xSpeed = 8;
    final private int baseYSpeed = 30;
    public int ySpeed = 30;

    public Player(Image right, Image left){
            velX = 0;
            velY = 0;
            setIV(right);
            width = this.getBoundsInLocal().getWidth();
            height = this.getBoundsInLocal().getHeight();
            moveRight = right;
            moveLeft = left;
            this.setFitWidth(playerHeight); this.setFitHeight(playerHeight);
    }

    public int getPlayerHeight() {
        return playerHeight;
    }
    public int getPlayerWidth() {
        return playerWidth;
    }
    public int getBaseXSpeed() {
        return baseXSpeed;
    }
    public int getBaseYSpeed() {
        return baseYSpeed;
    }
}
