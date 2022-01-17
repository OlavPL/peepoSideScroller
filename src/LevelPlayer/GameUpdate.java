package LevelPlayer;

import MainMenu.Main;
import Units.Platform;
import Units.Player;
import Units.PowerUp;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameUpdate extends AnimationTimer {
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private final Player player;
    private final GamePane pane;

    private Button tryAgain;
    private Text endGameText;
    private Text endGameText2;

    public AnimationTimer wonTimer;
    public AnimationTimer lostTimer;
    private  ImageView wBGIV;
    private  ImageView lBGIV;
    private  ImageView peepoHappy;
    private  ImageView ohno;

    private static int points = 0;


    public GameUpdate (Player player, GamePane pane){
        super();
        points = 0;
        this.pane = pane;
        this.player = player;
        try {
            ohno = new ImageView(new Image(new FileInputStream("images/ohno.png")));
            peepoHappy = new ImageView(new Image(new FileInputStream("images/peepoHappy.png")));
            lBGIV = new ImageView(new Image(new FileInputStream("images/lossBackground.png")));
            lBGIV.setX(0);
            lBGIV.setY(0);
            wBGIV = new ImageView(new Image(new FileInputStream("images/valentineWon.png")));
            wBGIV.setX(0);
            wBGIV.setY(0);

        }catch (FileNotFoundException FNFexc){
            FNFexc.printStackTrace();
        }

        wonTimer = new AnimationTimer() {

            final long startNanoTime = System.nanoTime();

            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                peepoHappy.setTranslateX(1000 + 150 * Math.cos(t));
                peepoHappy.setTranslateY(165 + 55 * Math.sin(t));
            }
        };

        lostTimer = new AnimationTimer() {
            final long startNanoTime = System.nanoTime();

            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                ohno.setTranslateX(900 + 200 * Math.cos(t));
                ohno.setTranslateY(470 + 120 * Math.sin(t));
            }
        };
    }

    @Override
    public void handle(long currentNanoTime) {
        try {
            update(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean keyPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private void update(GamePane pane) {
        if (keyPressed(KeyCode.W) || keyPressed(KeyCode.UP) || keyPressed(KeyCode.SPACE) && player.getTranslateY() >= 0){
            player.playerJump();
        }

        if ((keyPressed(KeyCode.A) || keyPressed(KeyCode.LEFT)) && player.getTranslateX() >= 2){
            playerMoveX(-player.getXSpeed(), pane);
        }

        if ((keyPressed(KeyCode.D) || keyPressed(KeyCode.RIGHT))
                && player.getTranslateX() + player.getWidth() <= pane.getWidth() - player.getWidth()+2) {
            playerMoveX(player.getXSpeed(), pane);
        }

        if (keyPressed(KeyCode.S)){
            Point2D tempVel = new Point2D(0,50);
            player.setTempVelocity(player.getVelocity());
            player.setVelocity(tempVel);
        }

        if (!keyPressed(KeyCode.S) && player.getVelocity().getY() < 10) {
            player.setVelocity(player.getVelocity().add(0,1));
        }

        if (keyPressed(KeyCode.R)){
            restartLevel();
        }

        playerMoveY((int)player.getVelocity().getY(), pane);
    }

    //X-axis player logic
    private void playerMoveX(int value, GamePane pane){
        boolean movingRight = value > 0;
        boolean movingLeft = value < 0;
        if (movingRight){
            player.moveRight();
        }
        if (movingLeft){
            player.moveLeft();
        }
        for (int i = 0; i < Math.abs(value); i++) {
            powerCol(pane, pane.getPowerList());
            for (Platform platform : pane.getPlatforms()) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getTranslateX() + player.getWidth() == platform.getTranslateX()) {
                            player.setTranslateX(player.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (player.getTranslateX() == platform.getTranslateX() + platform.getBoundsInParent().getWidth()) {
                            player.setTranslateX(player.getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }
        player.setTranslateX(player.getTranslateX() + (movingRight ? 1:-1));
        }
    }

    //Y-Axis player logic
    private void playerMoveY(int value, GamePane pane){

    player.setIsFalling(value > 0);
    for (int i = 0; i < Math.abs(value); i++) {
//Win state
        if (player.getBoundsInParent().intersects(pane.getPlatforms().get(0).getBoundsInParent())) {
            win();
            return;
        }
//Loss state
        if (player.getTranslateY()+player.getHeight() >= pane.getHeight()){
            lose();
            return;
        }
        //Collision check
        powerCol(pane, pane.getPowerList());
        if (player.isFalling()) {
            for (Platform platform : pane.getPlatforms()) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (player.getTranslateY() + player.getHeight() == platform.getTranslateY()) {
                        player.setTranslateY(player.getTranslateY() - 1);
                        player.setCanJump(true);
                        return;
                    }
                }
            }
        }

        for (Platform platform : pane.getPlatforms()) {
            if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                if (player.getTranslateY() == platform.getTranslateY() + platform.getHeight()) {
                    player.setTranslateY(player.getTranslateY() - 1);
                    return;
                }
            }
        }

        player.setTranslateY(player.getTranslateY() + (player.isFalling()? 1:-1));
        }
    }

    private void powerCol(GamePane pane, ArrayList<PowerUp> al){
        try {
            for (PowerUp pp : al) {
                if (player.getBoundsInParent().intersects(pp.getX(), pp.getY(), pp.getSize(), pp.getSize())) {
                    pp.givePower(player);
                    removePowerUp(pane, pp);
                }
            }
        }
        catch (NullPointerException NPexc) {
            NPexc.printStackTrace();
        }
    }

    public void win() {
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        stop();
        pane.getChildren().clear();

        endGameText = new Text(10,50,"You won!");
        endGameText.setTranslateX(250); endGameText.setTranslateY(200);
        endGameText.setFont(new Font("comic sans", 100));
        endGameText.setFill(Color.GOLD);
        endGameText.setStroke(Color.BLACK);

        endGameText2 = new Text(10,50,"EPIC");
        endGameText2.setTranslateX(350); endGameText2.setTranslateY(300);
        endGameText2.setFont(new Font("comic sans", 100));
        endGameText2.setFill(Color.GOLD);
        endGameText2.setStroke(Color.BLACK);

        againButton("Let's GO AGANE");
        wonTimer.start();
        pane.getChildren().addAll(wBGIV,peepoHappy, endGameText, endGameText2, tryAgain);
    }

    public void lose(){
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        stop();
        pane.getChildren().clear();

        endGameText = new Text(10,50,"Ohno, you did a lost");
        endGameText.setTranslateX(500);
        endGameText.setTranslateY(200);
        endGameText.setFont(new Font("comic sans", 100));
        endGameText.setFill(Color.DARKBLUE);
        againButton("Go Agane");
        pane.getChildren().addAll(lBGIV,ohno, endGameText, tryAgain);
        lostTimer.start();
    }

    private void againButton(String text) {
        tryAgain = new Button(text);
        tryAgain.setPrefSize(200,50);
        tryAgain.setFont(new Font(20));
        tryAgain.setTranslateX(860);
        tryAgain.setTranslateY(515);

        tryAgain.setOnAction(actionEvent -> {
            restartLevel();
        });
    }

    private void restartLevel(){
//        try {
//            lostTimer.stop();
//            wonTimer.stop();
//            points = 0;
//
////          Make a clear all method;
//            pane.clearPowerList();
//            coinScore.getChildren().clear();
//            pane.getUiPane().getChildren().clear();
//            pane.clearPlatforms();
//            pane.getChildren().clear();
//            pane.setLayoutX(0);
//            pane.setLayoutY(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        pane.restartLevel();
    }



    public int getPoints(){return points;}
    public static void addPoint(PowerUp pp){
        points++;
        GamePane.updateScore();
        GamePane.getCoinScore().getChildren().add(pp);
    }
    public Player getPlayer(){return player;}

    public void removePowerUp(GamePane pane, PowerUp pp){
        pane.getChildren().remove(pp);
    }

    public void setScene(Scene scene){
        scene.setOnKeyPressed(event -> {
            keys.put(event.getCode(), true);
        });
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);

            if (event.getCode() == KeyCode.S){
                player.setVelocity(new Point2D(0,10));
            }
        });
    }
}


