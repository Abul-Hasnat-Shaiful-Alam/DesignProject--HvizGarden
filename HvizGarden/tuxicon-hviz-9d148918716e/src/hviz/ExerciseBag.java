package hviz;

import processing.core.PApplet;

public class ExerciseBag {



		  int col;
		  int overCol;
		  int pressCol;
		  boolean over = false;
		  boolean pressed = false;
          PApplet parent;
		  //rcRect variables
		  int rad, sX, sY, pX, pY;

		  //rect1 variables
		  int r1sX, r1sY, r1pX, r1pY;

		  //rect2 variables
		  int r2sX, r2sY, r2pX, r2pY;

		  //rect3 variables
		  int r3sX, r3sY, r3pX, r3pY;


		  //arc1 variables
		  int arc1pX, arc1pY, arc1sX, arc1sY;
		  float arc1st, arc1sp;

		  //arc2 variables
		  int arc2pX, arc2pY, arc2sX, arc2sY;
		  float arc2st, arc2sp;

		  //arc3 variables
		  int arc3pX, arc3pY, arc3sX, arc3sY;
		  float arc3st, arc3sp;

		  //arc4 variables
		  int arc4pX, arc4pY, arc4sX, arc4sY;
		  float arc4st, arc4sp;

		  // Constructor
		  ExerciseBag  (int radius, int sizeX, int sizeY, int posX, int posY, int c, int o, int p, PApplet pp){
              
			parent = pp;  
		    //rcRect fields
		    //rcRect fields
		    rad = radius;
		    sX = sizeX;
		    sY = sizeY;
		    pX = posX;
		    pY = posY;
		    col = c;
		    overCol = o;
		    pressCol = p;

		    // rectangle 1 fields
		    r1pX = posX;
		    r1pY = posY + radius;  
		    r1sX = sizeX;
		    r1sY = sizeY-2*radius;

		    // rectangle 2 fields
		    r2pX = posX + radius;
		    r2pY = posY;
		    r2sX = sizeX-2*radius;
		    r2sY = radius;

		    // rectangle 3 fields
		    r3pX = posX + radius;
		    r3pY = posY + sizeY - radius;
		    r3sX = sizeX-2*radius;
		    r3sY = radius;

		    // arc1 fields
		    arc1pX = posX + radius;
		    arc1pY = posY + radius;
		    arc1sX = radius*2;
		    arc1sY = radius*2;
		    arc1st = pp.PI;
		    arc1sp = pp.TWO_PI-pp.PI/2;   

		    // arc2 fields
		    arc2pX = posX + sizeX - radius;
		    arc2pY = posY + radius;
		    arc2sX = radius*2;
		    arc2sY = radius*2;
		    arc2st = pp.TWO_PI-pp.PI/2;
		    arc2sp = pp.TWO_PI;   

		    // arc3 fields
		    arc3pX = posX + radius;
		    arc3pY = posY + sizeY - radius;
		    arc3sX = radius*2;
		    arc3sY = radius*2;
		    arc3st = pp.PI/2;
		    arc3sp = pp.PI;   

		    // arc4 fields
		    arc4pX = posX + sizeX - radius;
		    arc4pY = posY + sizeY - radius;
		    arc4sX = radius*2;
		    arc4sY = radius*2;
		    arc4st = 0;
		    arc4sp = pp.PI/2; 
		  }

		

		  boolean press() {
		    if (over == true){
		      pressed = true;
		      return true;
		    }
		    else{
		      return false;
		    }
		  }

		  void release(){
		    pressed = false;
		  }



		  void rcRect(int rad, int sX, int sY, int posX, int pY){

		    // Central section
			parent.rect(r1pX, r1pY, r1sX, r1sY);
		    // Upper rect
		    parent.rect(r2pX, r2pY, r2sX, r2sY);
		    // Bottom rect
		    parent.rect(r3pX, r3pY, r3sX, r3sY);
		    // Left top corner
		    parent.arc(arc1pX, arc1pY, arc1sX, arc1sY, arc1st, arc1sp);
		    // Right top corner
		    parent.arc(arc2pX, arc2pY, arc2sX, arc2sY, arc2st, arc2sp);
		    // Left bottom corner
		    parent.arc(arc3pX, arc3pY, arc3sX, arc3sY, arc3st, arc3sp);
		    // Right bottom corner
		    parent.arc(arc4pX, arc4pY, arc4sX, arc4sX, arc4st, arc4sp); 
		  }

		  void display(boolean drop) {
		    if (pressed == true){
		    	//parent.fill(pressCol);
		    }
		    else if (drop == true){
		    	//parent.fill(overCol);
		    	 parent.fill(204, 102, 0,50);
		    }
		    else{
		    	parent.fill(163,163,163);
		    } 
		    parent.noStroke();   
		    rcRect(rad, sX, sY, pX, pY);
		  }









		




	
	
	
	
}
