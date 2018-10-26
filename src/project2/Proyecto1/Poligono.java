package project2.Proyecto1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import project2.Engine.Element;
import project2.Engine.GameThread;
import project2.Stage.Stage;

public class Poligono implements Element
{
    private ArrayList<Point2D> points;
    
    private boolean fill;
    private Color fillingColor;
    private Color borderColor;

    public Poligono() 
    {
        this.points = new ArrayList<>();
        
        this.fill = false;
        this.borderColor = Color.BLACK;
    }
    
    public boolean add(double x, double y) 
    {
        return points.add( new Point2D.Double(x, y) );
    }
    
    public boolean add(Point2D e) 
    {
        return points.add(e);
    }

    public void clear() 
    {
        points.clear();
    }

    public Iterator<Point2D> iterator()
    {
        return points.iterator();
    }

    public void move( int vectorX, int vectorY){
        int N = this.points.size();
        
        for (int i = 0; i < N; i++) 
        {
            Point2D point = this.points.get(i);
            point.setLocation(point.getX() + vectorX, point.getY() + vectorY);
        }
    }
    
    public Polygon getPolygon()
    {
        Polygon poly = new Polygon();
        for (Point2D point : points) 
        {
            poly.addPoint( (int)point.getX(), (int)point.getY());
        }
        return poly;
    }
    
    public int getSize(){
        return this.points.size();
    }
    
    //Para pintar los poligonos
    public Poligono(boolean fill, Color fillingColor) 
    {
        this();
        this.fill = fill;
        this.fillingColor = fillingColor;
    }

    public void setFillingColor(Color fillingColor)
    {
        this.fillingColor = fillingColor;
    }

    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }

    @Override
    public void paint(Stage stage, Graphics buffer, GameThread game) {
        Polygon polygon = this.createPolygon(game);
        
        if(!fill)
        {
            buffer.setColor(this.borderColor);
            buffer.drawPolygon(polygon);
        }
        else
        {
            buffer.setColor(this.fillingColor);
            buffer.fillPolygon(polygon);
        }
    }
    
    private Polygon createPolygon(GameThread game)
    {
        int N = this.points.size();
        int[] x = new int[N];
        int[] y = new int[N];
        
        for (int i = 0; i < N; i++) 
        {
            Point2D point = this.points.get(i);
//            Point2D window = game.toWindow(point);
//            x[i] = (int)window.getX();
//            y[i] = (int)window.getY();
//            Los estaba transformando dos veces
            x[i] = (int)point.getX();
            y[i] = (int)point.getY();
        }
        
        return new Polygon(x, y, N);
    }
    
    public Point2D getPoint(int index){
        return this.points.get(index);
    }
}
