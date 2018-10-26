/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import project2.Engine.Animator;
import project2.Engine.GameThread;
import project2.Engine.ImageManager;
import project2.Stage.Stage;
import project2.Stage.StageElement;

/**
 *
 * @author chico
 */
public class Boss extends StageElement implements Animator{
    private int lifePoints;
    
    private HashMap<String, Animation > animations;
    
    private Animation currentAnimation; //[Animacion]
    private String currentImageId; //[Animacion]
    
    
    boolean isAttacking;
    ArrayList<Proyectile> proyectiles;
    ArrayList<Proyectile> leftProyectiles;
    ArrayList<Proyectile> rightProyectiles;

    
    private Poligono bBox;

    public Boss(Animation animation) {
        this.id = "Stage-Boss";
        this.lifePoints = 20000;
        
        this.bBox = new Poligono();
        this.location = new Location(-55, 3500);
        
        this.currentAnimation = animation;
        
        this.proyectiles = new ArrayList<>();
        this.leftProyectiles = new ArrayList<>();
        this.rightProyectiles = new ArrayList<>();
        
        this.isAttacking = false;
        
    }
    
    public void attack(){
        double random = Math.random()%10;
        //System.out.println("Attack: " + random);
        if(random >= 0.5){
            setIsAttacking(true);
        } else {
            setIsAttacking(false);
        }
        
    }

    public boolean isIsAttacking() {
        return isAttacking;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    
    public String getCurrentImagenId(){
        return this.currentImageId;
    }
    
    public int getLifePoints(){
        return this.lifePoints;
    }
    
    public void setLifePoints(int lifepoints){
        this.lifePoints = lifepoints;
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
    
    public void moveBbox(int vectorX, int vectorY){//[Colision]
        this.bBox.move( vectorX, vectorY );
    }
    
    public Polygon getPolygon() //[Colision]
    {
        return this.bBox.getPolygon();
    }
    
    public int getBBoxSize(){
        return this.bBox.getSize(); //[Colision]
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
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
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
    
    public void receiveDamage(Stage stage ,Rectangle2D laser, Laser laserObject){
        Polygon polygon = this.getPolygon();
        if( polygon.contains( laser.getX(), laser.getY()) ){
            stage.removeLaser(laserObject);
            sustractLife();
        }
        if(this.getLifePoints() <= 0){
            this.getbBox().clear();
        }
    } 
    
    public void sustractLife(){
        int laserDamage = 100; //el "laser" (u otro tipo de proyectil) deberia decirme cuanto daño hace
        this.setLifePoints(this.getLifePoints() - laserDamage);
//        System.out.println("Boss Vida: " + this.getLifePoints());
    }
    
    @Override
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        if(this.lifePoints > 0){
            Imagen imagen = stage.getImagenById(this.getCurrentImagenId());
            Rectangle2D enemyWall = game.toWindow( 
                        new Rectangle2D.Float(
                                this.getLocation().getX(), 
                                this.getLocation().getY(), 
                                imagen.getWidth(), 
                                imagen.getHeight()));

            int deltaY = 100;
            int deltaX = 400;
            if(this.getbBox().getSize() == 0 || this.getbBox().getSize() == 4){
                this.bBox.clear();
                this.GenerateBbox(
                        (int)enemyWall.getX() + deltaX, 
                        (int)enemyWall.getY(), 
                        (int)enemyWall.getWidth() + (int)enemyWall.getX() - deltaX, 
                        (int)enemyWall.getHeight() + (int)enemyWall.getY() - deltaY);
            }

            buffer.drawImage(ImageManager.getImage(imagen.getRoute()), 
                    (int)enemyWall.getX(),
                    (int)enemyWall.getY(), 
                    (int)enemyWall.getWidth(),
                    (int)enemyWall.getHeight(),null);
            
            //Recargar y disparar
//            if(stage.getViewport().getY2() >= 3000){
                if(this.proyectiles.size() <= 20){
                    this.loadAmmo(stage);
                } else if(this.proyectiles.size() >= 10){
                    for(int i = 0; i < this.proyectiles.size(); i++){
                        Proyectile proyectile = this.proyectiles.get(i);
                        if( i == 1){
//                            System.out.println("X proyectil(0): " + proyectile.getX());
//                            System.out.println("Y proyectil(0): " + proyectile.getY());
                        }

                        proyectile.moveByMagnitud();
    //                    if(proyectile.getY() >= stage.getViewport().getY1()){
                        if(stage.isInViewport(proyectile.getX(), proyectile.getY()) == true){
                            proyectile.paint(stage, buffer, game);
                        } else {
                            this.proyectiles.remove(proyectile);

                        }
                    }
                }
                
                if(this.lifePoints <= 10000){
                        if(this.leftProyectiles.size() <= 14){
                        this.loadLeftAmmo(stage);
                    } else if(this.leftProyectiles.size() >= 10){
                        for(int i = 0; i < this.leftProyectiles.size(); i++){
                            Proyectile proyectile = this.leftProyectiles.get(i);

                            proyectile.moveByMagnitud();
        //                    if(proyectile.getY() >= stage.getViewport().getY1()){
                            if(stage.isInViewport(proyectile.getX(), proyectile.getY()) == true){
                                proyectile.paint(stage, buffer, game);
                            } else {
                                this.leftProyectiles.remove(proyectile);

                            }
                        }
                    }
                }
                
                if(this.lifePoints <= 5000){
                        if(this.rightProyectiles.size() <= 24){
                        this.loadRightAmmo(stage);
                    } else if(this.rightProyectiles.size() >= 10){
                        for(int i = 0; i < this.rightProyectiles.size(); i++){
                            Proyectile proyectile = this.rightProyectiles.get(i);

                            proyectile.moveByMagnitud();
        //                    if(proyectile.getY() >= stage.getViewport().getY1()){
                            if(stage.isInViewport(proyectile.getX(), proyectile.getY()) == true){
                                proyectile.paint(stage, buffer, game);
                            } else {
                                this.rightProyectiles.remove(proyectile);

                            }
                        }
                    }
                }
            
            

            this.bBox.paint(stage, buffer, game);
        }
    }
    
    public void loadAmmo(Stage stage){
        Imagen imagen = stage.getImagenById(this.currentImageId);
        double random = Math.random()%10;
        int grade = 15;
        if((float)random >= 0.4){
            grade = 50;
        }
        for(int i = 0; i < 15; i++){
            float angle = (grade*i);
            Proyectile proyectile = new Proyectile(
                    this.getLocation().getX() + (imagen.getWidth()/2), 
                    this.getLocation().getY() - (imagen.getHeight()/2), 
                    stage.getAnimationByid("a0006"), (float)(angle*Math.PI/180) );
//            System.out.println("i : " + (float)(angle*Math.PI/180));
            
            this.proyectiles.add(proyectile);
            stage.add(proyectile);
            stage.perform(System.currentTimeMillis());
        }
    }
    
    public void loadLeftAmmo(Stage stage){
        Imagen imagen = stage.getImagenById(this.currentImageId);
        double random = Math.random()%10;
        int grade = 15;
        if((float)random >= 0.4){
            grade = 50;
        }
        for(int i = 0; i < 25; i++){
            float angle = (grade*i);
            Proyectile proyectile = new Proyectile(
                    this.getLocation().getX() + (imagen.getWidth()/2) - 200, 
                    this.getLocation().getY() - (imagen.getHeight()/2), 
                    stage.getAnimationByid("a0006"), (float)(angle*Math.PI/180) );
//            System.out.println("i : " + (float)(angle*Math.PI/180));
            
            this.leftProyectiles.add(proyectile);
            stage.add(proyectile);
            stage.perform(System.currentTimeMillis());
        }
    }
    
    public void loadRightAmmo(Stage stage){
        Imagen imagen = stage.getImagenById(this.currentImageId);
        double random = Math.random()%10;
        int grade = 15;
        if((float)random >= 0.4){
            grade = 50;
        }
        for(int i = 0; i < 25; i++){
            float angle = (grade*i);
            Proyectile proyectile = new Proyectile(
                    this.getLocation().getX() + (imagen.getWidth()/2) + 200, 
                    this.getLocation().getY() - (imagen.getHeight()/2), 
                    stage.getAnimationByid("a0006"), (float)(angle*Math.PI/180) );
//            System.out.println("i : " + (float)(angle*Math.PI/180));
            
            this.rightProyectiles.add(proyectile);
            stage.add(proyectile);
            stage.perform(System.currentTimeMillis());
        }
    }

    
}
