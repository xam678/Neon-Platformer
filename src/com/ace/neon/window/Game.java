package com.ace.neon.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.ace.neon.framework.GameObject;
import com.ace.neon.framework.KeyInput;
import com.ace.neon.framework.ObjectId;
import com.ace.neon.objects.Player;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = -263086405887877294L;
	
	private static final float VERSION = 0.01f;
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	
	private boolean running = false;
	private Thread thread;
	
	Handler handler;
	Camera cam;
	
	public void init()
	{
		handler = new Handler();
		cam = new Camera(0, 0);
		
		handler.addObject(new Player(100, 100, handler, ObjectId.Player));
		
		
		handler.createLevel();
		
		this.addKeyListener(new KeyInput(handler));
		
	}
	public synchronized void start()
	{
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() 
	{
		this.requestFocus();
		
		init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("fps: "+frames+" tick: "+updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	public void tick()
	{
		handler.tick();
		for(int i = 0; i < handler.object.size();i++){
			if(handler.object.get(i).getId() == ObjectId.Player){
				GameObject tempObject = handler.object.get(i);
				cam.tick(tempObject);
			}
		}
	}
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		Graphics2D g2d = (Graphics2D) g;
//////////////////////////////////////////////////
//////////////////Draw all graphics here//////////
		g2d.translate(cam.getX(), cam.getY()); //Start camera
		
		
		g.setColor(Color.white);
		g.fillRect(-300, 0, getWidth()+600, getHeight());
		
//////////////////////////////////////////////////
		handler.render(g);
		
		g2d.translate(-cam.getX(), -cam.getY()); // End Camera
		g.dispose();
		bs.show();
	}
	public static void main(String[] args)
	{
		new Window(WIDTH, HEIGHT, "Neon Platform Game v "+VERSION, new Game());
	}

}
