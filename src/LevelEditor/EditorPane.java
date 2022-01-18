package LevelEditor;

import Methods.Methods;
import MainMenu.Main;
import MainMenu.MainMenuWindow;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EditorPane extends BorderPane {
    private final static int TileSize = 40;
    private static int ColumnNum = 200;
    private static int RowNum = 60;
    private static ToolIcon selectedTool;
    private static final String TOOL_SELECT = "tool-select";
    private static final String LVL_INPUT_NAME = "lvl-input-name";

    public EditorPane() {
        VBox left = new VBox();
        TilePane toolPane = new TilePane();
        toolPane.setPrefColumns(1);

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e-> Main.setScene(new MainMenuWindow()));

        ToolIcon tool1 = new ToolIcon('@', toolPane, "Images/player.png");
        tool1.setSelected(true);
        setTool(tool1);
        ToolIcon tool2 = new ToolIcon('4', toolPane, "Images/peepoJump50.png");
        ToolIcon tool3 = new ToolIcon('3', toolPane, "Images/peepoRun50.png");
        ToolIcon tool4 = new ToolIcon('2', toolPane, "Images/pepeCoin60.png");
        ToolIcon tool5 = new ToolIcon('#', toolPane, "Images/platform.png");
        ToolIcon tool6 = new ToolIcon(' ', toolPane, "Images/ohno.png");
        ToolIcon tool7 = new ToolIcon('1', toolPane, "Images/winPlatform.png");

        toolPane.getChildren().addAll( tool1, tool2, tool3, tool4, tool5, tool6, tool7);
        toolPane.getStyleClass().add(TOOL_SELECT);


        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setVgap(0);
        grid.setHgap(0);

        for (int i = 0; i <ColumnNum; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(TileSize));
        }
        for (int i = 0; i < RowNum ; i++) {
            grid.getRowConstraints().add(new RowConstraints(TileSize));
            for (int j = 0; j < ColumnNum; j++) {
                GridIcon tempIcon = new GridIcon(' ', grid, TileSize);
                tempIcon.setBorder(Icon.gridBorder);
                grid.add(tempIcon, j, i);
            }
        }

        TextField lvlName = new TextField();
        lvlName.setPrefColumnCount(4);
        lvlName.setPromptText("Lv.'Name'");
        lvlName.getStyleClass().add(LVL_INPUT_NAME);

        Button finishBtn = new Button("Finish");
        finishBtn.setOnAction(e-> saveLevel(grid, lvlName.getText()));

        ScrollPane gridParent = new ScrollPane();
        gridParent.setContent(grid);
        gridParent.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * 0.0005;
            gridParent.setVvalue(gridParent.getVvalue() - deltaY);
        });
        setupHorizontalScrolling(gridParent);

        left.getChildren().addAll(exitBtn, toolPane, lvlName, finishBtn);
        setLeft(left);
        setCenter(gridParent);
    }

    public static void setBackground(Icon node, String path){
        try {
            node.setBackground(
                new Background(new BackgroundImage(
                new Image(new FileInputStream(path)),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT
                ,BackgroundPosition.CENTER, new BackgroundSize(TileSize, TileSize,false,false,true,true)
            )));
        }catch (FileNotFoundException FNFexc){
            FNFexc.printStackTrace();
        }
    }

    public static void setTool(ToolIcon tool){selectedTool = tool;}
    public static ToolIcon getTool(){return selectedTool;}

    private void saveLevel(GridPane pane, String lvlName){
        try {
            Stream<Path> files = Files.list(Paths.get("Levels"));
            int amtLevels = (int) files.count();
            String name = lvlName;
            if (name.equals(""))
                name = ""+amtLevels;
            FileWriter file = new FileWriter("Levels/"+amtLevels+"Lv."+name+".txt");
            PrintWriter pWriter = new PrintWriter(file);

            GridIcon icon;
            for (int i = 0; i < RowNum ; i++)    {
                for (int j = 0; j < ColumnNum; j++) {
                    icon = (GridIcon) Methods.getNodeByRowColumnIndex(i,j,pane);
                    pWriter.print(icon.getIdentifier());
                }
                pWriter.println();
            }
            System.out.println("Save Successful");
            Main.setScene(new EditorPane());
            pWriter.close();
        } catch (IOException IOexc) {
            IOexc.printStackTrace();
        }
    }

    private void setupHorizontalScrolling(ScrollPane sp) {
        this.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double deltaY = scrollEvent.getDeltaY()*8; // *2 to make the scrolling a bit faster
                double width = sp.getContent().getBoundsInLocal().getWidth();
                double hvalue = sp.getHvalue();
                sp.setHvalue(hvalue + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
            }
        });
    }

}
