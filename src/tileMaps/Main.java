package tileMaps;

import V0_2.Meth;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.StyledEditorKit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/*Husker nesten ingen, men her er noen av kildene
 * https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
 *https://docs.oracle.com/javase/8/javafx/api/overview-summary.html
 * https://longbaonguyen.github.io/courses/platformer/platformer.html
 *  */

// Use ~~ before keywords to reach for functions in comments
public class Main extends Application {
    //Background graphic
    Image tempImg;
    ImageView background;
    public AnimationTimer gameTimer;
    public AnimationTimer wonTimer;
    public AnimationTimer lostTimer;
    private AnimationTimer pwrSTimer;
    private AnimationTimer pwrJTimer;

    private final Pane root = new Pane();
    private final Pane gamePane = new Pane();
    private final Pane uiPane = new Pane();
    private final HBox coinScore = new HBox();

    Image coinImg60;
    Image coinImg35;
    Image speedImg70;
    Image jumpImg70;

    private Button tryAgain;
    private Text endGameText;
    private Text endGameText2;
    // Background width = 7680px, height = 4320px;
    private final int bgWidth = 7680;
    private final int bgHeight = 4320;
    private ImageView wBGIV;
    private ImageView lBGIV;
    private ImageView peepoHappy;
    private ImageView ohno;
    public Text score;
    ArrayList<Sprite> pointsAL;

    //Player functionality
    Player player;
    private Point2D playerVelocity = new Point2D(0,0);
    private Point2D tempPlayerVel = new Point2D(0,0);

    private HashMap<KeyCode, Boolean> keys;
    public KeyCodeCombination ctrlR = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);


    public int powerID = 0;
    public static int ppSize = 60;
    public int points = 0;

    public double sCounter;
    public double jCounter;

    //Platforms
    private ArrayList<Platform> platforms;
    public Image coinGrey;
    ArrayList<PowerUp> powerList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initContent();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);

            if (event.getCode() == KeyCode.S){
                playerVelocity = new Point2D(0,10);
            }
        });
        Meth.setFillStage(primaryStage);
        primaryStage.setTitle("EpicGamerTime");
        primaryStage.setScene(scene);
        primaryStage.show();

//~~Animation Timers
            {
            gameTimer = new AnimationTimer() {
                @Override
                public void handle(long currentNanoTime) {
                    try {
                        update();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            gameTimer.start();

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

            pwrSTimer = new AnimationTimer() {
                public void handle(long sTime) {
                    if (sCounter > 0){
                        sCounter--;
                    }
                    else{
                        player.xSpeed = player.getBaseXSpeed();
                        pwrSTimer.stop();
                    }
                }
            };

            pwrJTimer = new AnimationTimer() {
                public void handle(long sTime) {
                    if (jCounter > 0){
                        jCounter--;
                    }
                    else{
                        player.ySpeed = player.getBaseYSpeed();
                        pwrJTimer.stop();
                    }
                }
            };
        }
    }
//Initialize
    private void initContent() throws Exception{
        keys = new HashMap<>();
//ImageViews
        {
        tempImg = new Image(new FileInputStream( "images/valentineMapBig.png"));
        background = new ImageView(tempImg);
        background.setX(0);
        background.setX(0);

        wBGIV = new ImageView(new Image(new FileInputStream("images/valentineWon.png")));
        wBGIV.setX(0);
        wBGIV.setY(0);

        tempImg = new Image(new FileInputStream("images/peepoHappy.png"));
        peepoHappy = new ImageView(tempImg);

        tempImg = new Image(new FileInputStream("images/lossBackground.png"));
        lBGIV = new ImageView(tempImg);
        lBGIV.setX(0);
        lBGIV.setY(0);

        tempImg = new Image(new FileInputStream("images/ohno.png"));
        ohno = new ImageView(tempImg);
        }


//Initializing all panes
        {
        root.setBackground(new Background(new BackgroundFill(Color.rgb(0, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        gamePane.setPrefSize(bgWidth, bgHeight);

        score = new Text(50,20,""+points);
        score.setX(100);
        score.setY(75);
        score.setFont(Font.font("Comic Sans", FontWeight.BOLD,30));
        updateScore();

//        createPlatforms();

        player = new Player(
        new Image(new FileInputStream("images/peepoSpeedR.gif")),
        new Image(new FileInputStream("images/peepoSpeedL.gif"))
        );
        }
        moveView();
        gamePane.getChildren().add(background);
        gamePane.getChildren().add(player);

        Map map1 = Map.newMap("maps/map1");
        map1.loadMap(gamePane);
//        platforms = new ArrayList<>();
//        platforms.addAll(map1.getPlatforms());

        platforms = new ArrayList<>();
        platforms.addAll(map1.Platforms());

        powerList = new ArrayList<>();
        powerList.addAll(map1.Coins());
        powerList.addAll(map1.Speeds());
        powerList.addAll(map1.Jumps());

/*      loadPwrPoint();
        for (int i = 0; i < powerList.size(); i++) {
            gamePane.getChildren().add(powerList.get(i));
        }
        for (Node platform : platforms){
            gamePane.getChildren().add(platform);
        }*/
        root.getChildren().add(gamePane);

        uiPane.getChildren().add(coinScore);
        root.getChildren().add(uiPane);
    }

//    ~~Movement and ~~collisions
    //All game logic for the loop
    void update(){

        if (keyPressed(KeyCode.W) || keyPressed(KeyCode.UP) || keyPressed(KeyCode.SPACE) && player.getTranslateY() >= 0){
            playerJump();
        }

        if ((keyPressed(KeyCode.A) || keyPressed(KeyCode.LEFT)) && player.getTranslateX() >= 2){
            playerMoveX(-player.xSpeed);
        }

        if ((keyPressed(KeyCode.D) || keyPressed(KeyCode.RIGHT))
            && player.getTranslateX() + player.getPlayerWidth() <= bgWidth - player.getPlayerWidth()+2) {
            playerMoveX(player.xSpeed);
        }

        if (keyPressed(KeyCode.S)){
                Point2D tempVel = new Point2D(0,50);
                tempPlayerVel = playerVelocity;
                playerVelocity = tempVel;
        }

         if (!keyPressed(KeyCode.S) && playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0,1);
         }

        if (keyPressed(KeyCode.R)){
            restartLevel();
        }

        switch (powerID) {
            case 0:
                break;
            case 1:
                player.xSpeed = 16;
                sCounter = 120;
                powerID = 0;
                pwrSTimer.start();
                break;

            case 2:
                player.ySpeed = 50;
                jCounter = 120;
                powerID = 0;
                pwrJTimer.start();
                break;
        }
        playerMoveY((int)playerVelocity.getY());
    }

    //player jump Game logic
    private void playerJump(){
        if (player.canJump) {
            playerVelocity = playerVelocity.add(0,-player.ySpeed);
            player.canJump = false;
        }
    }

    //Key pressed checker
    private boolean keyPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    //X-axis player logic
    private void playerMoveX(int value){
        boolean movingRight = value > 0;
        boolean movingLeft = value < 0;
        if (movingRight){
            player.setIV(player.moveRight);
        }
        if (movingLeft){
            player.setIV(player.moveLeft);
        }
        for (int i = 0; i < Math.abs(value); i++) {
            if (powerCol(powerList))
                for (int j = 0; j<platforms.size(); j++) {
                if (player.getBoundsInParent().intersects(platforms.get(j).getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getTranslateX() + player.getPlayerWidth() == platforms.get(j).getTranslateX()) {
                            player.setTranslateX(player.getTranslateX()-1);
                            return;
                        }
                    }else {
                        if (player.getTranslateX() == platforms.get(j).getTranslateX() + platforms.get(j).getBoundsInParent().getWidth()) {
                            player.setTranslateX(player.getTranslateX()+1);
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1:-1));
        }
    }

    //Y-Axis player logic
    private void playerMoveY(int value){
        player.falling = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
//Win state
            if (player.getBoundsInParent().intersects(platforms.get(0).getBoundsInParent())) {
                    win();
//Loss state
            } else if (player.getTranslateY()+player.getPlayerHeight() >= bgHeight){
                    lose();
            } else {
//Collision check
                if (powerCol(powerList))
                if (player.falling) {
                    for (int j = 0; j<platforms.size(); j++) {
                        if (player.getBoundsInParent().intersects(platforms.get(j).getBoundsInParent())) {
                            if (player.getTranslateY() + player.getPlayerHeight() == platforms.get(j).getTranslateY()) {
                                player.setTranslateY(player.getTranslateY() - 1);
                                player.canJump = true;
                                return;

                            }
                        }
                    }
                }else{
                    for (int j = 0; j<platforms.size(); j++) {
                        if (player.getBoundsInParent().intersects(platforms.get(j).getBoundsInParent())) {
                            if (player.getTranslateY() == platforms.get(j).getTranslateY()+platforms.get(j).getHeight()){
                                player.setTranslateY(player.getTranslateY() + 1);
                                return;
                            }
                        }
                    }
                }
                player.setTranslateY(player.getTranslateY() + (player.falling? 1:-1));
            }
        }
    }

    //Powers Collider
    private boolean powerCol( ArrayList<PowerUp> al){
        if (!al.isEmpty()) {
            for (PowerUp pp : powerList) {
                int index = al.indexOf(pp);
                if (player.getBoundsInParent().intersects(pp.getX(),pp.getY(),ppSize,ppSize)) {
                    points++;
                    updateScore();
                    setAway2(pp);
                    switch (pp.ID) {
                        case 0 -> {coinScore.getChildren().add(pp);}
                        case 1 -> {powerID = pp.ID; pwrSTimer.start();}
                        case 2 -> {powerID = pp.ID; pwrJTimer.start();}
                    }

                }
            }
            return true;
        }
        return false;
    }

    private void setAway2(PowerUp pp) {
        pp.setX(-500);
        pp.setY(-500);
    }

    private void updateScore(){
        uiPane.getChildren().remove(score);
        score.setText(""+points);
        uiPane.getChildren().add(score);
    }

    public void win() {
        gamePane.setLayoutX(0);
        gamePane.setLayoutY(0);
        gameTimer.stop();
        gamePane.getChildren().clear();

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
        gamePane.getChildren().addAll(wBGIV,peepoHappy, endGameText, endGameText2, tryAgain);
    }

    public void lose(){
        gamePane.setLayoutX(0);
        gamePane.setLayoutY(0);
        gameTimer.stop();
        gamePane.getChildren().remove(background);
        gamePane.getChildren().clear();

        endGameText = new Text(10,50,"Ohno, you did a lost");
        endGameText.setTranslateX(500); endGameText.setTranslateY(250);
        endGameText.setFont(new Font("comic sans", 100));
        endGameText.setFill(Color.DARKBLUE);
        againButton("Go Agane");

        gamePane.getChildren().addAll(lBGIV,ohno, endGameText, tryAgain);
        lostTimer.start();
    }

    private void againButton(String text) {
        tryAgain = new Button(text);
        tryAgain.setPrefSize(200,50);
        tryAgain.setFont(new Font(20));
        tryAgain.setTranslateX(960-100);
        tryAgain.setTranslateY(540-25);

        tryAgain.setOnAction(actionEvent -> {
               restartLevel();
        });
    }

    private void restartLevel(){
        try {
            lostTimer.stop();
            wonTimer.stop();
            gameTimer.start();
            points = 0;

//          Make a clear all method;
            powerList.clear();
            coinScore.getChildren().clear();
            uiPane.getChildren().clear();
            platforms.clear();
            uiPane.getChildren().clear();
            gamePane.getChildren().clear();
            root.getChildren().clear();
            gamePane.setLayoutX(0);
            gamePane.setLayoutY(0);
            initContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// player ~~View
    private void moveView() {
        int sHalfW = (int) Meth.getScreenW()/2;
        int sHalfH = (int) Meth.getScreenH()/2;
        player.translateXProperty().addListener((abs, PosX, newPosX) -> {
            int offset = newPosX.intValue();

            if (offset > sHalfW && offset < bgWidth - sHalfW) {
                gamePane.setLayoutX(-(offset - sHalfW));
            }
        });

        player.translateYProperty().addListener((abs, PosY, newPosY) -> {
            int offset = newPosY.intValue();

            if (offset > sHalfH && offset < bgHeight - sHalfH) {
                gamePane.setLayoutY(-(offset - sHalfH));
            }
        });
    }

    public static void main(String[] args ) {
        launch(args);
    }
}
