package main.java;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character extends MouseAdapter{
	
	private float x, y, radius;
	private int nodeValue;
	private int linkValue;
	private int r,g,b;
	private MainApplet parent;
	private String name,colour;
	private ArrayList<Character> targets;

	public Character(MainApplet parent, String name,int value,String colour, float x, float y){

		this.parent = parent;
		this.name = name;
		this.nodeValue = value;
		this.colour = colour;
		transfer_hex_to_rgb(this.colour);
		this.x = x;
		this.y = y;
		this.radius = 25;

		this.targets = new ArrayList<Character>();
	}

	public void display(){
		this.parent.noStroke();
		this.parent.addMouseMotionListener(this);
		this.parent.fill(r, g, b);
		//Color.decode(colour);
		this.parent.ellipse(x, y, radius, radius);
		//this.parent.rect(x-name.length()*10, y-20, name.length()*20, 40, 12, 12, 12, 12);
		
		this.parent.textSize(13);
		this.parent.fill(255);
		this.parent.text(name, x-name.length()*10+5, y+10);
	}
	public void addTarget(Character target, int linkValue){ 
		target.linkValue = linkValue;
		this.targets.add(target);
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
	public void mouseDragged(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
		System.out.println(name + "X: " + x + " Y: " + y);
        //System.out.println("mouseDragged");
    }
	
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	public float getRadius(){
		return this.radius;
	}
}
