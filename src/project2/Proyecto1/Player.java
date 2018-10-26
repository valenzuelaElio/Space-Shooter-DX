/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import project2.Proyecto1.Utils.KeyUtils;
import project2.Proyecto1.Utils.AnimationType;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import project2.Engine.GameThread;
import project2.Engine.ImageManager;
import project2.Proyecto1.Utils.ModeType;
import project2.Stage.Stage;
import project2.Stage.StageElement;

/**
 *
 * @author chico
 */
public class Player extends StageElement{
    
    private static final int DELTA = 5; 
    
    private final HashMap<String , Animation> playerAnimations;
    
    boolean shoot;
    boolean isMoving;
    
    private int playerLifes;
    
    private ModeType playerState;
    private long playerStartInvencible;
    
    private Poligono bBox;
    
    public Player(String id){
        super();
        this.id = id;
        
        this.playerAnimations = new HashMap<>();
        
        this.shoot = false;
        
        this.playerLifes = 3;
        this.playerState = ModeType.NORMAL;
        
        this.bBox = new Poligono();
        
    }

    public long getPlayerStartInvencible() {
        return playerStartInvencible;
    }

    public void setPlayerStartInvencible(long playerStartInvencible) {
        this.playerStartInvencible = playerStartInvencible;
    }
    
    public void invencibleToNormal( long timesTamp ){
        if(timesTamp - this.playerStartInvencible >= 1000){
            if(this.playerState == ModeType.INVENCIBLE){
                this.playerState = ModeType.NORMAL;
                this.playerStartInvencible = timesTamp;

            }
        }
    }

    public ModeType getPlayerState() {
        return playerState;
    }

    public void setPlayerState(ModeType playerState) {
        this.playerState = playerState;
    }
    
    public int getPlayerLifes() {
        return playerLifes;
    }

    public void setPlayerLifes(int playerLifes) {
        this.playerLifes = playerLifes;
    }
    
    public boolean isIsMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }
    
    public void addAnimation(String key, Animation animation){
        this.playerAnimations.put(key, animation);
    }
    
    private Animation GetOnePlayerAnimation(String key){
        return this.playerAnimations.get(key);
    }
    
    public void setCurrentAnimation(String key){
        this.currentAnimation = this.GetOnePlayerAnimation(key);
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
    
    public Polygon getPolygon() {
        return this.bBox.getPolygon();
    }
    
    public int getBBoxSize(){
        return this.bBox.getSize(); //[Colision]
    }
    
    public void GenerateBbox(int x1, int y1, int x2, int y2 ){//[Colison]
        
        this.bBox.add(x1, y1);
        this.bBox.add(x2, y1);
        this.bBox.add(x2, y2);
        this.bBox.add(x1, y2);
        
    }
    
    public void moveBbox(int vectorX, int vectorY ){//[Colison]
        this.bBox.move( vectorX, vectorY );

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
    
    @Override
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        Graphics2D buffer2D = (Graphics2D) buffer;
        
        Imagen imagen = stage.getImagenById(this.getCurrentImageId());
        String route = imagen.getRoute();
        
        
        Rectangle2D playerWall = game.toWindow( 
                        new Rectangle2D.Float(
                                this.getX(), 
                                this.getY() + (int)stage.getViewport().getY1(), 
                                imagen.getWidth()/2, 
                                imagen.getHeight()/2));
        
        if(this.getbBox().getSize() == 0 || this.getbBox().getSize() == 4){
            this.bBox.clear();
            this.GenerateBbox(
                    (int)playerWall.getX(), 
                    (int)playerWall.getY(), 
                    (int)playerWall.getWidth() + (int)playerWall.getX(), 
                    (int)playerWall.getHeight() + (int)playerWall.getY());
        }
        
        buffer2D.drawImage(ImageManager.getImage(route), 
                (int)playerWall.getX(), 
                (int)playerWall.getY(), 
                (int)playerWall.getWidth(),
                (int)playerWall.getHeight(),null);
        
        //this.bBox.paint(stage, buffer, game);
    }
    
    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
    
    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String getId(){
        return this.id;
    }
    
    public void goRigt(long timestamp, ModeType mode){
        String key = KeyUtils.createKey(mode, AnimationType.AVANZAR);
        setCurrentAnimation(key);
        
        moveBbox(Player.DELTA, 0);
        xMovement(Player.DELTA);
        setIsMoving(true);
    }
    
    public void goLeft(long timestamp, ModeType mode){
        String key = KeyUtils.createKey(mode, AnimationType.AVANZAR);
        setCurrentAnimation(key);
        
        moveBbox(-Player.DELTA, 0);
        xMovement(-Player.DELTA);
        setIsMoving(true);
    }
    
    public void goUp(long timestamp, ModeType mode){
        String key = KeyUtils.createKey(mode, AnimationType.AVANZAR);
        setCurrentAnimation(key);
        
        moveBbox(0, Player.DELTA);
        yMovement(-Player.DELTA);
        setIsMoving(true);
    }
    
    public void goDown(long timestamp, ModeType mode){
        String key = KeyUtils.createKey(mode, AnimationType.AVANZAR);
        setCurrentAnimation(key);
        
        moveBbox(0, -Player.DELTA);
        yMovement(Player.DELTA);
        setIsMoving(true);
    }
    
    public void stay(long timestamp, ModeType mode){
        String key = KeyUtils.createKey(mode, AnimationType.QUIETO);
        setCurrentAnimation(key);
        
        moveBbox(0, 0);
        yMovement(0);
        setIsMoving(false);
    } 
    
    private void xMovement(int addX){
        this.location.setX(this.location.getX() + addX);
        
    }
    
    private void yMovement(int addY){
        this.location.setY(this.location.getY() + addY);
        
    }
    
    public float getX(){
        return this.location.getX();
        
    }
    
    public void setX(int x){
        this.location.setX(getX() + x);
    }
    
    public float getY(){
        return this.location.getY();
    }
    
    public void setY(int y){
        this.location.setX(getY() + y);
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }
    
    public void move(String key, Stage stage ){
        
        Imagen imagenPlayer = stage.getImagenById(this.getCurrentImageId());
        switch(key){
            //dependiendo de la key se aplica un vector en esa direccion
            case "Nothing-Nothing": 
                this.invencibleToNormal(System.currentTimeMillis()); 
                this.stay(startTime, this.playerState); break;
            //4 Direcciones:
            case "Right-Nothing"  : 
                if( stage.rigthLimit( this.getX(), imagenPlayer.getWidth()) == true ){
                    this.goRigt(startTime, this.playerState);
                } break;
            case "Left-Nothing"   : 
                if( stage.leftLimit( this.getX(), imagenPlayer.getWidth())){ 
                    this.goLeft(startTime, this.playerState);
                } break;
            case "Nothing-Up"     : 
                if( stage.UpperLimit( this.getY(), imagenPlayer.getHeight())) {
                    this.goDown(startTime, this.playerState);
                } break;
            case "Nothing-Down"   : 
                if(stage.downLimit( this.getY(), imagenPlayer.getHeight())){
                    this.goUp(startTime, this.playerState);
                }   break;
            //4 Direcciones Diagonales: (Innecesarias).
//            case "Right-Up"       : this.goRigt(startTime, ModeType.NORMAL); this.goUp(startTime, ModeType.NORMAL); break;
//            case "Right-Down"     : this.goRigt(startTime, ModeType.NORMAL); this.goDown(startTime, ModeType.NORMAL); break;
//            case "Left-Up"        : this.goLeft(startTime, ModeType.NORMAL); this.goUp(startTime, ModeType.NORMAL); break;
//            case "Left-Down"      : this.goLeft(startTime, ModeType.NORMAL); this.goDown(startTime, ModeType.NORMAL); break;
        }
    }

    public void moveToCenter(Stage stage) {
        while(this.getX() > stage.getViewport().getX2()/2){
            if(this.getX() > stage.getViewport().getX2()/2){
                xMovement(-5);
            } else {
                xMovement(5);
            }
        }
        
        while(this.getX() < stage.getViewport().getX2()/2){
            if(this.getX() < stage.getViewport().getX2()/2){
                xMovement(5);
            } else {
                xMovement(-5);
            }
        }
        
        yMovement(5);
    }
    
    
    
    
}
