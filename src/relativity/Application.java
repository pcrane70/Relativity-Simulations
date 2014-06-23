package relativity;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;

public class Application extends StateBasedGame
{
	public static final int MAINMENU = 0;
	public static final int ATOMICCLOCK = 1;
	public static final int LENGTHCONTR = 2;
	public static final int ADDITIVEVEL = 3;
	public static final int WIDTH = 720;
	public static final int HEIGHT = 405;
	public static final int FPS = 60;
	public static final String LOC = "/home/philkuz/Programming/Java/Internal Assessment/RelativitySimulations/data/";
	public Application(String appName)
	{
		super(appName);
	}

	public void initStatesList(GameContainer arg0) throws SlickException 
	{
		//this.addState(new LengthContraction(LENGTHCONTR));
		this.addState(new Menu(MAINMENU));
		this.addState(new AtomicClock(ATOMICCLOCK));
		this.addState(new LengthContraction(LENGTHCONTR));
		this.addState(new AdditiveVelocities(ADDITIVEVEL));
	}
	
	public static void main(String[] args)
	{
		try{
			AppGameContainer app = new AppGameContainer(new Application("Relativity Simulations (C) Phillip Kuznetsov 2014 v.1.1.0"));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(false);
			app.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
}
