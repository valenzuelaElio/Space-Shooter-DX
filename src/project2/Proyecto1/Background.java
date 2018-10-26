/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1;

import java.awt.Image;
import project2.Engine.ImageManager;

/**
 *
 * @author chico
 */
public class Background {
    private String imagenId;
    private boolean xRepeat;
    private boolean yRepeat;

    public Background(String imagenId, boolean xRepeat, boolean yRepeat) {
        this.imagenId = imagenId;
        this.xRepeat = xRepeat;
        this.yRepeat = yRepeat;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }

    public boolean isxRepeat() {
        return xRepeat;
    }

    public void setxRepeat(boolean xRepeat) {
        this.xRepeat = xRepeat;
    }

    public boolean isyRepeat() {
        return yRepeat;
    }

    public void setyRepeat(boolean yRepeat) {
        this.yRepeat = yRepeat;
    }
    
    
}
    
        
