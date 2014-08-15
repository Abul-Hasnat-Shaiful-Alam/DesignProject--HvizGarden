package hviz;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PVector;
import vialab.SMT.Zone;
public class FertilizerParticleSystem {

		  ArrayList<FertilizerParticles> particles;
		  Iterator<FertilizerParticles> it;
		  PVector origin;
		  PVector updatedLocation;
		  PApplet parent;
		  Zone[] zone;
		  boolean growth;
		  int treeNum;
		  int maxTree;
		  Lsystem[] tree;
		  
		  FertilizerParticleSystem(PVector location,PApplet p) {
			parent = p;  
		    origin = location.get();
		    particles = new ArrayList<FertilizerParticles>();
		  }

		  void addParticle() {
		    particles.add(new FertilizerParticles(origin,parent));
		  }
             
		  void changeLocation(PVector newLocation) {
			    origin= newLocation;
			  }
		  
		  void recieveZone(Zone[] zone, Lsystem[] tree, int maxTree)
		  {
			  
			  this.zone = zone;
			  this.maxTree=maxTree;
			  this.tree = tree;
		  }
		  
		
		  
		  void run() {
		   it = particles.iterator();
		  //  updatedLocation = it.next().;
		    //System.out.println( updatedLocation);
		    while (it.hasNext()) {
		      FertilizerParticles p = it.next();
		      p.recieveZone(zone,tree,maxTree);
		      p.run();
		      this.growth = p.growth;
		      this.treeNum = p.treeNum;
		      if (p.isDead()) {
		        it.remove(); 
		      }
		    }
		  }
		}

