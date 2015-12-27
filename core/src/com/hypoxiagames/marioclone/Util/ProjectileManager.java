package com.hypoxiagames.marioclone.Util;

import com.badlogic.gdx.math.Vector2;


public class ProjectileManager {
	private int speed;
	private Vector2 velocity;
	private Vector2 bulletLocation;
	public ProjectileManager(Vector2 location){
		speed = 45 * 5;
		velocity = new Vector2().setZero();
		setBulletLocation(new Vector2().setZero());
	}
	
	// Spawns a new bullet at the location 
	public void NewBullet(Vector2 location, int direction){
		//TODO Put in logic for spawning a new bullet.
	}
	
	// Logic to figure out where the bullet should spawn from, and which direction to move in.
	public Vector2 findBulletSpawn(){
		//TODO Logic for bullet spawning
		/*
		 * Not sure what will have to be done for this... Testing will have to be done tomorrow. I will get around
		 * to it but for now I'm just going to put some example code.
		 * */
		return new Vector2(0,0);
	}

	/********************
	 *                  *
	 *   Get/Setters    *
	 *                  *
	 ********************/
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Vector2 getBulletLocation() {
		return bulletLocation;
	}

	public void setBulletLocation(Vector2 bulletLocation) {
		this.bulletLocation = bulletLocation;
	}
}
