import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;



/**
 * This class represents the various levels of the game.
 * 
 * @author Walter Tan and Raphael Naval
 */
public class Level {
	
	public int[][] map;
	private FileReader fr;
	private BufferedReader br, reader;
	private int row, col;
	public final ArrayList<Point> startPos, goals;
	public BufferedImage img;
	
	public Level(File f) throws Exception {
		startPos = new ArrayList<Point>();
		goals = new ArrayList<Point>();
		map = importLevel(f);
		//img = getBufferedImage();
	}
	
	/**
	 * Reads in the level text file and converts it into a 2D array.
	 * 
	 * FileReader and BufferedReader are used to read in an external textfile that represents the level.
	 * The file only consists of 0s, 1s, s, and x characters.
	 * 0 is for nonexistent square
	 * 1 is a normal square
	 * s is the starting position of the character
	 * x is the goal position(s)
	 * A loop traverses the text file, and following the above guidelines, generates an array of the level.
	 * 
	 * @param txt
	 * @return array
	 * @throws Exception
	 */
	public int[][] importLevel(File txt) throws Exception  {
		
		
		reader = new BufferedReader(new FileReader(txt));
		row = 0;
		String s2;
		while ((s2 =reader.readLine())!=null) {
			row++;
			col = s2.length();
		}
		reader.close();
		
		int[][] array = new int[col][row];
		String s;
		fr = new FileReader(txt); 
		br = new BufferedReader(fr);
		int y = 0;
		while((s = br.readLine()) != null) {
			for(int x=0; x<col; x++) {
				if(s.charAt(x) == 's') {	//starting pos
					startPos.add(new Point(x,y));
					array[x][y] = 1;
				} else if(s.charAt(x) == 'x') {	//goal
					goals.add(new Point(x,y));
					array[x][y] = 9;
				} else {
					array[x][y] = Character.getNumericValue(s.charAt(x));
				}
			}
			y++;
		} 
		br.close();
		return array;
		
	}
	
	public BufferedImage getBufferedImage() {
		img = new BufferedImage(1000, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		
		for(int y=0; y<row; y++) {
			for(int x=0; x<col; x++) {
				if(map[x][y] == 1) {
					g.setColor(java.awt.Color.white);
					g.fillRect(x*20+1, y*20+1, 18, 18);
				}
				else if(map[x][y] == 9) {
					g.setColor(java.awt.Color.green);
					g.fillRect(x*20+1, y*20+1, 18, 18);
				}
			}
			
		}
		return img;
		
	}
	/**
	 * Draws the level with Java Graphics.
	 * 
	 * The array is traversed, and a white square, green square, or nothing is drawn depending on the value in the array.
	 * The level is drawn row by row, which is also how the 2D array is traversed.
	 * 
	 * @param g
	 */
	public void draw(Graphics g, Image i, Image j) {
		for(int y=0; y<row; y++) {
			for(int x=0; x<col; x++) {
				if(map[x][y] == 1) {
					//g.setColor(Color.white);
					//g.fillRect(x*20+1, y*20+1, 18, 18);
					i.draw(x*20+1, y*20+1, .07f);
				}
				else if(map[x][y] == 9) {
					g.setColor(Color.green);
					g.fillRect(x*20+1, y*20+1, 18, 18);
					//j.draw(x*20+1, y*20+1, .07f);
				}
			}
			
		}
	}

}
