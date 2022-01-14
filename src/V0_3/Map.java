package V0_3;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Map extends Pane {
    private char[][] tiles;
    private final int width = 50;
    private final int height = 50;
    private ArrayList<Platform> platforms;
    private ArrayList<PowerUp> coins, speeds, jumps;


    private static Image coinImg60, speedImg50, jumpImg50, peepoHappy;
    private ArrayList<Image> images = new ArrayList<>(Arrays.asList(peepoHappy, coinImg60, speedImg50, jumpImg50));

    public Map(int width, int height) throws FileNotFoundException {
        tiles = new char[width][height];
        platforms = new ArrayList<>();
        coins = new ArrayList<>();
        speeds = new ArrayList<>();
        jumps = new ArrayList<>();
        coinImg60 = new Image(new FileInputStream("images/pepeCoin60.png"));
        speedImg50 = new Image(new FileInputStream("images/peepoRun50.png"));
        jumpImg50 = new Image(new FileInputStream("images/peepoJump50.png"));
        peepoHappy = new Image(new FileInputStream("images/peepoHappy.png"));
    }

    public static Map newMap(String mapURL){
        try {
            ArrayList<String> lines = new ArrayList<>();
            int width = 0;
            int height = 0;
            java.io.File file = new File(mapURL);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (true){
                String line = reader.readLine();
                if (line == null) {
                    reader.close();
                    break;
                }

                if (!line.startsWith("/")){
                    lines.add(line);
                    width = Math.max(width, line.length());
                }
            }


            Map newMap = new Map(width, height);
            for (int y = 0; y<height; y++){
                String line = (String)lines.get(y);
                for (int x = 0; x < line.length(); x++){
                    char ch = line.charAt(x);
                    int tile = ch - 'A';
                    if (tile>=0 && tile < lines.size()){
                        break;
                    }
                    else{
                        switch (ch){

                            case '#': newMap.platforms.add(new Platform(
                                    x * newMap.width, y * newMap.height, newMap.width, newMap.width, Color.DARKOLIVEGREEN));
                            break;
                            case '1' : newMap.platforms.add(0, new Platform(
                                    x * newMap.width, y * newMap.height, newMap.width, newMap.width, Color.GOLD));
                            break;
                            case '2':  newMap.coins.add(new PowerUp(x*newMap.width, y*newMap.height, coinImg60, 0));
                            break;
                            case '3':  newMap.speeds.add(new PowerUp(x*newMap.width, y*newMap.height, speedImg50, 1));
                            break;
                            case '4':  newMap.jumps.add(new PowerUp(x*newMap.width, y*newMap.height, jumpImg50, 2));
                            break;
/*                            case '!':  newMap.enemies.add(new Enemy1(jumpImg70));
                            break;
                            case '@':  newMap.enemies.add(new Enemy2(jumpImg70));
                            break;*/
                        }
                    }
                }
            }
            return newMap;
        }catch (IOException filexc){
            filexc.printStackTrace();
            System.out.println("File reader Problem.");
            return null;
        }
    }

    public void loadMap(Pane pane){
        for (int i = 0; i < coins.size(); i++) {
            pane.getChildren().add(coins.get(i));
        }
        for (int i = 0; i < speeds.size(); i++) {
            pane.getChildren().add(speeds.get(i));
        }
        for (int i = 0; i < jumps.size(); i++) {
            pane.getChildren().add(jumps.get(i));
        }
        for (int i = 0; i < platforms.size(); i++) {
            pane.getChildren().add(platforms.get(i));
        }
    }

    public ArrayList<Platform> Platforms() {
        return platforms;
    }
    public ArrayList<PowerUp> Coins() {
        return coins;
    }
    public ArrayList<PowerUp> Jumps() {
        return jumps;
    }
    public ArrayList<PowerUp> Speeds() {
        return speeds;
    }
}
