package LevelEditor;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GridIcon extends Icon{
    private static final Border idleBorder = new Border(
            new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, new BorderWidths(1))
    );

    public GridIcon(char c, TilePane pane){
        super(c, pane, "");
        setBorder(idleBorder);
        setMaxSize(20,20);
    }

    @Override
    protected void select(ObservableList<Node> list){
        setSelect(true);
        for (Node t: list) {
            if(!t.equals(this)){
                Icon ti = (Icon)t;
                if(ti.getSelect()) {
                    ti.setSelect(false);
                    ti.setBorder(idleBorder);
                }
            }
        }
    }

    @Override
    protected void onClick(Pane p){
        setOnMousePressed(ev -> {
            select(p.getChildren());
            if (getSelect()) {
                setBorder(selectedBorder);
                if(EditorPane.getTool().getIdentifier() == ' '){
                    this.setBackground(Background.EMPTY);
                    this.setIdentifier(' ');
                }
                else {
                    EditorPane.setBackground(this, EditorPane.getTool().getImagePath());
                    this.setIdentifier(EditorPane.getTool().getIdentifier());
                }
            }
            else {setBorder(idleBorder);}
        });
    }
}
