package LevelsMenu;

import MainMenu.Main;
import MainMenu.MainMenuWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LevelsPane extends BorderPane {

    public LevelsPane(){
        super();
        Label title = new Label("Select Level");
        title.setFont(new Font("ComicSans", 60));
        FlowPane top = new FlowPane(title);
        top.setAlignment(Pos.CENTER);

        TilePane levelPane = new TilePane();
        levelPane.setHgap(10);
        levelPane.setVgap(10);
        levelPane.setPadding(new Insets(10));

        int amtLevels = 0;
        try (Stream<Path> files = Files.list(Paths.get("src/Levels"))) {
            amtLevels = (int) files.count();
        }catch (IOException IOExc){
            IOExc.printStackTrace();
        }
        if(amtLevels > 0) {
            for (int i = 0; i < amtLevels; i++) {
                levelPane.getChildren().add(new LvlThumbnail());
            }
        }

        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(e-> {
            Main.setScene(new MainMenuWindow());
            LvlThumbnail.exitScene();
        });


        setTop(top);
        setCenter(levelPane);
        setLeft(mainMenu);
    }
}
