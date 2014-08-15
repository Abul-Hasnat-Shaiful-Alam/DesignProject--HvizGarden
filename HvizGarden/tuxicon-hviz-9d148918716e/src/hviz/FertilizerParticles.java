package hviz;

import processing.core.PApplet;
import processing.core.PVector;
import vialab.SMT.Zone;

public class FertilizerParticles {


		  PVector location;
		  PVector velocity;
		  PVector acceleration;
		  float lifespan;
		  PApplet parent;
		  Zone[] zone;
		  boolean growth;
		  int treeNum;
		  int maxTree;
		  Lsystem[] tree;
		  int[] growth_calls = {1,1,1,2,1,2,1,5,1,2,1,5,1,2,1}; 
		  
		  FertilizerParticles(PVector l,PApplet p) {
			parent = p;
		    acceleration = new PVector(0,(float)0.2);
		    velocity = new PVector(p.random(-1,1),p.random(-2,0));
		    location = l.get();
		    lifespan = (float)250.0;
		  }

		  void run() {
		    update();
		    display();
		  }

		  // Method to update location
		  void update() {
		    velocity.add(acceleration);
		    location.add(velocity);
		    lifespan -= 1.0;
		    for(int i=1; i<=maxTree;i++)
		    {
		    	
		    	// System.out.println("test at rott" + zone[1].getX()+"root width:"+zone[1].getWidth()+"root Y:"+zone[1].getY()+"root Height:"+zone[1].getHeight()+"partlocaX:"+location.x+"partlocaY:"+location.y);
		    	if (zone[i].contains(location.x, location.y))
		    		
		    	{
		    		System.out.println("Im at root intersection"+i);
		    		//treeNum = i;
		    		tree[i].drawing=true;
		    		
		    	}
		    	
		    }
		  }
		  
		  void recieveZone(Zone[] zone,  Lsystem[] tree, int maxTree)
		  {   
			 //
			  this.zone = zone;
			  this.maxTree = maxTree;
			  this.tree = tree;
		  }
		  

		  // Method to display
		  void display() {
		    parent.stroke(91,55,21,lifespan);
		    parent.fill(91,55,21,lifespan);
		    parent.ellipse(location.x,location.y,3,3);
		  }
		  
		  // Is the particle still useful?
		  boolean isDead() {
		    if (lifespan < 0.0) {
		      return true;
		    } else {
		      return false;
		    }
		  }
		
	
	
}
