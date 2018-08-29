package com.game.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {

    // The coordinates for the player
    private double x;
    private double y;
    
    private BufferedImage player;
    
    // The constructor
    public Player(double x, double y, Game game) {
	this.x = x;
	this.y = y;
	
	SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
	
	player = ss.grabImage(1, 1, 32, 32);
    }
    
    
    public void tick() {
//	x++;
//	y++;
    }
    
    
    public void render(Graphics g) {
	g.drawImage(player, (int)x, (int)y, null);
    }
    
}
