/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Model;

import java.util.ArrayList;

/**
 *
 * @author chico
 */
public class JsonProject 
{
    private long pixelToWorld;
    private JsonResolution originalResolution;
    private ArrayList<JsonScene> scenes;

    public JsonProject() 
    {
        this.scenes = new ArrayList<>();
    }

    public long getPixelToWorld() {
        return pixelToWorld;
    }

    public void setPixelToWorld(long pixelToWorld) {
        this.pixelToWorld = pixelToWorld;
    }

    public JsonResolution getOriginalResolution() {
        return originalResolution;
    }

    public void setOriginalResolution(JsonResolution originalResolution) {
        this.originalResolution = originalResolution;
    }

    public ArrayList<JsonScene> getScenes() {
        return scenes;
    }

    public void setScenes(ArrayList<JsonScene> scenes) {
        this.scenes = scenes;
    }
    
    
    
    
    
}
