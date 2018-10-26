/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import project2.Engine.Element;
import project2.Engine.GameThread;
import project2.Engine.ImageManager;
import project2.Stage.Stage;

/**
 *
 * @author chico
 */
public class Layer implements Comparable<Layer> ,Element{
    int DISTANCIA = 5;
    
    private boolean mainLayer;
    private float layerSpeed;
    private int depth;
    
    private Background bg;
    
    private float xPos = 0;
    private float yPos = 0;
    
    public Layer(boolean mainLayer, float layerSpeed, int depth) {
        this.mainLayer = mainLayer;
        this.layerSpeed = layerSpeed;
        this.depth = depth;
        
    }
    
    public Layer(boolean mainLayer, float layerSpeed, int depth, Background bg){
        this.mainLayer = mainLayer;
        this.layerSpeed = layerSpeed;
        this.depth = depth;
        
        this.bg = bg;
        
    }

    public boolean isMainLayer() {
        return mainLayer;
    }

    public void setMainLayer(boolean mainLayer) {
        this.mainLayer = mainLayer;
    }

    public float getLayerSpeed() {
        return layerSpeed;
    }

    public void setLayerSpeed(float layerSpeed) {
        this.layerSpeed = layerSpeed;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Background getBg() {
        return bg;
    }

    public void setBackground(Background bg) {
        this.bg = bg;
    }
    
    public String getBackgroundImagenId(){
        return this.bg.getImagenId();
    }
    
    public Boolean isRepeatOnX(){
        return this.bg.isxRepeat();
    }
    
    public Boolean isRepeatOnY(){
        return this.bg.isyRepeat();
    }
    
    @Override
    public int compareTo(Layer layer) {
        int compareQuantity = layer.getDepth();
        return ( this.depth - compareQuantity );
    }
    
    private float moveBySpeedX(int dir){
        if(dir > 0){
            return this.xPos = ( this.xPos + this.getLayerSpeed() );
        } else {
            return this.xPos = ( this.xPos - this.getLayerSpeed() );
        }
    }
    
    public float getXpos(){
        return this.xPos;
    }
    
    public void updateXPos(int dir){
        this.xPos = this.moveBySpeedX(dir);
    }
    
    public void setXpos(float xPos){
        this.xPos = xPos;
    }
    
    private float moveBySpeedY(int dir){
        if(dir > 0){
            this.setYpos(this.yPos + this.getLayerSpeed());
            return this.getYpos();
        } else {
            this.setYpos(this.yPos - this.getLayerSpeed());
            return this.getYpos();
        }
    }
    
    public float getYpos(){
        return this.yPos;
    }
    
    public void updateYPos(int dir){
        this.setYpos( this.moveBySpeedY(dir) );
    }
    
    public void setYpos(float yPos){
        this.yPos = yPos;
    }

    @Override
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        
        Imagen imagen = stage.getImagenById(this.getBackgroundImagenId());
            
        for(int l = 0; l < DISTANCIA; l++){
            this.setYpos(l*imagen.getHeight()-l);
            if(stage.getViewport().getY2() >= 3300){
                //this.updateYPos(-1);//Movimiento en base a la velocidad del layer.
            }

            Rectangle2D wall = game.toWindow( 
                    new Rectangle2D.Float(
                            this.getXpos(), 
                            this.getYpos(), 
                            imagen.getWidth(), 
                            imagen.getHeight()));

            String route = imagen.getRoute();
            
            buffer.drawImage(ImageManager.getImage(route), 
                    (int)wall.getX(),
                    (int)wall.getY(), 
                    (int)wall.getWidth(),
                    (int)wall.getHeight(),null); //bufferedImage = no necesita imageObserver
            
            for(int x = 0; x < 10; x++){
                buffer.drawImage(ImageManager.getImage(route), 
                    x*imagen.getWidth()-(x*10),
                    (int)wall.getY(), 
                    (int)wall.getWidth(),
                    (int)wall.getHeight(),null); //bufferedImage = no necesita imageObserver
            }
            if(game.getViewport().getY2() >= ((DISTANCIA-1)*imagen.getHeight())){
                DISTANCIA++;
            }
        }
    }
    
    
}