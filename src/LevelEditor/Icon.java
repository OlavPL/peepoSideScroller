package LevelEditor;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Icon extends Pane{
    private String imagePath;
    private char identifier;
    private boolean isSelected = false;
    protected static final Border emptyBorder = new Border(new BorderStroke(Color.PINK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY));
    protected static final Border selectedBorder = new Border(new BorderStroke(Color.PINK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(1)));

    public Icon(char c, Pane pane, String s){
        setIdentifier(c);
        onClick(pane);
        setImagePath(s);
    }

    protected void select(ObservableList<Node> list){
        isSelected = true;
        for (Node t: list) {
            Icon ti = (Icon)t;
            if(!t.equals(this)){
                ti.setSelect(false);
                ti.setBorder(emptyBorder);
            }
        }
    }

    protected void onClick(Pane p){
        setOnMousePressed(ev -> {
            select(p.getChildren());
            if (getSelect()) {setBorder(selectedBorder);}
            else            {setBorder(emptyBorder);}
        });
    }

    public void setImagePath(String s){imagePath = s;}
    public String getImagePath(){return imagePath;}
    public void setSelect(boolean b){isSelected = b;}
    public boolean getSelect(){return isSelected;}
    public char getIdentifier(){return identifier;}
    public void setIdentifier(char c){identifier = c;}
}












