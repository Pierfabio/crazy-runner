import java.sql.Connection;
import java.util.ArrayList;


import java.util.Scanner;



import DBController.DBConnection; 

import DBController.QueryController;
import Models.Score;
import Models.User;
import processing.core.*;

public class CrazyRunner extends PApplet {
	
	Runner runner;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	static int score = 0;
	static int maxScore = 0;
	String scoreStr = "";
	int lives = 1;
	PFont font;
	int fontSize = 400;
	int time = 0;
	static boolean go = false;
	static boolean isScoreUpdated = false;
	
	
	
	public static void main(String[] args) {
		
		DBConnection conn = new DBConnection();
		Connection c =conn.getDatabaseConnection();
		
		
		
		User user = Starter.start();
		
		QueryController cont = new QueryController();
		
	    
	    int id = cont.checkUser(user);
	    
	    System.out.println("Utente numero: "+id);
	    
	    if(id==-1){
	    	
	    	if(cont.checkNickname(user.getNickname())){
	    		user.setId(cont.insertUser(user));
	    	}
	    	
	    	
	    }
	    else{
    		user.setId(id);
    	}
	    
	    
	    
//	    Integer idUser = cont.insertUser(new User(nick, name));
	    
	   
	    
	  
	    
	    Score maxS = cont.readScore(user);
	    
	    System.out.println("id "+maxS.getId());
	    
	    //+" score: "+maxS.getScore());
	    
	 
	    
	    
	    
	    maxScore = maxS.getScore();
	    
	    System.out.println("Max Score: " + maxScore);
		
		System.out.println("Launching PApplet");
		PApplet.main("CrazyRunner");
		while(!go) {
			//System.out.println("Playing Crazy Runner");
			System.out.println("Current score: " + score);			
		}
		if(go) {
			System.out.println(score);
			Score s = new Score();
			s.setId(id);
			s.setScore(score);
			
			isScoreUpdated = cont.updateScore(s);
			if(isScoreUpdated) {
				System.out.println("New Max Score!!!!!");
			}
			else {
				System.out.println("Try Again!!!");
				
			
			}
		}
	}
	
	
	public void settings() {
		size(1000, 400);
	}
		
	public void setup() {
		runner = new Runner(this);
		updateScore(0);
		font = createFont("fonts/design_block.ttf", fontSize);
	}
	
	public void draw() {  
		  background(178, 223, 219);
		  
		  if(lives <= 0) {
		    println("GAME OVER");
//		    noLoop();
		    go = true;
		    
		    
		    while(!isScoreUpdated){ //gestione nuovo punteggio massimo
		    	exit();
		    }
		    
		    
		    
		    
		   
		  }
		  
		  if(millis()-time >= 1000) {
		    addRandomEnemy();
		    time = millis();
		  }
		  
		  drawScore();
		  drawLives();
		  
		  runner.update(mouseY);
		  runner.show();
		  
		  for(int i = enemies.size()-1; i >= 0; i--) {
		    //println("new draw vehicle cycle");
		    Enemy e = enemies.get(i);
		    e.update();
		    e.show();
		    boolean collided = e.collide(runner);
		    if(collided && e instanceof Square) {
		      enemies.remove(e);
		      lives--;
		    }
		    if(collided && e instanceof Circle) {
		      enemies.remove(e);
		      updateScore(+2);
		    }
		    if(e.pos.x < -100 && e instanceof Square) {
		      enemies.remove(e);
		      updateScore(+1);
		    }
		    if(e.pos.x < -100 && e instanceof Circle) {
		      enemies.remove(e);
		      updateScore(-1);
		    }
		  }
		  
		  drawBorders();
		}

		public void drawBorders() {
		  pushStyle();
		  stroke(0, 150, 136);
		  strokeWeight(18);
		  line(0, 0, width, 0);
		  line(0, height, width, height);
		  popStyle();
		}

		public void updateScore(int i) {
		  score += i;
		  score = score <= 0 ? 0 : score;
		  String actualScoreStr = Integer.toString(score);
		  
		  scoreStr = "";
		  int maxStrLength = 3;
		  for(i=0; i<maxStrLength-actualScoreStr.length(); i++) {
		    scoreStr = scoreStr.concat("0");
		  }
		  scoreStr = scoreStr.concat(actualScoreStr);
		  //println(scoreStr);  
		}

		public void drawScore() {
		  pushMatrix();
		    float w = (float) (width*0.5);
		    float h = (float) (height*0.5);
		    translate(w, h);
		    
		    pushStyle();
		      rectMode(CENTER);
		      textAlign(CENTER, CENTER);
		      //textSize(size);
		      textFont(font, fontSize);
		      fill(224, 242, 241);
		      text(scoreStr, 0, 0);
		    popStyle();
		    
		  popMatrix();
		  
		  //MAX SCORE
		    pushStyle();
		      rectMode(CENTER);
		      //textAlign(CENTER, CENTER);
		      //textSize(size);
		      textFont(font, fontSize/10);
		      fill(224, 242, 241);
		      text("MAX SCORE: " + maxScore, (float) (width*0.65), (float) (height*0.1));
		    popStyle();
		}

		public void drawLives() {
		  pushMatrix();
		    
		    float w = (float) (width*0.98);
		    float h = (float) (height*0.92);
		    translate(w, h);
		    
		    pushStyle();
		      fill(224, 242, 241);
		      noStroke();
		      for(int i = 0; i < lives; i++) {
		        ellipse(0, -22*i, 18, 18);
		      }
		    popStyle();
		    
		  popMatrix();
		}

		public void addRandomEnemy() {
		  float dimension = random(20, 35);
		  PVector startPos = new PVector(0, 0);
		  
		  Enemy eNew = null;
		  int num = round(random((float) -0.5,(float) 1.5));
		  switch(num) {
		   case 0:
		     startPos = new PVector(width+dimension/2, random(0+dimension/2, height-dimension/2));
		     eNew = new Circle(startPos, dimension, this);
		     enemies.add(eNew);
		     break;
		   case 1:
		     startPos = new PVector(width+dimension, random(0, height-dimension));
		     eNew = new Square(startPos, dimension, this);
		     enemies.add(eNew);
		     break;
		  }
		  //println("enemy " + eNew + " added");
		}

		public void keyPressed () {
		  if (key == '+') {
		    updateScore(+1);
		  }
		  
		  if (key == '-') {
		    updateScore(-1);
		  }
		  
		  if (key == 'k') {
		    lives=0;
		  }
		}
}
