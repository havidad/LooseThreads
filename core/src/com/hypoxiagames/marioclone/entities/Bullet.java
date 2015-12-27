package com.hypoxiagames.marioclone.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Sprite{

	private int travelDirection; // 0 = South, 1 = North, 2 = West, 3 = East
	private Vector2 location;
	
	public Bullet(Vector2 Location, int direction){
		location = Location;
		travelDirection = direction;
	}
	
	public void destroy(){
		this.destroy();
	}
}
