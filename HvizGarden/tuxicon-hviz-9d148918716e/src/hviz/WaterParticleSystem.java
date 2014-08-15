package hviz;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class WaterParticleSystem {

	static final int maxSignals = 2;
	PApplet parent;



	  
	  ArrayList par;  // par: particles
	  //ArrayList signalArray;  // holds all sine waves, one for each track
	  int currentTrack;
	  
	  WaterParticleSystem(PApplet p) {
	    par = new ArrayList();
	    //signalArray = new ArrayList();
	    currentTrack = 0;
	    parent = p;
	    }      
	  
	  
	  void run() {
	    int time = parent.millis();
	    float Hue = 0;  
	  //  System.out.println("FPS: " + frameRate + " Particles: " + par.size());  // Debug
	    
	    // (efficiency) Cycle through the ArrayList backwards because we are deleting at the back
	    for (int i = par.size()-1; i>=0; --i) {
	      WaterParticles SP = (WaterParticles) par.get(i);
	      SP.run(time);
	      
	      // draw the object
	    //  Hue = map(2*height/3-SP.pos.y, 0, height, 50, 255);
	      parent.stroke(205, 0, 205);
	      parent.line(SP.prevPos.x, SP.prevPos.y,  SP.pos.x, SP.pos.y);
	      
	      if (SP.dead) par.remove(i);
	    }
	  }
	  
	  
	  void addParticle(PVector origin, PVector target) {
	   
	    
	    par.add(new WaterParticles(
	      parent.millis()+2000, 2000, 
	      origin, target,parent
	      
	    ));
	  }
	  
	  private boolean withSound(int i) {
	    if (i%maxSignals == 0) return true;
	    else                   return false;
	  }
	
	
	
	
}
