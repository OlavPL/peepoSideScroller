package V0_1;

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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.util.ArrayList;
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
    private final HBox coinScore2 = new HBox();

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
    ArrayList<ImageView> pointsAL;

    //Player functionality
    public Node player;
    public ImageView playerRunR;
    public ImageView playerRunL;
    private Point2D playerVelocity = new Point2D(0,0);
    private Point2D tempPlayerVel = new Point2D(0,0);
    int playerWidth = 50;
    int playerHeight = 60;
    boolean falling;
    boolean canJump;
    private HashMap<KeyCode, Boolean> keys;

    final private int baseXSpeed = 12;
    public int xSpeed = 12;
    final private int baseYSpeed = 30;
    public int ySpeed = 30;
    public int powerID = 0;
    public static int ppSize = 60;
    public int points = 0;

    public double sCounter;
    public double jCounter;

    //Platforms
    private ArrayList<Node> platforms = new ArrayList<>();
    public ArrayList<Node> jumpPWRS = new ArrayList<>();
    public ArrayList<ImageView> ivJumpPWRS = new ArrayList<>();
    public ImageView iVPJump;
    public ArrayList<Node> speedPWRS = new ArrayList<>();
    public ArrayList<ImageView> ivSpeedPWRS = new ArrayList<>();
    public ImageView iVPRun;
    public ArrayList<Node> coinPWRS = new ArrayList<>();
    public ArrayList<ImageView> ivCoinPWRS = new ArrayList<>();
    public ArrayList<ImageView> coinS = new ArrayList<>();
    public ImageView iVPCoin;
    public ImageView iVPCoinScore;
    public Image coinGrey;

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
                        xSpeed = baseXSpeed;
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
                        ySpeed = baseYSpeed;
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

        speedImg70 = new Image(new FileInputStream( "images/peepoRun70.png"));
        iVPRun = new ImageView(speedImg70);

        jumpImg70 = new Image(new FileInputStream( "images/peepoJump70.png"));
        iVPJump = new ImageView(jumpImg70);
        iVPJump.setX(2800);
        iVPJump.setY(300);
        iVPJump.setFitWidth(ppSize);
        iVPJump.setFitHeight(ppSize);

        coinImg35 = new Image(new FileInputStream("images/pepeCoin35.png"));
        coinImg60 = new Image(new FileInputStream("images/pepeCoin60.png"));
        iVPCoin = new ImageView(coinImg60);

        Image ioPCoin35 = new Image(new FileInputStream("images/pepeCoin35.png"));
        iVPCoinScore = new ImageView(ioPCoin35);
        coinGrey = new Image(new FileInputStream("images/pepeCoin35Grey.png"));

        tempImg = new Image(new FileInputStream("images/valentineWon.png"));
        wBGIV = new ImageView(tempImg);
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
        gamePane.setPrefSize(bgWidth, bgWidth);

        score = new Text(50,20,""+points);
        score.setX(100);
        score.setY(50);
        score.setFont(new Font("Comic Sans", 20));
        updateScore();

        createPlatforms();
        createSpeedP();
        placeSpeedPWRS();
        createJumpP();
        placeJumpPWRS();
        createCoinP();
        placeCoinPWRS();
        player = createEntity(5,5,playerWidth,playerHeight, Color.TRANSPARENT);
        canJump = true;
        }

        moveView();
//initialize player ImageView
        tempImg = new Image(new FileInputStream("images/peepoSpeedR.gif"));
        playerRunR = new ImageView(tempImg);
        playerRunR.setX(player.getTranslateX()-5); playerRunR.setY(player.getTranslateY());
        playerRunR.setFitWidth(playerHeight); playerRunR.setFitHeight(playerHeight);

        tempImg = new Image(new FileInputStream("images/peepoSpeedL.gif"));
        playerRunL = new ImageView(tempImg);
        playerRunL.setX(player.getTranslateX()); playerRunL.setY(player.getTranslateY());
        playerRunL.setFitWidth(playerHeight); playerRunL.setFitHeight(playerHeight);
        playerRunL.setVisible(false);


        gamePane.getChildren().add(background);
        for (Node jumpP : jumpPWRS){
            gamePane.getChildren().add(jumpP);
        }
        for (ImageView iv : ivJumpPWRS){
            gamePane.getChildren().add(iv);
        }
        for (Node speedP : speedPWRS){
            gamePane.getChildren().add(speedP);
        }
        for (ImageView iv : ivSpeedPWRS){
            gamePane.getChildren().add(iv);
        }
        for (Node coinP : coinPWRS){
            gamePane.getChildren().add(coinP);
        }
        for (ImageView iv : ivCoinPWRS){
            gamePane.getChildren().add(iv);
        }
        for (Node platform : platforms){
            gamePane.getChildren().add(platform);
        }
        gamePane.getChildren().addAll(player,playerRunR,playerRunL);

        ImageView pointS1 = new ImageView(coinGrey);
        ImageView pointS2 = new ImageView(coinGrey);
        ImageView pointS3 = new ImageView(coinGrey);
        pointsAL= new ArrayList<>();
        pointsAL.add(pointS1);
        pointsAL.add(pointS2);
        pointsAL.add(pointS3);
        for (ImageView iv: pointsAL) {
            coinScore2.getChildren().add(iv);
        }

        uiPane.getChildren().add(coinScore);
        uiPane.getChildren().add(coinScore2);
        root.getChildren().add(gamePane);
        root.getChildren().add(uiPane);
    }

    //Method for Node creation (Player & platforms & powers)
    private Node createEntity(int x, int y, int w, int h, Color color){
        Rectangle rectangle = new Rectangle(w,h, color);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);

        return rectangle;
    }

//    ~~Movement and ~~collisions

    //All game logic for the loop
    void update(){

        if (keyPressed(KeyCode.W) || keyPressed(KeyCode.UP) || keyPressed(KeyCode.SPACE) && player.getTranslateY() >= 0){
            playerJump();
        }

        if ((keyPressed(KeyCode.A) || keyPressed(KeyCode.LEFT)) && player.getTranslateX() >= 2){
            playerMoveX(-xSpeed);
        }

        if ((keyPressed(KeyCode.D) || keyPressed(KeyCode.RIGHT)) && player.getTranslateX() + playerWidth <= bgWidth - playerWidth+2) {
            playerMoveX(xSpeed);
        }

        if (keyPressed(KeyCode.S)){
                Point2D tempVel = new Point2D(0,50);
                tempPlayerVel = playerVelocity;
                playerVelocity = tempVel;
            }

         if (!keyPressed(KeyCode.S) && playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0,1);
        }

        switch (powerID) {
            case 0:
                break;
            case 1:
                xSpeed = 26;
                sCounter = 120;
                powerID = 0;
                pwrSTimer.start();
                break;

            case 2:
                ySpeed = 60;
                jCounter = 120;
                powerID = 0;
                pwrJTimer.start();
                break;
        }
        playerMoveY((int)playerVelocity.getY());
    }

    //player jump Game logic
    private void playerJump(){
        if (canJump) {
            playerVelocity = playerVelocity.add(0,-ySpeed);
            canJump = false;
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
            playerRunR.setVisible(true);
            playerRunL.setVisible(false);
        }
        if (movingLeft){
            playerRunR.setVisible(false);
            playerRunL.setVisible(true);
        }
        for (int i = 0; i < Math.abs(value); i++) {
            if (powerCheck(jumpPWRS,2, pwrSTimer, ivJumpPWRS))
            if (powerCheck(speedPWRS,1, pwrJTimer, ivSpeedPWRS))
            if (powerCCheck(coinPWRS, ivCoinPWRS))
            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getTranslateX() + playerWidth == platform.getTranslateX()) {
                            return;
                        }
                    }else {
                        if (player.getTranslateX() == platform.getTranslateX() + platform.getBoundsInParent().getWidth()) {
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1:-1));
            playerRunR.setTranslateX(player.getTranslateX()-5 + (movingRight ? 1:-1));

            playerRunL.setTranslateX(player.getTranslateX()-5 + (movingRight ? 1:-1));
        }
    }

    //Y-Axis player logic
    private void playerMoveY(int value){
        falling = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
//Win state
            if (player.getBoundsInParent().intersects(platforms.get(1).getBoundsInParent())) {
                if (player.getTranslateY() + playerHeight >= platforms.get(1).getTranslateY()
                    && player.getTranslateY() + playerHeight <= platforms.get(1).getTranslateY()+5) {
                    win();
                }
//Loss state
            } else if (player.getBoundsInParent().intersects(platforms.get(2).getBoundsInParent())) {
                if (player.getTranslateY() + playerHeight >= platforms.get(2).getTranslateY()
                        && player.getTranslateY() + playerHeight <= platforms.get(2).getTranslateY()+5)
                    lose();
            } else {
//Collision check
            if (powerCheck(jumpPWRS,2, pwrSTimer, ivJumpPWRS))
            if (powerCheck(speedPWRS,1, pwrJTimer, ivSpeedPWRS))
            if (powerCCheck(coinPWRS, ivCoinPWRS))
                if (falling) {
                    for (Node node : platforms) {
                        if (player.getBoundsInParent().intersects(node.getBoundsInParent())) {
                            if (player.getTranslateY() + playerHeight == node.getTranslateY()) {
                                if (player.getTranslateY() + playerHeight == node.getTranslateY()) {
                                    player.setTranslateY(player.getTranslateY() - 1);
                                    canJump = true;
                                    return;
                                }
                            }
                        }
                    }
                }
                player.setTranslateY(player.getTranslateY() + (falling? 1:-1));
                playerRunR.setTranslateY(player.getTranslateY() - 3 + (falling? 1:-1));
                playerRunL.setTranslateY(player.getTranslateY() - 3 + (falling? 1:-1));
            }
        }
    }

    //Powers Collider
    private boolean powerCheck(ArrayList<Node> al, int ID, AnimationTimer aniTimer, ArrayList<ImageView> ivAL) {
        if (!al.isEmpty()) {
           for (Node node : al) {
                if (player.getBoundsInParent().intersects(node.getBoundsInParent())) {
                    powerID = ID;
                    points++;
                    updateScore();
                    aniTimer.start();
                    setAway(al, ivAL, node);
                }
            }
            return true;
        }
        return false;
    }

    private boolean powerCCheck( ArrayList<Node> al, ArrayList<ImageView> ivAL){
        if (!al.isEmpty()) {
            for (Node node : al) {
                int index = al.indexOf(node);
                if (player.getBoundsInParent().intersects(node.getBoundsInParent())) {
                    points++;
                    updateScore();
                    coinScore.getChildren().add(coinS.get(index));
                    setAway(al, ivAL, node);
                }
            }
            return true;
        }
        return false;
    }


    private void setAway(ArrayList<Node> al, ArrayList<ImageView> ivAL, Node node) {
        node.setTranslateX(-500);
        node.setTranslateY(-500);
        node.getId();

        int index = al.indexOf(node);
        ivAL.get(index).setTranslateX(-500);
        ivAL.get(index).setTranslateY(-500);
    }

    //Creates all the platforms
    private void createPlatforms(){
        int platH = 30;
        int platW = 500;
        final Color platColor = Color.DARKOLIVEGREEN;

        Node startPlatform = createEntity(0, 170, platW, 50, platColor);
        platforms.add(startPlatform);

        Node winPlatform = createEntity(bgWidth-1000, bgHeight-500, platW, platH, Color.GOLD);
        platforms.add(winPlatform);

        Node lostPlatform = createEntity(0, bgHeight, bgWidth, platH, platColor);
        platforms.add(lostPlatform);

        Node platform1 = createEntity(800, 500, platW, platH, platColor);
        platforms.add(platform1);

        Node platform2 = createEntity(1700, 500, platW, platH, platColor);
        platforms.add(platform2);

        Node platform3 = createEntity(2400, 350, platW, platH, platColor);
        platforms.add(platform3);

        Node platform4 = createEntity(5100, 400, platW*3, platH, platColor);
        platforms.add(platform4);

        Node platform5 = createEntity(6000, 400-1000, 50, 1000, platColor);
        platforms.add(platform5);

        Node platform6 = createEntity(5600, 900, 300, platH, platColor);
        platforms.add(platform6);

        Node platform7 = createEntity(2000, 800, platW, platH, platColor);
        platforms.add(platform7);

        Node platform8 = createEntity(1700, 500, 50, 1000, platColor);
        platforms.add(platform8);


    }


    //Creates all the powers
    private void createSpeedP(){
        Node xSpeed1 = createEntity(2200, 300, 50, 50, Color.TRANSPARENT);
        speedPWRS.add(xSpeed1);
        Node xSpeed2 = createEntity(2600, 300, 50, 50, Color.TRANSPARENT);
        speedPWRS.add(xSpeed2);
    }
    private void placeSpeedPWRS(){
        ImageView speedPIV1 = new ImageView();
        setIV(speedPIV1, iVPRun, ppSize, ivSpeedPWRS, speedPWRS.get(0));

        ImageView speedPIV2 = new ImageView();
        setIV(speedPIV2, iVPRun, ppSize, ivSpeedPWRS, speedPWRS.get(1));
    }
    private void createJumpP(){
        Node ySpeed1 = createEntity(2800, 300, 50, 50, Color.TRANSPARENT);
        jumpPWRS.add(ySpeed1);
        Node ySpeed2 = createEntity(2400, 200, 50, 50, Color.TRANSPARENT);
        jumpPWRS.add(ySpeed2);
    }
    private void placeJumpPWRS(){
        ImageView jumpPIV1 = new ImageView();
        setIV(jumpPIV1, iVPJump, ppSize, ivJumpPWRS, jumpPWRS.get(0));

        ImageView jumpPIV2 = new ImageView();
        setIV(jumpPIV2, iVPJump, ppSize, ivJumpPWRS, jumpPWRS.get(1));
    }
    private void createCoinP() throws  Exception{
        Node coin1 = createEntity(6050, 300, 50, 50, Color.TRANSPARENT);
        coinPWRS.add(coin1);
        ImageView coinS1 = new ImageView( new Image(new FileInputStream("images/pepeCoin35.png")));
        coinS.add(coinS1);

        Node coin2 = createEntity(5800, 300, 50, 50, Color.TRANSPARENT);
        coinPWRS.add(coin2);
        ImageView coinS2 = new ImageView( new Image(new FileInputStream("images/pepeCoin35.png")));
        coinS.add(coinS2);
    }
    private void placeCoinPWRS(){
        ImageView coinPIV1 = new ImageView();
        setIV(coinPIV1, iVPCoin, ppSize, ivCoinPWRS, coinPWRS.get(0));

        ImageView coinPIV2 = new ImageView();
        setIV(coinPIV2, iVPCoin, ppSize, ivCoinPWRS, coinPWRS.get(1));
    }
    private void setIV(ImageView iv, ImageView image, int size, ArrayList iva, Node node){
        iv.setImage(image.getImage());
        iv.setX(node.getTranslateX());
        iv.setY(node.getTranslateY());
        iv.setFitWidth(size);
        iv.setFitHeight(size);
        iva.add(iv);
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
                try {
                    lostTimer.stop();
                    wonTimer.stop();
                    gameTimer.start();
                    points = 0;
//                    Make a clear all method;
                    platforms.clear();
                    speedPWRS.clear();
                    ivSpeedPWRS.clear();
                    jumpPWRS.clear();
                    ivJumpPWRS.clear();
                    coinPWRS.clear();
                    ivCoinPWRS.clear();
                    coinScore.getChildren().clear();
                    uiPane.getChildren().clear();
                    gamePane.getChildren().clear();
                    root.getChildren().clear();
                    initContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }
// player ~~View
    private void moveView(){
        int sHalfW = (int)Meth.getScreenW()/2;
        int sHalfH = (int)Meth.getScreenH()/2;
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
