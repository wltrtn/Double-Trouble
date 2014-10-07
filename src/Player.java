import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * This class represents each of the multiple players that the user controls.
 * 
 * @author Walter Tan and Raphael Naval 
 */
public class Player {
	public Point position;
	private Color col;
	
	public Player(Point startPoint, Color c) {
		position = new Point(startPoint);
		col = c;
	}
	
	/**
	 * Draws the player using Java Graphics
	 * 
	 * The player is drawn with its specified color. It is drawn as a colored circle 
	 * that takes up one grid square.
	 * @param g
	 */
	public void draw(Graphics g, Image i) {
		i.draw(position.x*20, position.y*20, 0.16f);
		//g.fillOval(position.x*20+1, position.y*20+1, 18, 18);
		
	}
}
