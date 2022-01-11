package LevelEditor;

import MainMenu.Main;
import MainMenu.MainMenuWindow;
import com.sun.javafx.scene.control.InputField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.*;

public class EditorPane extends BorderPane {
    private static ToolIcon selectedTool;
    private static final String TOOL_SELECT = "tool-select";
    private static final String LVL_INPUT_NAME = "lvl-input-name";
    private static int blocks = 1500;
    public EditorPane() {
        VBox left = new VBox();
        TilePane toolPane = new TilePane();
        toolPane.setPrefColumns(1);

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e-> Main.setScene(new MainMenuWindow()));

        ToolIcon tool1 = new ToolIcon('1', toolPane, "Images/peepoJump50.png");
        tool1.setSelect(true);
        setTool(tool1);
        ToolIcon tool2 = new ToolIcon('2', toolPane, "Images/peepoRun50.png");
        ToolIcon tool3 = new ToolIcon('3', toolPane, "Images/pepeCoin60.png");
        ToolIcon tool4 = new ToolIcon('#', toolPane, "Images/pepeCoin35Grey.png");
        ToolIcon tool5 = new ToolIcon(' ', toolPane, "Images/ohno.png");

        toolPane.getChildren().addAll( tool1, tool2, tool3, tool4, tool5);
        toolPane.getStyleClass().add(TOOL_SELECT);

        TilePane grid = new TilePane();
        grid.setPrefColumns(153);
        for (int i = 0; i < blocks; i++) {
            grid.getChildren().add(new GridIcon(' ',grid));
        }
        grid.setPrefColumns(50);
        grid.setPrefRows(30);
        grid.setPrefTileHeight(20);
        grid.setPrefTileWidth(20);

        TextField lvlName = new TextField();
        lvlName.setPrefColumnCount(4);
//        lvlName.setPrefSize(50,30);
        lvlName.getStyleClass().add(LVL_INPUT_NAME);

        Button finishBtn = new Button("Finish");
        finishBtn.setOnAction(e-> saveLevel(grid, lvlName));

        left.getChildren().addAll(exitBtn, toolPane, lvlName, finishBtn);
        setLeft(left);
        setCenter(grid);
    }

    public static void setBackground(Icon node, String path){
        try {
            node.setBackground(new Background(new BackgroundImage(
                        new Image(new FileInputStream(path)),
                        BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT
                        ,BackgroundPosition.CENTER,BackgroundSize.DEFAULT
                    )));
        }catch (FileNotFoundException FNFexc){
            FNFexc.printStackTrace();
        }
    }

    public static void setTool(ToolIcon tool){selectedTool = tool;}
    public static ToolIcon getTool(){return selectedTool;}

    private void saveLevel(TilePane pane, TextField input){
        if(!input.getText().isEmpty()) {
            try {
                FileWriter file = new FileWriter("Levels/" + input.getText() + ".txt");
                PrintWriter pWriter = new PrintWriter(file);
                int columns = (int) getCenter().getBoundsInLocal().getWidth() / 21;
                int nodes = blocks;
                System.out.println(columns);
                GridIcon icon;
                for (int i = 0; i < blocks; i++) {
                    icon = (GridIcon) pane.getChildren().get(i);
                    if (nodes-- % columns == 0) {
                        pWriter.println(icon.getIdentifier());
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
}
