package hviz;


import processing.core.PApplet;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;




public class Lsystem {

	
	int xpos, ypos; //coordinates in drawspace of base
	String system; //actual system
	String axiom;
	int iterations;
	int global_time;
	int objectId;
	String system_whole;
	String Frule, Xrule;
	double minus_radians;
	double plus_radians;
	double width_factor;
	boolean doSth=true;
	float root_width;
	double length_factor;
	int ticks_per_branch;
	int life_time;
	double perm_len;
	int leaf_size;
	int branch_vibrant, leaf_prev, leaf_next;
	int leaf_change_counter, leaf_change_increment;
	int timeCheck=1;
	int endTime=300;
	int storePoint= 0;
	boolean drawing=true;
	int startGrowthTime=0;
	int stackHighest=0;
	PApplet parent;
	boolean drawnBranch=false;
	Multimap<Integer, String> myMultimap = ArrayListMultimap.create();
	@SuppressWarnings("deprecation")
	Lsystem(int rootx, int rooty, double d, int breed, int global_time, PApplet p, int tree_id)
	{
		parent = p;  
		branch_vibrant = parent.color(108,28,107,200);
		leaf_next = parent.color(109,255,19,170);
		leaf_prev = parent.color(15,40);
		leaf_change_counter = 400;
		leaf_change_increment = leaf_change_counter;
		life_time = 0;
		
        objectId=tree_id;
        System.out.println(tree_id);
		xpos = rootx;
		ypos = rooty;
		root_width = 30;
		width_factor  = (float) 0.6;
		length_factor = (float) 0.4;
		perm_len = (float) (d/2);
		leaf_size = (int) parent.random(15,25);
		ticks_per_branch = (int) parent.max(4,30-((float)global_time/50));
		
		//    parent.registerDraw(this);
		Xrule = "F";
		switch(breed)
		{
		case 1:
			axiom = "F[F]";
			iterations = 3;
			Frule = "F[+F][-F]";
			Xrule = Frule;
			minus_radians = (float) 0.6;
			plus_radians = (float) 0.6;
			width_factor = (float) 0.5;
			break;

		case 2:
			axiom = "+F";
			iterations = 5;
			Frule = "F[-FF]";
			Xrule = Frule;
			minus_radians = (float) 1.0;
			plus_radians = (float) 0.8;
			break;

		case 3:
			axiom = "F-F";
			iterations = 3 + (int)parent.random(-2,4);
			length_factor = parent.random((float)0.55,(float)0.8);
			Frule = "X[+F]";
			Xrule = "X";
			minus_radians = (float) 0.8;
			plus_radians = (float) 0.8;
			break;

		case 4:
			axiom = "FFF";
			iterations = 2;
			Frule = "F[+FF][-FF]";
			Xrule = Frule;
			minus_radians = (float) 0.8;
			plus_radians = (float) 0.8;
			break;

		case 5:
			axiom = "F[F]";
			iterations = 4;
			Frule = "F+F[-F-F]";
			Xrule = Frule;
			plus_radians = (float) 0.1;
			minus_radians = (float) 0.9;
			length_factor = (float) 0.8;
			perm_len = d/3.7;
			break;

		case 6:
			axiom = "F";
			iterations = 4;
			Frule = "F[F-F+F]";
			Xrule = Frule;
			plus_radians = 0.7;
			minus_radians = 0.7;
			length_factor = 0.6;
			width_factor = 0.7;
			break;

		case 7:
			axiom = "FX";
			iterations = 3;
			Frule = "F[+X][-X]F";
			Xrule = "[-X][--X][+X][++X]";
			plus_radians = 0.2;
			minus_radians = 0.2;
			length_factor = 0.7;
			perm_len = d/3.7;
			break;

		}

		minus_radians += parent.random((float)-0.1,(float)0.1);
		plus_radians += parent.random((float)-0.1,(float)0.1);
		root_width = root_width + (int)parent.random(-4,4);
		system = axiom;


		for (int i = 0; i < iterations; ++i)
		{
			// System.out.println(system);
			System.out.println(' ');
			system = applyFrule(system);

		}
         
		 if(this.objectId==1)
		 {
           system_whole=system;
           system=system_whole;
		 }
		 System.out.println(system_whole);
		//   System.out.println(breed);

	}

	// Frule applied to system
	String applyFrule(String input)
	{
		String output = "";

		for(int i = 0; i < input.length(); ++i)
		{
			if (input.charAt(i) == 'F')
			{
				if (parent.random(1,100) > 200) //flip to X
				{
					output = output + Xrule;
				}
				else
				{
					output = output + Frule;
				}
			}
			else if (input.charAt(i) == 'X')
			{
				if(parent.random(1,100) > 200)
				{
					output = output + Frule;
				}
				else
				{
					output = output + Xrule;
				}
			}
			else
			{   
				output = output + input.charAt(i);
			}
		}

		return output;
	}


	void stopLoop()
	{
		parent.noLoop();
	}

	void resumeLoop()
	{
		parent.loop();
	}

	public  void draw()
	{ 

		
		int time = life_time;

		int gray = 0;
		Branch[] stack = new Branch[64];
		int stack_pointer = 0;   	     
   

		int curx, cury;
		curx = 0;
		cury = 0;
		float angel = 0;
		float prev_angel = 0;
		float len = (float)perm_len;
		float temp_len = len;  
		float branch_width = root_width;
		int branch_count = 0;
		plus_radians += parent.sin((float)time/10)/400;
		minus_radians += parent.cos((float)time/5)/1000;

		if (this.objectId == 1) 
		{
		System.out.println(drawing);
		}
		if(drawing)
		{
			life_time=startGrowthTime; 
			++life_time;
			parent.pushMatrix();
			parent.translate(xpos,ypos);
			/*plus_radians += parent.sin((float)time/10)/400;
			minus_radians += parent.cos((float)time/5)/1000;
*/
			for (int i = 0; i < system.length(); ++i)
			{

				char operator = system.charAt(i);
				if (this.objectId == 1) 
				{
			     System.out.println(i);
			     System.out.println(operator);
				}
				
					storePoint=i;
				
				if (operator == 'F' || operator == 'X')
				{
					//controlling the growth




				//	if(drawing)
					//{
					++branch_count;

					if (branch_count > time/ticks_per_branch)
					{
						len = len * ((time%ticks_per_branch) + 1) / ticks_per_branch;

					}
                    
					//}


					parent.noStroke();
					//branch color
					if (global_time < 200)
					{
						parent.fill(gray,40);
					}
					else
					{
						parent.fill(blend_color(parent.color(gray,40),branch_vibrant,((float)global_time-200-branch_count*50)/(400)));
					}
					gray = gray + 1;

					int next_x = curx + (int)(len*parent.sin(angel));
					int next_y = cury - (int)(len*parent.cos(angel));

					int curx_1 = curx - (int)(branch_width*parent.cos(prev_angel));
					int curx_2 = curx + (int)(branch_width*parent.cos(prev_angel));
					int cury_1 = cury - (int)(branch_width*parent.sin(prev_angel));
					int cury_2 = cury + (int)(branch_width*parent.sin(prev_angel));

					branch_width = parent.max((int) (branch_width*width_factor),2);

					int next_x1 = next_x - (int)(branch_width*parent.cos(angel));
					int next_x2 = next_x + (int)(branch_width*parent.cos(angel));
					int next_y1 = next_y - (int)(branch_width*parent.sin(angel));
					int next_y2 = next_y + (int)(branch_width*parent.sin(angel));

					parent.beginShape();
					parent.vertex(curx_1,cury_1);
					parent.vertex(curx_2,cury_2);
					parent.vertex(next_x2,next_y2);
					parent.vertex(next_x1,next_y1);
					parent.endShape();
					if (this.objectId == 1) 
					{
					
				//	System.out.println("curx:"+curx+"cury:"+cury+"nextx:"+next_x+"nexty:"+next_y);
                   
					} 
					curx = next_x;
					cury = next_y;
			//		System.out.println(angel);
					prev_angel = angel;
/*					if (this.objectId == 1) 
					{
					System.out.println("After change  - curx:"+curx+"cury:"+cury);
					
					
*/					
					
	
					
					
					if (this.objectId == 1) 
					{
					//System.out.println("branchCount"+branch_count+"time"+time+"ticks"+ticks_per_branch);
					}
					
				
					
					if (branch_count > time/ticks_per_branch)
					{  
						if (this.objectId == 1) 
						{
				    //   System.out.println("it breaks");
							}
					;
					
				
				     break;
	                }
					else
					{
						if (this.objectId == 1) 
						{
					//		System.out.println("it did not break");
					   
						}
					}
					if (this.objectId != 0) 
					{
					if (time%ticks_per_branch==0)
					{
						
						drawing=false;
					//    System.out.println("stackPointer"+stack_pointer+"stackHighest"+stackHighest);
						if(stack_pointer==stackHighest)
						{
							
						}
						
				/*	     
					     System.out.println("I'm here");
					     
					     if(i>1)
					     {
						 if(system.charAt(i-1)=='+')
						 {
							 System.out.println("break condition check:"+(myMultimap.containsEntry(stack_pointer, "plus")));
						     if(!(myMultimap.containsEntry(stack_pointer, "plus")))
						     {
						    	 drawing=false;
						     }
						     else
						     {
						    	 drawing=true;
						     }
						    
						 }
						 
						 if(system.charAt(i-1)=='-')
						 {
						     if(!(myMultimap.containsEntry(stack_pointer, "minus")))
						     {
						    	 drawing=false;
						     }

						     else
						     {
						    	 drawing=true;
						     }
						   
						 }
					     
					     }
					     */
					
					  
					}
					}
				
					len = temp_len;
					len *= length_factor;

				}
				else if (operator == '+')
				{

					angel += plus_radians;
					
					if (this.objectId == 1) 
					{
					if(!(myMultimap.containsEntry(stack_pointer, "plus")))
					{ myMultimap.put(stack_pointer,"plus");
					  System.out.println("Stuff that is going in:" +  stack_pointer + "plus");
					}
					}
				}
				else if (operator == '-')
				{

					angel -= minus_radians;
					if (this.objectId == 1) 
					{
					if(!(myMultimap.containsEntry(stack_pointer, "minus")))
					{ myMultimap.put(stack_pointer,"minus");
					  System.out.println("Stuff that is going in:" +  stack_pointer + "plus");
					}
					}
				}
				else if (operator == '[') //push
				{
					if (stack[stack_pointer] == null)
					{
						stack[stack_pointer] = new Branch();
					}

					stack[stack_pointer].xpos = curx;
					stack[stack_pointer].ypos = cury;
					stack[stack_pointer].angel = angel;
					stack[stack_pointer].branch_width = branch_width;
					stack[stack_pointer].prev_angel = prev_angel;
					stack[stack_pointer].branch_length = len;
					++stack_pointer;
				}
				else if (operator == ']')
				{
					--stack_pointer;
					//fade from color to color
					//counter tracks when next change, increased by increment each time
					float factor = ((float)time - leaf_change_counter + leaf_change_increment - (branch_count*(float)30*(leaf_change_counter-time)/leaf_change_increment))/leaf_change_increment;
					parent.fill(blend_color(leaf_prev,leaf_next, factor));

					if (time > leaf_change_counter)
					{
						leaf_prev = leaf_next;
						leaf_next = parent.color(parent.random(1,255),parent.random(1,255),parent.random(1,255),parent.random(150,255));
						leaf_change_increment *= 0.7;
						leaf_change_increment = parent.max(leaf_change_increment,80);
						leaf_change_counter += leaf_change_increment;

					}
					parent.ellipse(curx,cury,leaf_size,leaf_size);

					curx = stack[stack_pointer].xpos;
					cury = stack[stack_pointer].ypos;
					angel = stack[stack_pointer].angel;
					branch_width = stack[stack_pointer].branch_width;
					prev_angel = stack[stack_pointer].prev_angel;
					len = stack[stack_pointer].branch_length;

				}
				
				/*timeCheck++;
				// storePoint=i;
				System.out.println(timeCheck);
				
				if (timeCheck>endTime) { 
					drawing=false;
					break;
				}*/
			}

			parent.popMatrix();

		/*	timeCheck++;
			if (timeCheck>endTime) { 
				drawing=false;
				//startGrowthTime=life_time;
			}*/
			startGrowthTime=life_time;
		}
		
		else
		{
			
			//System.out.println("not draw loop");
			timeCheck=0;
		/*	int localTime=time;
			++localTime;
			plus_radians += parent.sin((float)localTime/10)/400;
			minus_radians += parent.cos((float)localTime/5)/1000;
*/
			++life_time;
			parent.pushMatrix();
			parent.translate(xpos,ypos);
			// System.out.println(storePoint);  
			for (int i = 0; i < storePoint; ++i)
			{

				char operator = system.charAt(i);

				//    storePoint=i;
				if (operator == 'F' || operator == 'X')
				{
					//controlling the growth





					++branch_count;

					if (branch_count > time/ticks_per_branch)
					{
						len = len * ((time%ticks_per_branch) + 1) / ticks_per_branch;

					}



					parent.noStroke();
					//branch color
					if (global_time < 200)
					{
						parent.fill(gray,40);
					}
					else
					{
						parent.fill(blend_color(parent.color(gray,40),branch_vibrant,((float)global_time-200-branch_count*50)/(400)));
					}
					gray = gray + 1;

					int next_x = curx + (int)(len*parent.sin(angel));
					int next_y = cury - (int)(len*parent.cos(angel));

					int curx_1 = curx - (int)(branch_width*parent.cos(prev_angel));
					int curx_2 = curx + (int)(branch_width*parent.cos(prev_angel));
					int cury_1 = cury - (int)(branch_width*parent.sin(prev_angel));
					int cury_2 = cury + (int)(branch_width*parent.sin(prev_angel));

					branch_width = parent.max((int) (branch_width*width_factor),2);

					int next_x1 = next_x - (int)(branch_width*parent.cos(angel));
					int next_x2 = next_x + (int)(branch_width*parent.cos(angel));
					int next_y1 = next_y - (int)(branch_width*parent.sin(angel));
					int next_y2 = next_y + (int)(branch_width*parent.sin(angel));

					parent.beginShape();
					parent.vertex(curx_1,cury_1);
					parent.vertex(curx_2,cury_2);
					parent.vertex(next_x2,next_y2);
					parent.vertex(next_x1,next_y1);
					parent.endShape();

					curx = next_x;
					cury = next_y;
					prev_angel = angel;

					if (branch_count > time/ticks_per_branch)
					{
						break;
					}
					len = temp_len;
					len *= length_factor;

				}
				else if (operator == '+')
				{

					angel += plus_radians;

				}
				else if (operator == '-')
				{

					angel -= minus_radians;
				}
				else if (operator == '[') //push
				{
					if (stack[stack_pointer] == null)
					{
						stack[stack_pointer] = new Branch();
					}

					stack[stack_pointer].xpos = curx;
					stack[stack_pointer].ypos = cury;
					stack[stack_pointer].angel = angel;
					stack[stack_pointer].branch_width = branch_width;
					stack[stack_pointer].prev_angel = prev_angel;
					stack[stack_pointer].branch_length = len;
					++stack_pointer;
				}
				else if (operator == ']')
				{
					--stack_pointer;
					//fade from color to color
					//counter tracks when next change, increased by increment each time
					float factor = ((float)time - leaf_change_counter + leaf_change_increment - (branch_count*(float)30*(leaf_change_counter-time)/leaf_change_increment))/leaf_change_increment;
					parent.fill(blend_color(leaf_prev,leaf_next, factor));

					if (time > leaf_change_counter)
					{
						leaf_prev = leaf_next;
						leaf_next = parent.color(parent.random(1,255),parent.random(1,255),parent.random(1,255),parent.random(150,255));
						leaf_change_increment *= 0.7;
						leaf_change_increment = parent.max(leaf_change_increment,80);
						leaf_change_counter += leaf_change_increment;

					}
					parent.ellipse(curx,cury,leaf_size,leaf_size);

					curx = stack[stack_pointer].xpos;
					cury = stack[stack_pointer].ypos;
					angel = stack[stack_pointer].angel;
					branch_width = stack[stack_pointer].branch_width;
					prev_angel = stack[stack_pointer].prev_angel;
					len = stack[stack_pointer].branch_length;

				}
			}

			parent.popMatrix();	


		}




	}











	//end draw function

	//color fading function to blend
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

		output = parent.color((1-factor)*parent.red(x)+factor*parent.red(y),(1-factor)*parent.green(x)+factor*parent.green(y),(1-factor)*parent.blue(x)+factor*parent.blue(y),(1-factor)*parent.alpha(x)+factor*parent.alpha(y));
		return output;
	}



}
