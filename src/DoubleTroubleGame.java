import java.awt.Font;
import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;

/**
 * This is the class for the main container of the game. 
 * It includes the main, initial, update, and render methods.
 * 
 * @author Walter Tan and Raphael Naval
 */
public class DoubleTroubleGame extends BasicGame {
	
	public static final int HEIGHT = 400;
    public static final int WIDTH = 1000;
    
	Player first;
	Player second;
	Level level0,currentLevel;
	int[][] map;
	Level[] levelsArray;
	int levelCounter;
	boolean complete, newLevel;
	int numMoves;
	
	Sound moveFX = null;
	Sound endFX = null;
	Sound backgroundMusic = null;
	Sound booFX = null;
	Image background = null;
	Image red = null;
	Image aqua = null;
	Image spacetile = null;
	Image goal = null;
	
	public DoubleTroubleGame() {
		super("Double Trouble");
	}
	
	/**
	 * Sets up initial values for the game.
	 * 
	 * It loads the levels, resets the move counter to 1, and 
	 * initializes the positions of the characters.
	 * 
	 * @param gc
	 * @throws SlickException 
	 */
	public void init(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		
		backgroundMusic = new Sound("reflections.wav");
		
		
		backgroundMusic.loop();
		
		moveFX = new Sound("blop.wav");
		endFX = new Sound("end.wav");
		booFX = new Sound("boo.wav");
		
		background = new Image("space.png");
		red = new Image("red.png");
		aqua = new Image("aqua.png");
		spacetile = new Image("spacetile.png");
		//goal = new Image("goal.png");
		
		levelCounter = 0;
		levelsArray = new Level[20];
		String lvString;
		
		for(int i=0; i<20; i++) {
			lvString = "level" + i + ".txt";
			try {

				levelsArray[i] = new Level(new File(lvString));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		currentLevel = levelsArray[levelCounter];
		map = currentLevel.map;
		first = new Player(currentLevel.startPos.get(0), Color.red);
		second = new Player(currentLevel.startPos.get(1), Color.blue);
		numMoves = 0;
		newLevel = true;
		
	}
	
	public void loadNextLevel() {
		levelCounter++;
		if(levelCounter == 20) {
			complete = true;
			return;
		}
			
		currentLevel = levelsArray[levelCounter];
		map = currentLevel.map;
		first.position.setLocation(currentLevel.startPos.get(0));
		second.position.setLocation(currentLevel.startPos.get(1));
		numMoves = 0;
		newLevel = true;
	}
	
	/**
	 * Updates player positions based on input.
	 * 
	 * A key listener is used to read user input. The arrow keys are used for movement. If the input puts the character
	 * on an invalid spot, the character is not moved to that spot. It is constantly checked if both characters have reached
	 * their goal positions. Finally, it is checked if the game is in the finished state, allowed the user to restart if
	 * he wishes
	 * 
	 * @param gc, delta
	 * @throws SlickException
	 */
	public void update(GameContainer gc, int delta) throws SlickException {
		
		if(first.position.equals(currentLevel.goals.get(0)) || first.position.equals(currentLevel.goals.get(1))) {
			if(second.position.equals(currentLevel.goals.get(0)) || second.position.equals(currentLevel.goals.get(1))) {
				loadNextLevel();
				endFX.play();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			numMoves++;
			if(map[(int) (first.position.x-1)][(int) first.position.y] != 0) {
				first.position.x--;
				moveFX.play();
			}	if(map[(int) (second.position.x-1)][(int) second.position.y] != 0) {
				second.position.x--;
				moveFX.play();
			}
				
		} else	if (input.isKeyPressed(Input.KEY_RIGHT)) {
			numMoves++;
			if(map[(int) (first.position.x+1)][(int) first.position.y] != 0) {
				first.position.x++;
				moveFX.play();
			}	if(map[(int) (second.position.x+1)][(int) second.position.y] != 0)	{
				second.position.x++;
				moveFX.play();
			}

		} else	if (input.isKeyPressed(Input.KEY_UP)) {
			numMoves++;
			if(map[(int) (first.position.x)][(int) first.position.y-1] != 0)	{
				first.position.y--;
				moveFX.play();
			}	if(map[(int) (second.position.x)][(int) second.position.y-1] != 0)	{
				second.position.y--;
				moveFX.play();
			}
			
		} else	if (input.isKeyPressed(Input.KEY_DOWN)) {
			numMoves++;
			if(map[(int) (first.position.x)][(int) first.position.y+1] != 0)	{
				first.position.y++;
				moveFX.play();
			}	if(map[(int) (second.position.x)][(int) second.position.y+1] != 0)	{
				second.position.y++;
				moveFX.play();
			}
		}
		
		if(input.isKeyPressed(Input.KEY_S) && !complete) {
			loadNextLevel();
			booFX.play();
		}
	}
	
	/**
	 * Renders the game, drawing all the necessary components.
	 * 
	 * First, it is checked if the level is completed or not. If the game is not, the move counter is drawn, as well as
	 * the level and its characters. If the game is complete, it displays a congratulations message, and also prompts for 
	 * a restart.
	 * 
	 * @param gc, g
	 * @throws SlickException
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {

		if (complete == false) {
			g.setColor(Color.green);
			background.draw(0,0);
			g.setColor(Color.white);
			g.drawString("Moves: " + numMoves, 700, 20);
			currentLevel.draw(g, spacetile, goal);
			first.draw(g, red);
			second.draw(g, aqua);
			
			//g.setFont(new TrueTypeFont(new java.awt.Font("Verdana", Font.PLAIN, 32), false));
			g.drawString("Level "+(1+levelCounter), 10,10);
			
			if (levelCounter == 0) {
				//g.setFont(new TrueTypeFont(new java.awt.Font("Verdana", Font.PLAIN, 15), true));
				g.drawString("INSTRUCTIONS", 700, 70);
				g.drawString("Use the arrow keys to move both", 700, 90);
				g.drawString("characters simultaneously.", 700, 110);
				g.drawString("Complete a level by moving both", 700, 130);
				g.drawString("characters to their goals at the" , 700, 150);
				g.drawString("same time. The characters cannot", 700, 170);
				g.drawString("walk over gaps. If you're bad, ", 700, 190);
				g.drawString("press S to skip the level.", 700, 210);
			}
		}
		
		if(complete == true) {
			g.clear();	
			g.setColor(Color.green);
			g.setFont(new TrueTypeFont(new java.awt.Font("Verdana", Font.PLAIN, 32), false));
			g.drawString("CONGRATULATIONS", 230,150);
			g.drawString("YOU BEAT THE GAME", 230, 200);
//			g.setFont(new TrueTypeFont(new java.awt.Font("Verdana", Font.PLAIN, 16), true));
//			g.drawString("Press R to restart", 230, 250);
		}
	}
	
	/**
	 * The main method for the game.
	 * 
	 * It creates a new Slick2D game container. The display mode is also set to static values.
	 * The game is then started.
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new DoubleTroubleGame());
		app.setDisplayMode(WIDTH, HEIGHT, false);
		app.start();
	}
	

}
