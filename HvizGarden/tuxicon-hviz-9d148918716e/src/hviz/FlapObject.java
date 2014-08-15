package hviz;

import processing.core.PApplet;

public class FlapObject {

	  int x;
	  int y;
	  int w;
	  int h;
      int count;
      String type;
      
	  FlapObject(int xx, int yy, int ww, int hh, int count, String type)
	  {
	    x = xx;
	    y = yy;
	    w = ww;
	    h = hh;
	    this.count = count;
	    this.type = type;
	  }
	  
	  void display(PApplet p)
	  {
	   // p.strokeWeight(10);
	    p.stroke(178,34,34,150);
	    p.rect(x+50,y+50,w,h);
	    p.fill(204, 102, 0);
	    p.textSize(20);
	    p.text("Routine:", x+20, y+20);
	    p.text(type+"X"+count, x+65, y+68);
	    p.noStroke();
	    p.noFill();
	  }
	
	
}
