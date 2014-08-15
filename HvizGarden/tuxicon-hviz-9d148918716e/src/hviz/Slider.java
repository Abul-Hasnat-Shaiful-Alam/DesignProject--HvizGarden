package hviz;

import processing.core.PApplet;
import processing.core.PImage;
import vialab.SMT.Touch;

public class Slider {


		
		PApplet parent;
		float x, y, redX, bigX, bigY;
		boolean mouseOver = false;
		
		
		public Slider(PApplet p, float x, float y) {
			parent = p;
			this.x = x;
			this.y = y;
			redX = 0;
			bigX = x + 256;
			bigY = y + 48;

		}
		
		public void display(){
			parent.noStroke();
			parent.fill(255);
			parent.rect(x, y, 255, 48);
			parent.fill(204, 102, 0);
			parent.textSize(20);
			parent.text("Slide to Increase",x+30, y+30);
			if (redX >= 255)
				parent.fill(0, 200, 0);
			else
				parent.fill(204, 102, 0);
			if (redX != 0) 
				parent.rect(x, y, redX, 48);
			   
		}
		
		public void update(Touch t){
			if (t.getLastPoint().x  > x-1 && t.getLastPoint().x  < bigX && t.getLastPoint().y > y && t.getLastPoint().y < bigY)
				mouseOver = true;
			else 
				mouseOver = false;
			if (mouseOver)
				{
				
				redX = t.getLastPoint().x - x;
				}
		}
		
		public float getValue(){
			return redX;
		}

	
	
	
}
