/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2.Engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Pablo Rojas
 */
public class ImageManager
{
    private static ImageManager instance;
    
    private HashMap<String, BufferedImage> images;
    
    private ImageManager()
    {
        this.images = new HashMap<>();
    }

    public BufferedImage get(Object key)
    {
        return images.get(key);
    }

    public BufferedImage put(String key, BufferedImage value)
    {
        return images.put(key, value);
    }

    public BufferedImage remove(Object key)
    {
        return images.remove(key);
    }
    
    private static ImageManager getInstance()
    {
        if( ImageManager.instance == null )
        {
            ImageManager.instance = new ImageManager();
        }
        return ImageManager.instance;
    }
    
    public static boolean registerImage(String filename) throws IOException 
    {
        ImageManager manager = ImageManager.getInstance();
        BufferedImage image = manager.get(filename);
        if( image == null )
        {
            File file = new File(filename);
            if( file.exists() ) 
            {
                image = ImageIO.read(file);
                manager.put(filename, image);
                return true;
            }
        }
        return false;
    }
    
    public static BufferedImage getImage(String filename)
    {
        ImageManager manager = ImageManager.getInstance();
        return manager.get(filename);
    }
            
}
