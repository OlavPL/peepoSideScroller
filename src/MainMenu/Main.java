package MainMenu;

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
        stage.setScene(scene);
        stage.setTitle("EpicGame");
        stage.show();
    }

    public static void setScene(Pane parent){
        scene = new Scene(parent);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("../application.css").toExternalForm()));
        pStage.setScene(scene);
    }
    public static void addCSS(String s){

    }
    public static Stage getStage(){
        return pStage;
    }
}
