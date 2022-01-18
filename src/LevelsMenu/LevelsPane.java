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
            String s = file.getName();
            levelPane.getChildren().add(new LvlThumbnail(s));
        }

        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(e-> {
            Main.setScene(new MainMenuWindow());
        });


        setTop(top);
        setCenter(levelPane);
        setLeft(mainMenu);
    }
}
