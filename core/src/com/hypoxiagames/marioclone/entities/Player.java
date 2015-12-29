package com.hypoxiagames.marioclone.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.marioclone.screens.GameScreen;

public class Player extends Sprite implements InputProcessor {
	GameScreen screen;
	// Player movement velocity
	public Vector2 velocity = new Vector2(0, 0);

	// Change these values to change different parameters for the characters
	// movement in the world
	private float speed = 35 * 2;

	private static xDir xDirection;
	public static yDir yDirection;

	private Vector2 location;

	public boolean canMoveLeft = true, canMoveRight = true, canMoveUp = true, canMoveDown = true;

	// Used to see if a player can land on this a specific tile
	private TiledMapTileLayer collisionLayer;

	public boolean collidedGround, collidedWall;

	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		this.collisionLayer = collisionLayer;
		this.setSize(32, 64);
		location = new Vector2(getX(), getY());
		setxDirection(xDir.none);
		yDirection = yDir.none;
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

		// Save old position
		location.x = getX();
		location.y = getY();

		// updateMovement();

		// Move on X Axis
		setX(getX() + velocity.x * delta);

		// Move on Y Axis
		setY(getY() + velocity.y * delta);

		// Offsets player by 3 pixels to the direction opposite of which they
		// are moving, and stops them from moving
		// any farther to any direction, until the player chooses a new
		// direction to go to.
		if (collidedWall) {
			speed *= 0.20f;
			if (getxDirection() == xDir.left) {
				if (yDirection == yDir.down) {
					setX(location.x + 6f);
					setY(location.y + 6f);
				} else if (yDirection == yDir.up) {
					setX(location.x + 6f);
					setY(location.y - 6f);
				} else
					setX(location.x + 6f);
			} else if (getxDirection() == xDir.right) {
				if (yDirection == yDir.down) {
					setX(location.x - 6f);
					setY(location.y + 6f);
				} else if (yDirection == yDir.up) {
					setX(location.x - 6f);
					setY(location.y - 6f);
				} else
					setX(location.x - 6f);
			}
			setxDirection(xDir.none);

			if (yDirection == yDir.down) {
				setY(location.y + 6f);
				yDirection = yDir.none;
			} else if (yDirection == yDir.up) {
				setY(location.y - 6f);
				yDirection = yDir.none;
			}

			if ((Gdx.input.isKeyPressed(Keys.A)))
				setxDirection(xDir.left);
			if ((Gdx.input.isKeyPressed(Keys.D)))
				setxDirection(xDir.right);
			if ((Gdx.input.isKeyPressed(Keys.W)))
				yDirection = yDir.up;
			if ((Gdx.input.isKeyPressed(Keys.S)))
				yDirection = yDir.down;
		}
		speed = 35 * 2;
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

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	// Begins code for the player controlled inputs. Such as moving and attack.
	// So far only movement code
	// has been done
	@Override
	public boolean keyDown(int keycode) {
		if (!collidedWall) {
			switch (keycode) {
			case Keys.W:
				yDirection = yDir.up;
				break;
			case Keys.S:
				yDirection = yDir.down;
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
			if (collidedWall)
				setY(location.y - 10);
			if (Gdx.input.isKeyPressed(Keys.S))
				yDirection = yDir.down;
			else
				yDirection = yDir.none;
			break;
		case Keys.S:
			if (collidedWall)
				setY(location.y + 10);
			if (Gdx.input.isKeyPressed(Keys.W))
				yDirection = yDir.up;
			else
				yDirection = yDir.none;
			break;
		case Keys.A:
			if (collidedWall)
				setX(location.y + 10);
			if (Gdx.input.isKeyPressed(Keys.D))
				setxDirection(xDir.right);
			else
				setxDirection(xDir.none);
			break;
		case Keys.D:
			if (collidedWall)
				setX(location.y - 10);

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

	public static xDir getxDirection() {
		return xDirection;
	}

	public static void setxDirection(xDir xDirection) {
		Player.xDirection = xDirection;
	}

}
