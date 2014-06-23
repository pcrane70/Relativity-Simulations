package relativity;

import java.text.DecimalFormat;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import relativity.tools.Button;
import relativity.tools.Simulation;

public class AdditiveVelocities extends Simulation
{
	final static float C = 0.1f;
	final static float HT = Application.HEIGHT;
	final static float WT = Application.WIDTH;
	
	private int state;
	private Image shipL, shipR;
	//coordinates and velocities of the respective parts
	private float xL, xR, y1;
	private float vL = 0.5f*C;
	private float vR = 0.5f*C; 
	private float vA, y2, xA, xB;//velocities and y when the other ship is at "rest"
	private float xsB1, ysB1;
	private float xsB3, ysB3;
	private float xsB4, ysB4;
	
	public Button spB1, spB2, spB3, spB4;
	
	public AdditiveVelocities(int state)
	{
		super(state);
		this.state = state;
	}
	//initializes the variables of the simulation when the simulation is called
	public void startingCds()
	{
		xL = WT/2-shipL.getWidth();
		xR = WT/2;
		y1 = HT/4-shipR.getHeight()/2;
		y2 = y1+HT/2;
		xA = xL;
		xB = xR;
		xsB1 = 280;
		ysB1 = 45;
		xsB3 = 110;
		ysB3 = HT/2+50;
		xsB4 = WT/2+110;
		ysB4 = HT/2+35;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg)	throws SlickException 
	{
		super.init(gc, sbg);
		shipR = (new Image("data/spaceship.png")).getFlippedCopy(true, false).getScaledCopy(.1f);
		shipL = (new Image("data/spaceship.png")).getScaledCopy(.1f);		
		startingCds();
		spB1 = new Button(xsB1, ysB1, 130, 20);
		spB2 = new Button(xsB1, ysB1+15, 130, 20);
		spB3 = new Button(xsB3, ysB3, 137, 20);
		spB4 = new Button(xsB4, ysB4, 137, 20);
	}
	//draws all the objects on the screen 
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		super.render(gc, sbg, g);
		//initiate foreground and framing
		g.setColor(Color.green);
		g.drawString("Additive Velocities under Special Relativity", 160 ,10);
		g.setColor(Color.cyan);
		//Instructions
		//g.drawString("Modify the speed of the top Space Ship by using \'-\' and \'+\'",
			//	10,	HT/2-30);
		g.setColor(Color.white);
		g.drawLine(0, HT/2, WT, HT/2);
		g.drawLine(WT/2, HT/2, WT/2, HT);
		g.drawString("View from a Stationary External Observer", 175, 25);
		g.drawString("View from A at rest", 90, HT/2+10);
		g.drawString("View from B at rest", WT/2+90, HT/2+10);
		g.setColor(Color.cyan);
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(3);
		df.setMaximumFractionDigits(3);
		g.drawString("A speed: "+df.format(vL/C)+"C", xsB1, ysB1);
		g.drawString("B speed: "+df.format(vR/C)+"C", xsB1, ysB1+15);
		
		g.drawString("A speed: "+df.format(0/C)+"C", xsB3,ysB3-15);
		g.drawString("B speed: "+df.format(vA/C)+"C", xsB3,ysB3);
		
		g.drawString("A speed: "+df.format(vA/C)+"C", xsB4,ysB4);
		g.drawString("B speed: "+df.format(0/C)+"C", xsB4, ysB4+15);
		//initiate cart and ball
		shipR.draw(xL, y1);
		shipL.draw(xR, y1);
		shipR.draw(xA, y2);
		shipL.draw(xB, y2);
		resetB.draw();
		backB.draw();
		infoB.draw();
	}

	//updates movement and handles all key input
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		super.update(gc, sbg, delta);
		Input input = gc.getInput();
		//
		vA = (float) ((vL+vR)/(1+vL*vR/Math.pow(C, 2)));
		xL -=delta*vL;
		xR +=delta*vR;
		xA -=delta*vA;
		xB +=delta*vA;
		
		//keyboard input handling
		float increment = 0.01f;
		if(input.isKeyDown(Input.KEY_MINUS))
		{
			if(vL >= increment*C)
			{
				if(vL >C-increment*C&& vL < C)
					vL -=increment/10*C;
				else
					vL -=increment*C;
			}
			else if(vL < increment*C)
				vL = 0;
		}
		if(input.isKeyDown(Input.KEY_9))
		{
			if(vR >= increment*C)
			{
				if(vR >C-increment*C&& vL < C)
					vR -=increment/10*C;
				else
					vR -=increment*C;
			}
			else if(vR < increment*C)
				vR = 0;
		}
		if(input.isKeyDown(Input.KEY_0))
		{
			if(vR <=C-increment*C)
				vR +=increment*C;
			if(vR + increment/10*C>C-increment*C&& vR + increment/10*C < C)
				vR +=increment/10*C;
			else if(vR +increment/10*C > C)
				vR = C-increment/10*C;
		}
		if(input.isKeyDown(Input.KEY_EQUALS))
		{
			if(vL <=C-increment*C)
				vL +=increment*C;
			if(vL + increment/10*C>C-increment*C&& vL + increment/10*C < C)
				vL +=increment/10*C;
			else if(vL +increment/10*C > C)
				vL = C-increment/10*C;
		}
		if(input.isKeyDown(Input.KEY_ENTER))
		{
			init(gc, sbg);
		}
		if(spB1.hit())
		{
			float vel = Simulation.enterSpeed();
			if(!(vel < 0))
			{
				vL = vel*C;
			}
		}
		if(spB2.hit())
		{
			float vel = Simulation.enterSpeed();
			if(!(vel < 0))
			{
				vR = vel*C;
			}
		}
		if(spB3.hit())
		{
			float vel = Simulation.enterSpeed();
			if(!(vel < 0))
			{
				float vA = vel*C;
				vR = (float)((vA-vL)/(1-vA*vL/Math.pow(C, 2)));
			}
		}
		if(spB4.hit())
		{
			float vel = Simulation.enterSpeed();
			if(!(vel < 0))
			{
				float vA = vel*C;
				vL = (float)((vA-vR)/(1-vA*vR/Math.pow(C, 2)));
			}
		}
			
	}
	public int getID() 
	{
		return state;
	}
}
