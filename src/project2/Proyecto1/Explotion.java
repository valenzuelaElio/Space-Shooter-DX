/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import project2.Stage.StageElement;

/**
 *
 * @author chico
 */
public class Explotion extends StageElement{

    public Explotion() {
        super();
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
        return "explosion";
    }

    @Override
    public void setId(String id) {
        this.id = id;
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
    
}
