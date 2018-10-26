/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import project2.Engine.GameThread;
import project2.Engine.Viewport;
import project2.Proyecto1.Imagen;
import project2.Proyecto1.Player;
import project2.Proyecto1.Utils.AxisX;
import project2.Proyecto1.Utils.AxisY;
import project2.Proyecto1.Utils.MovementManager;
import project2.Readers.XMLReader;
import project2.Stage.Stage;

/**
 *
 * @author chico
 */
public class MiJuego extends GameThread{
    
    Stage stage;
    int speed = 2;
    String key = MovementManager.createMovement(AxisX.N, AxisY.N); //Comienza en NN - no se mueve;
    
    boolean isGameOver = false;
    
    boolean right, left , up ,down;

    public MiJuego() {
        super( new Dimension(800, 600), new Viewport(0, 0, 800, 600));
        this.stage = new Stage();
        XMLReader.readXML(this.stage);
    }
    
    public MiJuego(Stage stage){
        super( new Dimension((int)stage.getViewport().getX2(), (int)stage.getViewport().getY2()), stage.getViewport());
        this.stage = stage;
        this.fullscreen = false;
        
    }

    @Override
    public void processInput() {
        try {
            this.isGameOver();
        } catch (Throwable ex) {
            Logger.getLogger(MiJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.buffer = this.window.getBuffer();
        this.buffer.clearRect(0, 0, this.window.getWidth(), this.window.getHeight());
        
        this.buffer.setColor(Color.WHITE);
        this.buffer.fillRect(0, 0, this.window.getWidth(), this.window.getHeight());
        
        if(this.viewport.getY2() <= 3200){
            speed = 3;
        }
        if(this.viewport.getY2() <= 3250 && this.viewport.getY2() >= 3200){
                speed = 1;
        }
        if(this.viewport.getY2() >= 3300){
            speed = 0;
        }
        
        this.viewport.move(0, speed);
        this.theSmoothMovement();
        this.stage.paint(this.buffer, this, System.currentTimeMillis());
        this.buffer.drawString( "FPS: " + this.avgFPS, 20, 60 );

        
    }

    @Override
    public void updateGame() {
        
        this.stage.perform(System.currentTimeMillis());
        this.window.update();
        this.buffer.dispose();
        
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void render() {
        
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        switch(e.getID())
        {
            case KeyEvent.KEY_TYPED   : this.keyTyped(e);    break;
            case KeyEvent.KEY_PRESSED : this.keyPressed(e);  break;
            case KeyEvent.KEY_RELEASED: this.keyReleased(e); break;
        }
        return true;
    }
    
     @Override
    public void keyTyped(KeyEvent e){
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        //MOVIMIENTO JUGADOR 4 direcciones //Cambiar el formato de la estructura
        if( e.getKeyCode() == KeyEvent.VK_LEFT ){
            this.left = true;
        } 
        if( e.getKeyCode() == KeyEvent.VK_RIGHT ){
            this.right = true;
        } 
        if( e.getKeyCode() == KeyEvent.VK_DOWN ){
            this.down = true;
        } 
        if( e.getKeyCode() == KeyEvent.VK_UP ){
            this.up = true;
        }
        
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            this.stage.getPlayer().setShoot(true);
        }
        
        if(e.getKeyCode() == KeyEvent.VK_F){
            this.showFullScreenFrame();
        }
        
        if( e.getKeyCode() == KeyEvent.VK_ESCAPE )
        {
            if(this.fullscreen)
            {
                showWindowedFrame();
            }
            else
            {
                System.exit(0);
            }
        }
        else if( e.getKeyCode() == KeyEvent.VK_F && e.isControlDown() )
        {
            if( !this.fullscreen )
            {
                showFullScreenFrame();
            }
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

        if( e.getKeyCode() == KeyEvent.VK_LEFT ){
            this.left = false;
        }
        if( e.getKeyCode() == KeyEvent.VK_RIGHT ){
            this.right = false;
        } 
        if( e.getKeyCode() == KeyEvent.VK_DOWN ){
            this.down = false;
        } 
        if( e.getKeyCode() == KeyEvent.VK_UP ){
            this.up = false;
        }
       
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            this.stage.getPlayer().setShoot(false);
            this.stage.newShoot();
        }
        
    }
    
    public void movement(AxisX axisX , AxisY axisY){
        key = MovementManager.createMovement(axisX, axisY);
        //System.out.println("Movement: " + key);
        this.movePlayer(this.stage, key);
        
    }
    
    public void movePlayer(Stage stage , String key){
        Player stagePlayer = stage.getPlayer();
        stagePlayer.move(key , stage);
    }
    
    public void theSmoothMovement(){
        if(this.right == true){
            movement(AxisX.R, AxisY.N);
        }
        
        if(this.left == true){
            movement(AxisX.L, AxisY.N);
        }
        
        if(this.up == true){
            movement(AxisX.N, AxisY.U);
        }
        
        if(this.down == true){
            movement(AxisX.N, AxisY.D);
        }
        
        if(this.right == false && this.left == false && this.up == false && this.down == false){
            movement(AxisX.N, AxisY.N);
        }
        
        
    }

    private void isGameOver() throws Throwable {
        if(this.stage.getPlayer().getPlayerLifes() == -1){
            this.isGameOver = true;
        }
        
        if(this.isGameOver == true){
            super.setRunning(false);
        }
    }

}
