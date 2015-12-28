package com.hypoxiagames.marioclone.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Sprite{

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
		direction = dir;
		
	}
	
	public void destroy(){
		this.destroy();
	}
}
