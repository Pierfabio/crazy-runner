import processing.core.*;

class Runner extends PApplet {
  
  public PVector pos, vel, acc;
  private float maxSpeed, frictionSpeed;
  public float diameter;
  
  PApplet parent;
  
  public Runner(PApplet p) {	
    parent = p;
  
    diameter = 50;
    
    maxSpeed = 5;
    frictionSpeed = (float) -2;
    
    pos = new PVector(parent.width/6, parent.height/2);
    vel = new PVector(0, 0);
    acc = new PVector(0, 0);
  }
  
  public void update(int mouseY_) {
    PVector mouse = new PVector(pos.x, mouseY_);
    //println(mouse);
    acc = PVector.sub(mouse, pos);
    acc.setMag((float) 0.50);
    vel.add(acc);
    vel.limit(maxSpeed);
    pos.add(vel);
    
    if (pos.y + diameter/2 > parent.height) {
      pos.y = parent.height - diameter/2;
      vel.y *= frictionSpeed; 
    } 
    else if (pos.y - diameter/2 < 0) {
      pos.y = diameter/2;
      vel.y *= frictionSpeed;
    }
  }

  public void show() {
    parent.pushStyle();
    parent.fill(0, 150, 136);
    parent.noStroke();
    parent.ellipse(pos.x, pos.y, diameter, diameter);
    parent.popStyle();
  }
  
}