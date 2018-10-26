/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.Dimension;

/**
 *
 * @author chico
 */
public class Imagen {
    private String id;
    private String route;
    private Dimension dimenison;

    public Imagen(String id, String route, int width, int height) {
        this.id = id;
        this.route = route;
        this.dimenison = new Dimension(width, height);
        
    }
    
    public String getID(){
        return this.id;
    }
    
    public void setID(String id){
        this.id = id;
    }
    
    public String getRoute(){
        return this.route;
    }
    
    public void setRoute(String route){
        this.route = route;
    }
    
    public int getWidth(){
        return (int)this.dimenison.getWidth();
    }
    
    public int getHeight(){
        return (int)this.dimenison.getHeight();
    }
    
}
