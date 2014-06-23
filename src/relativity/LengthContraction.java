package relativity;


import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;

import relativity.tools.Button;
import relativity.tools.Simulation;
import relativity.tools.Collisions;

public class LengthContraction extends Simulation
{
	final static float C = 0.5f;
	
	Collisions col = new Collisions();
	
	//coordinates and velocity of ships
	//Moving Ship
	private float xShip1, yShip1, vShip1;
	//Ship 2
	private float xShip2, yShip2;
	//Ship 3
	private float xShip3, yShip3;
	//the image of the ship
	private Image shipImg;
	//contraction amount
	private float contract;
	//Buttons
	public Button minB, plsB, speedB;
	//Speed
	private float xTitle, yTitle, xSpeed;
	
	public LengthContraction(int state)
	{
		super(state);
	}
	//performs length contraction factor based on the gamma factor of a speed
	public float contraction(float width, float speed)
	{
		float gamma = (float) (1/Math.sqrt(1-Math.pow(speed/C,2)));
		return width/gamma;
	}
	//initializes the variables of the simulation when the simulation is called
	public void init(GameContainer gc, StateBasedGame sbg)	throws SlickException 
	{
		super.init(gc, sbg);
		shipImg = (new Image("data/spaceship.png")).getScaledCopy(.5f);
		xShip1 = WT/2-shipImg.getWidth()/2;
		yShip1 = HT/4-shipImg.getHeight()/2;
		xShip2 = WT/4-shipImg.getWidth()/2;
		yShip2 = 3*HT/4 - shipImg.getHeight()/2;
		xShip3 = 3*WT/4-shipImg.getWidth()/2;
		yShip3 = yShip2;
		vShip1 = .5f*C;
		infoMessage("Modify the speed of the top Space Ship by using \'-\' and \'+\'"
				+ "\non the keyboard and screen. Input your own fraction of C by"
				+"\nclicking on the speed display.");
		xTitle = 90;
		yTitle = 25;
		xSpeed = 370;
		speedB = new Button(xTitle+xSpeed, yTitle, 120,20);
		minB = new Button(new Image("data/AtomicClock/minus.png"),xTitle+500,yTitle+5,.2f);
		plsB = new Button(new Image("data/AtomicClock/plus.png"),xTitle+518,yTitle+5,.2f);
		
	}

	//draws all the objects on the screen 
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		super.render(gc, sbg, g);
		g.setColor(Color.green);
		g.drawString("Length Contraction in Special Relativity", 175 ,8);
		g.setColor(Color.white);
		g.drawLine(0, HT/2, WT, HT/2);
		g.drawLine(WT/2, HT/2, WT/2, HT);
		contract = contraction(shipImg.getWidth(), vShip1);
		g.drawString("View from a Stationary External Observer", xTitle, yTitle);//10,40
		g.drawString("View from an Inertial Observer", 10, HT/2+10);
		g.drawString("Contraction comparison", WT/2+10, HT/2+10);
		
		//Speed display as a proportion of C
		g.setColor(Color.cyan);
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(3);
		df.setMaximumFractionDigits(3);
		g.drawString("Speed: "+df.format(vShip1/C).toString()+"C", xTitle + xSpeed, yTitle);
	
		g.setColor(Color.white);
		df.setMaximumFractionDigits(0);
		g.drawString("Contraction : "+df.format((1-contract/shipImg.getWidth())*100)+"%", WT/2+10,HT/2+40);
		
		shipImg.draw(xShip1, yShip1, contract,shipImg.getHeight());
		shipImg.draw(xShip2, yShip2);
		shipImg.draw(xShip3+(shipImg.getWidth()/2-contract/2), yShip3, contract,shipImg.getHeight());
		backB.draw();
		infoB.draw();
		minB.draw();
		plsB.draw();
	}

	//updates movement and handles all key input
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		super.update(gc, sbg, delta);
		Rectangle shipRect1 = col.getBounds(shipImg, xShip1, yShip1);
		
		//ship movement
		xShip1 += delta*vShip1;
		if(col.outOfBounds(shipRect1))
		{
			xShip1 = -shipImg.getWidth()+1;
		}
		//keyboard input handling
		float increment = 0.01f;
		if(input.isKeyDown(Input.KEY_MINUS)||minB.hit())
		{
			if(vShip1 >= increment*C)
			{
				if(vShip1 > C-increment*C&& vShip1 < C)
					vShip1 -=increment/10*C;
				else
					vShip1 -=increment*C;
			}
			else if(vShip1 < increment*C)
				vShip1 = 0;
		}
		if(input.isKeyDown(Input.KEY_EQUALS)||plsB.hit())
		{
			if(vShip1 <=C-increment*C)
				vShip1 +=increment*C;
			if(vShip1 + increment/10*C>C-increment*C&& vShip1 + increment/10*C < C)
				vShip1 +=increment/10*C;
			else if(vShip1 +increment/10*C > C)
				vShip1 = C-increment/10*C;
		}
		if(speedB.hit())
		{
			float input = 0;
			String troll = JOptionPane.showInputDialog(new JFrame(), "Set a fraction of the speed of light you wish to travel");
			if(!(troll == null))
			{
				try
				{
					input = Float.parseFloat(troll);
				}catch(NullPointerException e){}
				if(input < 1f && input >= 0)
				{
					vShip1 = input*C;
				}
			}
		}
	}	
}