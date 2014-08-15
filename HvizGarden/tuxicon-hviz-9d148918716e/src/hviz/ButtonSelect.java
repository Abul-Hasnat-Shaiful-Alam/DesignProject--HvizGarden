package hviz;

import processing.core.PApplet;

public class ButtonSelect {


		  int x;
		  int y;
		  int w;
		  int h;
		  boolean exerciseLoaded;
		  ButtonSelect(int xx, int yy, int ww, int hh)
		  {
		    x = xx;
		    y = yy;
		    w = ww;
		    h = hh;
		  }
		  
		  void display(PApplet p)
		  {
		   // p.strokeWeight(10);
			  
		    p.stroke(178,34,34,150);
		    p.rect(x,y,w,h);
		    p.noStroke();
		    
		    if (exerciseLoaded)
		    {
		    	p.fill(128,128,128,50);
		        p.rect(x,y,w,h);
		        p.noFill();
		    }
		  }
		
	}

	
	

