package relativity;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import relativity.tools.Button;

public class Menu extends BasicGameState
{
	final static float HT = Application.HEIGHT;
	final static float WT = Application.WIDTH;
	
	private int state;
	private ArrayList<Image> butImg;//button images
	private ArrayList<String> butNme;//nameImg
	private ArrayList<Button> but;//button array
	private float acButY;
	private Button infoB;
	//Simple Constructor
	public Menu(int state)
	{
		this.state = state;
	}

	//initiates
	public void init(GameContainer gc, StateBasedGame sbg)	throws SlickException 
	{
		butImg = new ArrayList<Image>();
		butNme = new ArrayList<String>();
		but = new ArrayList<Button>();
		
		butImg.add((new Image("data/Menu/AtomicClock.png")).getScaledCopy(.25f));
		butNme.add("Time Dilation");
		butImg.add((new Image("data/Menu/LengthContraction.png")).getScaledCopy(.25f));
		butNme.add("Length Contraction");
		butImg.add((new Image("data/Menu/AdditiveVelocities.png")).getScaledCopy(.25f));
		butNme.add("Additive Velocities");
		acButY = 100;
		infoB = new Button(new Image("data/Universal/info.png"),10f, HT-35f, .5f);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.setColor(Color.green);
		g.drawString("Relativitiy Simulations 2014",230, 20);
		g.setColor(Color.white);
		g.drawString("by Phillip Kuznetsov", 270,35);
		
		g.setColor(Color.cyan);
		float width = butImg.get(0).getWidth();
		float margin = (WT-width*butImg.size())/(butImg.size()+1);
		for(int x = 0; x<butImg.size(); x++)
		{
			float xC, yC;//coordinates of placement
			Image curImg = butImg.get(x);
			xC = margin*(x+1)+width*x;
			yC = acButY-15;
			
			but.add(new Button(curImg, xC, yC,1));
			g.drawString(butNme.get(x),xC,yC);
			curImg.draw(xC,yC+15);
		}
		g.setColor(Color.cyan);
		infoB.draw();
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		
		if(but.get(0).hit())
		{
			sbg.enterState(1);
		}
		else if(but.get(1).hit())
		{
			sbg.enterState(2);
		}
		else if(but.get(2).hit())
		{
			sbg.enterState(3);
		}
		
		if(infoB.hit())
		{
			JOptionPane.showMessageDialog(new JFrame(), 
					"All simulations can be reset by \'Delete\' or by"
					+ "\npressing the reset icon The main menu can be" 
					+ "\nreached by \'Backspace\' or by pressing the back icon");
		}
	}

	public int getID() 
	{
		return state;
	}

}
