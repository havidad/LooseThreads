package com.hypoxiagames.loosethreads.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hypoxiagames.loosethreads.CollisionManager;
import com.hypoxiagames.loosethreads.screens.GameScreen;

public class Player implements InputProcessor{
	GameScreen screen;
	// Player movement velocity
	public Vector2 velocity = new Vector2(0, 0);
	TiledMap map;
	public CollisionManager colManager;
	Array<Vector2> collisionPoints = new Array<Vector2>();
	
	TextureAtlas animationTexture;
	private TextureRegion animationRegion;
	public TextureRegion getAnimationRegion() {
		return animationRegion;
	}

	public void setAnimationRegion(TextureRegion animationRegion) {
		this.animationRegion = animationRegion;
	}

	private Sprite sprite;
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	private static float unitScale = GameScreen.UNITSCALE;

	// Change these values to change different parameters for the characters
	// movement in the world
	private float speed = 200 * unitScale;

	private static xDir xDirection;
	private static yDir yDirection;

	private Vector2 location;

	public boolean canMoveLeft = true, canMoveRight = true, canMoveUp = true, canMoveDown = true;
	private ArrayList<Boolean> moveDir;
	private boolean wHeld, aHeld, sHeld, dHeld;

	boolean isFlipped;

	public int posX, posY;
	public float oldX;
	public float oldY;

	public Player(TextureAtlas bloopTextureAtlas, Sprite sprite, TiledMap map, GameScreen screen) {
		this.animationTexture = bloopTextureAtlas;
		animationRegion = animationTexture.createSprite("down1");
		this.sprite = sprite;
		this.sprite.setSize(32 * unitScale, 45 * unitScale);
		this.sprite.setTexture(animationRegion.getTexture());
		this.map = map;
		moveDir = new ArrayList<Boolean>();
		moveDir.add(canMoveLeft);
		moveDir.add(canMoveRight);
		moveDir.add(canMoveUp);
		moveDir.add(canMoveDown);
		location = new Vector2(sprite.getX(), sprite.getY());
		posX = (int) location.x;
		posY = (int) location.y;
		setxDirection(xDir.none);
		setyDirection(yDir.none);
		colManager = new CollisionManager(map, this, this.screen);
		// Bottom Collision Point
		collisionPoints.add(new Vector2(location.x + (sprite.getWidth() / 2), location.y));
		// Top Collision Point
		collisionPoints.add(new Vector2(location.x + (sprite.getWidth() / 2), location.y + sprite.getHeight()));
		// Left Collision Point
		collisionPoints.add(new Vector2(location.x, location.y + (sprite.getHeight() / 2)));
		// Right Collision Point
		collisionPoints.add(new Vector2(location.x + sprite.getWidth(), location.y + (sprite.getHeight() / 2)));

	}

	public enum xDir {
		left, right, none
	}

	public enum yDir {
		up, down, none
	}
	
	public void checkWallsAround() {
		colManager.checkWallCollision(collisionPoints);
		// Check to see which directions we can't move.
		canMoveDown = colManager.wallBelow(collisionPoints.get(0));
		canMoveUp = colManager.wallAbove(collisionPoints.get(1));
		canMoveLeft = colManager.wallLeft(collisionPoints.get(2));
		canMoveRight = colManager.wallRight(collisionPoints.get(3));
		
	}
	
	public void updateAnimation(float delta){
		
	}
	
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		updateAnimation(Gdx.graphics.getDeltaTime());
		sprite.draw(spriteBatch);
	}

	public void update(float delta) {
		// Save old position
		oldX = (this.location.x);
		oldY = (this.location.y);
		collisionPoints.set(0, new Vector2(location.x + (sprite.getWidth() / 2), location.y));
		collisionPoints.set(1, new Vector2(location.x + (sprite.getWidth() / 2), location.y + sprite.getHeight()));
		collisionPoints.set(2, new Vector2(location.x, location.y + (sprite.getHeight() / 2)));
		collisionPoints.set(3, new Vector2(location.x + sprite.getWidth(), location.y + (sprite.getHeight() / 2)));

		// Limits the player to only going too fast.
		if (velocity.y > speed)
			velocity.y = speed;
		else if (velocity.y < -speed)
			velocity.y = -speed;
		if (velocity.x > speed)
			velocity.x = speed;
		else if (velocity.x < -speed)
			velocity.x = -speed;

		checkWallsAround();
		updateMovement();
		
		// Move on X Axis
		if (canMoveLeft || canMoveRight)
			sprite.setX(sprite.getX() + velocity.x * delta);
		else
			sprite.setX(sprite.getX());

		// Move on Y Axis
		if (canMoveDown || canMoveUp)
			sprite.setY(sprite.getY() + velocity.y * delta);
		else
			sprite.setY(sprite.getY());

		setLocation(new Vector2(sprite.getX(), sprite.getY()));

		posX = (int) location.x;
		posY = (int) location.y;

		System.out.println(posX + "," + posY);
		
		colManager.checkTeleportingZones(location.x, location.y);
	}

	public void updateMovement() {
		// Logic to decide which directions the player should move
		if (sHeld || wHeld){
			switch(getyDirection()){
			case up:
				if(canMoveUp && wHeld)
					velocity.y = speed;
				else
					velocity.y = 0;
				break;
			case down:
				if(canMoveDown && sHeld)
					velocity.y = -speed;
				else
					velocity.y = 0;
				break;
			default:
				velocity.y = 0; 
				break;
			}
		}
		else
			velocity.y =0;
		if (aHeld || dHeld){
			switch(getxDirection()){
			case right:
				if(canMoveRight && dHeld)
					velocity.x = speed;
				else
					velocity.x = 0;
				break;
			case left:
				if(canMoveLeft && aHeld)
					velocity.x = -speed;
				else
					velocity.x = 0;
				break;
			default:
				velocity.x = 0; 
				break;
			}
		}
		else
			velocity.x = 0;
	}
	
	public void moveToPoint(float x, float y){
		sprite.setY(y);
		sprite.setX(x);
	}
	
	public void disableMovement(){
		for(Boolean bool : moveDir)
			bool = false;
		
	}
	public void enableMovement(){
		for(Boolean bool : moveDir)
			bool = true;
		
	}

	// Begins code for the player controlled inputs. Such as moving and attack.
	// So far only movement code
	// has been done
	@Override
	public boolean keyDown(int keycode) {
		// Switch Statements for the initial deciscion of movement direction.
		// Sets values so we can keep track of keys kept
		// pressed down.
		checkWallsAround();
		switch (keycode) {
		case Keys.W:
			wHeld = true;
			if (canMoveUp)
				setyDirection(yDir.up);
			else
				setyDirection(yDir.none);
			break;
		case Keys.S:
			sHeld = true;
			if (canMoveDown)
				setyDirection(yDir.down);
			else
				setyDirection(yDir.none);
			break;
		case Keys.A:
			aHeld = true;
			if (canMoveLeft)
				setxDirection(xDir.left);
			else
				setxDirection(xDir.none);
			break;
		case Keys.D:
			dHeld = true;
			if (canMoveRight)
				setxDirection(xDir.right);
			else
				setxDirection(xDir.none);
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// Sets the direction moving to none, unless they are trying to move in
		// the opposite direction.
		// also lets us know when a button is no longer held.
		switch (keycode) {
		case Keys.W:
			wHeld = false;
			if (sHeld)
				setyDirection(yDir.down);
			else
				setyDirection(yDir.none);
			break;
		case Keys.S:
			sHeld = false;
			if (wHeld)
				setyDirection(yDir.up);
			else
				setyDirection(yDir.none);
			break;
		case Keys.A:
			aHeld = false;
			if (dHeld)
				setxDirection(xDir.right);
			else
				setxDirection(xDir.none);
			break;
		case Keys.D:
			dHeld = false;
			if (aHeld)
				setxDirection(xDir.left);
			else
				setxDirection(xDir.none);
			break;
		case Keys.ESCAPE:
			Gdx.app.exit();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	// Bunch of different get/setters
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public static xDir getxDirection() {
		return xDirection;
	}

	public static void setxDirection(xDir xDirection) {
		Player.xDirection = xDirection;
	}

	public static yDir getyDirection() {
		return yDirection;
	}

	public static void setyDirection(yDir yDirection) {
		Player.yDirection = yDirection;

	}
}