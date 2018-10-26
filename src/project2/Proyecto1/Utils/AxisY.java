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
public enum AxisY {
    
    U("Up"),
    D("Down"),
    N("Nothing");
    
    private String descripcion;
    
    private AxisY(String descripcion){
        this.descripcion = descripcion;
    }
    
    public String getDescripcion(){
        return this.descripcion;
    }
    
}
