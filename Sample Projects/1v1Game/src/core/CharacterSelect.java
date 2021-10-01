package core;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CharacterSelect extends BasicGameState 
{	
	private int id;
	public static int backgroundColor1, backgroundColor2, backgroundColor3;
	public static int backgroundColor;

	public static float time, timeStart, timeSeconds;
	public static boolean readyFor2;
	public static GameContainer gc;
	public static String p1Select;
	public static String p2Select;
	
	private String p1Name, p2Name;

	public boolean lavaPressed;
	public boolean gameStartPressed;
	private float w;
	private float h;
	private float champW;
	private float champH;

	private boolean player1Select;
	
	private float buttonH, buttonW;

	private Image tank;
	private Image ranger;
	private Image assassin;
	private Image mage;
	private Image man;
	private Image player1;
	private Image player2;
	private Input input;
	private Image startButton;
	private int mouseX, mouseY;
	private float playerSelectW, playerSelectH;


	public static boolean isSelecting;

	CharacterSelect(int id) 
	{
		this.id = id;
	}

	//initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		//1366
		//768
		lavaPressed = false;
		gc.setShowFPS(true);
		backgroundColor = 2;
		timeStart = System.currentTimeMillis();
		isSelecting = true;
		readyFor2 = false;
		setImage("res/defaultImage.png");
//		w = 150;
//		h = 200;
		w = (float) (gc.getWidth()/9.1066666666);
		h = (float) (gc.getHeight()/3.84);
//		champW = 70;
//		champH = 125;
		champW = (float) (gc.getWidth()/19.5142857); 
		champH = (float) (gc.getHeight()/6.144);		
				
		p1Select = "";
		p2Select = "";
		gameStartPressed = false;
		player1Select = true;
		
		buttonW = (float) (gc.getWidth()/2.4); //800
		buttonH = (float) (gc.getHeight()/10.0125); //107.865169
		
//		playerSelectW = 200;
//		playerSelectH = 400;
		playerSelectW = (float) (gc.getWidth()/6.83);
		playerSelectH = (float) (gc.getHeight()/1.92);
		
		p1Name = "";
		p2Name = "";

		input = gc.getInput();
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();

		this.gc = gc;
	}


	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{

		g.setBackground(new Color(backgroundColor1, backgroundColor2, backgroundColor3));
		g.setColor(new Color(255,255,255));
		//		g.fillOval(75, 100, 100, 100);

		

		// draw rectangle outlines

		g.setColor(new Color(255,255,255));

		//top row of rectangles
		g.drawRect(Game.gc.getWidth()/3, Game.gc.getHeight()/5, w, h);
		g.drawRect((Game.gc.getWidth()/3) + (Game.gc.getWidth()/3) - w, Game.gc.getHeight()/5, w, h);

		//bottom row of rectangles
		g.drawRect(Game.gc.getWidth()/2 -(w/2), Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5, w, h);
		g.drawRect((Game.gc.getWidth()/3) - w, Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + Game.gc.getHeight()/5, w, h);
		g.drawRect((Game.gc.getWidth()/3) + (Game.gc.getWidth()/3), Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + Game.gc.getHeight()/5, w, h);
		
		//start button for rectangle
	
		
		
		
		
		//drawing who the players have selected
		g.setColor(new Color(255,255,255));
		g.fillOval(Game.gc.getWidth()/30, Game.gc.getHeight()/4, playerSelectW, playerSelectH);
		g.fillOval((Game.gc.getWidth()/30)*25, Game.gc.getHeight()/4, playerSelectW, playerSelectH);
		
		g.drawString("Player 1 is: ", Game.gc.getWidth()/30 , Game.gc.getHeight()/4 -60);
		g.drawString("Player 2 is: ", (Game.gc.getWidth()/30)*25, Game.gc.getHeight()/4-60);
		g.drawString(p1Name, Game.gc.getWidth()/30 , Game.gc.getHeight()/4 -30);
		g.drawString(p2Name, (Game.gc.getWidth()/30)*25, Game.gc.getHeight()/4-30);
		
		if (p1Select == "ranger") {
			setImage("res/rangerIdle1.png");
			ranger.setFilter(Image.FILTER_NEAREST);
			ranger.draw((Game.gc.getWidth()/30)+ (champW/2), Game.gc.getHeight()/4 + (champH/2), playerSelectW - champW, playerSelectH - champH);
			p1Name = "Crick the Outlaw";
		
		}
		
		if (p1Select == "tank") {
			setImage("res/tankIdle.png");
			tank.setFilter(Image.FILTER_NEAREST);
			tank.draw((Game.gc.getWidth()/30)+ (champW/2), Game.gc.getHeight()/4 + (champH/2), playerSelectW - champW, playerSelectH - champH);
			
			p1Name = "Njáll the Sentinel";
		}
		
		if (p2Select == "ranger") {
			setImage("res/rangerIdle1.png");
			ranger.setFilter(Image.FILTER_NEAREST);
			ranger.draw(((Game.gc.getWidth()/30)*25) + (champW/2), Game.gc.getHeight()/4 + (champH/2), playerSelectW - champW, playerSelectH - champH);
			p2Name = "Crick the Outlaw";
		}
		
		if (p2Select == "tank") {
			setImage("res/tankIdle.png");
			tank.setFilter(Image.FILTER_NEAREST);
			tank.draw(((Game.gc.getWidth()/30)*25) + (champW/2), Game.gc.getHeight()/4 + (champH/2), playerSelectW - champW, playerSelectH - champH);
			p2Name = "Njáll the Sentinel";
		}
		
		if (p1Select == "assassin") {
			setImage("res/assassinIdle.png");
			assassin.setFilter(Image.FILTER_NEAREST);
			assassin.draw((Game.gc.getWidth()/30)+ (champW/2), Game.gc.getHeight()/4 + (champH/2), playerSelectW - champW, playerSelectH - champH);
			
			p1Name = "Vir the Eternal Spirit";
		}
		if (p2Select == "assassin") {
			setImage("res/assassinIdle.png");
			assassin.setFilter(Image.FILTER_NEAREST);
			assassin.draw(((Game.gc.getWidth()/30)*25) + (champW/2), Game.gc.getHeight()/4 + (champH/2), playerSelectW - champW, playerSelectH - champH);
			p2Name = "Vir the Eternal Spirit";
		}
		
		
		

		//draw the images for selection
		//		man.draw((Game.gc.getWidth()/3) + (w/6), Game.gc.getHeight()/5, champW, champH);
		//		tank.draw((Game.gc.getWidth()/3) + (w/6), Game.gc.getHeight()/5, champW, champH);
		setImage("res/rangerShooting2.png");
		ranger.setFilter(Image.FILTER_NEAREST);
		ranger.draw((Game.gc.getWidth()/3) + (champW/2), Game.gc.getHeight()/5 + (champH/4), champW, champH);
		setImage("res/tankIdle.png");
		tank.setFilter(Image.FILTER_NEAREST);
		tank.draw((Game.gc.getWidth()/3) + (Game.gc.getWidth()/3) - (champW) - (champW/2),Game.gc.getHeight()/5 + (champH/3), champW, champH);
		setImage("res/assassinIdle.png");
		assassin.setFilter(Image.FILTER_NEAREST);
		assassin.draw(Game.gc.getWidth()/2 -(champW/2), Game.gc.getHeight()/5 + Game.gc.getHeight()/5 +  Game.gc.getHeight()/5 + (champH/4), champW, champH);	
		setImage("res/mageIdle1.png");
		mage.setFilter(Image.FILTER_NEAREST);
		mage.draw(Game.gc.getWidth()/3 - w + (champW/2), Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + (champH/3), champW, champH);
		
		//		assassin.draw((Game.gc.getWidth()/3) + (w/6), Game.gc.getHeight()/5, champW, champH);

		if (p2Select != "" && p1Select != "") {
			setImage("res/startButton.png");
			startButton.setFilter(Image.FILTER_NEAREST);
			startButton.draw(Game.gc.getWidth()/5 + (buttonW/(float) 4.65),Game.gc.getHeight()/2 - (buttonH/4),buttonW,buttonH);
		}
		

	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		time++;
		updateBackground();
		timeSeconds = (System.currentTimeMillis() - timeStart) / 1000;

		if (readyFor2) {
			sbg.enterState(3);
		}

		if (p2Select != "" && p1Select != "" && gameStartPressed) {
			readyFor2 = true;
		}
		if (lavaPressed) {
			sbg.enterState(2);
		}

		mouseX = input.getMouseX();
		mouseY = input.getMouseY();

		//		if ((xpos>75 && xpos < 175) && (ypos >160 && ypos<260)) {
		//			if (input.isMouseButtonDown(0)) {
		//		
		//				
		//		
		//			}
		//		}
	}



	//when you enter the gamestate
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		lavaPressed = false;
		gc.setShowFPS(true);
		backgroundColor = 2;
		timeStart = System.currentTimeMillis();
		isSelecting = true;
		readyFor2 = false;
		setImage("res/defaultImage.png");
//		w = 150;
//		h = 200;
		w = (float) (gc.getWidth()/9.1066666666);
		h = (float) (gc.getHeight()/3.84);
//		champW = 70;
//		champH = 125;
		champW = (float) (gc.getWidth()/19.5142857); 
		champH = (float) (gc.getHeight()/6.144);		
				
		p1Select = "";
		p2Select = "";
		gameStartPressed = false;
		player1Select = true;
		
		buttonW = (float) (gc.getWidth()/2.4); //800
		buttonH = (float) (gc.getHeight()/10.0125); //107.865169
		
//		playerSelectW = 200;
//		playerSelectH = 400;
		playerSelectW = (float) (gc.getWidth()/6.83);
		playerSelectH = (float) (gc.getHeight()/1.92);
		
		p1Name = "";
		p2Name = "";

		input = gc.getInput();
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();

		this.gc = gc;

	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{

	}

	//bind control to start game
	public void keyPressed(int key, char c)
	{
//		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_U) && p1Select != "" && p2Select != "") {
//			gameStartPressed = true;
//		}
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_K)) {
			lavaPressed = true;
		}

	}

	//click controls
	public void mousePressed(int button, int x, int y)
	{
		//left mouse button
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (mouseX >= Game.gc.getWidth()/3 && mouseX <= Game.gc.getWidth()/3 + w) {
				if (mouseY >= Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + h) {
					p1Select = "ranger";
				}
			}
			if (mouseX >= (Game.gc.getWidth()/3) + (Game.gc.getWidth()/3) - w && mouseX <= (Game.gc.getWidth()/3) + (Game.gc.getWidth()/3)) {
				if (mouseY >= Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + h) {
					p1Select = "tank";
				}
			}
			if (mouseX >= Game.gc.getWidth()/2 -(w/2) && mouseX <= Game.gc.getWidth()/2 -(w/2) + w) {
				if (mouseY >= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5+h) {
					p1Select = "assassin";
				}
			}
			if (mouseX >= Game.gc.getWidth()/3 - w && mouseX <= Game.gc.getWidth()/3) {
				if(mouseY >= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5+h) {
					p1Select = "mage";
				}
			}
			if (mouseX >= (2*Game.gc.getWidth()) && mouseX <= (2*Game.gc.getWidth() + w)) {
				if(mouseY >= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5+h) {
					p1Select = "man";
				}
			}
		}

		//right mouse button
		if (button == Input.MOUSE_RIGHT_BUTTON) {
			if (mouseX >= Game.gc.getWidth()/3 && mouseX <= Game.gc.getWidth()/3 + w) {
				if (mouseY >= Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + h) {
					p2Select = "ranger";
				}
			}

			if (mouseX >= (Game.gc.getWidth()/3) + (Game.gc.getWidth()/3) - w && mouseX <= (Game.gc.getWidth()/3) + (Game.gc.getWidth()/3)) {
				if (mouseY >= Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + h) {
					p2Select = "tank";
				}
			}
			if (mouseX >= Game.gc.getWidth()/2 -(w/2) && mouseX <= Game.gc.getWidth()/2 -(w/2) + w) {
				if (mouseY >= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5+h) {
					p2Select = "assassin";
				}
			}
			if (mouseX >= Game.gc.getWidth()/3 - w && mouseX <= Game.gc.getWidth()/3) {
				if(mouseY >= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5+h) {
					p2Select = "mage";
				}
			}
			if (mouseX >= (2*Game.gc.getWidth()) && mouseX <= (2*Game.gc.getWidth() + w)) {
				if(mouseY >= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5 && mouseY <= Game.gc.getHeight()/5 + Game.gc.getHeight()/5 + + Game.gc.getHeight()/5+h) {
					p2Select = "man";
				}
			}
		}
		
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (mouseX >= Game.gc.getWidth()/5 + (buttonW/(float) 4.65) && mouseX <= Game.gc.getWidth()/5 + (buttonW/(float) 4.65)+buttonW) {
				if (mouseY >= Game.gc.getHeight()/2 - (buttonH/4) && mouseY <= Game.gc.getHeight()/2 - (buttonH/4)+buttonH) {
					if (p1Select != "" && p2Select != "") {
						gameStartPressed = true;
					}
				}
			}
		}
	}

	//updates colors, changes dynamically
	public void updateBackground() {
		//		if (backgroundColor == 1) {
		//			//pink and light blue
		//			backgroundColor1 = (int) Math.abs((time % 510) - 255);
		//			backgroundColor2 = 120;
		//			backgroundColor3 = 120;
		//		} else if (backgroundColor == 2) {
		//			//green and purple
		//			backgroundColor1 = 120;
		//			backgroundColor2 = (int) Math.abs((time % 510) - 255);
		//			backgroundColor3 = 120;
		//		} else if (backgroundColor == 3) {
		//			//blue and yellow
		//			backgroundColor1 = 120;
		//			backgroundColor2 = 120;
		//			backgroundColor3 = (int) Math.abs((time % 510) - 255);
		//		}
	}

	// Returns the ID code for this game state
	public int getID() 
	{
		return 0;
	}

	//sets selection images
	public void setImage(String filepath)
	{
		try
		{
			man = new Image(filepath);
			tank = new Image(filepath);
			ranger = new Image(filepath);
			mage = new Image(filepath);
			assassin = new Image(filepath);
			player1 = new Image(filepath);
			player2 = new Image(filepath);
			startButton = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}




