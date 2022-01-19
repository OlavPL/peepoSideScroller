package LevelsMenu;

import MainMenu.Main;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LvlThumbnail extends BorderPane {
    private static final Border lvlBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));

//    Path path;
    public LvlThumbnail(String fileName) {
        super();
        System.out.println(fileName);
        String name = fileName.substring(0,fileName.length()-4);
        setUserData(fileName);
        setCenter(new Label(name));
        setBorder(lvlBorder);

        setOnMouseClicked(e->{
//            try {
//                URI uri = Objects.requireNonNull(LevelsPane.class.getResource("/Levels")).toURI();
//                Path myPath;
//                if (uri.getScheme().equals("jar")) {
//                   myPath = fileSystem.getPath("Levels");
//                } else {
//                    myPath = Paths.get(uri);
//                }
//                Stream<Path> walk = Files.walk(myPath, 1);
//                int kek = 1;
//                for (Iterator<Path> it = walk.iterator(); it.hasNext();){
//                    if(kek++ == 1)
//                        it.next();
//                    File s = it.next().toFile();
//                    if(s.getName().equals(getUserData())){
//                        System.out.println(name+" Selected");
//                        Main.setLevelScene(s);
//                    }
//                }
//
//
//            }catch (URISyntaxException | IOException URISexc){
//                URISexc.printStackTrace();
//            }
            System.out.println(name+" Selected");
            Main.setLevelScene("/Levels/"+fileName);
        });
    }
}
