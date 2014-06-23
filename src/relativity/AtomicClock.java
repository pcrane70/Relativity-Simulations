package relativity;


import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import relativity.tools.Collisions;
import relativity.tools.Simulation;
import relativity.tools.Button;

public class AtomicClock extends Simulation
{
	final static float C = 0.1f;//set speed of light
	final static float HT = Application.HEIGHT;
	final static float WT = Application.WIDTH;
	public int state;

	//x-moving paddle specs
	private float xPaddle1;
	private float yPaddle1;
	private int distance = 80;
	private float speed = 0.1f*C;
	//stationary paddle specs
	private float xPaddle2;
	private float yPaddle2;
	//coordinates of the x-movement ball (ball 1 on the top part of the screen)
	private float xB1, yB1;
	//coordinates of the stationary paddle set's ball (ball 2 in the bottom half)
	private float xB2,yB2; 
	//time passed for each ball (number of paddle hits)
	private int tBall1, tBall2;
	//used for ball trail trailX & trailY: moving paddles; statX & statY: stationary paddles.
	private ArrayList<Float> statX, statY, trailX, trailY;
	//color of ball for standardization of alpha change
	private Color bColor;
	//private float title 
	private float xTitle = 90;
	private float yTitle = 25;
	//button coords for the speed;
	private float xSpB = xTitle+390;
	private float ySpB = 25;
	//directions of balls 1 for down, -1 for up
	private int bDir1 = 1;
	private int bDir2 = 1;
	//image declarations: paddle, ball, and back button
	private Image pdleImg, ballImg;
	
	//button coding: minus, plus, and restart;
	private Button minus, plus, timeB, speedB;
	
	//Constructor to initiate the state for reference in Application.java
	public AtomicClock(int state)
	{
		super(state);
	}
	//initializes the variables of the Simulation when the simulation is called
	public void init(GameContainer gc, StateBasedGame sbg)	throws SlickException 
	{
		super.init(gc, sbg);
		//initiating the misc. variables
		pdleImg = new Image("data/AtomicClock/paddle.png");
		ballImg = new Image("data/AtomicClock/ball.png");
		trailX = new ArrayList<Float>();
		trailY = new ArrayList<Float>();
		statX = new ArrayList<Float>();
		statY = new ArrayList<Float>();
		//initating starting coordinates of the paddles
		xPaddle1 = 34f;
		yPaddle1 = 68f;
		xPaddle2 = 120;
		yPaddle2 = HT/2 + 68f;
		//initiates starting coordinates of the balls (B1 and B2) 
		xB1 = xPaddle1 + (float)pdleImg.getWidth()/2-ballImg.getWidth()/2;
		yB1 = yPaddle1+(float)pdleImg.getHeight();
		xB2 = xPaddle2 + (float)pdleImg.getWidth()/2-ballImg.getWidth()/2;
		yB2 = yPaddle2+(float)pdleImg.getHeight();
		tBall1 = 0;
		tBall2 = 0;
		//saving color to allow trail alpha variation
		bColor = ballImg.getColor(ballImg.getWidth(), ballImg.getHeight());
	
		float y = 30;
		float x = xTitle + 510;
		float sep = 18;
		minus = new Button(new Image("data/AtomicClock/minus.png"),x,y,.2f);
		plus = new Button(new Image("data/AtomicClock/plus.png"),x+sep,y,.2f);
		timeB = new Button(WT/2, HT/2+68f, 200, 100);
		speedB = new Button(xSpB,ySpB, 100, 20);
		infoMessage("Modify the speed of the top \"Photon Emitters\" by using \'-\' and \'+\' "
				+ "\nPress Backspace to return to the Menu at any time"+
				"\nReset the time by clicking on the counter box or pressing \"Enter\""
				+"\nEnter the speed value by clicking on the speed");
	}

	//method containing the drawing functions of the game elements
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		super.render(gc,sbg,g);
		//title render
		g.setColor(Color.green);
		g.drawString("Time Dilation Simulation", 270, 10);
		
		//instruction render
		/*
		g.setColor(Color.cyan);
		g.drawString("Modify the speed of the top \"Photon Emitters\" by using \'-\' and \'+\'",
				10,	HT/2-30);
		g.drawString("Press Backspace to go back to the menu at any time. \'Enter\' resets time."
				+ "", 10,HT/2+30);*/
		//titles render
		g.setColor(Color.white);
		g.drawString("View from a Stationary External Observer", xTitle, yTitle);
		g.drawString("View from an Inertial Observer (at the same speed as the paddles)", 
				70, HT/2+10);
		g.drawLine(0, HT/2, WT, HT/2);//Line that splits the application in two
		
		//Time Dilation rectangle render
		int rectW = 200;
		int rectH = 100;
		g.drawString("Time in each Frame of Reference", WT/2-45f, HT/2+50f);
		g.drawString("Stationary",WT/2+5f, HT/2+71f);
		g.drawString(tBall2+"s",WT/2+5f, HT/2+101f);
		g.drawString(tBall1+"s", WT/2+5f+rectW/2, HT/2+101f);
		g.drawString("Inertial", WT/2+5f+rectW/2, HT/2+71f);
		g.drawRect(WT/2, HT/2+68f, rectW, rectH);
		g.drawLine(WT/2+rectW/2, HT/2+68F, 
				WT/2+rectW/2, HT/2+68F+rectH);
		
		//display Speed as a proportion of C render
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		g.setColor(Color.cyan);
		g.drawString("Speed: "+df.format(speed/C).toString()+"C", xSpB, ySpB);
		//render paddles and balls
		//l117 saves the color so path rendering can occur without losing the original color 
		ballImg.setColor(bColor.getRed(), bColor.getGreen(), bColor.getBlue(),1);
		//first ball and paddle set render
		pdleImg.draw((int)xPaddle1,(int)yPaddle1);
		pdleImg.draw((int)xPaddle1,(int)yPaddle1+distance);	
		ballImg.draw((int)xB1, (int)yB1);
		//second ball and paddle set render
		pdleImg.draw((int)xPaddle2, (int)yPaddle2);
		pdleImg.draw((int)xPaddle2, (int)yPaddle2+distance);
		ballImg.draw((int)xB2, (int)yB2);
		
		//trail generations based on the past trailX.size() locations.  Renders a trail by 
		//drawing proportionally smaller balls at the past locations.
		//the size of each array is assumed the same as the initiation of the array 
		//creates the same size
		for(int ball = 0; ball < trailX.size(); ball++)
		{
			float scale = (trailX.size()-ball)/(float)trailX.size();
			float ballDems = ballImg.getWidth()*scale;
			int index = trailX.size()-1- ball;
			//changes the color to more opaque by cons
			ballImg.setColor(bColor.getRed(), bColor.getGreen(), bColor.getBlue(),scale);
			ballImg.draw(trailX.get(index), trailY.get(index),ballDems,ballDems);
			ballImg.draw(statX.get(index), statY.get(index),ballDems,ballDems);
		}
		
		//button draw;
		minus.draw();
		plus.draw();
		backB.draw();
		infoB.draw();
		resetB.draw();
	}

	//actions to perform in between rendering. Determines all of the movement and keyboard inputs
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		super.update(gc, sbg, delta);
		Collisions c = new Collisions();
		//Bounds for the rectangle
		Rectangle pdlRect = new Rectangle(xPaddle1, yPaddle1, pdleImg.getWidth(), pdleImg.getHeight()*2+distance);
		
		//the "y" speed of the laterally moving ball. Because the x-speed must be equal to
		//the speed of the moving objects, the y- speed must be related in such a way
		//the tangent of the speeds is the speed of light, hence C. Thus y^2=C^2-x^2. 
		//(Pythogorean theorem)
		float ySpeed = (float) Math.sqrt(Math.pow(C, 2)-Math.pow(speed, 2));
		//the speed and direction (up or down) combined with the screen update rate
		float yChange = bDir1*delta*ySpeed;
		//collision detection of the paddles, with the conditions of the bottom line of the top
		//paddle, and the top line of the bottom paddle
		if((yPaddle1+pdleImg.getHeight()>yB1+yChange)||(yPaddle1+distance<yB1+
				ballImg.getHeight()+yChange))
		{
			bDir1*=-1;//changes the direction;
			tBall1++;
			yChange*=-1;
		}
		if((yPaddle2+pdleImg.getHeight()>yB2+bDir2*delta*C)||(yPaddle2+distance<yB2+
				ballImg.getHeight()+bDir2*delta*C))
		{
			bDir2*=-1;//changes the direction;
			tBall2++;
		}
		if((yPaddle1+pdleImg.getHeight()>yB1)||(yPaddle1+distance<yB1+
				ballImg.getHeight()))
		{
			init(gc, sbg);
		}
		//following lines add previous points to the "trail" arrays to display pathing
		trailX.add(xB1);
		trailY.add(yB1);
		statX.add(xB2);
		statY.add(yB2);
		//"25" is the number of objects in the trail, and going over causes the loop to remove the 
		//oldest instance at index 0
		if(trailX.size()>25)
		{
			trailX.remove(0);
			trailY.remove(0);
			statX.remove(0);
			statY.remove(0);
		}
		//distance modifiers. The paddles and ball increase at the same rate on x, while y changes 
		//via the Pythagorean theorem and the speed of light (constant 'C') and the x-speed
		xPaddle1+=delta*speed;
		xB1+=delta*speed;
		yB1+=yChange;
		yB2+=bDir2*delta*C;
		//causes the paddles to loop back to the left side once it leaves the right side.
		//paddles guaranteed never to leave view
		if(c.outOfBounds(pdlRect))
		{
			xPaddle1=-pdleImg.getWidth()+1;
			xB1=-pdleImg.getWidth()/2-ballImg.getWidth()/2+1;
		}
		//speed modulation of the paddles. "-" key decreases speed; "+/=" key increases speed
		//increment is the amount which the speed changes with every key registration
		float increment = 0.01f;
		if(input.isKeyDown(Input.KEY_MINUS)||minus.hit())
		{
			if(speed >= increment*C)
				speed -=increment*C;
			else if(speed < increment*C)
				speed = 0;
		}
		if((input.isKeyDown(Input.KEY_EQUALS)||plus.hit())&& speed < C-increment*C)
		{
			speed +=increment*C;
		}
		//time reset
		if(input.isKeyPressed(Input.KEY_ENTER)||timeB.hit())
		{
			tBall1 = 0;
			tBall2 = 0;
		}
		//lol
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
					speed = input*C;
				}
			}
		}
	}
}