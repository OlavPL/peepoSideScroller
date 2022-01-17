package LevelEditor;

import javafx.scene.layout.*;

public class ToolIcon extends Icon {

    public ToolIcon(char c, Pane pane, String s){
        super( c, pane, s);
        EditorPane.setBackground(this, s);
    }

    @Override
    protected void onClick(Pane p){
        setOnMouseClicked(ev -> {
            EditorPane.setTool(this);
            select(p.getChildren());
            if (getSelect()) {setBorder(selectedBorder);}
            else            {setBorder(emptyBorder);}
        });
    }


}


