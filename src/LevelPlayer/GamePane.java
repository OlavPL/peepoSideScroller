package LevelPlayer;

import MainMenu.Main;
import MainMenu.Meth;
import Units.Player;
import Units.PowerUp;
import Units.Platform;
import Units.PowerUpType;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.*;
import java.util.*;

public class GamePane extends Pane {

    private final Pane uiPane;
    private final HBox coinScore;
    public Text score;
    private final int tileSize = 50;
    private final Player player;

    private final GameUpdate gameUpdate;
    private final ArrayList<Platform> platforms;
    private final ArrayList<PowerUp> coins;
    private final ArrayList<PowerUp> speeds;
    private final ArrayList<PowerUp> jumps;
    ImageView background;
    String path;
    public GamePane(String path, Pane rootPane){

        this.path = path;
        uiPane = new Pane();
        coinScore = new HBox();
        background = new ImageView(new Image (
            Objects.requireNonNull(getClass().getClassLoader().
            getResourceAsStream( "Images/valentineMapBig.PNG")))
        );
        background.setX(0);
        background.setX(0);
        player = new Player();
        getChildren().addAll(background, player);
        Image coinImg, speedImg, jumpImg;
        platforms = new ArrayList<>();
        coins = new ArrayList<>();
        speeds = new ArrayList<>();
        jumps = new ArrayList<>();
        coinImg = new Image (
            Objects.requireNonNull(getClass().getClassLoader().
            getResourceAsStream("Images/pepeCoin50.PNG"))
        );
        speedImg = new Image (
            Objects.requireNonNull(getClass().getClassLoader().
            getResourceAsStream("Images/peepoRun50.PNG"))
        );
        jumpImg = new Image (
            Objects.requireNonNull(getClass().getClassLoader().
            getResourceAsStream("Images/peepoJump50.PNG"))
        );

        gameUpdate = new GameUpdate(player,this);

//        Reads specified file and constructs the level based in the txt file characters.
        ArrayList<String> lines = new ArrayList<>();
        int width = 0;
        int height = 0;
        try {
            InputStream in = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    reader.close();
                    break;
                }
                lines.add(line);
                width = Math.max(width, line.length());
                height++;
            }
        }catch (IOException IOexc){
            IOexc.printStackTrace();
        }

        height = lines.size();
        for (int y = 0; y<height; y++){
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++){
                char ch = line.charAt(x);
                int tile = ch - 'A';
                if (tile>=0 && tile < lines.size()){
                    break;
                }
                    switch (ch) {
                        case '#' -> platforms.add(new Platform(
                                x * tileSize, y * tileSize, tileSize, tileSize, Color.DARKOLIVEGREEN));
                        case '1' -> platforms.add(0, new Platform(
                                    x * tileSize, y * tileSize, tileSize, tileSize, Color.GOLD));
                        case '2' -> coins.add(new PowerUp(x * tileSize, y * tileSize, coinImg,  PowerUpType.Coin));
                        case '3' -> speeds.add(new PowerUp(x * tileSize, y * tileSize, speedImg,16, 120, PowerUpType.Speed));
                        case '4' -> jumps.add(new PowerUp(x * tileSize, y * tileSize, jumpImg,50, 120, PowerUpType.Jump));
                        case '@' -> {player.setTranslateY(y * tileSize);  player.setTranslateX(x * tileSize);}
                    }
//                if (ch == '#'){
//                    platforms.add(new Platform(
//                            x * tileSize, y * tileSize, tileSize, tileSize, Color.DARKOLIVEGREEN));
//                }
//                if (ch =='1'){
//                    platforms.add(0, new Platform(
//                            x * tileSize, y * tileSize, tileSize, tileSize, Color.GOLD));
//                }
//                if(ch =='2'){
//                    coins.add(new PowerUp(x * tileSize, y * tileSize, coinImg,  PowerUpType.Coin));
//                }
//                if (ch =='3'){
//                    speeds.add(new PowerUp(
//                            x * tileSize, y * tileSize, speedImg,16, 120, PowerUpType.Speed));
//                }
//                if (ch =='4'){
//                    jumps.add(new PowerUp(x * tileSize, y * tileSize, jumpImg,50, 120, PowerUpType.Jump));
//                }
//                if (ch =='@'){
//                    player.setTranslateY(y * tileSize);  player.setTranslateX(x * tileSize);
//                }
            }
        }

        for (PowerUp coin : coins) {
            getChildren().add(coin);
        }
        for (PowerUp speed : speeds) {
            getChildren().add(speed);
        }
        for (PowerUp jump : jumps) {
            getChildren().add(jump);
        }
        for (Platform platform : platforms) {
            getChildren().add(platform);
        }

        moveView();
        score = new Text(50,20,""+gameUpdate.getPoints());
        score.setX(25);
        score.setY(85);
        score.setFont(Font.font("Comic Sans", FontWeight.BOLD,30));
        updateScore();
        uiPane.getChildren().add(coinScore);
        rootPane.getChildren().addAll(this, uiPane);
        setWidth(width*50);
        setHeight(height*50);
        gameUpdate.start();
    }

    private void moveView() {
        int sHalfW = (int) Meth.getScreenW()/2;
        int sHalfH = (int) Meth.getScreenH()/2;
        player.translateXProperty().addListener((abs, PosX, newPosX) -> {
            int offset = newPosX.intValue();

            if (offset > sHalfW && offset < background.getBoundsInParent().getWidth() - sHalfW) {
                setLayoutX(-(offset - sHalfW));
            }
        });

        player.translateYProperty().addListener((abs, PosY, newPosY) -> {
            int offset = newPosY.intValue();

            if (offset > sHalfH && offset < background.getBoundsInParent().getHeight() - sHalfH) {
                setLayoutY(-(offset - sHalfH));
            }
        });
    }

    public void updateScore(){
        uiPane.getChildren().remove(score);
        score.setText(""+gameUpdate.getPoints());
        uiPane.getChildren().add(score);

    }
    public HBox getCoinScore(){return coinScore;}

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
    public void clearPlatforms(){platforms.clear();}
    public ArrayList<PowerUp> getPowerList(){
        ArrayList<PowerUp> arr = new ArrayList<>();
        arr.addAll(coins);
        arr.addAll(jumps);
        arr.addAll(speeds);
        return arr;
    }
    public void clearPowerList(){
        coins.clear();
        jumps.clear();
        speeds.clear();
    }
    public ArrayList<PowerUp> Coins() {return coins;}
    public ArrayList<PowerUp> Jumps() {return jumps;}
    public ArrayList<PowerUp> Speeds() {return speeds;}

    public Pane getUiPane() {
        return uiPane;
    }
    public void restartLevel() {
        Main.setLevelScene(path);
    }
    public void setScene(Scene scene){
        gameUpdate.setScene(scene);
    }
}
