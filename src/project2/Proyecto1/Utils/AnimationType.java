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
public enum AnimationType
 {
    DISPARO("Disparo"),
    QUIETO("Quieto"),
    SALTO("Salto"),
    AVANZAR("Avanzar"),
    RETROCEDER("Retroceder");
    
    private String descripcion;

    private AnimationType(String descripcion) 
    {
        this.descripcion = descripcion;
    }

    public String getDescripcion() 
    {
        return descripcion;
    }
}
