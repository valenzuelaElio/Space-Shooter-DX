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
public class Laser extends StageElement implements Animator{
    
    
    private Animation animation;
    private String currentImageId;
    
    private boolean isStarted;
    private boolean isAlive;
    private long startTime;
    private int index;

    public Laser(float x, float y , Animation animation) {
        this.location = new Location(x,y);
        
        this.isStarted = false;
        this.isAlive = true;
        this.index = 0;
        
        this.animation = animation;
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
            this.location.setY(this.location.getY() + 20);
            return getY();
        } else {
            this.location.setY(this.location.getY() - 20);
            return getY();
        }
    }
    
    public void updateYPos(int dir){
        this.location.setY(this.moveBySpeedY(dir));
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
        return "shoot";
    }

    @Override
    public void setId(String id) {
        
    }

    @Override
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        Graphics2D buffer2D = (Graphics2D) buffer;
        
        
        Rectangle2D laserWall = game.toWindow( 
                        new Rectangle2D.Float(this.getX(), this.getY(), 9/2, 54/2 ));
        
        Imagen imagen = stage.getImagenById(this.currentImageId);
        
        
        buffer2D.drawImage(ImageManager.getImage(imagen.getRoute()), 
            (int)laserWall.getX(), 
            (int)laserWall.getY(),
            (int)laserWall.getWidth(),
            (int)laserWall.getHeight(),null);
        
        stage.destroyHittedEnemy(laserWall, this);
        stage.getStageBoss().receiveDamage(stage, laserWall, this);
        
        
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

    
    

    
    
}
