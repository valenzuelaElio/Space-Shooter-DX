

package project2.Engine;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import java.util.HashMap;
import javax.swing.ImageIcon;
import project2.Proyecto1.Utils.ModeType;
import project2.Stage.Stage;

/**
 *
 * @author Pablo Rojas
 */
abstract public class GameThread extends Thread implements KeyEventDispatcher, KeyListener
{
    protected float avgFPS;
    
    private GraphicsDevice graphicsDevice;
    private boolean running;
    private HashMap<String, Image> pool;

    protected boolean fullscreen;
    protected Window window;
    protected Viewport viewport;
    protected Dimension windowDimension;
    protected Graphics buffer;
    
    
    public GameThread(Dimension dimension, Viewport viewport)
    {
        this();
        this.windowDimension = dimension;
        this.viewport = viewport;
    }
    
    public GameThread()
    {
        this.window = new Window(this);
        this.window.setTitle("Space Shooter DX");
        this.running = false;
        this.pool = new HashMap<>();
        
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }
    
    
    
    @Override
    public void run()
    {
        //this.showFullScreenFrame();
        this.showWindowedFrame();
        
        long beforeTime, timeDiff, sleepTime;
        long period = 16; //60 FPS
        
        long t1, //inicio del periodo  
                t2, // termino del periodo
                frameTime; //tiempo que se demoro en dibujar un frame
        
        long start = 0; //la suma de tiempo de una cantidad finita de fps
        
        ArrayList<Long> frameTimes = new ArrayList<>(); //Lista de fps para promediar
        
        beforeTime = System.currentTimeMillis();
        
        this.setRunning(true);
        //this.running = true;
        
        //while(this.running) 
        while(this.isRunning()) 
        {
            
            t1 = System.currentTimeMillis();
            processInput();
            updateGame();
            render();
            
            timeDiff = System.currentTimeMillis( ) - beforeTime;
            sleepTime =  period - timeDiff;
            if (sleepTime <= 0)
            {
                sleepTime = 5;
            }
            
            try 
            {
                Thread.sleep(sleepTime);
            }
            catch(InterruptedException ex)
            {
            }
            beforeTime = System.currentTimeMillis( );
            t2 = System.currentTimeMillis();
            
            if( start == 0 || t2-start >= 1000 )
            {
                start = t2;
                long sum = 0;
                for (Long time : frameTimes)
                {
                    sum+=time;
                }
                float avg = ((float)sum)/frameTimes.size();
                frameTimes.clear();
                this.avgFPS = avg;
            }
            else
            {
                frameTime = t2-t1;
                frameTimes.add(sleepTime);
            }
            
        }
        System.exit(0);
        
    }
    
    public void showFullScreenFrame()
    {
        if (graphicsDevice.isFullScreenSupported())
        {
            
            this.window.setVisible(false);
            this.window.dispose();
            this.window.setUndecorated(true);
            this.window.setResizable(false);
            this.window.setAlwaysOnTop(true);
            
            DisplayMode mode = new DisplayMode(800, 600, 32, 60);
            this.graphicsDevice.setFullScreenWindow(this.window);
            this.graphicsDevice.setDisplayMode(mode);
            
            this.window.validate();
            this.fullscreen = true;
        }
        else
        {
            this.showWindowedFrame();
        }
    }
    
    public void showWindowedFrame()
    {
        
        this.window.setVisible(false);
        this.window.dispose();
        this.window.setUndecorated(false);
        this.window.setVisible(true);
        this.window.setResizable(false);
        this.window.setAlwaysOnTop(false);
        
        this.window.setSize(this.windowDimension.width, this.windowDimension.height);
        this.window.validate();
        this.fullscreen = false;
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent e)
    {
        switch(e.getID())
        {
            case KeyEvent.KEY_TYPED   : this.keyTyped(e);    break;
            case KeyEvent.KEY_PRESSED : this.keyPressed(e);  break;
            case KeyEvent.KEY_RELEASED: this.keyReleased(e); break;
        }
        return true;
    }
    
     @Override
    public void keyTyped(KeyEvent e){
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        //MOVIMIENTO JUGADOR
        if( e.getKeyCode() == KeyEvent.VK_LEFT )
        {
            
        }
        else if( e.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
    
    public Point2D toWorld(Point2D world)
    {
        double x = this.convertirXAMundo(world.getX());
        double y = this.convertirYAMundo(world.getY());
        return new Point2D.Double(x, y);
    }
    
    public Rectangle2D toWorld(Rectangle2D world)
    {
        double x1 = this.convertirXAMundo(world.getX());
        double y1 = this.convertirYAMundo(world.getY());
        double x2 = this.convertirXAMundo(world.getX() + world.getWidth());
        double y2 = this.convertirYAMundo(world.getY() + world.getHeight());
        
        x1 = this.convertirXAMundo(x1);
        y1 = this.convertirXAMundo(y1);
        x2 = this.convertirXAMundo(x2);
        y2 = this.convertirXAMundo(y2);
        double width = x2-x1;
        double height = y2-y1;
        
        return new Rectangle2D.Double(x1, y1, width, height);
    }
    
    
    public Point2D toWindow(Point2D world)
    {
        int x = this.convertirXAVentana(world.getX());
        int y = this.convertirYAVentana(world.getY());
        return new Point2D.Float(x, y);
    }
    
    public Rectangle2D toWindow(Rectangle2D world)
    {
        int x = this.convertirXAVentana(world.getX());
        int y = this.convertirYAVentana(world.getY());
        int w = this.convertirMagnitudXAVentana(world.getWidth());
        int h = this.convertirMagnitudYAVentana(world.getHeight());
        
        return new Rectangle2D.Float(x, y, w, h);
    }
    
    private double convertirXAMundo(double xVentana)
    {
        float anchoVentana = this.window.getWidth();
        float anchoMundo = this.viewport.getX2() - this.viewport.getX1();
        double xMundo = this.viewport.getX1() + (xVentana/anchoVentana)*anchoMundo;
        return xMundo;
    }
    
    private double convertirYAMundo(double yVentana)
    {
        float altoVentana = this.window.getHeight();
        float altoMundo = this.viewport.getY2() - this.viewport.getY1();
        
        yVentana = altoVentana - yVentana;
        double yMundo = this.viewport.getY1() + (yVentana/altoVentana)*altoMundo;
        return yMundo;
    }
    
    private int convertirXAVentana(double xMundo)
    {
        float anchoMundo = this.viewport.getX2() - this.viewport.getX1();
        int yVentana = (int)Math.round(( (xMundo - this.viewport.getX1())/anchoMundo )*this.window.getWidth());
        
        return yVentana;
    }
    
    private int convertirYAVentana(double yMundo)
    {
        float altoMundo = this.viewport.getY2() - this.viewport.getY1();
        int yVentana = this.window.getHeight() - (int)Math.round(( (yMundo - this.viewport.getY1())/altoMundo )*this.window.getHeight());
        
        return yVentana;
    }
    
    private int convertirMagnitudXAVentana(double xMundo)
    {
        float worldWidth = this.viewport.getX2() - this.viewport.getX1();
        float windowWidth = this.window.getWidth();
        
        return (int)Math.round(xMundo*(windowWidth/worldWidth));
    }
    
    private int convertirMagnitudYAVentana(double yMundo)
    {
        float worldHeight = this.viewport.getY2() - this.viewport.getY1();
        float windowHeight = this.window.getHeight();
        
        return (int)(yMundo*(windowHeight/worldHeight));
    }
    
    public Image getImage(String filename)
    {
        Image image = this.pool.get(filename);
        if( image == null )
        {
            ImageIcon imageIcon = new ImageIcon(filename);
            image = imageIcon.getImage();
            this.pool.put(filename, image);
        }
        
        return image;
    }
    
    public Window getVentana()
    {
        return this.window;
    }
    
    public Viewport getViewport(){
        return this.viewport;
    }
    
    public void setRunning(boolean state)
    {
        synchronized(this)
        {
            this.running = state;
        }
    }
    
    public boolean isRunning()
    {
        boolean state;
        synchronized(this)
        {
            state = this.running;
        }
        return state;
    }
    
    
    
    abstract public void processInput();
    abstract public void updateGame();
    abstract public void render();
    
}
