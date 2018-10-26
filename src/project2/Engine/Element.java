

package project2.Engine;

import java.awt.Graphics;
import project2.Proyecto1.Location;
import project2.Stage.Stage;
/**
 *
 * @author Pablo Rojas
 */
public interface Element 
{
    public void paint(Stage stage, Graphics buffer, GameThread game);
}
