package LevelEditor;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class GridIcon extends Icon{

    public GridIcon(char c, GridPane pane, int size){
        super(c, pane, "");
        setBorder(gridBorder);
        setMaxSize(size,size);
    }

    @Override
    protected void select(ObservableList<Node> list){
        setSelected(true);
        for (Node t: list) {
            if(!t.equals(this)){
                Icon ti = (GridIcon) t;
                if(ti.isSelected()) {
                    ti.setSelected(false);
                }
                ti.setBorder(gridBorder);
            }
        }
    }

    @Override
    protected void onClick(Pane p){
        setOnMousePressed(e -> {
        select(p.getChildren());
        setBorder(selectedBorder);
        if(EditorPane.getTool().getIdentifier() == ' '){
            this.setBackground(Background.EMPTY);
            this.setIdentifier(' ');
            return;
        }
            EditorPane.setBackground(this, EditorPane.getTool().getImagePath());
            this.setIdentifier(EditorPane.getTool().getIdentifier());
        });
    }
}
