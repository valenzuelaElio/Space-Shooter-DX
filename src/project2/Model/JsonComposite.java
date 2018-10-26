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
public class JsonComposite {
    private ArrayList<JsonImage> sImages;
    private ArrayList<JsonLayer> layers;

    public JsonComposite() {
    }

    public ArrayList<JsonImage> getsImages() {
        return sImages;
    }

    public void setsImages(ArrayList<JsonImage> sImages) {
        this.sImages = sImages;
    }

    public ArrayList<JsonLayer> getLayers() {
        return layers;
    }

    public void setLayers(ArrayList<JsonLayer> layers) {
        this.layers = layers;
    }
    
    
    
}
