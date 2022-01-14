package Units;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprite extends ImageView implements Cloneable{

    protected double velX;
    protected double velY;
    private double width;
    private double height;

    public Sprite(){
        velX = 0;
        velY = 0;
    }

    public Sprite(String s){
        velX = 0;
        velY = 0;
        setIV(s);
        width = this.getBoundsInLocal().getWidth();
        height = this.getBoundsInLocal().getHeight();
    }
    public Sprite(ImageView iv){
        velX = 0;
        velY = 0;
        setIV(iv);
        width = this.getBoundsInLocal().getWidth();
        height = this.getBoundsInLocal().getHeight();
    }
    public Sprite(Image i){
        velX = 0;
        velY = 0;
        setIV(i);
        width = this.getBoundsInLocal().getWidth();
        height = this.getBoundsInLocal().getHeight();
    }
    public Sprite(int x, int y, ImageView iv){
        velX = 0;
        velY = 0;
        setIV(iv);
        width = this.getBoundsInLocal().getWidth();
        height = this.getBoundsInLocal().getHeight();
    }
    public Sprite(int x, int y, Image i){
        velX = 0;
        velY = 0;
        setIV(i);
        width = this.getBoundsInLocal().getWidth();
        height = this.getBoundsInLocal().getHeight();
    }

    public void setXY(double x, double y){
        this.setX(x);
        this.setY(y);
    }
    public void setIV(Image i){
        this.setImage(i);
        width = i.getWidth();
        height = i.getHeight();
    }
    public void setIV(Image i, double w, double h){
        this.setImage(i);
        width = w;
        height = h;
    }
    public void setIV(String s){
        Image i = new Image(s);
        setIV(i);
    }
    public void setIV(ImageView iv){
        this.setImage(iv.getImage());
    }
    public void setIV(String s, double w, double h){
        Image i = new Image(s);
        setIV(i, w, h);
    }
    public ImageView getIV(){
        return this;
    }

    public void setVel(double vX, double vY) {
        velX = vX;
        velY = vY;
    }
    public void addVel(double vX, double vY) {
        velX += vX;
        velY += vY;
    }

    public void update(double time) {
        this.setX(this.getX() + velX * time);
        this.setY(this.getY() + velY * time);
    }
    public void render(Pane pane){
        pane.getChildren().add(this);
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(this.getX(), this.getY(), width, height);
    }
    public boolean intersects(Sprite s){
        return s.getBoundary().intersects(this.getBoundary());
    }
    public String toString(){
        return "Position: ["+this.getX()+", "+this.getY()+"]  "+
                "Velocity: ["+ velX+", "+velY+"]  " +
                "Width: "+this.getWidth()+ "  Height: "+this.getHeight();
    }
    public double getHeight(){
        return this.getBoundsInLocal().getHeight();
    }
    public double getWidth(){
            return this.getBoundsInLocal().getWidth();
    }
    public void setHeight(double n){
        height = n;
        setFitHeight(n);
    }
    public void setWidth(double n){
        width = n;
        setFitWidth(n);
    }
    public Rectangle2D getView(){
        return this.getViewport();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
