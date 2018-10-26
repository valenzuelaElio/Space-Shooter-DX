/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Stage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import project2.Engine.Animator;
import project2.Engine.GameThread;
import project2.Engine.HUD;
import project2.Engine.ImageManager;
import project2.Engine.Viewport;
import project2.Proyecto1.Imagen;
import project2.Proyecto1.Animation;
import project2.Proyecto1.Boss;
import project2.Proyecto1.Enemy;
import project2.Proyecto1.Laser;
import project2.Proyecto1.Layer;
import project2.Proyecto1.Player;
import project2.Proyecto1.Proyectile;
import project2.Proyecto1.Utils.AnimationType;
import project2.Proyecto1.Utils.KeyUtils;
import project2.Proyecto1.Utils.ModeType;
import project2.Readers.XMLReader;

/**
 *
 * @author chico
 */
public class Stage {
    
    private long lastShoot;
    
    ArrayList<StageElement> stageElements;
    
    Boss stageBoss;
    
    //MUNDO; contiene toda la informacion de la escena.
    int width;
    int height;
    
    Viewport viewport;
    
    ArrayList<Imagen> imagens;
    ArrayList<Animation> animations;
    ArrayList<Layer> layers;
    
    Player player;
    ArrayList<Enemy> enemies;
    
    ArrayList<Animator> animators;
    
    ArrayList<Laser> lasers;
    
    HUD hud;
    
    public Stage(){
        
        this.lastShoot = 0;
        this.viewport = new Viewport(0, 0, 0, 0);
        
        
        this.imagens = new ArrayList();
        this.animations = new ArrayList();
        this.layers = new ArrayList();
        
        this.enemies = new ArrayList();
        
        this.animators = new ArrayList();
        
        this.lasers = new ArrayList<>();
        
        this.stageElements = new ArrayList();
        
        XMLReader.readXML(this);
        this.loadImagens();

        

        
        String key = KeyUtils.createKey(ModeType.NORMAL, AnimationType.QUIETO);
        
        this.stageBoss = new Boss(this.getAnimationByid("a0007"));
        this.stageElements.add(this.stageBoss);
        this.hud = new HUD(this);
        this.player.setCurrentAnimation(key);
        this.animators.add(this.stageBoss);
        this.animators.add(this.player);
        for(int i = 0; i < this.enemies.size(); i++ ) {
            this.animators.add(this.enemies.get(i));
        }
        
        
        this.perform(System.currentTimeMillis());
        
    }
    
    public boolean add(Animator e){
        return animators.add(e);
    }
    
    public void perform(long timestamp){
        ArrayList<Animator> toRemoveAnimators = new ArrayList<>();
        for (Animator animator : this.animators) {
            if(animator.isAlive() == true) {
                if( animator.isStarted() == false ) {
                    animator.start(timestamp);
                }
                animator.perform(timestamp);
            } else {
                toRemoveAnimators.add(animator);
            }
        }
        
        for (Animator animator : toRemoveAnimators) {
            this.animators.remove(animator);
        }
    }
    
    public void setStageDimension(int width , int height){
        this.width = width;
        this.height = height;
    }
    
    public void setViewport(Viewport vpt){
        this.viewport = vpt;
    }
    
    public Viewport getViewport(){
        return this.viewport;
    }
    
    public void addImagen(Imagen imagen){
        this.imagens.add(imagen);
    }
    
    public int getImagensSize(){
        return this.imagens.size();
    }
    
    public Imagen getImagen(int index){
        return this.imagens.get(index);
    }
    
    public Imagen getImagenById(String id){
        for(Imagen imagen : this.imagens){
            if(imagen.getID().equals(id)){
                return imagen;
            }
        }
        return null;
    }
    
    public void loadImagens(){
        for(int i = 0 ; i < this.getImagensSize(); i++){
            Imagen imagen = this.getImagen(i);
            if(imagen != null){
                try{
                    if( !ImageManager.registerImage( imagen.getRoute() ) ){
                        System.out.println("La Imagen no existe");
                    }
                }
                catch (IOException ex){
                    System.out.println(imagen.getRoute() + "no se pudo cargar");
                }
            }
        }
    }
    
    public void addAnimation(Animation animation){
        this.animations.add(animation);
    }
    
    public Animation getAnimationByid(String id){
        for(Animation aux: this.animations){
            if(aux.getId().equals(id)){
                return aux;
            }
        }
        return null;
    }
    
    public int getLayersSize(){
        return this.layers.size();
    }
    
    public void addLayer(Layer layer){
        this.layers.add(layer);
    }
    
    public Layer getLayer(int index){
        return this.layers.get(index);
    }
    
    public void orderLayerByDepth(){
        Collections.sort(this.layers);
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    public Enemy getEnemyById(String id){
        for(Enemy aux : this.enemies){
            if(aux.getId() == id){
                return aux;
            }
        }
        return null;
    }
    
    public Boss getStageBoss(){
        return this.stageBoss;
    }
    
    public void setStageBoss(Boss boss){
        this.stageBoss = boss;
    }
    
    public void paint(Graphics buffer, GameThread game, long timestamp){
        //Pintar los layers:
        Layer layer;
        this.orderLayerByDepth();
        for(int i = 0; i < this.getLayersSize(); i++){
            layer = this.getLayer(i);
            layer.paint(this, buffer, game);
        }
        
        for(StageElement stageElement : this.stageElements){
            stageElement.paint(this, buffer, game);
        }
        
        //Pintar lasers
        if(this.player.isShoot() == true){
            this.addLaserToScene(timestamp);
            
        }
        
        
        Laser laser;
        for(int i = 0; i < lasers.size(); i++){
            laser = lasers.get(i);
            laser.updateYPos(1);
            if(laser.getY() <= this.viewport.getY2()){
                laser.paint(this, buffer, game);
            } else {
                this.lasers.remove(laser);
            }
        }
        
        if(this.stageBoss.getLifePoints() <= 0){
            this.player.moveToCenter(this);
        }
        
        this.hud.paint(this, buffer, game);
        
        //buffer.drawString(aci, width, width);
    }
    
    public void playerShoot(){
        this.player.setShoot(true);
    }
    
    public void destroyHittedEnemy(Rectangle2D laser, Laser laserObject){
        Iterator<Enemy> it = this.enemies.iterator();
        while(it.hasNext())
        {
            try{
                if(this.enemies.size() != 0){
                    Enemy enemy = it.next();
                    if(verifyEnemies(enemy)){
                        Polygon poly = enemy.getPolygon();
                        if( poly.contains(laser.getX(), laser.getY()) ) {
                            this.stageElements.remove(enemy);
                            this.lasers.remove(laserObject);
                            enemy.getbBox().clear();
                        }
                    }
                    
                } else {
                    break;
                }
            } catch (Exception e){
                System.out.println("Hubo un error");
            }
        }
    }
    
    public boolean verifyEnemies(Enemy enemy){
        if(isInViewport( enemy.getLocation().getX(), enemy.getLocation().getY() ) == true){
            return true;
        } else {
            return false;
        }
        
    }
    
    public boolean isInViewport(float x, float y){
        return this.viewport.isInViewport(x, y);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void addEnemy(Enemy enemy){
        this.enemies.add(enemy);
    }
    
    public void addStageElement(StageElement stageElement){
        this.stageElements.add(stageElement);
    }
    
    public StageElement getStageElementById(String id){
        for(StageElement aux : this.stageElements){
            if(aux.getId().equals(id)){
                return aux;
            }
            
        }
        return null;
    }
    
    public void removeLaser(Laser laser){
        this.lasers.remove(laser);
    }

    private void addLaserToScene(long timestamp) {
        
        if( timestamp - lastShoot < 200 )
        {
            return;
        }
        
        lastShoot = timestamp;
        Imagen imagen;
        imagen = getImagenById(this.player.getCurrentImageId());
        Laser laser = new Laser( 
                    imagen.getWidth()/4 + this.player.getX(),
                    (imagen.getHeight()/4 + this.player.getY()) + (int)this.viewport.getY1(),
                    getAnimationByid("a0006"));
            this.animators.add(laser);
            this.perform(System.currentTimeMillis());
            this.lasers.add(laser); 
    }

    public boolean rigthLimit(float x , int width){
        if( x + (width / 2) < this.getViewport().getX2()){//OK mas o menos
            return true;
        } else {
            return false;
        }
    }
    
    public boolean leftLimit(float x, int width){
        if( x > this.getViewport().getX1()){ //OK
            return true;
        } else {
            return false;
        }
    }
    
    public boolean downLimit(float y , int height){ //OK (+ o -)
        if(  y  + this.getViewport().getY1() > this.getViewport().getY1() + 40 ){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean UpperLimit(float y, int height){ //OK (+ o -)
        if( y + this.getViewport().getY1() < this.getViewport().getY2() - 10){
            return true;
        } else {
            return false;
        }
    }
    
    public void PlayerCollition(Enemy enemy){
        String key = KeyUtils.createKey(ModeType.INVENCIBLE, AnimationType.QUIETO);
        Polygon playerPoligon = this.getPlayer().getPolygon();
        int puntos = enemy.getBBoxSize();
        for(int i = 0; i < puntos; i++){
            if(playerPoligon.contains(enemy.getbBox().getPoint(i))){
                if(this.getPlayer().getPlayerState() != ModeType.INVENCIBLE){
                    this.getPlayer().setPlayerLifes( this.getPlayer().getPlayerLifes()-1 );
                    System.out.println("Vidas : " + this.getPlayer().getPlayerLifes());
                    this.getPlayer().setPlayerState(ModeType.INVENCIBLE);
                    this.getPlayer().setCurrentAnimation(key);
                    this.getPlayer().setPlayerStartInvencible(System.currentTimeMillis());
                    
                    
                } else{
                    
                    
                }
                
            }
        }
    }
    
    public void bossAttackCollition(Proyectile proyectile){
        String key = KeyUtils.createKey(ModeType.INVENCIBLE, AnimationType.QUIETO);
        Polygon playerPoligon = this.getPlayer().getPolygon();
        int puntos = proyectile.getBBoxSize();
        for(int i = 0; i < puntos; i++){
            if(playerPoligon.contains(proyectile.getbBox().getPoint(i))){
                if(this.getPlayer().getPlayerState() != ModeType.INVENCIBLE){
                    this.getPlayer().setPlayerLifes( this.getPlayer().getPlayerLifes()-1 );
                    System.out.println("Vidas : " + this.getPlayer().getPlayerLifes());
                    this.getPlayer().setPlayerState(ModeType.INVENCIBLE);
                    this.getPlayer().setCurrentAnimation(key);
                    this.getPlayer().setPlayerStartInvencible(System.currentTimeMillis());
                    proyectile.setIsDestroyed(true);
                    
                } else{
                    
                    
                }
                
            }
        }
    }

    public void newShoot() 
    {
        this.lastShoot = 0;
    }
    
    
    
    
    
    
    
    
    
}
