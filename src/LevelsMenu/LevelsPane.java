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

import java.io.File;
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


        File dir = new File("Levels");
        File[] directoryListing = dir.listFiles();
        if (directoryListing == null) {
            return;
        }
        for (File file : directoryListing) {
            levelPane.getChildren().add(new LvlThumbnail(file.getName()));
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
