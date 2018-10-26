/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

/**
 *
 * @author chico
 */
public class Frame {
    private String id;
    private float duration;
    
    public Frame(String id, float duration) {
        this.id = id;
        this.duration = duration;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    
    
}
