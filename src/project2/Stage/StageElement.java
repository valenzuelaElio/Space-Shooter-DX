/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Stage;

import java.awt.Graphics;
import java.awt.Polygon;
import project2.Engine.Animator;
import project2.Engine.Element;
import project2.Engine.GameThread;
import project2.Proyecto1.Animation;
import project2.Proyecto1.Location;
import project2.Proyecto1.Poligono;

/**
 *
 * @author chico
 */
abstract public class StageElement implements Element, Animator {
    protected Location location;
    protected String id;
    
    //[Animation]
    protected Animation currentAnimation;
    protected String currentImageId;
    protected boolean isStarted;
    protected boolean isAlive;
    protected int index;
    protected long startTime;

    public StageElement() {
        //[Animation]
        this.isStarted = false;
        this.isAlive = true;
        this.index = 0;
        
        
    }
    
    abstract public Location getLocation();
    abstract public void setLocation(Location location);
    
    abstract public String getId();
    abstract public void setId(String id);

    @Override
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        //paint(stage, buffer, game);
    }
    
    
}
