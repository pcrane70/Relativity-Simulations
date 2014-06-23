package relativity;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class BlackHole extends BasicGameState
{
	public int state;
	final static float C = 0.15f;
	final static float G = 6.67f*(float)Math.pow(10,-11);
	private float mass = 0;
	
	public Image blackHole;
	
	public BlackHole(int state)
	{
		this.state = state;
	}
	//initializes the variables of the simulation when the simulation is called
	public void init(GameContainer gc, StateBasedGame sbg)	throws SlickException 
	{
		blackHole = new Image("data/blackhole.png");
	}

	//draws all the objects on the screen 
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.setColor(Color.green);
		g.drawString("Schwarzchild Radius in Black Holes Simulation",10 ,10);
		blackHole.drawCentered(Application.WIDTH/2, Application.HEIGHT/2);
	}

	//updates movement and handles all key input
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		mass++;
		System.out.println(mass);
	}
	public int getID() 
	{
		return state;
	}
}
