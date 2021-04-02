package tileMaps;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class TileMap {

    private Image[][] tiles;
    private LinkedList sprites;
    private Sprite player;

    private final Sprite goalSprite = new Sprite("peepoHappy.png");
    private final Sprite coinSprite = new Sprite("pepeCoin60.png");
    private final Sprite speedSprite = new Sprite("peepoSpeed.png");
    private final Sprite jumpSprite = new Sprite("peepoJump.png");
    private final Sprite enemy1Sprite = new Sprite("ohno.png");
    private final Sprite enemy2Sprite = new Sprite("ohno.png");

    public TileMap(int width, int height) {
        tiles = new Image[width][height];
        sprites = new LinkedList();
    }

    public TileMap loadMap(String filename)
    throws IOException, CloneNotSupportedException{
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
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

        height = lines.size();
        TileMap newMap = new TileMap(width, height);
        for (int y = 0; y<height; y++){
            String line = (String)lines.get(y);
            for (int x = 0; x < line.length(); x++){
                char ch = line.charAt(x);
                int tile = ch - 'A';
                if (tile>=0 && tile < lines.size()){
                    newMap.setTile(x, y, (Image)lines.get(tile));
                }
                else if (ch == '*'){
                    addSprite(newMap, goalSprite, x, y);
                    }
                else if (ch == '!'){
                    addSprite(newMap, coinSprite, x, y);
                }
                else if (ch == '@'){
                    addSprite(newMap, speedSprite, x, y);
                }
                else if (ch == '$'){
                    addSprite(newMap, jumpSprite, x, y);
                }
                else if (ch == '0'){
                    addSprite(newMap, enemy1Sprite, x, y);
                }
                else if (ch == '1'){
                    addSprite(newMap, enemy2Sprite, x, y);
                }
            }
        }
        Sprite playerSprite = new Sprite();
        Sprite player = (Sprite)playerSprite.clone();
        player.setX(TileMapRenderer.tilesToPixels(3));
        player.setY(0);
        newMap.setPlayer(player);
        return newMap;
    }

    public int getWidth(){
        return tiles.length;
    }

    public int getHeight(){
        return tiles[0].length;
    }

    public Image getTile(int x, int y){
        if(x < 0 || x >= getWidth() || y < 0 || y >= getHeight()){
            return null;
        }else
        {
            return tiles[x][y];
        }
    }

    public void setTile(int x, int y, Image tile){
        tiles[x][y] = tile;
    }

    public Sprite getPlayer(){
        return player;
    }

    public void setPlayer(Sprite player){
        this.player = player;
    }

//    adds sprite to parent Sprite list
    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }

//    adds a sprite to map based on parent sprite
    private void addSprite
    (TileMap map, Sprite parentSprite, int tileX, int tileY)
    throws CloneNotSupportedException {
        if (parentSprite != null){
            Sprite sprite = (Sprite)parentSprite.clone();
            sprite.setX
            (
                (int)(TileMapRenderer.tilesToPixels(tileX) +
                (TileMapRenderer.tilesToPixels(1)-sprite.getWidth())/2)
            );

            sprite.setY
            (
                (int)(TileMapRenderer.tilesToPixels
                (tileY+1) - sprite.getHeight())
            );
            map.addSprite(sprite);
        }
    }

    public Iterator getSprites(){
        return sprites.iterator();
    }
}
