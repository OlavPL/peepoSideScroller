package LevelEditor;

import MainMenu.Main;
import MainMenu.MainMenuWindow;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class EditorPane extends BorderPane {
    private final static int TILE_SIZE = 20;
    private final static int COLUMN_NUM = 90;
    private static final int BLOCKS = 1500;
    private static ToolIcon selectedTool;
    private static final String TOOL_SELECT = "tool-select";
//    private static final String LVL_INPUT_NAME = "lvl-input-name";

    public EditorPane() {
        VBox left = new VBox();
        TilePane toolPane = new TilePane();
        toolPane.setPrefColumns(1);

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e-> Main.setScene(new MainMenuWindow()));

        ToolIcon tool1 = new ToolIcon('@', toolPane, "Images/player.png");
        tool1.setSelect(true);
        setTool(tool1);
        ToolIcon tool2 = new ToolIcon('4', toolPane, "Images/peepoJump50.png");
        ToolIcon tool3 = new ToolIcon('3', toolPane, "Images/peepoRun50.png");
        ToolIcon tool4 = new ToolIcon('2', toolPane, "Images/pepeCoin60.png");
        ToolIcon tool5 = new ToolIcon('#', toolPane, "Images/platform.png");
        ToolIcon tool6 = new ToolIcon(' ', toolPane, "Images/ohno.png");
        ToolIcon tool7 = new ToolIcon('1', toolPane, "Images/winPlatform.png");

        toolPane.getChildren().addAll( tool1, tool2, tool3, tool4, tool5, tool6, tool7);
        toolPane.getStyleClass().add(TOOL_SELECT);

        TilePane grid = new TilePane();
        grid.setPrefColumns(153);
        for (int i = 0; i < BLOCKS; i++) {
            grid.getChildren().add(new GridIcon(' ',grid));
        }
        grid.setPrefColumns(COLUMN_NUM);
        grid.setPrefRows(30);
        grid.setPrefTileHeight(TILE_SIZE);
        grid.setPrefTileWidth(TILE_SIZE);

//        TextField lvlName = new TextField();
//        lvlName.setPrefColumnCount(4);
////        lvlName.setPrefSize(50,30);
//        lvlName.getStyleClass().add(LVL_INPUT_NAME);

        Button finishBtn = new Button("Finish");
        finishBtn.setOnAction(e-> saveLevel(grid));

        left.getChildren().addAll(exitBtn, toolPane, finishBtn);
        setLeft(left);
        setCenter(grid);
    }

    public static void setBackground(Icon node, String path){
        try {
            node.setBackground(new Background(new BackgroundImage(
                        new Image(new FileInputStream(path)),
                        BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT
                        ,BackgroundPosition.CENTER, new BackgroundSize(TILE_SIZE, TILE_SIZE,false,false,true,true)
                    )));
        }catch (FileNotFoundException FNFexc){
            FNFexc.printStackTrace();
        }
    }

    public static void setTool(ToolIcon tool){selectedTool = tool;}
    public static ToolIcon getTool(){return selectedTool;}

    private void saveLevel(TilePane pane){
        try {
            Stream<Path> files = Files.list(Paths.get("Levels"));
            int amtLevels = (int) files.count();

            FileWriter file = new FileWriter("Levels/Level" + ++amtLevels + ".txt");
            PrintWriter pWriter = new PrintWriter(file);
            int columns = pane.getPrefColumns();
            System.out.println(columns);
//                    = (int) getCenter().getBoundsInLocal().getWidth() / 21;
            int count = 1;
            System.out.println(columns);
            GridIcon icon;
            for (int i = 0; i < BLOCKS; i++) {
                count++;
                icon = (GridIcon) pane.getChildren().get(i);
                if (count == 90) {
                    pWriter.println(icon.getIdentifier());
                    count = 1;
                }
                else{
                    pWriter.print(icon.getIdentifier());
                }
            }
            System.out.println("Save Successful");
            Main.setScene(new EditorPane());
            pWriter.close();
        } catch (IOException IOexc) {
            IOexc.printStackTrace();
        }
    }
}
