package com.hypoxiagames.marioclone;

import java.awt.List;

import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.marioclone.entities.Bullet;
import com.hypoxiagames.marioclone.entities.Player;
import com.hypoxiagames.marioclone.entities.Player.xDir;
import com.hypoxiagames.marioclone.screens.GameScreen;

public class ProjectileManager {
	private int speed;
	private Vector2 velocity;
	private Vector2 initLocation;
	private Vector2 bulletLocation;
	private Bullet bullet;
	private GameScreen gameScreen;
	List bulletList = new List();

	public ProjectileManager(Vector2 location, GameScreen screen) {
		speed = 45 * 5;
		velocity = new Vector2().setZero();
		setBulletLocation(new Vector2().setZero());
		bullet = new Bullet();
		initLocation = location;
		gameScreen = screen;
		findBulletSpawn();
	}

	// Spawns a new bullet at the location
	public void NewBullet(Vector2 location, Bullet.Direction direction) {
		// TODO Put in logic for spawning a new bullet.
	}

	// Logic to figure out where the bullet should spawn from, and which
	// direction to move in.
	public void findBulletSpawn() {
		switch (Player.getxDirection()) {
		case left:
			bulletLocation = new Vector2(initLocation.x - 5, initLocation.y);
			break;
		case none:
			break;
		case right:
			break;
		default:
			break;
		}

	}

	/*******************
	 * * Get/Setters * *
	 *******************/
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
