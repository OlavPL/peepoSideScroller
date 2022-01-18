package MainMenu;

import LevelPlayer.GamePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Objects;


public class Main extends Application {
    private static Scene scene;
    private static Stage pStage;
    @Override
    public void start(Stage stage){
        stage.setHeight(600);
        stage.setWidth(600);
        pStage = stage;

        scene = new Scene(new MainMenuWindow());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("../application.css").toExternalForm()));
        Meth.setFillStage(stage);
        stage.setScene(scene);
        stage.setTitle("EpicGame");
        stage.show();
    }

    public static void setScene(Pane parent){
        scene = new Scene(parent);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("../application.css").toExternalForm()));
        pStage.setScene(scene);
    }

    public static void setLevelScene(String url){
        Pane newPane = new Pane();
        GamePane tempGP = new GamePane(url, newPane);
        scene = new Scene(newPane);
        tempGP.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("../application.css").toExternalForm()));
        pStage.setScene(scene);
    }

    public static Scene getScene(){return scene;}
    public static Stage getStage(){
        return pStage;
    }
}
