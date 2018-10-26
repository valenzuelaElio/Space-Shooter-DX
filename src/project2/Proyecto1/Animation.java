/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.Image;
import java.util.ArrayList;
import project2.Engine.ImageManager;

/**
 *
 * @author chico
 */
public class Animation{
    private String id;
    private ArrayList<Frame> frames;

    public Animation(String id) {
        this.id = id;
        this.frames = new ArrayList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void addFrame(Frame frame){
        this.frames.add(frame);
    }
    
    public int getFramesSize(){
        return this.frames.size();
    }
    
    public Frame getFrame(int index){
        return this.frames.get(index);
    }
    
    public int getMaxTime(){
        float maxTime = 0;
        for(int i = 0; i < this.getFramesSize(); i++){
            maxTime = maxTime + this.getFrame(i).getDuration();
        }
        return (int)maxTime;
    }
    
}