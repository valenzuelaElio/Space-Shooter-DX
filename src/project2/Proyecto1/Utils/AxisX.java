/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Proyecto1.Utils;

/**
 *
 * @author chico
 */
public enum AxisX {
    
    R("Right"),
    L("Left"),
    N("Nothing");
    
    private String descripcion;
    
    private AxisX(String descripcion){
        this.descripcion = descripcion;
    }
    
    public String getDescripcion(){
        return this.descripcion;
    }
    
}
