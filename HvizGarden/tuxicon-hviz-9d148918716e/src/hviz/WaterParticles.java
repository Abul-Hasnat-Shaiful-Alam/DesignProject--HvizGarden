package hviz;

import processing.core.PApplet;
import processing.core.PVector;
import static java.lang.Math.pow;

class WaterParticles {
	  PVector o;
	  PVector t;
	  
	  public PVector pos;
	  public PVector prevPos;
	  
	  int time_start;
	  int time_duration;
	  int time_end;
	 
	  boolean canSing;
	  boolean singing;
	  PApplet parent;
	  boolean dead;
	  
	 
	  // Sound stuff

	  
	  WaterParticles() {
	    dead = true;
	  }
	 
	  WaterParticles(int endTime, int duration, PVector origin, PVector target, PApplet p) {
	    time_end = endTime;
	    time_duration = duration;
	    time_start = endTime-duration;
	    parent = p;
	    o = origin;
	    t = target;
	    pos =  new PVector(o.x, o.y);
	    prevPos = new PVector(pos.x, pos.y); 
	    
	    
	    dead = false;
	    
	   
	   
	  }
	  
	  void run(int ms) {   // ms: current time in milliseconds
	    if (ms>time_start && ms<time_end) {  // if we are supposed to be running
	      // Sound stuff
	      // Position stuff
	      float state = (float)((ms-time_start) / (float)time_duration);  // how far we're dead// state: how much we are dead (0-1)
	      //float distance = dist(o.x,o.y, t.x, t.y);

	      prevPos = new PVector(pos.x, pos.y);  
	      pos = new PVector(
	        o.x + (t.x-o.x)*state, 
	       (float) (o.y + (t.y-o.y)*pow(state,2)) 
	        //o.y + (t.y-o.y)*pow(state,1+mouseY/float(width))   // something that looks fancy
	      );

	      
	      //println(pos +""+ prevPos);  // Debug stuff
	      
	      return;
	    }
	    else {
	      dead = true;
	      return;
	    }
	  }
	  

	  
	}