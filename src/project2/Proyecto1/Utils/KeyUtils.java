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
public class KeyUtils 
{
    static public String createKey( ModeType mode, AnimationType tipe )
    {
        return mode.getDescripcion() + "-" + tipe.getDescripcion();
    }
}
