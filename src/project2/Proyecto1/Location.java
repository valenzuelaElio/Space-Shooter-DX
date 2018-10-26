/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.geom.Point2D;

/**
 *
 * @author chico
 */
public class Location {
    
    private float x;
    private float y;

    public Location(float x, float y) {
        this.x = x;
        this.y = y;
        
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public void move(float x, float y){
        this.x += x;
        this.y += y;
        
    }
    
    
    
    
    
}
