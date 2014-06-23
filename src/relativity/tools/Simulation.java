package relativity.tools;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import relativity.Application;

public abstract class Simulation extends BasicGameState
{
	private int state;
	public Input input;
	public Button backB, infoB, resetB;
	private String information;
	
	final public static float HT = Application.HEIGHT;
	final public static float WT = Application.WIDTH;
	
	public Simulation(int state)
	{
		this.state = state;
		backB = new Button(0,0,10,10);
	}
	//initializes the variables of the simulation when the simulation is called
	public void init(GameContainer gc, StateBasedGame sbg)	throws SlickException 
	{
		infoB = new Button(new Image("data/Universal/info.png"),10, HT-35f, .5f);
		backB = new Button((new Image("data/Universal/back.png")).getFlippedCopy(true, false),10,10f,.5f);
		resetB = new Button(new Image("data/Universal/restart.png"), WT-35f,10f, .5f);
	}
	//draws all the objects on the screen 
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		
	}
	//updates movement and handles all key input
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_BACK)||backB.hit())
		{
			sbg.enterState(0);
		}
		if(input.isKeyPressed(Input.KEY_DELETE)||resetB.hit())
		{
			this.init(gc,sbg);
		}
		if(information != null && infoB.hit())
		{
			JOptionPane.showMessageDialog(new JFrame(), information);
		}
	}
	//returns the current id
	public int getID() 
	{
		return state;
	}
	//
	public void infoMessage(String information)
	{
		this.information = information;
	}
	public static float enterSpeed()
	{
		float input = 0;
		String troll = JOptionPane.showInputDialog(new JFrame(), "Set a fraction of the speed of light you wish to travel");
		if(troll == null)
		{
			return -1;
		}
		try
		{
			input = Float.parseFloat(troll);
		}catch(NullPointerException e){}
		if(input < 1f && input >= 0)
			return input;
		else
			return -1;
	}
}
