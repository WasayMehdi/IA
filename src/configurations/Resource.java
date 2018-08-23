package configurations;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Resource {
	
	 public static final Image loadImage(String fileName){
		    try {
		        return ImageIO.read(Resource.class.getResourceAsStream("res/"+fileName));
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	return null;
		    }
	}
	 
	
	public static final Image loadImage(final int width, final int height, final String fileName) {
		return loadImage(fileName).getScaledInstance(width, height, Image.SCALE_DEFAULT);
	}
	
	public static final ImageIcon loadImageIcon(final int width, final int height, final String fileName) {
		return new ImageIcon(loadImage(width, height, fileName));
	}
	
	public static final ImageIcon loadImageIcon(final String fileName) {
		return new ImageIcon(loadImage(fileName));
	}
	 
	

}
