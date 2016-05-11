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
	//因為是7集，所以直接寫在一個Array中
	JSONObject[] data = new JSONObject[10];
	JSONArray[] nodes = new JSONArray[10]; 
	JSONArray[] links = new JSONArray[10];
	private ArrayList<Character> characters = new ArrayList<Character>();
	private int episode = 1;
	private final static int width = 1200, height = 650;
	private boolean mouseOverNode; 
	private Character mouseLockNode, mouseOnNode; 
	
	
	public void setup() {
		size(width,height);
		smooth();
		loadData();
		loadNode();
		link();
		Ani.init(this);
		cp5 = new ControlP5(this);
		//按鈕設定
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
			starwars = ImageIO.read(new File("./starwars.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//ALL IN 
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
	
	//CLEAR
	public void button2(){
		for(Character character : characters){
			character.setInCircle(false);
			ani = Ani.to(character, (float) 2, "x", character.getX());
			ani = Ani.to(character, (float) 2, "y", character.getY());
			
		}
	}
	//next episode
	public void button3(){
		if(episode<7){
			episode++;
		}else{
			episode=1;
		}
		loadNode();
		link();
		
	}
		
	//設定球的位置
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
	//鍵盤事件
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
		loadNode();
		link();
	
		
	}
       //畫出物件
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
				mouseOnNode = character;
			}
			else{
				ani = Ani.to(character, (float) 0.5, "radious", 20);
			}
		}
		for(Character character : characters){
			if(dist(character.x, character.y, mouseX, mouseY) < 20 / 2 && !mousePressed){
				mouseOverNode = true;
				break;
			}else{
				mouseOverNode = false;
			}
		}
		if(mouseOnNode != null){
			if(dist(mouseOnNode.x,mouseOnNode.y,mouseX,mouseY)<20){
				fill(255,180 ,84 );
				rect(mouseX, mouseY - 15, mouseOnNode.getName().length() * 30, 30, 15);
				fill(255);
				textSize(16);
				text(mouseOnNode.getName(), mouseX+20 , mouseY+5);
			}
		}
		
		
	}
	protected void paintComponet(Graphics g){
		super.paintComponents(g);
		g.drawImage(starwars,550,340 ,100 , 132, null);
	}
	    //滑鼠事件
		public void mousePressed(){
			if(mouseOverNode){
				mouseLockNode = mouseOnNode;
			}
		}
		public void mouseDragged(){
			
		if(mouseLockNode != null){
			mouseLockNode.x = mouseX;
			mouseLockNode.y = mouseY;
		}
			
	}
		
		public void mouseReleased(){
			if(mouseLockNode != null){
				if(dist(mouseLockNode.x, mouseLockNode.y, 600, 350) < 500 / 2){
					mouseLockNode.setInCircle(true);
					moveNodeToCircle();
				}else{
					mouseLockNode.setInCircle(false);
					ani = Ani.to(mouseLockNode, (float) 0.1, "x", mouseLockNode.getX());
					ani = Ani.to(mouseLockNode, (float) 0.1, "y", mouseLockNode.getY());
					moveNodeToCircle();
				}
			}
			mouseLockNode = null;
		}
		
		

	private void loadData(){
		for(int i = 1;i<=7;i++){
			data[i] = loadJSONObject(path+fileHead+Integer.toString(i)+fileTail);
			nodes[i] = data[i].getJSONArray("nodes");
			links[i] = data[i].getJSONArray("links");
		}
	}
	
	//load Node
	private void loadNode(){
		characters.clear();
		int a=0,b=0;
		for(int i = 0;i<nodes[episode].size();i++ ){
			if(i%10==0){
				a = 30+ i*5;
				b = 100;
			}
			JSONObject obj = nodes[episode].getJSONObject(i);
			Character character = new Character(this, obj.getString("name"),i, obj.getString("colour"), (float) a, (float) b);
			characters.add(character);
			b+=55;
		}
		
	}
	//Link
	private void link(){
		for(int i=0; i<links[episode].size();i++){
			JSONObject obj = links[episode].getJSONObject(i);
			int source = obj.getInt("source");
			int target = obj.getInt("target");
			int weight = obj.getInt("value");
			characters.get(source).addTarget(characters.get(target), weight);
		}
	}
	
}
