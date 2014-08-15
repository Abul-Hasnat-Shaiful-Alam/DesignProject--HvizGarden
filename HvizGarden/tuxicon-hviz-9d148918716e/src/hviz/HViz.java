package hviz;



import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.jogamp.opengl.util.packrect.Rect;

import processing.core.*;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import vialab.SMT.*;


import controlP5.*;



public class HViz extends PApplet {
	
	/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/43149*@* */
	/* !do not delete the line above, required for linking your tweak if you re-upload */
	// L-system 
	// goals for L system

	/*
	  Flow with song
	  

	*/
	
	FertilizerParticleSystem ps;
	Lsystem[] tree;
	float count;
	float wind;
	int max_trees, cur_tree;
	int breed_track;
	ControlP5 cp5;
	PFont font;
	PFont font2;
	PImage bubble;
	boolean bDisplayMessage;
	int startTime;
	final int DISPLAY_DURATION = 12000; // 1s
	String tweet2 = "test";
	int rightwing_pointer=0;
	int leftwing_pointer=0;
	int top_pointer=0;
	int top_leftPointer=0;
	int top_rightPointer=0;
	PImage img;
	Zone z;
	Zone pipe;
	PImage ppm;
	final static int IMAGE_FILES=7;
	//final static int IMAGE_COPIES=4;
	PImage[] days = new PImage[IMAGE_FILES];
	ImageZone[] days_calendar = new ImageZone[IMAGE_FILES];
	boolean rotated=false;
	Touch pipeTouch;
	Pipe p;
	float increaseX;
	WaterParticleSystem wp;
	PVector origin;
	PVector target;
	boolean pipeEnabled=false; 
	boolean changeExPlanScreen=false;
	boolean selectUser=true;
	boolean treeScreen=false;
	ImageZone fertilizer;
	ImageZone gardenPipe;
	PImage body_structure;
	ImageZone body;
	PImage chest_structure;
	PImage shoulder_structure;
	ImageZone chest;
	ImageZone chestPlan;
	ImageZone shoulder;
	ImageZone seedZone;
	int diameter = 40;
	int xpos = 400;
	int ypos = 75;
	int xsize = 200;
	int ysize = 150;
	ExerciseBag rr3;
	ExerciseBag scr;
	int col3 = 153;
	int overCol3 = 102;
	int pressedCol3 = 153;
	Zone exerciseBag; 
	boolean addedInBagChest=false;
	boolean addedInBagShoulder=false;
	PFont myFont;
	String[] fontList = PFont.list();
	int numfonts = fontList.length;
	String fontName = "";
	Range range;
	Slider sliderOne;
	Slider sliderTwo;
	ButtonZone exPlanButton;
	ButtonZone exScheduleButton;
	ButtonZone trackProgress;
	ButtonZone selectUserType;
	ButtonZone expert;
	ButtonZone novice;
	boolean planningScreen=false;
	Zone flap1;
	Zone flap2;
	int[] growth_calls = {1,1,1,2,1,2,1,5,1,2,1,5,1,2,1}; 
    boolean flapIntersect;
  
	int flap1X = 650;
	int flap1Y = 130;
	int widthFlap1 = 470;
	int heightFlap1 = 70;
	int flap2X = 650;
	int flap2Y = 380;
	int widthFlap2 = 470;
	int heightFlap2 = 70;
	float positionX=1150;
	float positionY=140;
	float shadeX;
	float shadeY;
	ButtonSelect[] days_button = new ButtonSelect[7];
	FlapObject[] chestRectangleFlap1 =  new FlapObject[7];
	FlapObject[] chestRectangleFlap2 =  new FlapObject[7];
	boolean dayEnabled=false;
	int day;
    Zone chestQty; 	
    int chestBagCount=16;
    int shoulderBagCount=5;
    boolean defaultText=true;
    PImage Arrow_up;
    PImage Arrow_down;
    boolean slideTouch;
    boolean helpText;
    boolean notInTarget;
    boolean flap1Intersect;
    boolean flap2Intersect;
    boolean chestFlap2ObjectExist;
	boolean flap2Moved;
	boolean  exerciseBagIntersect;
	PImage Slider_tool;
	PImage seed;
	PImage speech;
	boolean operateOnChest=false;
	int particleCount=0;
	boolean operateOnShoulder=false;
	ImageZone speechBub;
	
	Zone[] rootZone = new Zone[64];
	public void setup() {
	  size(1650,800,P3D);
	  smooth();
	  //positionX = width/2;
	  //positionY = (height/2)+140;
	  TouchClient.init(this, TouchSource.MULTIPLE);
	  frameRate(30);
	  max_trees = 64;
	  cur_tree = 0;
	  tree = new Lsystem[max_trees];
	  breed_track = 1;
	  tree[0] = new Lsystem(width/5,height,height/2.1,2,0,this,0);
	  count = 0;
	  wind = (float) 0.3;
	
	  hint(ENABLE_STROKE_PURE);
	  hint(ENABLE_OPTIMIZED_STROKE);
      Slider_tool = loadImage("slider.png");           
	  for (int i=0; i<IMAGE_FILES; i++) {  
		    days[i] = loadImage(i+7 + ".png");
		  }          
	  for (int i=0; i<IMAGE_FILES; i++) {  
		  
		  System.out.println("i:" + i);
	  days_calendar[i] = new ImageZone("day" + i,days[i],600+(i*80),280);
	  days_button[i] = new ButtonSelect(590+(i*80), 270, 90,85);
	  
	  }
	  wp = new WaterParticleSystem(this);
	  ps = new FertilizerParticleSystem(new PVector(width/2,50),this);
	  font = loadFont("MyriadPro-Regular-56.vlw");
	 // font2 = loadFont("Sentinel2.vlw");
	 // bubble = loadImage("bubble.png");
	  //TouchClient.init(this, TouchSource.MOUSE);
	  
	//  TouchClient.add(new Zone("MyZone",400,50,300,100));
	  
	  
	  img = loadImage("Fertilizer-Bag.png");
	  
	  Arrow_up = loadImage("arrow-up_final.png");
	  Arrow_down = loadImage("arrow-down_final.png");
	  ppm = loadImage("garden.png");
	  seed = loadImage("Seed.png");
	 // speech = loadImage("speech-bubble.png");
	 // speechBub =  new ImageZone("SpeechBubble",speech,1100,50);
	  fertilizer = new ImageZone("ImageZone",img,550, 160);
	  fertilizer.setSize(80, 84);
	  TouchClient.add(fertilizer);
	  TouchClient.add(speechBub);
	  gardenPipe = new ImageZone("Garden",ppm, 780,160);
	  TouchClient.add(gardenPipe);
	  seedZone = new ImageZone("Seed",seed,1010, 160);
	  seedZone.setSize(100, 100);
	  TouchClient.add(seedZone);
	  
	  gardenPipe.setSize(114, 81);
	  exPlanButton = new ButtonZone("ExPlan",500,50,200,50,"Plan a Routine");
	  selectUserType = new ButtonZone("ExUser",270,50,200,50,"Select Type of User");
	  exScheduleButton = new ButtonZone("ExSchedule",730,50,200,50,"Schedule a Routine");
	  trackProgress = new ButtonZone("TrackProg",960,50,200,50,"Track Progress");
	  TouchClient.add(exPlanButton);
	  TouchClient.add(exScheduleButton);
	  TouchClient.add(trackProgress);
	  TouchClient.add(selectUserType);
	  body_structure = loadImage("BodyFront_final.png");
	  body = new ImageZone("BodyZone",body_structure,1000,100);
	  TouchClient.add(body);
	  flap1 = new Zone("flap1",flap1X,flap1Y,widthFlap1,heightFlap1);
	  flap2 = new Zone("flap2",flap2X,flap2Y,widthFlap2,heightFlap2);
	  chestQty = new Zone("chestQty",550,600,223,150);
	  TouchClient.add(flap1);
	  TouchClient.add(flap2);
	  chest_structure = loadImage("Chest-final.png");
	  shoulder_structure = loadImage("Shoulders-final.png");
	 
	  chest =  new ImageZone("ChestZone",chest_structure,1070,220);
	
	 
	 // chestPlan =  new ImageZone("ChestPlan",chest_structure,);
	  
	  TouchClient.add(chest);
	  
	  
	  shoulder = new ImageZone("ShoulderZone",shoulder_structure,1035,220);
	  
	  
	  TouchClient.add(shoulder);
	  rr3 = new ExerciseBag(40,550,375,200,200,col3,overCol3,pressedCol3, this); 
	 
	  p = new Pipe(780,-500,30,800); 
	  
	  for (int i=0; i<7; i++)
	  {
	  chestRectangleFlap2[i] = new FlapObject(flap2X,flap2Y,200,30,0,"Chest");
	  }
	  fontName = fontList[2];
      myFont = createFont(fontName, 32);
 	 for (int i=0; i<IMAGE_FILES; i++) {  
		 TouchClient.add(days_calendar[i]);
		 }
  
      exerciseBag = new Zone("ExBag",200,200,550,375);
      exerciseBag.add(new Zone("Slide",60,120,255,48));
      exerciseBag.add(new Zone("Slide2",60,195,255,48));
      TouchClient.add(exerciseBag);
      
      expert = new ButtonZone("expert",700,300,200,50,"Lets Build Some Muscles");
      novice = new ButtonZone("Novice",700,400,200,50,"Just Get Me Healthy");
      TouchClient.add(expert);
      TouchClient.add(novice);
      
    	   
}
	

 
 public void pressExPlan(){
	  System.out.println("Button Pressed");
	  changeExPlanScreen=true;
	  treeScreen=false;
      planningScreen=false; 
 } 
 

 public void pressExSchedule(){
	  System.out.println("Button Pressed");
	  planningScreen=true;
	  changeExPlanScreen=false;
	  treeScreen=false;
	  chest.setLocation(570,640);
 for (int i=0; i<IMAGE_FILES; i++) {  
		  
		  //System.out.println("i:" + i);
	  days_calendar[i].setLocation(600+(i*80),280);
	  chestBagCount = (int)sliderOne.getValue();
 }
 }
 public void pressTrackProg(){
	  System.out.println("Button Pressed");
	  treeScreen=true;
	  planningScreen=false;
	  changeExPlanScreen=false;
	  fertilizer.setSize(80, 84);
	  gardenPipe.setSize(114, 81);
	  gardenPipe.setLocation(780,160);
	  fertilizer.setLocation(550,160);
	  chest.setLocation(-700,-700);
	  shoulder.setLocation(-700,-700);
	  body.setLocation(1250,100);
	  body.setSize(150, 300);
	  seedZone.setSize(100, 100);
	  seedZone.setLocation(1010,160);
 }
 
 public void pressExpert(){
	  System.out.println("Button Pressed");
	  changeExPlanScreen=true;
	  selectUser=false;
	  body.setLocation(1000,100);
	  
	  exerciseBag.setLocation(200,200);
	  body.putChildOnTop(chest);
	  //shoulder.setLocation(570,640);
	  
 }
 
 public void pressNovice(){
	  System.out.println("Button Pressed");
	  changeExPlanScreen=true;
	  selectUser=false;
}

 public void touchDownFlap1(Zone z, Touch t){ 
	 defaultText = false;
 }
 

 public void touchDownFlap2(Zone z, Touch t){ 
	 defaultText = false;
 }
 
 
 public void touchMovedFlap1(Zone z, Touch t){
	 
	 if (dayEnabled)
	 {
	 flap1Y -= (t.getLastPoint().y - t.getY());
	 heightFlap1 += (t.getLastPoint().y - t.getY());
	 z.setLocation(flap1X, flap1Y);
	 z.setSize(widthFlap1, heightFlap1);
	 helpText=false;
	 defaultText = false;
	 }
	 
	 else 
	 { 
		 helpText = true;
		 defaultText = false;
	 }
	 
 }
 
 public void touchUpFlap1(Zone z, Touch t){
 
	 defaultText = true;
	 
 }
 

 
 public void touchMovedFlap2(Zone z, Touch t){
	 
	// flap2Y -= (t.getLastPoint().y - t.getY());

	 if (dayEnabled)
	 {
	 heightFlap2 += ( t.getY()- t.getLastPoint().y);
	 z.setSize(widthFlap2, heightFlap2);
	 flap2Moved = true;
	 helpText=false;
	 defaultText = false;
	 }
	 
	 else 
	 { 
		 helpText = true;
		 defaultText = false;
	 }
	 
 }
 
 public void touchUpFlap2(Zone z, Touch t){
	 flap2Moved = false;
	 defaultText = true;
 }
 
public void touchMovedSlide(Zone z, Touch t){
	// System.out.println("test slide");
	 sliderOne.update(t);
	 slideTouch=true;
	 operateOnChest=true;
}

public void touchMovedSlide2(Zone z, Touch t){
	// System.out.println("test slide");
	 sliderTwo.update(t);
	 slideTouch=true;
	 operateOnShoulder=true;
}

public void touchUpSlide(Zone z, Touch t){
	// System.out.println("test slide");
	 //sliderOne.update(t);
	 slideTouch=false;
	 operateOnChest=false;
}
public void touchUpSlide2(Zone z, Touch t){
	// System.out.println("test slide");
	 //sliderOne.update(t);
	 slideTouch=false;
	 operateOnShoulder=false;
}
 
//		}





 public	void touchDownExBag(Zone z){
	 System.out.println("test: Exbag"); 
 }
 
 public	void drawExBag(){
	 
 }
 public	void touchDownGarden(Zone test, Touch t){
		  
	System.out.println("test"); 
   
pipeTouch = t; 
  //  System.out.println("X:"+t.x);
	
		}

public	void touchMovedGarden(Zone pipe, Touch t){
	  
	  System.out.println("X:"+t.getX()+"y:"+t.getY()+"pipe x:"+p.x+"pipe y:"+p.y+"FirstTouch:"+( pipeTouch.x));
	  p.x += (t.getX()- t.getLastPoint().x);
      p.x = constrain(p.x, 0 + p.w/2, width - p.w/2);  //don't go outside screen
      p.y += (t.getY()-t.getLastPoint().y);
      
      origin = new PVector(p.x+(p.w/2),p.y+p.h, 0);
      target = new PVector(t.x,height, 0);
      pipeEnabled = true; 
    
//nozzle.y = constrain(nozzle.y, 0 + nozzle.h/2, height - nozzle.h/2);  //don't go outside screen

  }
//


public void touchChestPlan(Zone z, Touch t) {
	
	z.rst();
	if(!(chestQty.contains(t.getLastPoint().x, t.getLastPoint().y)))
	{
		
	}
	
	
}

public void touchShoulderZone(Zone z) {
	
	//System.out.println("test");




}


public void touchChestZone(Zone z) {
	
	//System.out.println("test");





}

int mousePos;

public void touchMovedChestZone(Zone z, Touch t)
{
	//System.out.println("test");
	//z.setLocation(t.getLastPoint().x, t.getLastPoint().y);
	
	if (planningScreen)
	{
		z.rst();
		if(!(chestQty.contains(t.getLastPoint().x, t.getLastPoint().y)))
		{
		           notInTarget= true;
		           
			
		}
		
		if((flap1.contains(z.getX()+50, z.getY()+50)) && dayEnabled )
		{
			  
			//flap1.fill(0, 0, 0);
			
		   // flap1.textSize(20);
		   // flap1.text("May 2013", 800, 225);
			flapIntersect = true;
			notInTarget= false;
			textSize(20);
			text("May 2013", flap1.getX(), flap1.getY());
			flap1Intersect = true;
			
			//z.setLocation(570,640);
		}
		
		if((flap2.contains(z.getX()+50, z.getY()+50)) && dayEnabled )
		{
			//z.setLocation(570,640);
			  
			//		flap1.fill(0, 0, 0);
			//		flap1.textSize(20);
			 //    	flap1.text("May 2013", 800, 225);
                    flapIntersect = true;
					//System.out.println("test");
					notInTarget= false;
					flap2Intersect = true;
		}
		
		
	
		
		
		
	}
	
	
	if(changeExPlanScreen)
	{
		z.rst();
		
		if (exerciseBag.contains(z.getX()+50, z.getY()))
		{
			System.out.println("Zone Intersection detected");
			//TouchClient.remove(chest);
			//z.setLocation(1070, 220);
			//addedInBag= true;
			//sliderOne = new Slider(this, 270, 270);
			 exerciseBagIntersect = true;
			
			
		}
		
	}
	
	
}



public void touchMovedShoulderZone(Zone z, Touch t)
{
	//System.out.println("test");
	//z.setLocation(t.getLastPoint().x, t.getLastPoint().y);
	
	if (planningScreen)
	{
		z.rst();
		if(!(chestQty.contains(t.getLastPoint().x, t.getLastPoint().y)))
		{
		           notInTarget= true;
		           
			
		}
		
		if((flap1.contains(z.getX()+50, z.getY()+50)) && dayEnabled )
		{
			  
			//flap1.fill(0, 0, 0);
			
		   // flap1.textSize(20);
		   // flap1.text("May 2013", 800, 225);
			flapIntersect = true;
			notInTarget= false;
			textSize(20);
			text("May 2013", flap1.getX(), flap1.getY());
			flap1Intersect = true;
			
			//z.setLocation(570,640);
		}
		
		if((flap2.contains(z.getX()+50, z.getY()+50)) && dayEnabled )
		{
			//z.setLocation(570,640);
			  
			//		flap1.fill(0, 0, 0);
			//		flap1.textSize(20);
			 //    	flap1.text("May 2013", 800, 225);
                    flapIntersect = true;
					//System.out.println("test");
					notInTarget= false;
					flap2Intersect = true;
		}
		
		
	
		
		
		
	}
	
	
	if(changeExPlanScreen)
	{
		z.rst();
		
		if (exerciseBag.contains(z.getX()+50, z.getY()))
		{
			System.out.println("Zone Intersection detected");
			//TouchClient.remove(chest);
			//z.setLocation(1070, 220);
			//addedInBag= true;
			//sliderOne = new Slider(this, 270, 270);
			 exerciseBagIntersect = true;
			
			
		}
		
	}
	
	
}


public void touchUpShoulderZone(Zone z, Touch t)
{
	
	
   
	if (planningScreen)
	{
		
	if (notInTarget)
	{
	z.setLocation(570,640);
	}
	
 if((flap1.contains(z.getX()+50, z.getY()+50)) && dayEnabled)
	{
		z.setLocation(570,640);
		flapIntersect = false;
		flap1Intersect = false;
		++chestRectangleFlap2[day].count; 
		
        --chestBagCount;
        if(!chestFlap2ObjectExist)
		{
			chestFlap2ObjectExist = true;
		}
    }

	if((flap2.contains(z.getX()+50, z.getY()+50))&& dayEnabled )
	{
		z.setLocation(570,640);
		
		flapIntersect = false;
		flap2Intersect = false;
		
		++chestRectangleFlap2[day].count; 
        --chestBagCount;
		
		if(!chestFlap2ObjectExist)
		{
			chestFlap2ObjectExist = true;
		}
	}
	
	}
	
	if(changeExPlanScreen)
	{
		
		if (exerciseBag.contains(z.getX()+50, z.getY()))
		{
			//System.out.println("Zone Intersection detected");
			//TouchClient.remove(chest);
			z.setLocation(1035, 220);
			addedInBagShoulder = true;
			sliderTwo = new Slider(this, 260, 395);
			exerciseBagIntersect = false;
			
		}
	}
}






public void touchUpChestZone(Zone z, Touch t)
{
	
	
   
	if (planningScreen)
	{
		
	if (notInTarget)
	{
	z.setLocation(570,640);
	}
	
 if((flap1.contains(z.getX()+50, z.getY()+50)) && dayEnabled)
	{
		z.setLocation(570,640);
		flapIntersect = false;
		flap1Intersect = false;
		++chestRectangleFlap2[day].count; 
        --chestBagCount;
        if(!chestFlap2ObjectExist)
		{
			chestFlap2ObjectExist = true;
		}
    }

	if((flap2.contains(z.getX()+50, z.getY()+50))&& dayEnabled )
	{
		z.setLocation(570,640);
		
		flapIntersect = false;
		flap2Intersect = false;
		
		++chestRectangleFlap2[day].count; 
		days_button[day].exerciseLoaded=true;
        --chestBagCount;
		
		if(!chestFlap2ObjectExist)
		{
			chestFlap2ObjectExist = true;
		}
	}
	
	}
	
	if(changeExPlanScreen)
	{
		
		if (exerciseBag.contains(z.getX()+50, z.getY()))
		{
			//System.out.println("Zone Intersection detected");
			//TouchClient.remove(chest);
			z.setLocation(1070, 220);
			addedInBagChest= true;
			sliderOne = new Slider(this,260, 320);
			exerciseBagIntersect = false;
			
		}
	}
}


public	void touchUpGarden(Zone pipe, Touch t){
	pipeEnabled = false; 
	 p.x=600;
     p.y=-500;
}
//
// public void touchDown(){
//		  z.assign(TouchClient.getTouches()); 
//}	
	
public void touch(){
	
}

public void touchImageZone(Zone z) {
		  TouchClient.putZoneOnTop(z);
	//	  z.toss();
		 // z.rotate();
		  z.rst();
		  if(z.getGlobalMatrix().m00< (float)0.93)
			  {
			    ps.changeLocation(z.getOrigin());
			    ps.recieveZone(rootZone,tree,cur_tree);
			   
			    rotated= true;
			   
			 //   if
			    
			  }
		  else if (z.getGlobalMatrix().m00 > (float)0.93)
		  {
			  rotated = false;
			  
			  
		  }
		}

public void touchUpImageZone(Zone z) {
    particleCount=0;
    fertilizer.setSize(100, 104);
}



public void touchSeed(Zone z) {
	
	z.rst();
}




public void touchUpSeed(Zone z, Touch t){
	
	mousePos = t.getLastPoint().x;
	createTree();
	z.setLocation(1010, 160);
	
}

boolean treeCreated;
public void draw() {
	  
	  background(blend_color(color(225),color(137,209,234),((float)count-800)/100));
	  fill(178,34,34);
	  
	 if(treeScreen)
	 {
		// fill(255,255,255);
		// rect(300,height-450,600,height);
		
		// fill(255,255,255);
		// rect(mousePos-75,(height-450)*2,150,(height-370));
		
		 expert.setLocation(-700,-700);
		 novice.setLocation(-700,-700);
		
		 exerciseBag.setLocation(-700,-700);
		 
		
		 stroke(204, 102, 0);
		 noFill();
		 rect(525,125,670,150);
		// text("Chest", 800, 225);
		 line(748,125,748,275);
		 line(971,125,971,275);
		 line(525,155,1195,155);
		 line(525,245,1195,245);
		 fill(204, 102, 0);
		 text("Food",535,145);
		 text("Water",768,145);
		 text("Seeds",991,145);
		 text("Quantity:"+chestBagCount,535,265);
		 text("Quantity:"+chestBagCount,769,265);
		 text("Quantity:"+chestBagCount,982,265);
		 //text("Quantity:"+shoulderBagCount,793,735);
		 noFill();
         noStroke();
		  for (int i=0; i<IMAGE_FILES; i++) { 
			  days_calendar[i].setLocation(525+(i*100),280);
			 }
		 fill(255, 255, 255);
		 rect(525,300,670,30);
		 fill(204, 102, 0);
		 if(increaseX<670)
		 { rect(525,300,0+increaseX,30);
		   increaseX = (float) (increaseX + 0.5);
		 }
		 else
		 rect(525,300,670,30);	 
		 noFill();
		 
		 for (int i=0; i<IMAGE_FILES; i++) { 
			  if(days_button[i].exerciseLoaded == true)
			  {
				  days_button[i].display(this);
			  }
			 }
		 
	 
	 if(pipeEnabled)
	 {
		 p.display(this);
		 stroke(255, 40);
		 strokeWeight(5);
		 wp.run();
		 wp.addParticle(origin, target);
		 noStroke();
	 }
		  
	  
	  if (rotated)
	  {
		if(particleCount!=150)
		{
	  ps.addParticle();
	  ps.run();
	 // Iterator it = ps.particles.iterator();
	  ++particleCount;
		}
	  }
	 for (int i = 0; i < cur_tree+1; ++i) {
		 
		
		  //translate(random(150, 350), random(100, 650));
		//  fill(random(150, 230), random(150, 230), random(150, 230));
		//  rect(-25, -25, 350, 150);
		
		  tree[i].draw();
     	//  tree[i].stopLoop();
		 // tree[i].objectId = i;
		  tree[i].global_time = (int)count;
		
	 }
	 count++;
	// System.out.println(count); 
	 
	 
	 
	   
	 }
	 
	 else if (changeExPlanScreen)
	 {
		 fertilizer.setLocation(-700,-700);	
		 gardenPipe.setLocation(-700,-700);
		 expert.setLocation(-700,-700);
		 novice.setLocation(-700,-700);
		 seedZone.setLocation(-700,-700);
		 
		 
		 if(exerciseBagIntersect)
		 {
			   
			 rr3.display(true); 
			 textSize(25);
			 fill(255,255,255);
			 text("Ready to be dropped", exerciseBag.getX()+100, exerciseBag.getY()+100);
			
		 }
		 
		 float r = 0;
		 float g = 0;
		 float b = 0;
		 //positionX = random(75,325);
		 //positionY = random(75,325);
		 // Draw a blue background
		// background(255,255,255);
		 // Set ellipses and rects to CENTER mode
		 //ellipseMode(CENTER);
		 //rectMode(CENTER);
		 //Set a black stroke for shapes created beyond this point


		 noStroke();
		 fill(50,50,50,90);
		 ellipse(shadeX,shadeY,190,30);
		 //Sun

		 //Creature's body
		 strokeWeight(5);
		 stroke(0);
		 noFill();
		 //fill(100,100,0);
		 //ellipse(positionX-25,positionY+45,45,17);
		 //fill(100,100,0);
		 //ellipse(positionX+25,positionY+45,45,17);
		 //fill(100,100,0);
		 ellipse(1150,140,100,100);
		 //Creature's first eye
		 fill(255,255,255);
		 ellipse(positionX-15,positionY-20,30,30);
		 //Creature's second eye
		 fill(255,255,255);
		 ellipse(positionX+15,positionY-20,30,30);
		 //Creature's eyebrows
		 strokeWeight(3);
		 fill(0);
		 rect(positionX-20,positionY-50,10,5);
		 rect(positionX,positionY-50,10,5);
		 //Creature's mouth
		 strokeWeight(2);
		 //fill(255);
		 ellipse(positionX,positionY+20,30,10);
		 float firsteyeX=constrain(mouseX,1125,1145);
		 float firsteyeY=constrain(mouseY,110,120);
		 float secondeyeX=constrain(mouseX,1155,1175);
		 float secondeyeY=constrain(mouseY,110,120);
		 strokeWeight(2);
		 //First eye pupil
		 fill(r,g,b,200);
		 ellipse(firsteyeX,firsteyeY,8,8);
		 //Second eye pupil
		 fill(r,g,b,200); 
		 ellipse(secondeyeX,secondeyeY,8,8);
		 
		 TouchClient.add(body);
		
		 if(!exerciseBagIntersect)
		 {
			 rr3.display(false); 
			 stroke(204, 102, 0);
			 line(200,270,750,270);
			 textSize(25); 
			 fill(204, 102, 0);
			 text("Exercise Bag",380,230);
			 
			 
			 if (addedInBagChest)
			  {
				 fill(204, 102, 0);
				 textSize(25); 
				  text("1. Chest Pushups", 255, 300);
				  sliderOne.display();
				  
			 	  
				 if (operateOnChest)
				 {  
			
		      //textFont(myFont);
		      if(slideTouch)
			  {
			  image(Slider_tool,sliderOne.x+sliderOne.getValue(),sliderOne.y-48);
			  fill(204, 102, 0);
			  int value=(int)(sliderOne.getValue()); 
			  System.out.println(value);
			  
			  text("X"+value,sliderOne.x+sliderOne.getValue()+10,sliderOne.y-28);
			  noFill();
			  } 
			  }
			  
			  }
			 
			 if (addedInBagShoulder)
			  {
			  
				 fill(204, 102, 0);
				 textSize(25); 
				  text("2. Shoulders", 260, 390);
				  sliderTwo.display();
				  
			 if (operateOnShoulder)
			 {
		      //textFont(myFont);
		      if(slideTouch)
			  {
			  image(Slider_tool,sliderTwo.x+sliderTwo.getValue(),sliderTwo.y-48);
			  fill(204, 102, 0);
			  int value=(int)(sliderTwo.getValue()); 
			  System.out.println(value);
			  
			  text("X"+value,sliderTwo.x+sliderTwo.getValue()+10,sliderTwo.y-28);
			  noFill();
			  } 
			  }
			  
			  }
			 
			 
		 }
		  
		  for (int i=0; i<IMAGE_FILES; i++) { 
			  days_calendar[i].setLocation(-50, -50);
			 } 
		  
		  
	 }
	 
	 else if (selectUser)
	 {

		 fertilizer.setLocation(-700,-700);	
		 gardenPipe.setLocation(-700,-700);
		
		 
		 exerciseBag.setLocation(-700,-700);		
		 seedZone.setLocation(-700,-700);
		

		 for (int i=0; i<IMAGE_FILES; i++) { 
			  days_calendar[i].setLocation(-50, -50);
			 } 
		  
		 float r = 0;
		 float g = 0;
		 float b = 0;
		 noStroke();
		 fill(50,50,50,90);
		 ellipse(shadeX,shadeY,190,30);
		 //Sun

		 //Creature's body
		 strokeWeight(5);
		 stroke(0);
		 noFill();
		 //fill(100,100,0);
		 //ellipse(positionX-25,positionY+45,45,17);
		 //fill(100,100,0);
		 //ellipse(positionX+25,positionY+45,45,17);
		 //fill(100,100,0);
		 ellipse(1150,140,100,100);
		 //Creature's first eye
		 fill(255,255,255);
		 ellipse(positionX-15,positionY-20,30,30);
		 //Creature's second eye
		 fill(255,255,255);
		 ellipse(positionX+15,positionY-20,30,30);
		 //Creature's eyebrows
		 strokeWeight(3);
		 fill(0);
		 rect(positionX-20,positionY-45,10,5);
		 rect(positionX,positionY-45,10,5);
		 //Creature's mouth
		 strokeWeight(2);
		 //fill(255);
		 ellipse(positionX,positionY+20,30,10);
		 float firsteyeX=constrain(mouseX,1125,1145);
		 float firsteyeY=constrain(mouseY,110,120);
		 float secondeyeX=constrain(mouseX,1155,1175);
		 float secondeyeY=constrain(mouseY,110,120);
		 strokeWeight(2);
		 //First eye pupil
		 fill(r,g,b,200);
		 ellipse(firsteyeX,firsteyeY,8,8);
		 //Second eye pupil
		 fill(r,g,b,200); 
		 ellipse(secondeyeX,secondeyeY,8,8);
		 
		 
	 }
	 
	 else if (planningScreen)

	 {


		 fertilizer.setLocation(-700,-700);	
		 gardenPipe.setLocation(-700,-700);
		 expert.setLocation(-700,-700);
		 novice.setLocation(-700,-700);
		
		 exerciseBag.setLocation(-700,-700);
		// stroke();
		 fill(255,255,255);
		 
		 rect(flap1X,flap1Y,widthFlap1, heightFlap1);
		 fill(255,255,255);
		 rect(flap2X,flap2Y,widthFlap2, heightFlap2);
		 if(defaultText)
		 {
			 fill(204, 102, 0);
			 textSize(20);
			 text("Morning", flap1X+50,flap1Y+50);
			 image(Arrow_up, flap1X+300,flap1Y+20, 30, 30);
			 
			 textSize(20);
			 text("Evening", flap2X+50, flap2Y+50);
			 image(Arrow_down, flap2X+300,flap2Y+20, 30, 30);
		 }
		 else if (helpText)
		 {
			 fill(204, 102, 0);
			 text("Please Hold Day and pull the flap", flap1X+50,flap1Y+50);
			 text("Please Hold Day and pull the flap", flap2X+50,flap2Y+50);
		 }
		 
		 
		 noFill();
		 rect(550,200,670,180);
         fill(255);
		 textSize(13);    
		 text("MO                     TU                     WE                     TH                     FR                     SA                    SU", 625, 250);
		
		 stroke(204, 102, 0);
		 noFill();
		 rect(550,600,670,150);
		// text("Chest", 800, 225);
		 line(773,600,773,750);
		 line(996,600,996,750);
		 line(550,630,1220,630);
		 line(550,720,1220,720);
		// TouchClient.add(chest);
		 fill(204, 102, 0);
		 text("Chest",560,620);
		 text("Shoulders",793,620);
		 text("Quantity:"+chestBagCount,560,735);
		 text("Quantity:"+shoulderBagCount,793,735);
		 noFill();
		 
		// chest.setLocation(570,640);
		 shoulder.setLocation(870,640);
		 
		 body.setLocation(250, 100);
		 
		 if(dayEnabled)
		 {
			days_button[day].display(this);
			 
			 if (flapIntersect)
			 {   
				 
				 fill(204, 102, 0);
				 textSize(20);
				 if(flap1Intersect)
				 {
			     text("Ready to be dropped", flap1.getX()- 100, flap1.getY()-100);
				 }
				 if(flap2Intersect)
				 {
			     text("Ready to be dropped", flap2.getX()+ 100, flap2.getY()+100);
				 }
			
			 }
		    
			 
			 {
				 
			 }
			 
			 if(flap2Moved && chestFlap2ObjectExist)
			 {
				 chestRectangleFlap2[day].display(this);
				 System.out.println("Im in flaps and chest exists");
			 }
				 
			 
		 }
		 else 
		 {
			 
			  
			  for (int i=0; i<IMAGE_FILES; i++) { 
				  if(days_button[i].exerciseLoaded == true)
				  {
					  days_button[i].display(this);
				  }
				 } 
			  
			 
		 }
			 
		 noStroke();
	 }
	 
	 
	 
	}
	
	boolean bStop;
	int bracesOpen;
    int endBraces;
	int start;
	public void keyPressed(){
		
		//System.out.println(tree[1].system);
	//	System.out.println ();
		if(key=='q')
		{   
			int skipVariable=1;
			++tree[1].stackHighest;
			//System.out.println("Stack:" + tree[1].stack_pointer);
			do 
			{
			char [] treeString = tree[1].system_whole.toCharArray(); 
				start=tree[1].system_whole.indexOf("[+", rightwing_pointer);
				rightwing_pointer=start;
			 //   System.out.println(tree[1].system_whole[8]);
	
              for (;;++start)
               {  
            	   System.out.println(start);
            	  // char [] treeString = tree[1].system_whole.toCharArray();
            	   if( treeString[start] ==  '[')
            	   {   
            		   ++bracesOpen;
            		   System.out.println("braces:"+bracesOpen);
            		   
            	   }
            	   if(treeString[start] ==']')
            	   {
            		   --bracesOpen;
            	   }
            	   
            	   if(bracesOpen==0)
            	   {
            		   endBraces = start;    
            		   break;
            	   }
            	   
               }
              
              System.out.println( tree[1].system_whole.substring(rightwing_pointer, endBraces+1));
          //    tree[1].system.concat();
             // if(start!=4)
              //{
            	  if (tree[1].system_whole.substring(rightwing_pointer, endBraces)!= "[+F")
            	  {
            		   
            		  
            		  if (rightwing_pointer-2 > 0)
            		  {
            		 
            		  if( treeString[rightwing_pointer-2] != '-')
            		 {
            		     
            		  tree[1].system += tree[1].system_whole.substring(rightwing_pointer, endBraces+1);
            		  }
            		  else
            		  {
            			  skipVariable=skipVariable+1;
            		  }
            		  }
            		  
            		  else
            			  
            			  tree[1].system += tree[1].system_whole.substring(rightwing_pointer, endBraces+1);
            	  }
              
              
              /*else{
              
            	  tree[1].system += tree[1].system_whole.substring(rightwing_pointer, endBraces+1);
              	
              }
              */
              rightwing_pointer=start;
             // tree[1].drawing=true;
               System.out.println("New System:"+tree[1].system);
           	   --skipVariable;
		} while (skipVariable>0);
			
		}
		
          
		if(key=='t')
		{   
			int skipVariable=1;
			++tree[1].stackHighest;
			//System.out.println("Stack:" + tree[1].stack_pointer);
			
			
			char [] treeString = tree[1].system_whole.toCharArray(); 
				start=tree[1].system_whole.indexOf("[", rightwing_pointer);
				rightwing_pointer=start;
			 //   System.out.println(tree[1].system_whole[8]);
	
              for (;;++start)
               {  
            	   System.out.println(start);
            	  // char [] treeString = tree[1].system_whole.toCharArray();
            	   if( treeString[start] ==  '[')
            	   {   
            		   ++bracesOpen;
            		   System.out.println("braces:"+bracesOpen);
            		   
            	   }
            	   if(treeString[start] ==']')
            	   {
            		   --bracesOpen;
            	   }
            	   
            	   if(bracesOpen==0)
            	   {
            		   endBraces = start;    
            		   break;
            	   }
            	   
               }
              
              System.out.println( tree[1].system_whole.substring(rightwing_pointer, endBraces+1));
          //    tree[1].system.concat();
             // if(start!=4)
              //{
//            	  if (tree[1].system_whole.substring(rightwing_pointer, endBraces)!= "[+F")
//            	  {
//            		   
//            		  
//            		  if (rightwing_pointer-2 > 0)
//            		  {
//            		 
//            		  if( treeString[rightwing_pointer-2] != '-')
//            		 {
//            		     
//            		  tree[1].system += tree[1].system_whole.substring(rightwing_pointer, endBraces+1);
//            		  }
//            		  else
//            		  {
//            			  skipVariable=skipVariable+1;
//            		  }
//            		  }
//            		  
//            		  else
//            			  
            			  tree[1].system += tree[1].system_whole.substring(rightwing_pointer, endBraces+1);
            	//  }
              
              
              /*else{
              
            	  tree[1].system += tree[1].system_whole.substring(rightwing_pointer, endBraces+1);
              	
              }
              */
              rightwing_pointer=start;
             // tree[1].drawing=true;
               System.out.println("New System:"+tree[1].system);
    
		}
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if(key=='w')
		{        
			    
				start=tree[1].system_whole.indexOf("[-", leftwing_pointer);
				leftwing_pointer=start;
			 //   System.out.println(tree[1].system_whole[8]);
              for (;;++start)
               {  
            	  // System.out.println(start);
            	   char [] treeString = tree[1].system_whole.toCharArray();
            	   if( treeString[start] ==  '[')
            	   {   
            		   ++bracesOpen;
            		 //  System.out.println("braces:"+bracesOpen);
            		   
            	   }
            	   if(treeString[start] ==']')
            	   {
            		   --bracesOpen;
            	   }
            	   
            	   if(bracesOpen==0)
            	   {
            		   endBraces = start;    
            		   break;
            	   }
            	   
               }
              
           //   System.out.println( tree[1].system_whole.substring(rightwing_pointer, endBraces));
          //    tree[1].system.concat();
             // if(start!=4)
              //{
            	  if (tree[1].system_whole.substring(leftwing_pointer, endBraces)!= "[-F")
            	  {
            		  char [] treeString = tree[1].system_whole.toCharArray();  
            		  
            		  if (leftwing_pointer-2 > 0)
            		  {
            		 
            		  if( treeString[leftwing_pointer-2] != '+')
            		 {
            		     
            		  tree[1].system += tree[1].system_whole.substring(leftwing_pointer, endBraces+1);
            		  }
            		  }
            		  
            		  else
            			  
            			  tree[1].system += tree[1].system_whole.substring(leftwing_pointer, endBraces+1);
            	  }
              
              
              /*else{
              
            	  tree[1].system += tree[1].system_whole.substring(rightwing_pointer, endBraces+1);
              	
              }
              */
              leftwing_pointer=start;
             // tree[1].drawing=true;
           //   System.out.println(tree[1].system);
           	   
		}
		
		
		 if(key=='d') 
		 
		  {
		 
		   
		      tree[1].drawing=true;

		  }
		    
		

		 if(key=='a') 
		 
		  {
		  
		 //   bStop = !bStop;
		    if (bStop)
		      tree[1].stopLoop();
		    else
		    	tree[1].resumeLoop();
		  }
		 
		 if(key=='s') 
			 
		  {
		 
		   redraw();
		    //else
		    	//tree[1].drawing=false;
		  }
	
		  
		 if(key=='q') 
			 
		  {
		 
			 
		  }	 
		
		 if(key=='w') 
			 
		  {
		 
			 
		  }	 
		 if(key=='e') 
		  {
		 
			 
		  }	  
		 
		 
		 /*if(key=='a') 
		  {
		     //background(#AAFFEE);
			  bDisplayMessage = !bDisplayMessage; 
		    if (bDisplayMessage)
		    {
		      //fill(#FFAA88);
		      text("You got it!", 350, height / 2);
		p
		      // If the spent time is above the defined duration
		      
		    //  System.out.println("test" + (millis() - startTime > DISPLAY_DURATION));
		      if (millis() - startTime > DISPLAY_DURATION) 
		      {
		        // Stop displaying the message, thus resume the ball moving
		    	System.out.println("test");  
		        bDisplayMessage = false;
		      }
		    }
		  }
		
		  if(key=='d') 
		  {
		
			  for (int i = 0; i < cur_tree+1; ++i) {
					 
					
				  //translate(random(150, 350), random(100, 650));
				//  fill(random(150, 230), random(150, 230), random(150, 230));
				//  rect(-25, -25, 350, 150);
				
				  tree[i].drawing = false;
				
			 }

		  }
			  
		 if (key == 's')
			 
		 {
			 
			 	  for (int i = 0; i < cur_tree+1; ++i) {
						 
						
					  //translate(random(150, 350), random(100, 650));
					//  fill(random(150, 230), random(150, 230), random(150, 230));
					//  rect(-25, -25, 350, 150);
					
					  tree[i].drawing = true;
					
				 }

			  

			 
		 }
*/	
	} 
	
	public void createTree() {
	  if (cur_tree < max_trees-1)
	  {
	  cur_tree++;
	  //int breed = floor(random(1,(float) 7.999));
	  //++breed_track;
	  if (breed_track > 5)
	  {
	    breed_track = 1;
	  }
	  int breed = 1;
	 // pushMatrix();
	 // tint(random(150, 230), random(150, 230), random(150, 230));
	 // image(bubble,mouseX,height-mouseY);
	 // fill(0);
	  //textFont(font, 18);
	//  text(tweet2, 0, 0, 300, 100);
	  treeCreated=true;
	  tree[cur_tree] = new Lsystem(mousePos,height, height-450,breed,(int)count,this,cur_tree);
	  //mousePos=mouseX;
	  System.out.println("posRoot"+(mousePos-75));
	  rootZone[cur_tree] = new Zone("rootZone"+cur_tree,mousePos-75,(height-450)*2,150,(height-370));
	  TouchClient.add(rootZone[cur_tree]);
	//  popMatrix(); 
	  
	  }
	}



public	void touchDownDay0(Zone z){
	System.out.println("test day1");
	dayEnabled=true;
	day=0;
	
}

public	void touchUpDay0(Zone z){
	dayEnabled=false;
}

public	void touchDownDay1(Zone z){
	System.out.println("test day1");
	z.tint(0, 153, 204, 126);
	dayEnabled=true;
	day=1;
	
}

public	void touchUpDay1(Zone z){
	dayEnabled=false;
}

public	void touchDownDay2(Zone z){
	System.out.println("test day1");
	dayEnabled=true;
	day=2;
	
}

public	void touchUpDay2(Zone z){
	dayEnabled=false;
}

public	void touchDownDay3(Zone z){
	System.out.println("test day1");
	dayEnabled=true;
	day=3;
	
}

public	void touchUpDay3(Zone z){
	dayEnabled=false;
}

public	void touchDownDay4(Zone z){
	System.out.println("test day1");
	dayEnabled=true;
	day=4;
	
}

public	void touchUpDay4(Zone z){
	dayEnabled=false;
}

public	void touchDownDay5(Zone z){
	System.out.println("test day1");
	dayEnabled=true;
	day=5;
	
}

public	void touchUpDay5(Zone z){
	dayEnabled=false;
}

public	void touchDownDay6(Zone z){
	System.out.println("test day1");
	dayEnabled=true;
	day=6;
	
}

public	void touchUpDay6(Zone z){
	dayEnabled=false;
}



	int blend_color(int x, int y, float factor)
	  {
	    int output;
	    if (factor < 0) 
	    {
	      output = x;
	      return output;
	    }
	    else if (factor > 1)
	    {
	      output = y;
	      return output;
	    }
	    
	    output = color((1-factor)*red(x)+factor*red(y),(1-factor)*green(x)+factor*green(y),(1-factor)*blue(x)+factor*blue(y),(1-factor)*alpha(x)+factor*alpha(y));
	    return output;
	  }


	  public static void main(String args[]) {
		    PApplet.main(new String[] { "--present", "hviz.HViz" });
		    
		    ConfigurationBuilder cb = new ConfigurationBuilder();
		    cb.setOAuthConsumerKey("5qRJFuVSvoS3WI2YGHNbQ");
		    cb.setOAuthConsumerSecret("caM7G7O41FY7hequJJa55iEHbOTDUTkfkRsDK4vik");
		    cb.setOAuthAccessToken("57978748-zrW7iuhiDEi3Qr9VrKfWxRhwxxvrI4G3oAqJtxKtM");
		    cb.setOAuthAccessTokenSecret("hKqOy2inkyysNpLL6iVkm2diolTWMtGgerNa64aPDo");
	
		    Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		    Query query = new Query("#endomondo");
		    query.count(1);
		     
		    try {
		      QueryResult result = twitter.search(query);
		      ArrayList tweets = (ArrayList) result.getTweets();
		     
		      for (int i = 0; i < tweets.size(); i++) {
		    	  
		    	   Status t=(Status) tweets.get(i);
			        User u= (User) t.getUser();
			        String user= u.getName();  
		      
		        
		        String msg = t.getText();
		        Date d = t.getCreatedAt();
		     
		    //    println("Tweet by " + user + " at " + d + ": " + msg);
		        
		        List<Status> statusess = twitter.getUserTimeline(u.getId());
		     //   System.out.println(u.getId());
                for (Status status3 : statusess) 
                 {
                      //  System.out.println(status3.getText());
                 }
		        
		      };
		    }
		    catch (TwitterException te) {
		      println("Couldn't connect: " + te);
		    };
   
		    
		  } 
	    
	  
	
	
}