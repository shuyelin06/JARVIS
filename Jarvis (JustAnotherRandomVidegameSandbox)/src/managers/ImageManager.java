package managers;

import java.util.HashMap;

import org.newdawn.slick.Image;

public class ImageManager{
	final private static HashMap<String, Image> Images = new HashMap<String, Image>();
	
	public static int getSize() { return Images.size(); }
	public static HashMap<String, Image> getImageHash(){ return Images; }
	
	public static Image getPlaceholder() { return Images.get("placeholder"); }
	public static Image getImage(String name) {
		Image i = Images.get(name);
		
		if(i == null) return Images.get("placeholder"); 
		else return i;
	}
}