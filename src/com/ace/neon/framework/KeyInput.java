package com.ace.neon.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.ace.neon.window.Handler;

public class KeyInput extends KeyAdapter{
		
	
	Handler handler;
	private boolean[] keyDown = new boolean[4];
	
	public KeyInput(Handler handler){
		this.handler = handler;
		
		keyDown[0] = false;//d
		keyDown[1] = false;//a
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				
				//start me moving when key pressed
				//if(key == KeyEvent.VK_S){ tempObject.setVelY(5); keyDown[1] = true; }
				if(key == KeyEvent.VK_D){ tempObject.setVelX(5); keyDown[0] = true; }
				if(key == KeyEvent.VK_A){ tempObject.setVelX(-5); keyDown[1] = true; }
				
				if(key == KeyEvent.VK_SPACE && !tempObject.isJumping()){ 
					tempObject.setVelY(-10); 
					tempObject.setJumping(true);
					} 
				
				if(key == KeyEvent.VK_5) //Debug reset player loc
				{
					tempObject.setX(100);
					tempObject.setY(100);
				}
			}
		}
		if(key == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				//if(key == KeyEvent.VK_SPACE){ keyDown[0] = false;}
				//if(key == KeyEvent.VK_S) keyDown[1] = false;
				if(key == KeyEvent.VK_D) keyDown[0] = false;
				if(key == KeyEvent.VK_A) keyDown[1] = false;
				
				//Fix the sticky issue when pressing keys quickly
				if(!keyDown[0] && !keyDown[1]) tempObject.setVelX(0);
			}
		}
	}
}
