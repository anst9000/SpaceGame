/**
 * @project:    Space Game
 * @file:        Game.java
 * @author:     Anders Strömberg
 * @date:        Aug 24, 2018
 * @time:        10:56:52 PM
 */
package com.game.src.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public final String TITLE = "2D Space Game";

    private boolean running = false;
    private Thread thread;
    
    private BufferedImage image = 
	    new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
//-----------------------------------------------------------------------------    

private Player p;    
    
//-----------------------------------------------------------------------------    
public void init() {
    BufferedImageLoader loader = new BufferedImageLoader();
    try {
	spriteSheet = loader.loadImage("res/sprite_sheet.png");
    } catch(IOException e) {
	e.printStackTrace();
    }

    p = new Player(200, 200, this);
}
//-----------------------------------------------------------------------------    
    

//-----------------------------------------------------------------------------    
    private synchronized void start() {
	if (running) {
	    return;
	}
	running = true;
	thread = new Thread(this);
	thread.start();
    }
//-----------------------------------------------------------------------------    
    
    
//-----------------------------------------------------------------------------    
    private synchronized void stop() {
	if (!running) {
	    return;
	}
	running = false;
	try {
	    thread.join();
	} catch (InterruptedException ex)
	{
	    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
	}
	System.exit(1);
    }
//-----------------------------------------------------------------------------    
    
    
//-----------------------------------------------------------------------------    
    @Override
    public void run() {
	init();
	long lastTime = System.nanoTime();
	final double amountOfTicks = 60.0;
	double ns = 1000000000 / amountOfTicks;
	double delta = 0.0;
	int updates = 0;
	int frames = 0;
	long timer = System.currentTimeMillis();
	
	while (running) {
	    long now = System.nanoTime();
	    delta += (now - lastTime) / ns;
	    lastTime = now;
	    if (delta >= 1) {
		tick();
		updates++;
		delta--;
	    }
	    render();
	    frames++;
	    
	    if (System.currentTimeMillis() - timer > 1000) {
		timer += 1000;
		System.out.println(updates + " Ticks, Fps " + frames);
		updates = 0;
		frames = 0;
	    }
	}
	stop();
    }
//-----------------------------------------------------------------------------    

    
//-----------------------------------------------------------------------------    
    private void tick() {
	p.tick();
    }
//-----------------------------------------------------------------------------    
    
    
//-----------------------------------------------------------------------------    
    private void render() {
	BufferStrategy bs = this.getBufferStrategy();
	
	if (bs == null) {
	    createBufferStrategy(3);
	    return;
	}
	
	Graphics g = bs.getDrawGraphics();
	/////////////////////////////////
	
	g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	
	p.render(g);
	
	/////////////////////////////////
	g.dispose();
	bs.show();
	
	
    }
//-----------------------------------------------------------------------------    
    
    
//-----------------------------------------------------------------------------    
    public static void main(String args[]) {
        Game game = new Game();
        
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        
        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	
	game.start();
    }
    
    
    public BufferedImage getSpriteSheet() {
	return spriteSheet;
    }

}
