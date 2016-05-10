package main.java;

import java.lang.Object;
import processing.event.*;
import processing.event.KeyEvent;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.EventObject;
import controlP5.ControlP5;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/

@SuppressWarnings({ "serial", "unused" })
public class MainApplet extends PApplet{
	private String path = "resources/";
	private String fileHead = "starwars-episode-";
	private String fileTail = "-interactions.json";
    private Ani ani;
	private ControlP5 cp5;
	
	JSONObject[] data = new JSONObject[10];
	JSONArray[] nodes = new JSONArray[10]; 
	JSONArray[] links = new JSONArray[10];
	private ArrayList<Character> characters = new ArrayList<Character>();
	private int episode = 1;// which episode are you in
	private final static int width = 1200, height = 650;
	
	private boolean overNode;
	private Character lockNode, overNodeWhileNotPressed;
	
	
	public void setup() {
		size(width,height);
		smooth();
		loadData();
		loadNodeAndLink();
		Ani.init(this);
		cp5 = new ControlP5(this);
		
		cp5.addButton("button1").setLabel("ADD ALL")
		.setPosition(875,50)
		.setSize(200, 50);
		cp5.addButton("button2").setLabel("CLEAR")
		.setPosition(875,150)
		.setSize(200, 50);
		cp5.addButton("button3").setLabel("Next Episode")
		.setPosition(875,250)
		.setSize(200,50);
		
	}
	public void button1(){
		for(Character character : characters){
			character.setInCircle(true);
		}
		moveAllInCircle();
	}
	public void button2(){
		for(Character character : characters){
			character.setInCircle(false);
			ani = Ani.to(character, (float) 1, "x", character.getX());
			ani = Ani.to(character, (float) 1, "y", character.getY());
			
		}
	}
	
	public void keyPressed(){
		if(keyCode=='1'){
			episode = 1;
			
		}else if(keyCode=='2'){
			episode = 2;
			
		}else if(keyCode=='3'){
			episode = 3;
			
		}else if(keyCode=='4'){
			episode = 4;
		
		}else if(keyCode=='5'){
			episode = 5;
			
		}else if(keyCode=='6'){
			episode = 6;
			
		}else if(keyCode=='7'){
			episode = 7;
			
		}
		loadNodeAndLink();
		
	}

	public void draw() {
		background(255);
		//draw the circle
		fill(255);
		stroke(60, 119, 119);
		if(lockNode != null && dist(lockNode.x,lockNode.y,550,340)<520/2){
			strokeWeight(10);
		}else strokeWeight(5);
		ellipse(550,340,520,520);
		
		textSize(40);
		fill(100,50,20);
		text("Star Wars " + String.valueOf(episode), 485, 50);
		
		for(Character character : characters){
			character.display();
			if(dist(character.x, character.y, mouseX, mouseY) < 20 / 2 && !mousePressed){
				overNodeWhileNotPressed = character;
			}
			else{
				ani = Ani.to(character, (float) 0.5, "diameter", 20);
			}
		}
		for(Character character : characters){
			if(dist(character.x, character.y, mouseX, mouseY) < 20 / 2 && !mousePressed){
				overNode = true;
				break;
			}else{
				overNode = false;
			}
		}
		if(overNodeWhileNotPressed != null){
			if(dist(overNodeWhileNotPressed.x,overNodeWhileNotPressed.y,mouseX,mouseY)<20){
				ani = Ani.to(overNodeWhileNotPressed, (float) 0.5, "diameter", 50);
				fill(0, 162, 123);
				rect(mouseX, mouseY -  15, overNodeWhileNotPressed.getName().length() * 15, 30, 15);
				fill(255);
				textSize(16);
				text(overNodeWhileNotPressed.getName(), mouseX + 10, mouseY + 5);
			}
		}
		
		/*for(Character character: this.characters){
			for(Character target: character.getTargets()){
				strokeWeight(target.getLinkValue());
				line(character.x, character.y, target.x, target.y);
			}
		}*/
	}
		public void mousePressed(){
			if(overNode){
				lockNode = overNodeWhileNotPressed;
			}
		}
		public void mouseDragged(){
			
		if(lockNode != null){
			lockNode.x = mouseX;
			lockNode.y = mouseY;
		}
			
	}
		
		public void mouseReleased(){
			if(lockNode != null){
				if(dist(lockNode.x, lockNode.y, 550, 340) < 520 / 2){
					lockNode.setInCircle(true);
					moveAllInCircle();
				}else{
					lockNode.setInCircle(false);
					ani = Ani.to(lockNode, (float) 0.5, "x", lockNode.getX());
					ani = Ani.to(lockNode, (float) 0.5, "y", lockNode.getY());
					moveAllInCircle();
				}
			}
			lockNode = null;
		}
		
		

	private void loadData(){
		for(int i = 1;i<=7;i++){
			data[i] = loadJSONObject(path+fileHead+Integer.toString(i)+fileTail);
			nodes[i] = data[i].getJSONArray("nodes");
			links[i] = data[i].getJSONArray("links");
		}
	}
	
	private void loadNodeAndLink(){
		characters.clear();
		
		int x=0,y=0;
		
		for(int i = 0;i<nodes[episode].size();i++ ){
			if(i%11==0){
				x = 35+ i*5;
				y = 48;
			}
			JSONObject obj = nodes[episode].getJSONObject(i);
			Character character = new Character(this, obj.getString("name"),i, obj.getString("colour"), (float) x, (float) y);
			characters.add(character);
			y+=55;
		}
		for(int i = 0;i<links[episode].size();i++){
			JSONObject obj = links[episode].getJSONObject(i);
			int source = obj.getInt("source");
			int target = obj.getInt("target");
			int weight = obj.getInt("value");
			characters.get(source).addTarget(characters.get(target), weight);
		}
		
	}
	
		private void moveAllInCircle(){
			int counter = 0;
			float angle = 0;
			for(Character character : characters){
				if(character.isInCircle()){
					counter++;
				}
			}
			for(Character character : characters){
				if(character.isInCircle()){
					character.x = 550+260*cos(angle);
					character.y = 340-260*sin(angle);
					angle += (TWO_PI/(float)counter);
				}
			}
		}
		
	}
