package relativity.tools;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import relativity.Application;

public class Button 
{
	private Image look;
	private float x;
	private float y;
	private float width;
	private float height;
	public Button(Image img, float x, float y, float scale)
	{
		look = img.getScaledCopy(scale);
		this.x=x;
		this.y=y;
		this.width = look.getWidth();
		this.height = look.getHeight();
	}
	
	public Button(float x, float y, float width, float height)
	{
		this.x=x;
		this.y=y;
		this.width = width;
		this.height = height;
	}
	public Rectangle getBounds()
	{
		return (new Rectangle(x,y,width,height));
	}
	public boolean mouseOver()
	{
		if(this.getBounds().contains(Mouse.getX(), Application.HEIGHT-Mouse.getY()))
			return true;
		else
			return false;
	}
	public boolean hit()
	{
		if(mouseOver()&&Mouse.isButtonDown(0))
			return true;
		else 
			return false;
	}
	public void draw()
	{
		look.draw(x,y);
	}
	public void draw(Graphics g)
	{
		g.drawRect(x,y,width, height);
	}
}
