/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import project2.Engine.Animator;
import project2.Engine.GameThread;
import project2.Engine.ImageManager;
import project2.Stage.Stage;
import project2.Stage.StageElement;

/**
 *
 * @author chico
 */
public class Proyectile extends StageElement implements Animator{
    
    private static final int MAGNITUD = 2;
    
    private Animation animation;
    private String currentImageId;
    
    private boolean isStarted;
    private boolean isAlive;
    private long startTime;
    private int index;
    
    private float angle;
    private float dx;
    private float dy;
    
    private Poligono bBox;
    private boolean isDestroyed;

    public Proyectile(float x, float y , Animation animation , float angle) {
        this.location = new Location(x,y);
        
        this.isStarted = false;
        this.isAlive = true;
        this.index = 0;
        
        this.animation = animation;
        
        
        this.angle = (float)angle;
        this.dx = (float)(Proyectile.MAGNITUD*Math.cos(angle));
        this.dy = (float)(Proyectile.MAGNITUD*Math.sin(angle));
        
        this.bBox = new Poligono();
        this.isDestroyed = false;
        
        
        
    }

    public float getX() {
        return this.location.getX();
    }

    public void setX(int x) {
        this.location.setX(x);
    }

    public float getY() {
        return this.location.getY();
    }

    public void setY(int y) {
        this.location.setY(y);
    }
    
    private float moveBySpeedY(int dir){
        if(dir > 0){
            this.location.setY(this.location.getY() + MAGNITUD);
            return getY();
        } else {
            this.location.setY(this.location.getY() - MAGNITUD);
            return getY();
        }
    }
    
    private float moveBySpeedX(int dir){
        if(dir > 0){
            this.location.setX(this.location.getX() + MAGNITUD);
            return getX();
        } else {
            this.location.setX(this.location.getX() - MAGNITUD);
            return getX();
        }
    }
    
    public void updateYPos(int dir){
        this.location.setY(this.moveBySpeedY(dir));
    }
    
    public void updateXPos(int dir){
        this.location.setX(this.moveBySpeedX(dir));
    }
    
    public void moveByMagnitud(){
        this.location.setX(this.getX() + dx );
        this.location.setY(this.getY() - dy );
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
    public String getId() {
        return "BossShoot";
    }

    @Override
    public void setId(String id) {
        
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
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        if(this.isDestroyed == false){
            Imagen imagen = stage.getImagenById(this.currentImageId);

            Rectangle2D laserWall = game.toWindow( 
                            new Rectangle2D.Float(
                                    this.getX() , 
                                    this.getY(), 
                                    9/2, 
                                    54/4 ));

            if(this.getbBox().getSize() == 0 || this.getbBox().getSize() == 4){
                this.bBox.clear();
                this.GenerateBbox(
                        (int)laserWall.getX(), 
                        (int)laserWall.getY(), 
                        (int)laserWall.getWidth() + (int)laserWall.getX(), 
                        (int)laserWall.getHeight() + (int)laserWall.getY());
            }

//            buffer.drawImage(ImageManager.getImage(imagen.getRoute()), 
//                (int)laserWall.getX(), 
//                (int)laserWall.getY(),
//                (int)laserWall.getWidth(),
//                (int)laserWall.getHeight(),null);

            buffer.drawOval(
                    (int)laserWall.getX(), 
                    (int)laserWall.getY(), 
                    (int)laserWall.getWidth(), 
                    (int)laserWall.getHeight());
            buffer.fillOval(
                    (int)laserWall.getX(), 
                    (int)laserWall.getY(), 
                    (int)laserWall.getWidth(), 
                    (int)laserWall.getHeight());

            //this.bBox.paint(stage, buffer, game);
            stage.bossAttackCollition(this);
        } else {
            this.bBox.clear();
        }
        
        
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
        if(this.index >= this.animation.getFramesSize()){
            this.index = 0;
            
        }else if((timestamp - this.startTime) >= this.animation.getFrame(this.index).getDuration()){
            int N = this.animation.getFramesSize();
            this.index = (this.index + 1)%N;
            this.startTime = timestamp;
            
        }
        this.currentImageId = animation.getFrame(this.index).getId();
    }

    public String getCurrentImageId() {
        return currentImageId;
    }

    public void setIsDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }
    
    
    
    
    
}
