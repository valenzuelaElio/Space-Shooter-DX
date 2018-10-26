/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import project2.Engine.Animator;
import project2.Engine.Element;
import project2.Engine.GameThread;
import project2.Engine.ImageManager;
import project2.Stage.Stage;
import project2.Stage.StageElement;

/**
 *
 * @author chico
 */
public class Enemy extends StageElement implements Animator, Element{
    
    Random random = new Random();
    
    private Animation right;
    private Animation left;
    
    private Animation currentAnimation; //[Animacion]
    private String currentImageId; //[Animacion]
    
    private int index; //[Animator]
    private boolean isStarted; //[Animator]
    private boolean isAlive; //[Animator]
    private long startTime; //[Animator]
    
    private Poligono bBox;
    
    public Enemy(String id, Animation right, Animation left) {
        this.id = id;
        this.right = right;
        this.left = left;
        
        this.isStarted = false;
        this.isAlive = true;
        this.index = 0;
        
        this.bBox = new Poligono();
        
        setCurrentAnimation(right.getId());
        
        
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    public Animation getRight() {
        return right;
    }

    public void setLeft(Animation left) {
        this.left = left;
    }
    
    public Animation getLeft() {
        return left;
    }

    public void setRight(Animation right) {
        this.right = right;
    }

    @Override
    public boolean isStarted() {
        return this.isStarted;
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public void start(long timestamp) {
        this.isStarted = true;
        this.startTime = timestamp;
        
    }

    @Override
    public void perform(long timestamp) {
        if(this.index >= this.currentAnimation.getFramesSize()){
            this.index = 0;
            
        }else if((timestamp - this.startTime) >= this.currentAnimation.getFrame(this.index).getDuration()){
            int N = this.currentAnimation.getFramesSize();
            this.index = (this.index + 1)%N;
            this.startTime = timestamp;
            
        }
        this.currentImageId = currentAnimation.getFrame(this.index).getId();
    }
    
     private void moveToPlayer(Stage stage , GameThread game){
        int dir = 5;
        
        if(this.location.getY() != stage.getPlayer().getY()){
           if(this.location.getY() < stage.getPlayer().getY()){ //Eje Y
               this.location.setY(this.location.getY() + dir);
               this.moveBbox(0,-dir);
               
           } else if(this.location.getY() > stage.getPlayer().getY()){
               this.location.setY(this.location.getY() - dir);
               this.moveBbox(0,dir);
           }
        } else {
           this.moveBbox(0,0);
        }
        
        
        if(this.location.getX() != stage.getPlayer().getX()){
           if(this.location.getX() < stage.getPlayer().getX()){ //Eje X
               this.location.setX(this.location.getX() + 5);
               this.moveBbox(dir , 0);
           } else if(this.location.getX() > stage.getPlayer().getX()){
               this.location.setX(this.location.getX() - 5);
               this.moveBbox(-dir , 0);
               
           }
           
        } else {
            this.moveBbox(0,0);
        }
        
        
        
    }
    
    public void updatePosition(Stage stage, GameThread game){
        if(stage.getPlayer() != null){ //Si existe un jugador, lo persigo;
            if(this.getLocation().getY() < (stage.getViewport().getY2() + (stage.getViewport().getY2() - this.getLocation().getY()))){
                this.moveToPlayer(stage , game);
            } 
            if(this.getLocation().getY() <=  stage.getPlayer().getY() || this.getLocation().getY() == 2100){ //Si pasa de largo al jugador.
                this.moveStraight(stage,game);
            }
            if(stage.getViewport().getY2() >= 3200){
                this.moveOnX(stage, game);
            }
        } 
        
    }
    
    public void setCurrentAnimation(String key){
        if(this.right.getId().equals( key )){
            this.currentAnimation = this.right;
        } else {
            this.currentAnimation = this.left;
        }
    }
    
    public String getCurrentImageId(){
        return this.currentImageId;
    }    
    
    public Poligono getbBox() { //[Colison]
        return bBox;
    }

    public void setbBox(Poligono bBox) {//[Colison]
        this.bBox = bBox;
    }
    
    public void GenerateBbox(int x1, int y1, int x2, int y2 ){//[Colison]
        this.bBox.add(x1, y1);
        this.bBox.add(x2, y1);
        this.bBox.add(x2, y2);
        this.bBox.add(x1, y2);
        
    }
    
    public void moveBbox(int vectorX, int vectorY){//[Colison]
        this.bBox.move( vectorX, vectorY );
    }
    
    public Polygon getPolygon()
    {
        return this.bBox.getPolygon();
    }
    
    public int getBBoxSize(){
        return this.bBox.getSize();
    } 

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        Imagen imagen = stage.getImagenById(this.getCurrentImageId());
        Rectangle2D enemyWall = game.toWindow( 
                    new Rectangle2D.Float(
                            this.getLocation().getX(), 
                            this.getLocation().getY(), 
                            imagen.getWidth()/2, 
                            imagen.getHeight()/2));

        if(this.getbBox().getSize() == 0 || this.getbBox().getSize() == 4){
            this.bBox.clear();
            this.GenerateBbox(
                    (int)enemyWall.getX(), 
                    (int)enemyWall.getY(), 
                    (int)enemyWall.getWidth() + (int)enemyWall.getX(), 
                    (int)enemyWall.getHeight() + (int)enemyWall.getY());
        }
        
        this.updatePosition(stage , game);

        buffer.drawImage(ImageManager.getImage(imagen.getRoute()), 
                (int)enemyWall.getX(),
                (int)enemyWall.getY(), 
                (int)enemyWall.getWidth(),
                (int)enemyWall.getHeight(),null);
        
        stage.PlayerCollition(this);
        
        //this.bBox.paint(stage, buffer, game);
    }

    private void moveStraight(Stage stage, GameThread game) {
        this.location.setY(this.location.getY() - 15);
        this.moveBbox(0,15);
    }
    
    private void moveOnX(Stage stage, GameThread game ){
        int dir = 15;
        if(this.location.getX() >= stage.getViewport().getX2()){
            this.location.setX(this.location.getX() - dir);
        } else if(this.location.getX() <= stage.getViewport().getX1()){
            this.location.setX(this.location.getX() + dir);
        }
    }
    
}
