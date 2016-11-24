package com.ace.neon.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.ace.neon.framework.GameObject;
import com.ace.neon.framework.ObjectId;
import com.ace.neon.window.Handler;

public class Player extends GameObject{

	private float width = 64, height = 128;
	
	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;
	
	private Handler handler;
	
	public Player(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
	}


	public void tick(LinkedList<GameObject> object) {
		x += velX;
		y += velY;
		
		
		if(falling || jumping)
		{
			velY += gravity;
			
			if(velY > MAX_SPEED)
			{
				velY = MAX_SPEED;
			}
			
		}
		Collision(object);
	}
	private void Collision(LinkedList<GameObject> object)
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Block){
				
				if(getBoundsTop().intersects(tempObject.getBounds())){
					y = tempObject.getY() + 32;
					velY=0;
					
				}
				if(getBounds().intersects(tempObject.getBounds())){
					y = tempObject.getY() - height;
					velY=0;
					falling = false;
					jumping = false;
				}
				else
				{
					falling = true;
				}
				
				if(getBoundsRight().intersects(tempObject.getBounds())){
					x = tempObject.getX() - width;
					velX=0;
				}
				
				if(getBoundsLeft().intersects(tempObject.getBounds())){
					x = tempObject.getX() + 32;
					velX=0;
				}
			}
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
		
		
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.red);
		g2d.draw(getBounds());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsTop());
	
	}


	public Rectangle getBounds() {
		
		return new Rectangle((int)x+7, (int)(y+height)-20, (int)width-14, 20);
	}
	public Rectangle getBoundsTop() {
		
		return new Rectangle((int)x+2, (int)y+2, (int)width-5, (int)10);
	}
	public Rectangle getBoundsRight() {
			 
		return new Rectangle((int) ((int)x+width-5), (int)y+15, (int)5, (int)height-17);
	}
	public Rectangle getBoundsLeft() {
		
		return new Rectangle((int)x, (int)y+15, (int)5, (int)height-17);
	}


}
