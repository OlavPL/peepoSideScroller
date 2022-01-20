package MainMenu;

import LevelEditor.EditorPane;
import LevelsMenu.LevelsPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


public class MainMenuWindow extends BorderPane {
    Button levelsBtn = new Button("Level selection");
    Button createMapBtn = new Button("Create a new Level");
    Button mutebtn = new Button("Mute / Unmute");

    public MainMenuWindow (){
        super();
        VBox center = new VBox();
        center.getChildren().addAll(levelsBtn, createMapBtn/*, mutebtn*/);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(20);
        Label title = new Label("Main Menu");
        title.setFont(new Font("ComicSans", 60));
        FlowPane top = new FlowPane(title);
        top.setAlignment(Pos.CENTER);

        this.setTop(top);
        this.setCenter(center);
        levelsBtn.setOnAction(e-> Main.setScene(new LevelsPane()));
        createMapBtn.setOnAction(e-> {
            Main.setScene(new EditorPane());
        });
//        mutebtn.setOnAction(e-> );
    }
}
