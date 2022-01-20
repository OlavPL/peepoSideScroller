package MainMenu;

import LevelPlayer.GamePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;


public class Main extends Application {
    private static Scene scene;
    private static Stage pStage;
    private static String appdata;
    @Override
    public void start(Stage stage){
        stage.setMaxWidth(Screen.getPrimary().getBounds().getWidth());
        stage.setMaxHeight(Screen.getPrimary().getBounds().getHeight());
        stage.setMaximized(true);

        pStage = stage;

        appdata = System.getProperty("user.home")+"/Appdata/Roaming/peepoSideScroller";
        File levelDir = new File(appdata);
        if (!levelDir.exists()){
            levelDir.mkdirs();
        }

        scene = new Scene(new MainMenuWindow());
        scene.getStylesheets().add("application.css");
        Meth.setFillStage(stage);
        stage.setScene(scene);
        stage.setTitle("EpicGame");
        stage.show();
    }

    public static void setScene(Pane parent){
        scene = new Scene(parent);
        scene.getStylesheets().add("application.css");
//        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("../application.css").toExternalForm()));
        pStage.setScene(scene);
    }

    public static void setLevelScene(String path){
        Pane newPane = new Pane();
        GamePane tempGP = new GamePane(path, newPane);
        scene = new Scene(newPane);
        tempGP.setScene(scene);
        scene.getStylesheets().add("application.css");
//        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("../application.css").toExternalForm()));
        pStage.setScene(scene);
    }

    public static Scene getScene(){return scene;}
    public static Stage getStage(){
        return pStage;
    }
    public static String getAppdata(){return appdata;}

    public static void main(String[] args) {
        launch(args);
    }
}
