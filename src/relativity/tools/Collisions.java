package relativity.tools;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import relativity.Application;

public class Collisions 
{
	final static float HT = Application.HEIGHT;
	final static float WT = Application.WIDTH;
	Rectangle bounds;
	public Collisions()
	{
		bounds = new Rectangle(0, 0, WT, HT);
	}
	public boolean outOfBounds(float x, float y)
	{
		if(bounds.contains(x,y))
			return false;
		else
			return true;
	}
	public boolean outOfBounds(Rectangle r)
	{
		if(bounds.contains(r.getX(), r.getY()))
			return false;
		else if(bounds.contains(r.getX(), r.getY()+r.getHeight()))
			return false;
		else if(bounds.contains(r.getX()+r.getWidth(), r.getY()))
			return false;
		else if(bounds.contains(r.getX()+r.getWidth(), r.getY()+r.getHeight()))
			return false;
		else 
			return true;
	}
	public Rectangle getBounds(Image img, float x, float y)
	{
		return (new Rectangle(x,y,img.getWidth(), img.getHeight()));
	}
	
	public boolean intersect(Rectangle r1, Rectangle r2)
	{
		if(r1.intersects(r2))
			return true;
		else
			return false;
	}
}
