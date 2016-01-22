package com.hypoxiagames.loosethreads.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Sprite{

	// Helps to 
	public enum Direction{
		up,
		down,
		left,
		right,
		ul,
		ur,
		dl,
		dr,
		none
	}
	
	private Vector2 location;
	private Direction direction;
	
	public Bullet(){} // Placeholder until I get a bullet sprite.
	
	public Bullet(Sprite sprite,Vector2 Location, Direction dir){
		super(sprite);
		setDirection(dir);
		
	}
	
	public void destroy(){
		this.destroy();
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
