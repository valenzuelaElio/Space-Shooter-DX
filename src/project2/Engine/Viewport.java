

package project2.Engine;

import project2.Proyecto1.Player;

/**
 *
 * @author Pablo Rojas
 */
public class Viewport 
{
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public Viewport(float x1, float y1, float x2, float y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getX1()
    {
        return x1;
    }

    public void setX1(float x1)
    {
        this.x1 = x1;
    }

    public float getY1()
    {
        return y1;
    }

    public void setY1(float y1)
    {
        this.y1 = y1;
    }

    public float getX2()
    {
        return x2;
    }

    public void setX2(float x2)
    {
        this.x2 = x2;
    }

    public float getY2()
    {
        return y2;
    }

    public void setY2(float y2)
    {
        this.y2 = y2;
    }

    public void move(int x, int y)
    {
        this.x1 += x; 
        this.x2 += x; 
        
        this.y1 += y; 
        this.y2 += y; 
        
    }
    
    public boolean isInViewport(float x , float y){
        if(x > this.x1 && x < this.x2 && y > this.y1 && y < this.y2){
            return true;
        } else {
            return false;
        }
    }
    
    
    
    
    
    
}
