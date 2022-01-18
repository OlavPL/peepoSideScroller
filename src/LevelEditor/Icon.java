package LevelEditor;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Icon extends Pane{
    private String imagePath;
    private char identifier;
    private boolean isSelected = false;
    protected static final Border emptyBorder = new Border(new BorderStroke(Color.PINK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY));
    protected static final Border selectedBorder = new Border(new BorderStroke(Color.PINK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1)));
    protected static final Border gridBorder = new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1)));

    public Icon(char c, Pane pane, String s){
        setIdentifier(c);
        onClick(pane);
        setImagePath(s);
    }

    protected void select(ObservableList<Node> list){
        setSelected(true);
        for (Node t: list) {
            if(!t.equals(this)){
                Icon ti = (Icon) t;
                if(ti.isSelected()) {
                    ti.setSelected(false);
                }
                ti.setBorder(gridBorder);
            }
        }
    }

    protected void onClick(Pane p){
        setOnMousePressed(ev -> {
            select(p.getChildren());
            if (isSelected()) {setBorder(selectedBorder);}
            else            {setBorder(gridBorder);}
        });
    }

}












