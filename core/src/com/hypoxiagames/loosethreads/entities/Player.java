package com.hypoxiagames.loosethreads.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hypoxiagames.loosethreads.CollisionManager;
import com.hypoxiagames.loosethreads.screens.GameScreen;

public class Player extends Sprite implements InputProcessor {
	GameScreen screen;
	// Player movement velocity
	public Vector2 velocity = new Vector2(0, 0);
	
	TiledMap map;
	CollisionManager colManager;
	Array<Vector2> collisionPoints = new Array<Vector2>();
	
	private static float unitScale = GameScreen.UNITSCALE;

	// Change these values to change different parameters for the characters
	// movement in the world
	private float speed = 145 * unitScale;

	private static xDir xDirection;
	private static yDir yDirection;

	private Vector2 location;

	public boolean canMoveLeft = true, canMoveRight = true, canMoveUp = true, canMoveDown = true;

	public boolean collidedGround, collidedWall;
	
	boolean isFlipped;
	
	public int posX, posY;
	public float oldX;
	public float oldY;

	public Player(Sprite sprite, TiledMap map, GameScreen screen) {
		super(sprite);
		this.screen = screen;
		this.setSize(30*unitScale, 56*unitScale);
		this.map = map;
		location = new Vector2(getX(), getY());
		posX = (int) location.x;
		posY = (int) location.y;
		setxDirection(xDir.none);
		setyDirection(yDir.none);
		colManager = new CollisionManager(map, this, this.screen);
		// Bottom Collision Point
		collisionPoints.add(new Vector2(location.x + (getWidth() / 2),location.y)); 
		// Top Left Collision Point
		collisionPoints.add(new Vector2(location.x + (getWidth() / 2),location.y + getHeight())); 
		// Left Collision Point
		collisionPoints.add(new Vector2(location.x,location.y + (getHeight() / 2)));
		// Right Collision Point
		collisionPoints.add(new Vector2(location.x + getWidth(),location.y +(getHeight() / 2))); 
		
	}

	public enum xDir {
		left, right, none
	}

	public enum yDir {
		up, down, none
	}

	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	public void update(float delta) {
		// Save old position
		oldX = (this.location.x);
		oldY = (this.location.y);
		collisionPoints.set(0, new Vector2(location.x + (getWidth() / 2),location.y));
		collisionPoints.set(1, new Vector2(location.x + (getWidth() / 2),location.y + getHeight()));
		collisionPoints.set(2, new Vector2(location.x,location.y + (getHeight() / 2))); 
		collisionPoints.set(3, new Vector2(location.x + getWidth(),location.y +(getHeight() / 2)));
		
		if(velocity.x > 0 || velocity.x < 0 || velocity.y > 0 || velocity.y < 0)
			colManager.checkWallCollision(collisionPoints);
		
		updateMovement();
		
		
		// Limits the player to only going too fast.
		if (velocity.y > speed)
			velocity.y = speed;
		else if (velocity.y < -speed)
			velocity.y = -speed;

		if (velocity.x > speed)
			velocity.x = speed;
		else if (velocity.x < -speed)
			velocity.x = -speed;

		// Move on X Axis
		setX(getX() + velocity.x * delta);

		// Move on Y Axis
		setY(getY() + velocity.y * delta);
		setLocation(new Vector2(getX(), getY()));
		
		posX = (int)location.x;
		posY = (int)location.y;
		
		System.out.println(posX +","+ posY);
	}

	public void updateMovement() {
		// Logic to decide which directions the player should move
		if (!collidedWall) {
			switch (getxDirection()) {
			case none:
				velocity.x = 0;
				break;
			case left:
				velocity.x = -speed;
				break;
			case right:
				velocity.x = speed;
				break;
			}
			switch (yDirection) {
			case none:
				velocity.y = 0;
				break;
			case up:
				velocity.y = speed;
				break;
			case down:
				velocity.y = -speed;
				break;
			}
		}
	}

	// Begins code for the player controlled inputs. Such as moving and attack.
	// So far only movement code
	// has been done
	@Override
	public boolean keyDown(int keycode) {
		if (!collidedWall) {
			switch (keycode) {
			case Keys.W:
				setyDirection(yDir.up);
				break;
			case Keys.S:
				setyDirection(yDir.down);
				break;
			case Keys.A:
				setxDirection(xDir.left);
				break;
			case Keys.D:
				setxDirection(xDir.right);
				break;
			}
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
			if (Gdx.input.isKeyPressed(Keys.S))
				setyDirection(yDir.down);
			else
				setyDirection(yDir.none);
			break;
		case Keys.S:
			if (Gdx.input.isKeyPressed(Keys.W))
				setyDirection(yDir.up);
			else
				setyDirection(yDir.none);
			break;
		case Keys.A:
			if (Gdx.input.isKeyPressed(Keys.D))
				setxDirection(xDir.right);
			else
				setxDirection(xDir.none);
			break;
		case Keys.D:
			if (Gdx.input.isKeyPressed(Keys.A))
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