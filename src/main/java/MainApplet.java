package main.java;

import java.lang.Object;
import processing.event.*;
import processing.event.KeyEvent;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

import javax.imageio.ImageIO;

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
	private BufferedImage starwars;
	JSONObject[] data = new JSONObject[10];
	JSONArray[] nodes = new JSONArray[10]; 
	JSONArray[] links = new JSONArray[10];
	private ArrayList<Character> characters = new ArrayList<Character>();
	private int episode = 1;
	private final static int width = 1200, height = 650;
	
	private boolean overNode;
	private Character lockNode, mouseonnode;
	
	
	public void setup() {
		size(width,height);
		smooth();
		loadData();
		loadNodeAndLink();
		Ani.init(this);
		cp5 = new ControlP5(this);
		
		cp5.addButton("button1").setLabel("ADD ALL")
		.setPosition(900,350)
		.setSize(200, 50);
		cp5.addButton("button2").setLabel("CLEAR")
		.setPosition(900,450)
		.setSize(200, 50);
		cp5.addButton("button3").setLabel("Next Episode")
		.setPosition(900,550)
		.setSize(200,50);
		
		try {
			starwars = ImageIO.read(new File("starwars.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void button1(){
		    int tmp = 0;
			float angle = 0;
		for(Character character : characters){
			character.setInCircle(true);
			if(character.isInCircle()){
				tmp++;
			}
		}
			for(Character character: characters){
				if(character.isInCircle()){
					ani = Ani.to(character, (float) 0.1, "x", 600+250*cos(angle)) ;
					ani = Ani.to(character, (float) 0.1, "y", 350-250*sin(angle)) ;
					angle += (TWO_PI/tmp);
				}
			}
				
		}
	
	
	public void button2(){
		for(Character character : characters){
			character.setInCircle(false);
			ani = Ani.to(character, (float) 2, "x", character.getX());
			ani = Ani.to(character, (float) 2, "y", character.getY());
			
		}
	}
	
	public void button3(){
		if(episode<7){
			episode++;
		}else{
			episode=1;
		}
		loadNodeAndLink();
	}
		
	
	private void moveNodeToCircle(){
		int tmp = 0;
		float angle = 0;
		for(Character character : characters){
			if(character.isInCircle()){
				tmp++;
			}
		}
		for(Character character : characters){
			if(character.isInCircle()){
				character.x = 600+250*cos(angle);
				character.y = 350-250*sin(angle);
				angle += (TWO_PI/tmp);
			}
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
		background(0);
		//circle
		fill(255);
		stroke(150, 80, 20);	
		strokeWeight(10);
		ellipse(600,350,500,500);
		
		//words
		PFont wordstyle;
		wordstyle = createFont("Ethnocentric",36,true);
		
		textFont(wordstyle,26);
		this.fill(100,90,60);
		if(episode==1){
			this.text("Star Wars  I: The Phantom Menace", 320, 50);
		}else if(episode==2){
			this.text("Star Wars  II: Attack of the Clones", 320, 50);	
		}else if(episode==3){
			this.text("Star Wars  III: Revenge of the Sith", 320, 50);	
		}else if(episode==4){
			this.text("Star Wars  IV: A New Hope", 320, 50);	
		}else if(episode==5){
			this.text("Star Wars  V: The Empire Strikes Back", 320, 50);	
		}else if(episode==6){
			this.text("Star Wars  VI: Return of the Jedi", 320, 50);	
		}else{
			this.text("Star Wars  VII: The Force Awakens", 320, 50);	
		}
		
		for(Character character : characters){
			character.display();
			if(dist(character.x, character.y, mouseX, mouseY) < 20 / 2 && !mousePressed){
				mouseonnode = character;
			}
			else{
				ani = Ani.to(character, (float) 0.5, "radious", 20);
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
		if(mouseonnode != null){
			if(dist(mouseonnode.x,mouseonnode.y,mouseX,mouseY)<20){
				fill(255,180 ,84 );
				rect(mouseX, mouseY - 15, mouseonnode.getName().length() * 30, 30, 15);
				fill(255);
				textSize(16);
				text(mouseonnode.getName(), mouseX+20 , mouseY+5);
			}
		}
		
		/*for(Character character: this.characters){
			for(Character target: character.getTargets()){
				strokeWeight(target.getLinkValue());
				line(character.x, character.y, target.x, target.y);
			}
		}*/
	}
	protected void paintComponet(Graphics g){
		super.paintComponents(g);
		g.drawImage(starwars,550,340 ,100 , 132, null);
	}
	
		public void mousePressed(){
			if(overNode){
				lockNode = mouseonnode;
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
				if(dist(lockNode.x, lockNode.y, 600, 350) < 500 / 2){
					lockNode.setInCircle(true);
					moveNodeToCircle();
				}else{
					lockNode.setInCircle(false);
					ani = Ani.to(lockNode, (float) 0.1, "x", lockNode.getX());
					ani = Ani.to(lockNode, (float) 0.1, "y", lockNode.getY());
					moveNodeToCircle();
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
	
}
