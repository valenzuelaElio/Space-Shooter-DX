/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Engine;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import project2.Proyecto1.Imagen;
import project2.Proyecto1.Poligono;
import project2.Stage.Stage;

/**
 *
 * @author chico
 */
public class HUD implements Element{
    private Imagen imagenVidas;
    private int playerLifes;
    
    private int bossLifeStart;
    private int bossLife;

    public HUD(Stage stage) {
        this.playerLifes = stage.getPlayer().getPlayerLifes();
        this.imagenVidas = stage.getImagenById("img0001");
        
        this.bossLifeStart = stage.getStageBoss().getLifePoints();
        this.bossLife = 0;
    }

    @Override
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        this.updateLifes(stage);
        
        Rectangle2D wall = game.toWindow( 
                        new Rectangle2D.Float( 
                                0 , 
                                stage.getViewport().getY2(), 
                                this.imagenVidas.getWidth(), 
                                this.imagenVidas.getHeight() ));
        for(int i = 0; i < this.playerLifes; i++){
            
            
//            System.out.println("(int)wall.getX(): " + (int)wall.getX());
//            System.out.println("(int)wall.getY(): " + (int)wall.getY());
//            System.out.println("(int)wall.width: " + (int)wall.getWidth());
//            System.out.println("(int)wall.height: " + (int)wall.getHeight());
            
            
            buffer.drawImage(ImageManager.getImage(this.imagenVidas.getRoute()),
                    (int)wall.getX() + ((int)wall.getWidth()/2)*i,
                    (int)wall.getY(), 
                    (int)wall.getWidth()/2, 
                    (int)wall.getHeight()/2, null );
                    
        }
        
        this.updateBossLife(stage);
        if(stage.getViewport().getY2() >= 3000 && this.bossLifeStart != 0){
            Rectangle2D lifeBar = game.toWindow( 
                        new Rectangle2D.Float( 
                                ((int)wall.getX() + ((int)wall.getWidth()/2)*3) + 10, 
                                stage.getViewport().getY2() - 10, 
                                this.bossLife, 
                                20));
            
            this.bossLife = this.bossLifeStart - this.bossLife;
            float diferencia = (float)(6*this.bossLife*0.005);
            
            int vidaAMostrar = 600;
            System.out.println("vida : " + (vidaAMostrar - diferencia));
            
            buffer.drawRect(
                    (int)lifeBar.getX(), 
                    (int)lifeBar.getY(), 
                    (int)(vidaAMostrar - diferencia), 
                    (int)lifeBar.getHeight());
            
            buffer.fillRect(
                    (int)lifeBar.getX(), 
                    (int)lifeBar.getY(), 
                    (int)(vidaAMostrar - diferencia), 
                    (int)lifeBar.getHeight());
        }
                
                
    }
    
    public void updateLifes(Stage stage){
        this.playerLifes = stage.getPlayer().getPlayerLifes();
        
    }

    private void updateBossLife(Stage stage) {
        this.bossLife = stage.getStageBoss().getLifePoints();
    }
    
    
    
    
    
    
}
