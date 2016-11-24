package com.ace.neon.window;

import java.awt.Graphics;
import java.util.LinkedList;

import com.ace.neon.framework.GameObject;
import com.ace.neon.framework.ObjectId;
import com.ace.neon.objects.Block;


public class Handler {

	
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	private GameObject tempObject;
	
	public void tick()
	{
		for(int i = 0; i < object.size(); i++)
		{
			tempObject = object.get(i);
			
			tempObject.tick(object);
		}
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	
	public void addObject(GameObject object)
	{
		this.object.add(object);
	}
	public void removeObject(GameObject object)
	{
		this.object.remove(object);
	}
	
	
	public void createLevel()
	{
		// Level Z
		for(int xx = 0; xx < Game.WIDTH + 32; xx += 32)
		{
			addObject(new Block(xx, Game.HEIGHT-23, 0, ObjectId.Block) );
		}
		
		for(int i = 0; i < (32 * 10) ; i += 32)
		{
			addObject(new Block(250+i, Game.HEIGHT-250, 0, ObjectId.Block));
		}
		
		for(int i = 0; i < (32 * 18) ; i += 32)
		{
			addObject(new Block(0, i, 0, ObjectId.Block));
		}
		for(int i = 0; i < (32 * 18) ; i += 32)
		{
			addObject(new Block(Game.WIDTH-23, i, 0, ObjectId.Block));
		}
	}
}
