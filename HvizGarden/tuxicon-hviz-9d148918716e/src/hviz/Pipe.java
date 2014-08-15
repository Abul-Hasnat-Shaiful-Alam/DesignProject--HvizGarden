package hviz;

import processing.core.PApplet;

public class Pipe {

	  int x;
	  int y;
	  int w;
	  int h;
	  
	  Pipe(int xx, int yy, int ww, int hh)
	  {
	    x = xx;
	    y = yy;
	    w = ww;
	    h = hh;
	  }
	  
	  void display(PApplet p)
	  {
	    p.fill(178,34,34, 150);
	    p.rect(x,y,w,h);
	  }
	
}
