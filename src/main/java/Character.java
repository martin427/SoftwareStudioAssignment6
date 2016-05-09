package main.java;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import main.java.Character;
import main.java.MainApplet;
import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/


@SuppressWarnings("unused")
public class Character{
	
	float x,y;
	private float radius;
	private float X,Y;
	private int nodeValue;
	private int linkValue;
	
	private int r,g,b;
	
	private MainApplet parent;
	private String name,colour;
	private boolean inCircle;
	
	private ArrayList<Character> targets = new ArrayList<Character>();
	private ArrayList<Integer> weights = new ArrayList<Integer>();

	public Character(MainApplet parent, String name,int value,String colour, float x, float y){

		this.parent = parent;
		this.name = name;
		this.nodeValue = value;
		this.colour = colour;
		transfer_hex_to_rgb(this.colour);
		this.x = x;
		this.y = y;
		this.X = x;
		this.Y = y;
		this.radius = 20;

		this.targets = new ArrayList<Character>();
	}

	public void display(){
		this.parent.noStroke();
		this.parent.fill(r, g, b);
		this.parent.ellipse(x, y, radius, radius);
		//this.parent.rect(x-name.length()*10, y-20, name.length()*20, 40, 12, 12, 12, 12);
		if(inCircle){
			for(Character character : targets){
				parent.noFill();
				parent.stroke(0);
				parent.strokeWeight(weights.get(targets.indexOf(character))/(float)5);
				
				float a = (550+(x+character.x)/2)/2;
				float b = (340+(y+character.y)/2)/2;
				if(character.isInCircle()){
					parent.bezier(x, y, a, b, a, b, character.x, character.y);
				}
				
			}
		}
		
	}
	
	
	
	public void addTarget(Character target, int weight){ 
		targets.add(target);
		weights.add(weight);
	}
	
	public ArrayList<Character> getTargets(){ return this.targets; }
	
	public void setLinkValue(int linkValue){
		this.linkValue = linkValue;
	}
	public int getLinkValue(){
		return this.linkValue;
	}
	public void transfer_hex_to_rgb(String colorStr){
		r = Integer.valueOf( colorStr.substring( 1, 3 ), 16 );
        g = Integer.valueOf( colorStr.substring( 3, 5 ), 16 );
        b = Integer.valueOf( colorStr.substring( 5, 7 ), 16 );
	}
	public void setInCircle(boolean input){
		inCircle = input;
	}
	
	public boolean isInCircle(){
		return inCircle;
	}
	
	public String getName(){
		return name;
	}
	
	public float getX(){
		return X;
	}
	public float getY(){
		return Y;
	}
	public float getRadius(){
		return radius;
	}
}