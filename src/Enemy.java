import processing.core.*;

class Enemy extends PApplet {
  
  public PVector pos, vel, acc;
  
  public Enemy(PVector startPos) {
    pos = startPos;
    vel = new PVector(0, 0);
    acc = new PVector(0, 0);
  }
  
  public void update() {
    vel.add(acc);
    pos.add(vel);
  }
  
  public void show() {}
  
  public boolean collide(Runner r) {return false;}

}

class Circle extends Enemy {
  
  float diameter;
  
  PApplet parent;
  
  public Circle(PVector startPos, float d, PApplet p) {
    super(startPos);
    parent = p;
    this.vel = new PVector(random((float) -6.0, (float) -2.0), 0);
    diameter = d;
  }
  
  @Override
  public void show() {
	  parent.pushStyle();
	  parent.fill(77, 182, 172);
	  parent.noStroke();
	  parent.ellipse(pos.x, pos.y, diameter, diameter);
	  parent.popStyle();
  }
  
  @Override
  public boolean collide(Runner r) {
    PVector distVect = PVector.sub(r.pos, this.pos);
    float dist = distVect.mag();
    
    if(dist <= r.diameter/2+this.diameter/2) {
      //println("colliding");
      return true;
    }
    else {
      //println("not colliding");
      return false;
    }
  }
  
}

class Square extends Enemy {
  
  float width_;
  
  PApplet parent;  
  
  public Square(PVector startPos, float w, PApplet p) {
    super(startPos);
    parent = p;
    this.acc = new PVector(random((float) -0.05, (float) -0.01), 0);
    this.vel = new PVector(-4, 0);
    width_ = w;
  }
  
  @Override
  public void show() {
	  parent.pushStyle();
	  parent.fill(77, 182, 172);
	  parent.noStroke();
	  parent.rect(pos.x, pos.y, width_, width_);
	  parent.popStyle();
  }
  
  @Override
  public boolean collide(Runner r) {
    float radius = r.diameter/2;
    
    float testX = r.pos.x;
    float testY = r.pos.y;
    
    if(r.pos.x < this.pos.x) {
      testX = this.pos.x;
    }
    else if(r.pos.x > this.pos.x+width_) {
      testX = this.pos.x+width_;
    }
    
    if(r.pos.y < this.pos.y) {
      testY = this.pos.y;
    }
    else if(r.pos.y > this.pos.y+width_) {
      testY = this.pos.y+width_;
    }
    
    float distX = r.pos.x-testX;
    float distY = r.pos.y-testY;
    float distance = sqrt((distX*distX)+(distY*distY));

    if (distance <= radius) {
      //println("colliding");
      return true;
    }
    else {
      //println("not colliding");
      return false; 
    }
  }
  
}