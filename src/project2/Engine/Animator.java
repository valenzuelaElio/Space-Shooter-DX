/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Engine;

/**
 *
 * @author chico
 */
public interface Animator {
    
    public boolean isStarted();
    public boolean isAlive();
    public void start(long timestamp);
    public void perform(long timestamp);
    
}
