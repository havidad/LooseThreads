package com.hypoxiagames.loosethreads.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Monster extends Sprite {

	private Vector2 location;

	public Monster(Monster monster, Sprite sprite) {
		super(sprite);
		
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}
}
