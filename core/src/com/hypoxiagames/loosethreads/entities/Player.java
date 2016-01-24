package com.hypoxiagames.loosethreads.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hypoxiagames.loosethreads.CollisionManager;
import com.hypoxiagames.loosethreads.screens.GameScreen;

public class Player implements InputProcessor {
	GameScreen screen;
	// Player movement velocity
	public Vector2 velocity = new Vector2(0, 0);
	// TiledMap map;
	public CollisionManager colManager;
	Array<Vector2> collisionPoints = new Array<Vector2>();

	TextureAtlas animationTexture;
	private TextureRegion[] animRegion;
	private TextureRegion currentFrame;
	private TextureRegion[] upAnimation;
	private TextureRegion[] downAnimation;
	private TextureRegion[] rightAnimation;
	private TextureRegion[] leftAnimation;
	private Sprite sprite;
	private Animation animation;

	private static float unitScale = GameScreen.UNITSCALE;

	// Change these values to change different parameters for the characters
	// movement in the world
	private float speed = 255 * unitScale;

	private static xDir xDirection;
	private static yDir yDirection;

	private Vector2 location;

	public boolean canMoveLeft = true, canMoveRight = true, canMoveUp = true, canMoveDown = true;
	private ArrayList<Boolean> moveDir;
	public boolean wHeld, aHeld, sHeld, dHeld;

	boolean isFlipped;

	public int posX, posY;
	public float oldX;
	public float oldY;

	float stateTime;
	float animationSpeed;

	// To decide which collision point should be on based on which room they are
	// in.
	private boolean inBedroom;

	public boolean isInBedroom() {
		return inBedroom;
	}

	public void setInBedroom(boolean inBedroom) {
		this.inBedroom = inBedroom;
	}

	public Player(TextureAtlas bloopTextureAtlas, Sprite sprite, TiledMap map, GameScreen screen) {
		// Setting up the animations used by this sprite, as well as the initial
		// image for the sprite
		this.animationTexture = bloopTextureAtlas;

		// Texture Regions for each different animation
		animRegion = new TextureRegion[9];
		downAnimation = new TextureRegion[3];
		rightAnimation = new TextureRegion[3];
		upAnimation = new TextureRegion[3];
		leftAnimation = new TextureRegion[3];
		animationSpeed = 1 / 12f;
		initializeSpriteSheet();

		this.sprite = sprite;
		this.sprite.setSize(44 * unitScale, 70 * unitScale);

		// Initializing values dealing with the movement, points on the
		// character, etc.
		populateMoveDir();
		location = new Vector2(sprite.getX(), sprite.getY());
		posX = (int) location.x;
		posY = (int) location.y;
		setxDirection(xDir.none);
		setyDirection(yDir.none);
		inBedroom = true;

		// set up the collision manager, and the points that the collision
		// manager will use.
		colManager = new CollisionManager(map, this, this.screen);
		setCollisionPoints();

	}

	public enum xDir {
		left, right, none
	}

	public enum yDir {
		up, down, none
	}

	public void initializeSpriteSheet() {
		for (int i = 0; i < 9; i++)
			animRegion[i] = animationTexture.getRegions().get(i);
		// Simple down Animation population WARNING I HAD TO DO MATH TO DO THESE
		// SO THEY ARE FINICKY
		for (int i = 0; i < 3; i++) {
			downAnimation[i] = animationTexture.findRegion("down", i + 1);
			leftAnimation[i] = animationTexture.findRegion("left", i + 1);
			rightAnimation[i] = animationTexture.findRegion("right", i + 1);
			upAnimation[i] = animationTexture.findRegion("up", i + 1);
		}

		animation = new Animation(animationSpeed, downAnimation);
	}

	public void setCollisionPoints() {
		// Bottom Collision Point
		collisionPoints.add(new Vector2(location.x + (sprite.getWidth() / 2), location.y));
		// Top Collision Point
		collisionPoints.add(new Vector2(location.x + (sprite.getWidth() / 2), location.y + sprite.getHeight() - 10));
		// Left Collision Point
		collisionPoints.add(new Vector2(location.x, location.y + (sprite.getHeight() / 2)));
		// Right Collision Point
		collisionPoints.add(new Vector2(location.x + sprite.getWidth(), location.y + (sprite.getHeight() / 2)));
	}

	// Ininitalizes the movedirections into an array so we can do things with
	// them using array logic
	public void populateMoveDir() {
		moveDir = new ArrayList<Boolean>();
		moveDir.add(canMoveLeft);
		moveDir.add(canMoveRight);
		moveDir.add(canMoveUp);
		moveDir.add(canMoveDown);
	}

	public void checkWallsAround() {
		colManager.checkWallCollision(collisionPoints);
	}

	public void updateAnimation(float delta) {
		stateTime += delta;
		if (yDirection == yDir.down || sHeld)
			animation = new Animation(animationSpeed, downAnimation);
		if (yDirection == yDir.up || wHeld)
			animation = new Animation(animationSpeed, upAnimation);
		if (xDirection == xDir.right || dHeld)
			animation = new Animation(animationSpeed, rightAnimation);
		if (xDirection == xDir.left)
			animation = new Animation(animationSpeed, leftAnimation);
		if (xDirection == xDir.none && yDirection == yDir.none)
			animation = new Animation(1 / 4f, downAnimation);

		currentFrame = animation.getKeyFrame(stateTime, true);
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

		setLocation(new Vector2(sprite.getX(), sprite.getY()));

		// Sets the collision points on the player to his new location from last
		// movement.
		collisionPoints.set(0, new Vector2(location.x + (sprite.getWidth() / 2), location.y));
		collisionPoints.set(1, new Vector2(location.x + (sprite.getWidth() / 2),
				location.y + sprite.getHeight() + 0.5f));
		if (inBedroom)
			collisionPoints.set(2, new Vector2(location.x - 0.4f, location.y + (sprite.getHeight() / 2)));
		else
			collisionPoints.set(2, new Vector2(location.x, location.y + (sprite.getHeight() / 2)));
		collisionPoints.set(3, new Vector2(location.x + sprite.getWidth(), location.y + (sprite.getHeight() / 2)));

		// Checks collision with walls, using collision points.
		// Then checks to see where the player should be moving
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
		if (canMoveLeft || canMoveRight)
			sprite.setX(sprite.getX() + velocity.x * delta);
		else
			sprite.setX(sprite.getX());

		// Move on Y Axis
		if (canMoveDown || canMoveUp)
			sprite.setY(sprite.getY() + velocity.y * delta);
		else
			sprite.setY(sprite.getY());

		posX = (int) location.x;
		posY = (int) location.y;

		System.out.println(posX + "," + posY);

		colManager.checkTeleportingZones(location.x, location.y);
	}

	public void updateMovement() {
		/*
		 * This Block of code checks if there are walls around one last time,
		 * before we begin individual logic for these directions. This is to fix
		 * the bug, where if you are against a wall, and want to move in the
		 * direction of that wall once you can(eg a clearing in the wall), it
		 * wouldn't let you if you hit the button to move prematurely. These
		 * checks before moving the character, allow the character to move as
		 * soon as he can, and a wall is no longer blocking him
		 */
		// checkWallsAround();
		if (aHeld) {
			colManager.wallLeft(collisionPoints.get(2));
			if (canMoveLeft)
				setxDirection(xDir.left);
		}
		if (dHeld) {
			colManager.wallRight(collisionPoints.get(3));
			if (canMoveRight)
				setxDirection(xDir.right);
		}
		if (sHeld) {
			colManager.wallBelow(collisionPoints.get(0));
			if (canMoveDown)
				setyDirection(yDir.down);
		}
		if (wHeld) {
			colManager.wallAbove(collisionPoints.get(1));
			if (canMoveUp)
				setyDirection(yDir.up);
		}

		// Logic to decide which directions the player should move
		if (sHeld || wHeld) {
			switch (getyDirection()) {
			case up:
				if (canMoveUp && wHeld)
					velocity.y = speed;
				else
					velocity.y = 0;
				break;
			case down:
				if (canMoveDown && sHeld)
					velocity.y = -speed;
				else
					velocity.y = 0;
				break;
			default:
				velocity.y = 0;
				break;
			}
		} else
			velocity.y = 0;
		if (aHeld || dHeld) {
			switch (getxDirection()) {
			case right:
				if (canMoveRight && dHeld)
					velocity.x = speed;
				else
					velocity.x = 0;
				break;
			case left:
				if (canMoveLeft && aHeld)
					velocity.x = -speed;
				else
					velocity.x = 0;
				break;
			default:
				velocity.x = 0;
				break;
			}
		}else
			velocity.x = 0;
		if(aHeld && dHeld){
			xDirection = xDir.none;
		}
		
	}

	public void moveToPoint(float x, float y) {
		sprite.setY(y);
		sprite.setX(x);
	}

	public void disableMovement() {
		for (@SuppressWarnings("unused")
		Boolean bool : moveDir)
			bool = true;

	}

	public void enableMovement() {
		for (@SuppressWarnings("unused")
		Boolean bool : moveDir)
			bool = true;

	}

	// Begins code for the player controlled inputs. Such as moving and attack.
	// So far only movement code
	// has been done
	@Override
	public boolean keyDown(int keycode) {
		int i = 1;
		// Switch Statements for the initial deciscion of movement direction.
		// Sets values so we can keep track of keys kept
		// pressed down.
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
		// This is a speed boost, it basically makes it so pressing shift
		// makes you super man.
		case Keys.SHIFT_LEFT:
		case Keys.SHIFT_RIGHT:
			speed = 375 * unitScale;
			break;
		}
		updateMovement();
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
			//screen.getMainGame().switchScreens("Main Menu");
			//screen.switchScreen("Main Menu");
			Gdx.app.exit();
			break;
		case Keys.SHIFT_LEFT:
		case Keys.SHIFT_RIGHT:
			speed = 255 * unitScale;
			break;
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

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}
}