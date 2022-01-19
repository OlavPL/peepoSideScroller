package LevelsMenu;

import MainMenu.Main;
import MainMenu.MainMenuWindow;
import MainMenu.Meth;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class LevelsPane extends BorderPane {
    public static FileSystem fileSystem;
    public LevelsPane() {
        super();
        Label title = new Label("Select Level");
        title.setFont(new Font("ComicSans", 60));
        FlowPane top = new FlowPane(title);
        top.setAlignment(Pos.CENTER);

        TilePane levelPane = new TilePane();
        levelPane.setHgap(10);
        levelPane.setVgap(10);
        levelPane.setPadding(new Insets(10));

//        File dir;
        try {
            URI uri = Objects.requireNonNull(LevelsPane.class.getResource("/Levels")).toURI();
            Path myPath = Meth.getLevelPath(uri);
            Stream<Path> walk = Files.walk(myPath, 1);
            int kek = 1;
            for (Iterator<Path> it = walk.iterator(); it.hasNext();){
                if(kek++ == 1)
                    it.next();
                levelPane.getChildren().add(
                    new LvlThumbnail(it.next().getFileName().toString()));
            }

        }catch (URISyntaxException | IOException URISexc){
            URISexc.printStackTrace();
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
