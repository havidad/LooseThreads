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
	private float speed = 45 * 2, gravity = 85 * 1.0f;

	// Used to see if a player can land on this a secific tile
	private TiledMapTileLayer collisionLayer;

	//PlayerInput input;

	public boolean collidedGround, collidedWall;

	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		this.collisionLayer = collisionLayer;
		this.setSize(24, 48);
	}

	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	public void update(float delta) {

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
		float oldX = getX(), oldY = getY();
		// float tileWidth = collisionLayer.getTileWidth(), tileHeight =
		// collisionLayer.getTileHeight();

		// Move on X Axis
		setX(getX() + velocity.x * delta);

		// Move on Y Axis
		setY(getY() + velocity.y * delta);

		// Offsets player by 3 pixels to the direction opposite of which they are moving, and stops them from moving
		//any farther to any direction, until the player chooses a new direction to go to.
		if (collidedGround) {
			if (velocity.x < 0)
				setX(oldX + 3);
			else if (velocity.x > 0)
				setX(oldX - 3);
			if(velocity.y < 0)
				setY(oldY + 3);
			else if(velocity.y > 0)
				setY(oldY - 3);
			velocity.x = 0;
			velocity.y = 0;
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

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	
	
	// Begins code for the player controlled inputs. Such as moving and attack. So far only movement code 
	//has been done

	/* 		TODO Basic combat code should be done at some point. For now, we will use some sort of shooting 
	 * projectile, which should shoot off in the direction that the player is facing.
	 * 
	 * 
	 * 
	*/
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
		case Keys.W:
			if (!collidedGround)
				velocity.y += speed;
			break;
		case Keys.DOWN:
		case Keys.S:
			if (!collidedGround)
				velocity.y -= speed;
			break;
		case Keys.LEFT:
		case Keys.A:
			if (!collidedGround)
				velocity.x -= speed;
			break;
		case Keys.RIGHT:
		case Keys.D:
			if (!collidedGround)
				velocity.x += speed;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
		case Keys.DOWN:
		case Keys.W:
		case Keys.S:
			velocity.y = 0;
			break;
		case Keys.LEFT:
		case Keys.RIGHT:
		case Keys.A:
		case Keys.D:
			velocity.x = 0;
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

}
