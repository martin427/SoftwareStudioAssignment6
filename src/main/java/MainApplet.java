package main.java;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "resources/";
	private String file = path + "starwars-episode-1-interactions.json";
	
	JSONObject data;
	JSONArray nodes, links;
	private ArrayList<Character> characters;
	private int episode = 2;// which episode are you in
	private final static int width = 1200, height = 650;
	
	public void setup() {
		file = path + "starwars-episode-" + episode + "-interactions.json";
		size(800, 800);
		characters = new ArrayList<Character>();
		size(width, height);
		smooth();
		loadData();
		
	}
	public void keyPressed(){
		if(keyCode==49){
			episode = 1;
			setup();
		}else if(keyCode==50){
			episode = 2;
			setup();
		}else if(keyCode==51){
			episode = 3;
			setup();
		}else if(keyCode==52){
			episode = 4;
			setup();
		}else if(keyCode==53){
			episode = 5;
			setup();
		}else if(keyCode==54){
			episode = 6;
			setup();
		}else if(keyCode==55){
			episode = 7;
			setup();
		}
		
	}

	public void draw() {
		background(255);
		
		stroke(60, 119, 119);
		for(Character character: this.characters){
			for(Character target: character.getTargets()){
				strokeWeight(target.getLinkValue());
				line(character.getX(), character.getY(), target.getX(), target.getY());
			}
		}
		
		for(Character character: this.characters){
			character.display();
		}
			
	}

	private void loadData(){
		
		data = loadJSONObject(file);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		System.out.println("Number of nodes: " + nodes.size());
		System.out.println("Number of links: " + links.size());
		int setY=1;
		int setX=0;
		for(int i=0; i<nodes.size(); i++){
			JSONObject node = nodes.getJSONObject(i);
			setX++;
			characters.add(new Character(this, node.getString("name"),node.getInt("value"),node.getString("colour"), 30*setX, 30*setY+200));
			if((i+1)%4==0){
				setY++;
				setX=0;
			}
			
		}
		for(int i=0; i<links.size(); i++){
			JSONObject link = links.getJSONObject(i);
			characters.get(link.getInt("source")).addTarget(characters.get(link.getInt("target")), link.getInt("value"));
		}
		
		
	}

}
