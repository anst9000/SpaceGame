package com.game.src.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BufferedImageLoader {
    
    private BufferedImage image;
    
    public BufferedImage loadImage(String path) throws IOException {
	image = ImageIO.read(new File(path));
//	image = ImageIO.read(getClass().getResource(path));
	return image;
    }
    
    
    
    
}
