

package project2.Engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 *
 * @author Pablo Rojas
 */
public class Window extends JFrame
{
    private final Canvas canvas;
    private BufferStrategy strategy;
    private boolean initialized;
    private GameThread game;
    
    public Window( GameThread game )
    {
        this.game = game;
        this.canvas = new Canvas();
        this.add(this.canvas);
        this.initialized = false;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public Graphics getBuffer()
    {
        if(!this.initialized)
        {
            this.canvas.createBufferStrategy(2);
            this.strategy = this.canvas.getBufferStrategy();
            this.initialized = true;
        }
        
        return this.strategy.getDrawGraphics();
    }
    
    public void update()
    {
        this.strategy.show();
    }
    
    
    @Override
    public int getHeight()
    {
        return this.canvas.getHeight();
    }
    
    @Override
    public int getWidth()
    {
        return this.canvas.getWidth();
    }

}
